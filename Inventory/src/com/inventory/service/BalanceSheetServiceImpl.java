package com.inventory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.inventory.dao.BalanceSheetDAOImpl;
import com.inventory.dao.BillDAO;
import com.inventory.dao.BillDAOImpl;
import com.inventory.dao.MasterDAO;
import com.inventory.dao.MasterDAOImpl;
import com.inventory.dto.BalanceSheetDTO;
import com.inventory.dto.BalanceSheetDTOList;
import com.inventory.dto.StockDTOList;
import com.inventory.model.Bill;
import com.inventory.model.BillSource;
import com.inventory.model.Contact;
import com.inventory.model.Metal;
import com.inventory.model.PaymentDetails;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;

public class BalanceSheetServiceImpl implements BalanceSheetService {
	
	private static BalanceSheetServiceImpl myInstance = null;
	BalanceSheetDAOImpl balanceSheetDAO = null;
	BillDAO billDAO = null;
	MasterDAO masterDAO = null;
	
	private BalanceSheetServiceImpl(){
		try{
			balanceSheetDAO = BalanceSheetDAOImpl.getInstace();
			billDAO = BillDAOImpl.getInstace();
			masterDAO = MasterDAOImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BalanceSheetServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new BalanceSheetServiceImpl();
		}
		return myInstance;
	}

