package com.inventory.migration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.inventory.controller.MasterController;
import com.inventory.controller.PawnBillController;
import com.inventory.model.BillSource;
import com.inventory.model.Contact;
import com.inventory.model.Licence;
import com.inventory.model.Metal;
import com.inventory.model.Ornament;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillDetails;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.model.PawnBillStatus;
import com.inventory.model.pbold.CustomerDetails;
import com.inventory.model.pbold.OldBillDescription;
import com.inventory.model.pbold.OldBillDetails;
import com.inventory.utility.Calculator;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.StringUtils;

public class BillDetailsMigration {
	
	public static void migrateBillDetails() {
		try {
			List<OldBillDetails> billDetailsList = PawnBillController.getInstace().getPawnOldBillDetails();
			List<OldBillDescription> billDescriptionList = PawnBillController.getInstace().getPawnOldBillDescription();
			createMetal(billDescriptionList);
			createOrnament(billDescriptionList);
			
			if(billDetailsList!=null && billDetailsList.size()>0) {
				System.out.println("BillDetailsList : "+billDetailsList.size());
				saveBillDetails(billDetailsList);
				//createOrnament(billDescriptionList);
			}else {
				System.out.println("BillDetailsList : Zero");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void saveBillDetails(List<OldBillDetails> billDetailsList) {
		try {
			System.out.println("saveBillDetails");
			//String[] nameArray = new String[]{"N06427","N06432","N06915","N07119","N07749","A09099","A09922"};
			String[] nameArray = new String[]{"F08315","F08492","F09507", "F09560", "F10000", "G00548", "G01641", "G05133", "G08725"};//F08315, F08492, F09507, F09560, F10000, G00548, G01641, G05133, G08725
			List<String> nameList = new ArrayList<>(Arrays.asList(nameArray));
			List<String> errorLonnoList = new ArrayList<String>();
			
			List<OldBillDetails> billDetailsListFiltered = billDetailsList.stream() 
					  //.filter(bill -> bill.getStatus().equalsIgnoreCase("Pledge"))
					//.filter(billFilter-> nameList.contains(billFilter.getLoanno()))
					  .collect(Collectors.toList());
			
			List<Contact> contactList = MasterController.getInstace().getContactList(ConstantUtils.CONTACT_TYPE_PAWNCUSTOMER);
			List<OldBillDescription> billDescriptionList = PawnBillController.getInstace().getPawnOldBillDescription();
			List<Ornament> ornamentList = MasterController.getInstace().getOrnamentList(1);
			
			System.out.println("Pledge record:"+billDetailsListFiltered.size());
			System.out.println("contactList size :"+contactList.size());
			
			
			PawnBillStatus pawnBillPledgeStatus = PawnBillController.getInstace().getPawnBillStatusByCode(ConstantUtils.PAWN_BILL_STATUS_PLEDGE);
			PawnBillStatus pawnBillRedeemStatus = PawnBillController.getInstace().getPawnBillStatusByCode(ConstantUtils.PAWN_BILL_STATUS_REDEEM);
			
			Licence licence = MasterController.getInstace().getLicence(1);
			BillSource billSource = MasterController.getInstace().getBillSourceByCode(ConstantUtils.PAWN_RETAIL);			
			
			billDetailsListFiltered.stream()
			//.filter(billFilter-> billFilter.getLoanno().contains("N06427,N06432,N06915,N07119,N07749,A09099,A09922"))
			.forEach(bill-> {
				try {
				PawnBill pawnBill = new PawnBill();
				
				//pawnBill.setContact(bill.get);
				//System.out.println("out->"+bill.getCustomerid());
				contactList
				.stream()
				.forEach(contact ->{
					//System.out.println(contact.getOldContactId());
					//System.out.println(bill.getCustomerid());
					/*if(contact.getOldContactId()==bill.getCustomerid()) {
						System.out.println("Match");
						pawnBill.setContact(contact);
					}*/
					//System.out.println(contact.getOldContactId());
					if(contact.getOldContactId()!=null && bill!=null) {
					if(contact.getOldContactId().equals(bill.getCustomerid())) {
						//System.out.println("Match ed - "+contact.getOldContactId()+" - "+contact.getContactId());
						pawnBill.setContact(contact);
					}
					}
				});
				//System.out.println(bill.getLoanno());
				pawnBill.setBillNumber(bill.getLoanno());
				if(bill.getGrams()!=null && !bill.getGrams().equals("")) {
					pawnBill.setTotalNetWeight(Double.parseDouble(bill.getGrams().trim()));
				}
				if(bill.getAmount()!=null && !bill.getAmount().equals("")) {
					pawnBill.setPledgeAmount(Double.parseDouble(bill.getAmount().trim()));
				}
				if(bill.getStatus().equalsIgnoreCase("Pledge")) {
					pawnBill.setPawnBillStatusId(pawnBillPledgeStatus);
				}else {
					pawnBill.setPawnBillStatusId(pawnBillRedeemStatus);
				}
				pawnBill.setAmountInWords(bill.getAmountinword());
				
				if(bill.getRateofinterest()!=null && !bill.getRateofinterest().equals("")) {
					pawnBill.setRateOfInterest(Double.parseDouble(bill.getRateofinterest().trim()));
				}
				
				pawnBill.setBillDate(bill.getNewdate());
				
				if(bill.getGrossWeight()!=null && !bill.getGrossWeight().equals("")) {
					pawnBill.setTotalGrossWeight(Double.parseDouble(bill.getGrossWeight().trim()));
				}
				if(bill.getSlipamount()!=null && !bill.getSlipamount().equals("")) {
					pawnBill.setBillCharges(Double.parseDouble(bill.getSlipamount().trim()));
				}
				if(bill.getAmountpaidinhanddurningbilling()!=null && !bill.getAmountpaidinhanddurningbilling().equals("")) {
					pawnBill.setAmountToPay(Double.parseDouble(bill.getAmountpaidinhanddurningbilling().trim()));
				}
				
				pawnBill.setLicence(licence);
				pawnBill.setDeletedFlag(ConstantUtils.CONSTANT_N);
				pawnBill.setBillSource(billSource);
				
				
				Double rateOfInterestPM = ((Double.parseDouble(bill.getAmount().trim()) * Double.parseDouble(bill.getRateofinterest().trim()))/100);
				rateOfInterestPM = Calculator.getTwoDigitDecimal(rateOfInterestPM);
				pawnBill.setRateOfInterestPerMonth(rateOfInterestPM);
				
				
				List<PawnBillDetails> pawnBillDetailList = new ArrayList<PawnBillDetails>();
				
				billDescriptionList.forEach(billDescription ->{
					
					if(bill.getLoanno().trim().equalsIgnoreCase(billDescription.getLoanno())) {
					PawnBillDetails pawnDetails = new PawnBillDetails();
					
					pawnDetails.setDeletedFlag(ConstantUtils.CONSTANT_N);
					if(billDescription.getGrossweight()!=null && !billDescription.getGrossweight().trim().equals("")) {
						pawnDetails.setGrossWeight(Double.parseDouble(billDescription.getGrossweight().trim()));
					}
					pawnDetails.setNetWeight(Double.parseDouble(billDescription.getWeight()));
					pawnDetails.setNoOfPiece(Integer.parseInt(billDescription.getQuantity()));
					
					
					ornamentList.forEach(ornament -> {
						
						if(StringUtils.capitalize(billDescription.getJeweltype().trim()).equalsIgnoreCase(ornament.getMetalId().getMetalCode())) {
							if(StringUtils.capitalize(billDescription.getDescription().trim()).equalsIgnoreCase(ornament.getOrnamentName())) {
								pawnDetails.setOrnament(ornament);
							}
						}
							
							
							
						/*if(billDescription.getJeweltype().trim().equalsIgnoreCase(ConstantUtils.GOLD)
								|| billDescription.getJeweltype().trim().equalsIgnoreCase(ConstantUtils.SILVER)
								) {
							if(StringUtils.capitalize(billDescription.getDescription().trim()).equalsIgnoreCase(ornament.getOrnamentName())) {
								pawnDetails.setOrnament(ornament);
							}
						}else{
							if((StringUtils.capitalize(billDescription.getJeweltype().trim())+" "+StringUtils.capitalize(billDescription.getDescription().trim())).equalsIgnoreCase(ornament.getOrnamentName())) {
								pawnDetails.setOrnament(ornament);
							}
						}*/
						
					});
					
					
					
					pawnDetails.setPawnBill(pawnBill);
					pawnDetails.setsNo(Integer.parseInt(billDescription.getSno().trim()));
					
					
					
					pawnBillDetailList.add(pawnDetails);
					}
				});
				
				pawnBill.setPawnBillDetails(pawnBillDetailList);
				PawnBill pawnBillObj = null;
				pawnBillObj = PawnBillController.getInstace().saveOrUpdateBill(pawnBill);
				
				if(bill.getStatus().equalsIgnoreCase("Redeemed")) {
					PawnBillRedeemDetails redeemBill = new PawnBillRedeemDetails();
					
					redeemBill.setDeletedFlag(ConstantUtils.CONSTANT_N);
					redeemBill.setDeliveryNumber(bill.getDeliveryno().trim());
					redeemBill.setPawnBill(pawnBillObj);
					if(bill.getRedeemamountreceived()!=null && !bill.getRedeemamountreceived().equals("")) {
						redeemBill.setRedeemAmountReceived(Double.parseDouble(bill.getRedeemamountreceived()));
					}
					redeemBill.setRedeemDate(bill.getNewredeemdate());
					if(bill.getRedeemamount()!=null && !bill.getRedeemamount().equals("")) {
						redeemBill.setRedeemTotalAmount(Double.parseDouble(bill.getRedeemamount()));
					}
					if(bill.getRedeemamount()!=null && !bill.getRedeemamount().equals("")) {
						redeemBill.setRedeemAmountReceived(Double.parseDouble(bill.getRedeemamount()));
					}
					
					Integer nMonth = Calculator.monthDifference(bill.getNewdate(),bill.getNewredeemdate());
					redeemBill.setTotalNumberOfMonths(nMonth);
					if(pawnBillObj!=null) {
					redeemBill.setRedeemInterest(Calculator.getTwoDigitDecimal(pawnBillObj.getRateOfInterestPerMonth()*nMonth));
					}
					PawnBillController.getInstace().saveOrUpdatePawnBillRedeemDetails(redeemBill);
					
				}
				
				}catch(Exception ex) {
					errorLonnoList.add(bill.getLoanno());
					System.err.println("Problem->"+bill.getLoanno());
					//ex.printStackTrace();
				}
			});
			
			System.out.println("Completed");
			//System.out.println("errorLonnoList : "+errorLonnoList);
			writeToFile(String.join(", ", errorLonnoList));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void writeToFile(String content) {
		try {
		//String content = "Hello World !!";
		 
		Files.write(Paths.get("D:/output.txt"), content.getBytes());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createMetal(List<OldBillDescription> billDescriptionList) {
		try {
			System.out.println("createMetal billDescriptionList  count - "+billDescriptionList.size());
			
			List<OldBillDescription> billDescriptionListFiltered = billDescriptionList.stream() 
					  .filter(distinctByKey(p -> (StringUtils.capitalize(p.getJeweltype().trim())))) 
					  .collect(Collectors.toList());
			
			
			System.out.println("createMetal Filtered count - "+billDescriptionListFiltered.size());
			
			billDescriptionListFiltered.stream().forEach(metal -> {
				
				Metal metalObj =new Metal();
				
				metalObj.setDeletedFlag(ConstantUtils.CONSTANT_N);
				metalObj.setMetalCode(StringUtils.capitalize(metal.getJeweltype().trim()));
				metalObj.setMetalName(StringUtils.capitalize(metal.getJeweltype().trim()));
				
				MasterController.getInstace().saveOrUpdateMetal(metalObj);
			});
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createOrnament(List<OldBillDescription> billDescriptionList) {
		try {
			System.out.println("billDescriptionList  count - "+billDescriptionList.size());
			
			List<OldBillDescription> billDescriptionListFiltered = billDescriptionList.stream() 
					  .filter(distinctByKey(p -> (StringUtils.capitalize(p.getJeweltype().trim())+" "+StringUtils.capitalize(p.getDescription().trim())))) 
					  .collect(Collectors.toList());
			
			
			System.out.println("createOrnament Filtered count - "+billDescriptionListFiltered.size());
			
			List<Metal> metalList = MasterController.getInstace().getMetalList();
			Licence licence = MasterController.getInstace().getLicence(1);
			billDescriptionListFiltered.stream().forEach(orn -> {
				Ornament ornament = new Ornament();
				
				ornament.setDeletedFlag(ConstantUtils.CONSTANT_N);
				ornament.setLicenceId(1);
				
				metalList.forEach(metal ->{
					if(metal.getMetalCode().equalsIgnoreCase(StringUtils.capitalize(orn.getJeweltype().trim()))){
						ornament.setMetalId(metal);
					}
				});
					
				ornament.setOrnamentName(StringUtils.capitalize(orn.getDescription().trim()));
				
				/*if(orn.getJeweltype().trim().equalsIgnoreCase(ConstantUtils.GOLD)) {		
					metalList.forEach(metal ->{
						if(metal.getMetalCode().equalsIgnoreCase(ConstantUtils.GOLD)) {
							ornament.setMetalId(metal);
						}
					});
					ornament.setOrnamentName(StringUtils.capitalize(orn.getDescription().trim()));
				}else if(orn.getJeweltype().trim().equalsIgnoreCase(ConstantUtils.SILVER)) {
					metalList.forEach(metal ->{
						if(metal.getMetalCode().equalsIgnoreCase(ConstantUtils.SILVER)) {
							ornament.setMetalId(metal);
						}
					});
					ornament.setOrnamentName(StringUtils.capitalize(orn.getDescription().trim()));
				}else {
					metalList.forEach(metal ->{
						if(metal.getMetalCode().equalsIgnoreCase(ConstantUtils.OTHER)) {
							ornament.setMetalId(metal);
						}
					});
					ornament.setOrnamentName(StringUtils.capitalize(orn.getJeweltype().trim())+" "+StringUtils.capitalize(orn.getDescription().trim()));
				}*/
				
				MasterController.getInstace().saveOrUpdateOrnament(ornament);
				System.out.println(getCounter());
				
				
			});
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static <T> Predicate<T> distinctByKey(
		    Function<? super T, ?> keyExtractor) {
		  
		    Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
		    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; 
		}
	static Integer count=0;
	public static Integer getCounter() {
		return count++;
	}

}
