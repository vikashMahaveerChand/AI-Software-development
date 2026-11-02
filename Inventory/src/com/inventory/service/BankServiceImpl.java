package com.inventory.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.inventory.controller.BankController;
import com.inventory.controller.MasterController;
import com.inventory.dao.BankDAO;
import com.inventory.dao.BankDAOImpl;
import com.inventory.dao.BillDAO;
import com.inventory.dao.BillDAOImpl;
import com.inventory.dto.BankEntryDTO;
import com.inventory.dto.CashReportDTOList;
import com.inventory.model.Bank;
import com.inventory.model.BankEntry;
import com.inventory.model.BillSource;
import com.inventory.model.PaymentDetails;
import com.inventory.utility.ConstantUtils;

public class BankServiceImpl implements BankService {
	
	private static BankServiceImpl myInstance = null;
	BankDAO bankDAO = null;
	BillDAO billDAO = null;
	
	private BankServiceImpl(){
		try{
			bankDAO = BankDAOImpl.getInstace();
			billDAO = BillDAOImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static BankServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new BankServiceImpl();
		}
		return myInstance;
	}

	@Override
	public Bank saveOrUpdateBank(Bank bank) {
		// TODO Auto-generated method stub
		return bankDAO.saveOrUpdateBank(bank);
	}

	@Override
	public List<Bank> getBankList() {
		// TODO Auto-generated method stub
		return bankDAO.getBankList();
	}