	@Override
	public BalanceSheetDTO getBalanceSheetList(Date toDate, Integer licenceId, String contactTypeName,
			String billSource,String billType,Integer contact) {
		// TODO Auto-generated method stub
		//return balanceSheetDAO.getBalanceSheetList(toDate, licenceId, contactTypeName, billSource);
		
		BalanceSheetDTO balanceSheetDTO = null;
		try {
			
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				if(contactTypeName.equalsIgnoreCase(ConstantUtils.RETAIL_CONTACT_TYPE)) {
					billSource = ConstantUtils.RETAIL_BILLING_GST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.WHOLESALE_CONTACT_TYPE)) {
					billSource = ConstantUtils.WHOLESALE_BILLING_GST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.SUPPLIER_CONTACT_TYPE)) {
					billSource = ConstantUtils.PURCHASE_BILLING_GST;
				}
				
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				if(contactTypeName.equalsIgnoreCase(ConstantUtils.RETAIL_CONTACT_TYPE)) {
					billSource = ConstantUtils.RETAIL_BILLING_NONGST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.WHOLESALE_CONTACT_TYPE)) {
					billSource = ConstantUtils.WHOLESALE_BILLING_NONGST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.SUPPLIER_CONTACT_TYPE)) {
					billSource = ConstantUtils.PURCHASE_BILLING_NONGST;
				}
			}
			
			balanceSheetDTO = balanceSheetDAO.getBalanceSheetList(toDate, licenceId, contactTypeName, billSource,billType,contact);
			List<BalanceSheetDTOList> balanceSheetDTOListNew = new ArrayList<BalanceSheetDTOList>();
			if(balanceSheetDTO!=null && balanceSheetDTO.getBalanceSheetDTOList()!=null) {
				//balanceSheetDTOListNew = new ArrayList<BalanceSheetDTOList>();
				
				//Group by contact id starts
				Map<Integer, List<BalanceSheetDTOList>> contactMap = balanceSheetDTO.getBalanceSheetDTOList()
						.stream()
						.collect(Collectors.groupingBy(BalanceSheetDTOList::getContactId,Collectors.toList()));
				List<Double> amountBalanceList = new ArrayList<Double>();
				HashMap<String,List<Double>> metalBalanceMap = new HashMap<>();

				if(contactMap!=null) {
				contactMap.forEach((contactId,balanceSheetList)->{
					BalanceSheetDTOList listObj = new BalanceSheetDTOList();
					listObj.setContactId(contactId);
					listObj.setContactName(balanceSheetList.get(0).getContactName());
					
					listObj.setType(balanceSheetList.get(0).getType());
					listObj.setMetalBalance(balanceSheetList.get(0).getMetalBalance());
					listObj.setMetalName(balanceSheetList.get(0).getMetalName());
					listObj.settDate(balanceSheetList.get(0).gettDate());
					listObj.settNumber(balanceSheetList.get(0).gettNumber());
					listObj.settID(balanceSheetList.get(0).gettID());
					listObj.setWeight(balanceSheetList.get(0).getWeight());
					
					
					double totalBillAmount =  balanceSheetList.stream().filter(s->s.getAmountBalance()!=null && s.getType().equals("BILL")).mapToDouble(BalanceSheetDTOList::getAmountBalance).sum();
					double totalPaymentAmount =  balanceSheetList.stream().filter(s->s.getAmountBalance()!=null && s.getType().equals("PAYMENT")).mapToDouble(BalanceSheetDTOList::getAmountBalance).sum();
					double openingAmount = balanceSheetList.stream().filter(s->s.getAmountBalance()!=null && s.getType().equals("OPENING_BALANCE")).mapToDouble(BalanceSheetDTOList::getAmountBalance).sum();;
					
					System.out.println(contactId+" - "+totalBillAmount+" = "+totalPaymentAmount);
					
					double balanceAmount =Calculator.getTwoDigitDecimal(openingAmount + totalBillAmount - totalPaymentAmount);
					listObj.setAmountBalance(balanceAmount);
					amountBalanceList.add(balanceAmount);
					
					Map<String, List<BalanceSheetDTOList>> metalMap = balanceSheetList.stream()
							.filter(f -> f.getMetalBalance()!=null)// && f.getMetalBalance()>0
							.collect(Collectors.groupingBy(BalanceSheetDTOList::getMetalName,Collectors.toList()));
					StringBuffer metalBalanceStr = new StringBuffer();
					if(metalMap!=null) {
						Map<String, List<BalanceSheetDTOList>> map
			            = metalMap.entrySet()
			                  .stream()
			                  .sorted((i1, i2)
			                              -> i1.getKey().compareTo(
			                                  i2.getKey()))
			                  .collect(Collectors.toMap(
			                      Map.Entry::getKey,
			                      Map.Entry::getValue,
			                      (e1, e2) -> e1, LinkedHashMap::new));
						
						map.forEach((metal,balanceSheet)->{
							double billMetalBalance = balanceSheet.stream().filter(o -> o.getMetalBalance()!=null && o.getType().equals("BILL")).mapToDouble(BalanceSheetDTOList::getMetalBalance).sum();
							double paymentMetal = balanceSheet.stream().filter(o -> o.getMetalBalance()!=null && o.getType().equals("PAYMENT")).mapToDouble(BalanceSheetDTOList::getMetalBalance).sum();
							double openingMetalBalance = balanceSheet.stream().filter(o -> o.getMetalBalance()!=null && o.getType().equals("OPENING_BALANCE")).mapToDouble(BalanceSheetDTOList::getMetalBalance).sum();
							
							
							double metalBalanceTemp = Calculator.getTwoDigitDecimal(openingMetalBalance + billMetalBalance - paymentMetal);
							if(metalBalanceStr.length()==0) {
								metalBalanceStr.append(metal).append(" : ")
								.append((metalBalanceTemp<0?"Extra ":""))
								.append(Calculator.formatTwoDecimal(metalBalanceTemp))
								;
							}else {
								metalBalanceStr.append(", ").append(metal).append(" : ")
								.append((metalBalanceTemp<0?"Extra ":""))
								.append(Calculator.formatTwoDecimal(metalBalanceTemp));
							}
							
							if(metalBalanceMap.get(metal)!=null) {
								List<Double> tempList = new ArrayList<>();
								tempList = metalBalanceMap.get(metal);
								tempList.add(metalBalanceTemp);
								metalBalanceMap.put(metal, tempList);
							}else {
								List<Double> tempList = new ArrayList<>();
								tempList.add(metalBalanceTemp);
								metalBalanceMap.put(metal, tempList);
							}
						});
					}
					listObj.setMetalBalanceStr(metalBalanceStr.toString());
					
					listObj.setBalanceSheetDTOList(balanceSheetList);
					
					balanceSheetDTOListNew.add(listObj);
				});
				}
				//Group by contact id ends
				balanceSheetDTO.setBalanceSheetDTOList(balanceSheetDTOListNew);
				if(amountBalanceList.size()>0) {
					balanceSheetDTO.setTotalAmountBalance(amountBalanceList.stream().mapToDouble(Double::doubleValue).sum());
				}
				StringBuffer metalBalanceStr = new StringBuffer();
				if(metalBalanceMap.size()>0) {
					
					Map<String, List<Double>> map
		            = metalBalanceMap.entrySet()
		                  .stream()
		                  .sorted((i1, i2)
		                              -> i1.getKey().compareTo(
		                                  i2.getKey()))
		                  .collect(Collectors.toMap(
		                      Map.Entry::getKey,
		                      Map.Entry::getValue,
		                      (e1, e2) -> e1, LinkedHashMap::new));
					
					map.forEach((metal,metalBalanceList)->{
						double total = metalBalanceList.stream().mapToDouble(Double::doubleValue).sum();
						if(metalBalanceStr.length()==0) {
							metalBalanceStr.append(metal).append(" : ")
							.append((total<0?"Extra ":""))
							.append(Calculator.formatTwoDecimal(total));
						}else {
							metalBalanceStr.append(", ").append(metal).append(" : ")
							.append((total<0?"Extra ":""))
							.append(Calculator.formatTwoDecimal(total));
						}
						
					});
				}
				balanceSheetDTO.setTotalMetalBalanceStr(metalBalanceStr.toString());
				
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return balanceSheetDTO;
		
		
		/*BalanceSheetDTO balanceSheetDTO = null;
		try {
			balanceSheetDTO = new BalanceSheetDTO();
			List<BalanceSheetDTOList> balanceSheetList = new ArrayList<BalanceSheetDTOList>();
			
			if(billType.equalsIgnoreCase(ConstantUtils.GST_ENTRY)) {
				if(contactTypeName.equalsIgnoreCase(ConstantUtils.RETAIL_CONTACT_TYPE)) {
					billSource = ConstantUtils.RETAIL_BILLING_GST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.WHOLESALE_CONTACT_TYPE)) {
					billSource = ConstantUtils.WHOLESALE_BILLING_GST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.SUPPLIER_CONTACT_TYPE)) {
					billSource = ConstantUtils.PURCHASE_BILLING_GST;
				}
				
			}else if(billType.equalsIgnoreCase(ConstantUtils.NON_GST_ENTRY)) {
				if(contactTypeName.equalsIgnoreCase(ConstantUtils.RETAIL_CONTACT_TYPE)) {
					billSource = ConstantUtils.RETAIL_BILLING_NONGST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.WHOLESALE_CONTACT_TYPE)) {
					billSource = ConstantUtils.WHOLESALE_BILLING_NONGST;
				}else if(contactTypeName.equalsIgnoreCase(ConstantUtils.SUPPLIER_CONTACT_TYPE)) {
					billSource = ConstantUtils.PURCHASE_BILLING_NONGST;
				}
			}
			BillSource billSourceobj = masterDAO.getBillSourceByCode(billSource);
			
			List<Bill> billList = billDAO.getBillList(null, toDate, licenceId, billSourceobj.getBillSourceId(), null,billType);
			List<PaymentDetails> paymentDetailsList = billDAO.getPaymentDetailsList(null, toDate, licenceId, billSourceobj.getBillSourceId(), null);
			List<Contact> contactList = masterDAO.getContactList(contactTypeName);
			
			Map<Contact, List<Bill>> rawMapBill = null;
			Map<Contact, List<PaymentDetails>> rawMapPayment = null;
			
			if(billList!=null && billList.size()>0) {
				rawMapBill = billList.stream().collect(Collectors.groupingBy(Bill::getContact,Collectors.toList()));
			}
			
			if(paymentDetailsList!=null && paymentDetailsList.size()>0) {
				rawMapPayment = paymentDetailsList.stream().collect(Collectors.groupingBy(PaymentDetails::getContact,Collectors.toList()));
			}
			
			
			for(Contact contact:contactList) {
				BalanceSheetDTOList balanceList = new BalanceSheetDTOList();
				balanceList.setContactId(contact.getContactId());
				balanceList.setContactName(contact.getContactName());
				
				double totalBillAmount = 0.0;
				//StringBuffer totalBillMetalStr = new StringBuffer();
				Map<Metal,Double> billMetalMap = new HashMap<Metal,Double>();
				
				double totalPaymentAmount = 0.0;
				//StringBuffer totalPaymentMetalStr = new StringBuffer();
				Map<Metal,Double> paymentMetalMap = new HashMap<Metal,Double>();
				
				if(rawMapBill!=null && rawMapBill.size()>0) {
					List<Bill> billListTemp = rawMapBill.get(contact);
					
					if(billListTemp!=null) {
						totalBillAmount = billListTemp.stream().filter(s -> s.getTotalAmount()!=null).mapToDouble(Bill::getTotalAmount).sum();
						
						Map<Metal, List<Bill>> rawMapMetal = billListTemp.stream().collect(Collectors.groupingBy(Bill::getMetal,Collectors.toList()));
						
						if(rawMapMetal!=null) {
							HashMap<Metal, List<Bill>> mapMetal
				            = rawMapMetal.entrySet()
				                  .stream()
				                  .sorted((i1, i2)
				                              -> i1.getKey().getMetalName().compareTo(
				                                  i2.getKey().getMetalName()))
				                  .collect(Collectors.toMap(
				                      Map.Entry::getKey,
				                      Map.Entry::getValue,
				                      (e1, e2) -> e1, LinkedHashMap::new));
							
							mapMetal.forEach((k,v)->{							
								billMetalMap.put(k, v.stream().filter(o -> o.getMetalBalance()!=null).mapToDouble(Bill::getMetalBalance).sum());
							});
						}
					}
				}
				
				if(rawMapPayment!=null && rawMapPayment.size()>0) {
					List<PaymentDetails> paymentListTemp = rawMapPayment.get(contact);
					
					if(paymentListTemp!=null) {
						totalPaymentAmount = paymentListTemp.stream().filter(s -> s.getAmountPaid()!=null).mapToDouble(PaymentDetails::getAmountPaid).sum();
						
						
						Map<Metal, List<PaymentDetails>> rawMapMetal = paymentListTemp.stream().collect(Collectors.groupingBy(PaymentDetails::getMetal,Collectors.toList()));
						
						if(rawMapMetal!=null) {
							HashMap<Metal, List<PaymentDetails>> mapMetal
				            = rawMapMetal.entrySet()
				                  .stream()
				                  .sorted((i1, i2)
				                              -> i1.getKey().getMetalName().compareTo(
				                                  i2.getKey().getMetalName()))
				                  .collect(Collectors.toMap(
				                      Map.Entry::getKey,
				                      Map.Entry::getValue,
				                      (e1, e2) -> e1, LinkedHashMap::new));
							
							mapMetal.forEach((k,v)->{			
								paymentMetalMap.put(k, v.stream().filter(o -> o.getMetalPaid()!=null).mapToDouble(PaymentDetails::getMetalPaid).sum());
							});
						}
					}
				}
				
				//Calculation Starts
				double amountBalance = Calculator.getTwoDigitDecimal(totalBillAmount - totalPaymentAmount);
				
				balanceList.setAmountBalance(amountBalance);
				
				Map<Metal,Double> metalBalance = new HashMap<Metal,Double>();
				
				if(billMetalMap!=null) {
					billMetalMap.forEach((k,v)->{
						metalBalance.put(k, v);
					});
				}
				
				if(paymentMetalMap!=null) {
					paymentMetalMap.forEach((k,v)->{
						if(metalBalance.get(k)!=null) {
							metalBalance.put(k, v+metalBalance.get(k));
						}else {
							metalBalance.put(k,v);
						}
					});
				}
				StringBuffer metalBalanceStr = new StringBuffer();
				
				metalBalance.forEach((k,v)->{
					if(metalBalanceStr.length()==0) {
						metalBalanceStr.append(k).append(" : ").append(v);
					}else {
						metalBalanceStr.append(",").append(k).append(" : ").append(v);
					}
				});
				
				balanceList.setMetalBalanceStr(metalBalanceStr.toString());
				
				//Calculation Ends
				
				balanceSheetList.add(balanceList);
				
			}
			
			balanceSheetDTO.setBalanceSheetDTOList(balanceSheetList);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return balanceSheetDTO;*/
	}

}