	@Override
	public Bank getBank(Integer bankId) {
		// TODO Auto-generated method stub
		Bank bank = null;
		try {
			bank = bankDAO.getBank(bankId);
			Double totalDebitAmount =0.0;
			Double totalCreditAmount = 0.0;
			Double currentBalance =0.0;
			
			Double bankPaymentReceived =0.0;
			Double bankPaymentPaid =0.0;
			
			
			
			if(bank!=null && bank.getBankEntryList()!=null) {
				List<BankEntry> bankEntryList = bank.getBankEntryList();
				
				totalDebitAmount = bankEntryList.stream().filter(o -> o.getDebitAmount()!=null).mapToDouble(BankEntry::getDebitAmount).sum();
				totalCreditAmount = bankEntryList.stream().filter(o -> o.getCreditAmount()!=null).mapToDouble(BankEntry::getCreditAmount).sum();
			
				currentBalance = totalCreditAmount - totalDebitAmount;
				
				bank.setTotalDebitAmount(totalDebitAmount);
				bank.setTotalCreditAmount(totalCreditAmount);
				bank.setCurrentBalance(currentBalance);
				
			}/*else if(bank!=null) {
				bank.setTotalDebitAmount(totalDebitAmount);
				bank.setTotalCreditAmount(totalCreditAmount);
				bank.setCurrentBalance(currentBalance);
			}*/
			
			if(bank!=null && bank.getBankPaymentTransientList().size()>0) {
				System.out.println("==Bank Payment== "+bank.getBankPaymentTransientList().size());
				List<PaymentDetails> paymentDetailList = bank.getBankPaymentTransientList();
				List<BillSource> billSourceList = MasterController.getInstace().getBillSourceList();
				
				bankPaymentReceived = paymentDetailList.stream().
						filter(paymentDetailL -> billSourceList.stream().
								anyMatch(billSource -> paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)
										
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST)
										)).
						mapToDouble(PaymentDetails::getAmountPaid).sum();
				
				bankPaymentPaid = paymentDetailList.stream().
						filter(paymentDetailL -> billSourceList.stream().
								anyMatch(billSource -> paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST)
										|| paymentDetailL.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST)
										)).
						mapToDouble(PaymentDetails::getAmountPaid).sum();
				
				System.out.println("bankPaymentReceived "+bankPaymentReceived);
				System.out.println("bankPaymentPaid "+bankPaymentPaid);
			}
			
			
			currentBalance = (totalCreditAmount+bankPaymentReceived)- (totalDebitAmount+bankPaymentPaid);
			
			bank.setTotalDebitAmount((totalDebitAmount+bankPaymentPaid));
			bank.setTotalCreditAmount((totalCreditAmount+bankPaymentReceived));
			bank.setCurrentBalance(currentBalance);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return bank;
	}

	@Override
	public BankEntry saveOrUpdateBankEntry(BankEntry bankEntry) {
		// TODO Auto-generated method stub
		return bankDAO.saveOrUpdateBankEntry(bankEntry);
	}

	@Override
	public BankEntryDTO getBankEntryList(Date fromDate, Date toDate, Integer bankId) {
		// TODO Auto-generated method stub
		BankEntryDTO bankEntryDTO = null;
		List<BankEntry> bankEntryList = null;
		try {
			bankEntryDTO = new BankEntryDTO();
			bankEntryList = new ArrayList<BankEntry>();
			
			List<BankEntry> currentBankEntryList = new ArrayList<BankEntry>();
			List<BankEntry> oldBankEntryList = new ArrayList<BankEntry>();
			
			List<PaymentDetails> currentPaymentList = null;
			List<PaymentDetails> oldPaymentList = null;
			
			
			//final List<BankEntry> bankEntryPaymentDetailsList = new ArrayList<BankEntry>();;
			
			Double oldTotalDebitAmount =0.0;
			Double oldTotalCreditAmount = 0.0;
			Double oldCurrentBalance =0.0;
			Bank bank = null;
			
			Double totalDebitAmount =0.0;
			Double totalCreditAmount = 0.0;
			Double currentBalance =0.0;
			
			if(fromDate !=null && toDate!=null) {
				currentBankEntryList = bankDAO.getBankEntryList(fromDate, toDate, bankId);
				oldBankEntryList = bankDAO.getBankEntryList(null, fromDate, bankId);	
				
				currentPaymentList = billDAO.getBankPaymentDetails(fromDate, toDate, bankId);
				oldPaymentList = billDAO.getBankPaymentDetails(null, fromDate, bankId);
				
				if(currentPaymentList!=null && currentPaymentList.size()>0) {
					currentBankEntryList.addAll(convertPaymentListToBankEntry(currentPaymentList));
				}
				
				if(oldPaymentList!=null && oldPaymentList.size()>0) {
					oldBankEntryList.addAll(convertPaymentListToBankEntry(oldPaymentList));
				}
				
				if(oldBankEntryList!=null && oldBankEntryList.size()>0) {
					
					oldTotalDebitAmount = oldBankEntryList.stream().filter(o -> o.getDebitAmount()!=null).mapToDouble(BankEntry::getDebitAmount).sum();
					oldTotalCreditAmount = oldBankEntryList.stream().filter(o -> o.getCreditAmount()!=null).mapToDouble(BankEntry::getCreditAmount).sum();
				
					oldCurrentBalance = oldTotalCreditAmount - oldTotalDebitAmount;
					
					BankEntry oldBankEntry = new BankEntry();
					
					oldBankEntry.setBank(BankController.getInstace().getBank(bankId));
					oldBankEntry.setBankEntryId(-1);
					oldBankEntry.setCreditAmount(oldCurrentBalance);
					//oldBankEntry.setDebitAmount(oldTotalDebitAmount);
					oldBankEntry.setDeletedFlag(ConstantUtils.CONSTANT_N);
					oldBankEntry.setEntryNotes("Opening Balance C/F");
					
					bankEntryList.add(oldBankEntry);					
					
				}
				
				bankEntryList.addAll(currentBankEntryList);
				
			}
			
			//Sort the list by date-starts
			if(bankEntryList!=null && bankEntryList.size()>0) {
				//bankEntryList.sort((BankEntry be1, BankEntry be2)->be1.getEntryDate().compareTo(be2.getEntryDate()));
				
				//bankEntryList.stream()
			     //.sorted(Comparator.comparing(BankEntry::getEntryDate, Comparator.nullsFirst(Comparator.reverseOrder())));

				Collections.sort(bankEntryList, new Comparator<BankEntry>() {
					  public int compare(BankEntry o1, BankEntry o2) {
					      if (o1.getEntryDate() == null || o2.getEntryDate() == null)
					        return 0;
					      return o1.getEntryDate().compareTo(o2.getEntryDate());
					  }
					});
				
			}
			//sort the list be date- ends
			
			//Calculate total debit, credit and current balance
			totalDebitAmount = bankEntryList.stream().filter(o -> o.getDebitAmount()!=null).mapToDouble(BankEntry::getDebitAmount).sum();
			totalCreditAmount = bankEntryList.stream().filter(o -> o.getCreditAmount()!=null).mapToDouble(BankEntry::getCreditAmount).sum();
		
			currentBalance = totalCreditAmount - totalDebitAmount;
			
			
			//Configure the return parameter starts
			bankEntryDTO.setBankEntryList(bankEntryList);
			bankEntryDTO.setTotalDebit(totalDebitAmount);
			bankEntryDTO.setTotalCredit(totalCreditAmount);
			bankEntryDTO.setCurrentBalance(currentBalance);
			//Configure the return parameter ends
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		//return bankDAO.getBankEntryList(fromDate, toDate, bankId);
		return bankEntryDTO;
	}
	
	public List<BankEntry> convertPaymentListToBankEntry(List<PaymentDetails> paymentDetailsList){
		List<BankEntry> bankEntryList = null;
		try {
			final List<BankEntry> bankEntryPaymentDetailsList = new ArrayList<BankEntry>();;
			
			paymentDetailsList
			.stream()
			.filter(pd -> pd.getBank()!=null)
			.forEach(pdObject -> 
			{
				System.out.println("Amount Paid->"+pdObject.getAmountPaid());
				BankEntry bankEntryObject = new BankEntry();
				if(pdObject.getPaymentDate()!=null) {
					bankEntryObject.setEntryDate(pdObject.getPaymentDate());
				}
				if(pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_GST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_GST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_GST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_GST)
						
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_RETAIL_NONGST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_RETAIL_NONGST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_WHOLESALE_NONGST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_WHOLESALE_NONGST)
						
						) {
					
					if(pdObject.getAmountPaid()!=null) {
						bankEntryObject.setCreditAmount(pdObject.getAmountPaid());
					}
					
						
				}else if(pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_GST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_GST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.GOLD_SUPPLIER_NONGST)
						|| pdObject.getBillSource().getBillSourceName().equalsIgnoreCase(ConstantUtils.SILVER_SUPPLIER_NONGST)
						) {
					if(pdObject.getAmountPaid()!=null) {
						bankEntryObject.setDebitAmount(pdObject.getAmountPaid());
					}
					
				}
				
				bankEntryObject.setEntryNotes(pdObject.getContact().getContactName());
				
				bankEntryPaymentDetailsList.add(bankEntryObject);
				
			});
			
			bankEntryList = bankEntryPaymentDetailsList;
			
		}catch(Exception ex) {
			
		}
		return bankEntryList;
		
	}

}
