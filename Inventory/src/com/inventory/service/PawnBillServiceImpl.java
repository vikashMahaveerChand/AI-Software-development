package com.inventory.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.inventory.controller.MasterController;
import com.inventory.dao.BillDAO;
import com.inventory.dao.BillDAOImpl;
import com.inventory.dao.PawnBillDAO;
import com.inventory.dao.PawnBillDAOImpl;
import com.inventory.dto.PawnBillDTO;
import com.inventory.dto.PawnDailyReportDTO;
import com.inventory.dto.PawnDailyReportDTOList;
import com.inventory.model.BillNumber;
import com.inventory.model.Metal;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillAuctionDetails;
import com.inventory.model.PawnBillDetails;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.model.PawnBillStatus;
import com.inventory.model.pbold.OldBillDescription;
import com.inventory.utility.Calculator;
import com.inventory.utility.Common;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.StringUtils;

public class PawnBillServiceImpl implements PawnBillService {
	
	private static PawnBillServiceImpl myInstance = null;
	PawnBillDAO pawnBillDAO = null;
	BillDAO billDAO = null;
	BillService billService = null;
	private PawnBillServiceImpl(){
		try{
			pawnBillDAO = PawnBillDAOImpl.getInstace();
			billDAO = BillDAOImpl.getInstace();
			billService = BillServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static PawnBillServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new PawnBillServiceImpl();
		}
		return myInstance;
	}

	@Override
	public PawnBill saveOrUpdateBill(PawnBill bill) {
		// TODO Auto-generated method stub
		PawnBill billObj=null;
		try{
			if(bill!=null){
				if(bill.getPawnBillDetails()!=null){
					for(PawnBillDetails obj : bill.getPawnBillDetails()){
						bill.addPawnBillDetailList(obj);
					}
				}
				
				billObj = pawnBillDAO.saveOrUpdateBill(bill);
				
				if(billObj!=null){
					BillNumber billNumberObj = billDAO.getBillNumber(billObj.getLicence().getLicenceId(), billObj.getBillSource().getBillSourceId());
					
					if(billNumberObj!=null){
						String billNum = StringUtils.getBillNumber(billNumberObj.getBillLetter(), billNumberObj.getBillNumber());
						
						if(billObj.getBillNumber().equalsIgnoreCase(billNum)){
							//update bill number starts
							billService.updateBillNumber(billNumberObj);
							//update billNumber ends
							
						}
						
						
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return billObj;
	}

	@Override
	public PawnBillDTO getPawnBillList(Date fromDate, Date toDate, Integer licenceId, Integer pawnBillStatusId,
			Integer contactId,String pawnBillSource,Integer metalId) {
		// TODO Auto-generated method stub
		List<PawnBill> pawnBillList = null;
		PawnBillDTO pawnBillDTO = null;
		try {
			
			pawnBillDTO = new PawnBillDTO();
			pawnBillList = pawnBillDAO.getPawnBillList(fromDate, toDate, licenceId, pawnBillStatusId, contactId,pawnBillSource,metalId);
			//billList.forEach(bl -> bl.setAmount(bl.getBillAmount()+bl.getBillTaxAmount()));
			if(pawnBillList!=null && pawnBillList.size()>0) {
				pawnBillList
				.stream()
				//.filter(pb -> pb.getPawnBillStatusId()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE))
				.forEach(
						pbObject ->{
							if(pbObject.getPawnBillStatusId()!=null && pbObject.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
							Integer nMonth = Calculator.monthDifference(pbObject.getBillDate(),new Date());
							if(nMonth>1) {
							pbObject.setRedeemAmount(((nMonth-1)*pbObject.getRateOfInterestPerMonth())+pbObject.getPledgeAmount());
							}else {
								pbObject.setRedeemAmount(pbObject.getPledgeAmount());
							}
							pbObject.setNoOfMonths(nMonth);
							if(nMonth>12) {
								pbObject.setCompoundInterestAmount((Calculator.calculateCompoundInterest(pbObject.getPledgeAmount(), pbObject.getRateOfInterest(), (nMonth-1), 1)));
							}
							}
							String ornamentDescription = pbObject.getPawnBillDetailsList()
							.stream()
							.map(ora -> ora.getOrnament().getOrnamentName())
							.collect(Collectors.joining(","))
							;
							pbObject.setOrnamentDescription(ornamentDescription);
							
							
							List<PawnBillDetails> uniqueMetalList = 
							pbObject.getPawnBillDetailsList()
							.stream()
							.filter(Common.distinctByKey(p -> p.getOrnament().getMetalId().getMetalId()))
							.collect(Collectors.toList())
							;
							Metal metal = null;
							if(uniqueMetalList!=null && uniqueMetalList.size()==1) {
								metal = uniqueMetalList.get(0).getOrnament().getMetalId();
							}else {
								System.out.println("Duplicate Metal found - "+pbObject.getBillNumber());
								metal = uniqueMetalList.get(0).getOrnament().getMetalId();
							}
							
							pbObject.setMetal(metal);
							
							
						}
						);
				
				
			}
			
			if(metalId!=null) {
				pawnBillList = pawnBillList
				.stream()
				.filter(pb -> (pb.getMetal()!=null && pb.getMetal().getMetalId().equals(metalId)))
				.collect(Collectors.toList())
				;
			}
			
			
			pawnBillDTO.setPawnBillList(pawnBillList);
			
			if(pawnBillList!=null) {
				Double totalAmount = pawnBillList.stream().filter(pb -> pb.getPledgeAmount()!=null).mapToDouble(pb -> pb.getPledgeAmount()).sum();
				pawnBillDTO.setTotalAmount(totalAmount);
				pawnBillDTO.setBillCount(pawnBillList.size());
				
				Comparator<PawnBill> comparator = Comparator.comparing( PawnBill::getBillDate );
				
				PawnBill maxBillDate = null;
				if(pawnBillList.size()>0) {
				maxBillDate = pawnBillList.stream().max(comparator).get();
				}
				if(maxBillDate!=null) {
					pawnBillDTO.setLastBillDate(maxBillDate.getBillDate());
				}				
				Integer pledgeBillCount=(int)pawnBillList
		        		.stream()
		        		.filter(pb -> pb.getPledgeAmount()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE) && pb.getNoOfMonths()<=12)
		        		.count();				
		        		
		        Double pledgeBillTotalAmount=pawnBillList
		        		.stream()
		        		.filter(pb -> pb.getPledgeAmount()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE) && pb.getNoOfMonths()<=12)
		        		.mapToDouble(pb -> pb.getPledgeAmount())
		        		.sum();
		        
		        Integer redeemedBillCount=(int)pawnBillList
		        		.stream()
		        		.filter(pb -> pb.getPledgeAmount()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM))
		        		.count()
		        		;
		        Double redemeedBillTotalAmount =pawnBillList
		        		.stream()
		        		.filter(pb -> pb.getPledgeAmount()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM))
		        		.mapToDouble(pb -> pb.getPledgeAmount())
		        		.sum();
		        
		        Integer oldPledgeBillCount=(int)pawnBillList
		        		.stream()
		        		.filter(pb -> pb.getPledgeAmount()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE) && pb.getNoOfMonths()>12)
		        		.count()
		        		;
		        Double oldPledgeBillTotalAmount=pawnBillList
		        		.stream()
		        		.filter(pb -> pb.getPledgeAmount()!=null && pb.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE) && pb.getNoOfMonths()>12)
		        		.mapToDouble(pb -> pb.getPledgeAmount())
		        		.sum();
		        pawnBillDTO.setPledgeBillCount(pledgeBillCount);
		        pawnBillDTO.setPledgeBillTotalAmount(pledgeBillTotalAmount);
		        pawnBillDTO.setRedeemedBillCount(redeemedBillCount);
		        pawnBillDTO.setRedemeedBillTotalAmount(redemeedBillTotalAmount);
		        pawnBillDTO.setOldPledgeBillCount(oldPledgeBillCount);
		        pawnBillDTO.setOldPledgeBillTotalAmount(oldPledgeBillTotalAmount);
				
				/*Map<String, Long> counting = pawnBillList.stream().collect(
		                Collectors.groupingBy(PawnBill::getBillStatusCode, Collectors.counting()));

		        System.out.println(counting);*/
		        
		        
		        /*Map<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
		        		  .collect(groupingBy(BlogPost::getType));*/

		        //Map<PawnBillStatus,List<PawnBill>> pawnBillStatusMap = pawnBillList.stream().collect(groupingBy(PawnBillStatus::getPawnBillStatus));
		        
		        /*Map<String, Integer> sum = pawnBillList.stream().collect(
		                Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));

		        System.out.println(sum);*/
		        
		        
		        /*Map<PawnBillStatus, List<PawnBill>> collect =
		        		pawnBillList.stream()
		        	        .collect(Collectors.groupingBy(PawnBillStatus::getPawnBillStatus,
		        	                 Collectors.mapping(d->createFizz(d),Collectors.toList())));*/
		        
		        Map<PawnBillStatus,List<PawnBill>> pawnBillListMap = pawnBillList.stream().collect(Collectors.groupingBy(PawnBill::getPawnBillStatusId));
		        System.out.println(pawnBillListMap.size());
		        
		        //Map<String,Double> pawnBillMap= new HashMap<String,Double>();
		        
		        
		        
		        
		        pawnBillListMap.forEach((k, v) -> {
		            System.out.println(k + " : " + v.size());
		        });
		        
		        
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return pawnBillDTO;
	}

	@Override
	public PawnBill getPawnBill(Integer pawnBillId) {
		// TODO Auto-generated method stub
		PawnBill bill = null;
		try{
			bill = pawnBillDAO.getPawnBill(pawnBillId);
			List<PawnBillDetails> billDetails = new ArrayList<PawnBillDetails>();
			if(bill!=null){
				if(bill.getPawnBillDetailsList()!=null && bill.getPawnBillDetailsList().size()>0){
					for(PawnBillDetails obj:bill.getPawnBillDetailsList()){
						billDetails.add(obj);
					}
					
					bill.setPawnBillDetails(billDetails);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bill;
	}

	@Override
	public PawnBillStatus getPawnBillStatusByCode(String pawnBillStatusCode) {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnBillStatusByCode(pawnBillStatusCode);
	}

	@Override
	public PawnBill getPawnBillByBillNumber(String billNumber, Integer licenceId, String pawnBillSource) {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnBillByBillNumber(billNumber, licenceId, pawnBillSource);
	}

	@Override
	public PawnBillRedeemDetails saveOrUpdatePawnBillRedeemDetails(PawnBillRedeemDetails pawnBillRedeemDetails) {
		// TODO Auto-generated method stub
		PawnBillRedeemDetails redeemObj = null;
		try {
			redeemObj = pawnBillDAO.saveOrUpdatePawnBillRedeemDetails(pawnBillRedeemDetails);
			
			
			if(redeemObj!=null){
				BillNumber billNumberObj = billDAO.getBillNumber(redeemObj.getPawnBill().getLicence().getLicenceId(), MasterController.getInstace().getBillSourceByCode(ConstantUtils.PAWN_RETAIL_DELIVERY).getBillSourceId());
				
				if(billNumberObj!=null){
					String billNum = StringUtils.getBillNumber(billNumberObj.getBillLetter(), billNumberObj.getBillNumber());
					
					if(redeemObj.getDeliveryNumber().equalsIgnoreCase(billNum)){
						//update bill number starts
						billService.updateBillNumber(billNumberObj);
						//update billNumber ends
					}
				}
				
				//update bill status to redeem
				PawnBill billObjTemp = pawnBillDAO.getPawnBill(redeemObj.getPawnBill().getPawnBillId());
				
				if(billObjTemp!=null) {
					billObjTemp.setPawnBillStatusId(pawnBillDAO.getPawnBillStatusByCode(ConstantUtils.PAWN_BILL_STATUS_REDEEM));
					billObjTemp = pawnBillDAO.saveOrUpdateBill(billObjTemp);
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return redeemObj;
	}

	@Override
	public PawnBillRedeemDetails getPawnBillRedeemDetails(Integer pawnBillRedeemDetailsId) {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnBillRedeemDetails(pawnBillRedeemDetailsId);
	}

	@Override
	public List<com.inventory.model.pbold.OldBillDetails> getPawnOldBillDetails() {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnOldBillDetails();
	}

	@Override
	public List<OldBillDescription> getPawnOldBillDescription() {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnOldBillDescription();
	}

	@Override
	public List<PawnBillStatus> getPawnBillStatusList() {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnBillStatusList();
	}

	@Override
	public Integer updatePawnBillDetails(Integer pawnBillId, String pawnBillNumber, Date pawnBillDate) {
		// TODO Auto-generated method stub
		return pawnBillDAO.updatePawnBillDetails(pawnBillId, pawnBillNumber, pawnBillDate);
	}

	@Override
	public Integer updatePawnBillRedeemDetails(Integer pawnBillId, String deliveryNumber, Date redeemDate) {
		// TODO Auto-generated method stub
		return pawnBillDAO.updatePawnBillRedeemDetails(pawnBillId, deliveryNumber, redeemDate);
	}

	@Override
	public Integer updatePawnBillAuctionStatus(Integer pawnBillId, String auctionStatus) {
		// TODO Auto-generated method stub
		return pawnBillDAO.updatePawnBillAuctionStatus(pawnBillId, auctionStatus);
	}

	@Override
	public PawnBillAuctionDetails saveOrUpdatePawnBillAuctionDetails(PawnBillAuctionDetails pawnBillAuctionDetails) {
		// TODO Auto-generated method stub
		PawnBillAuctionDetails obj = null;
		try {
			obj = pawnBillDAO.saveOrUpdatePawnBillAuctionDetails(pawnBillAuctionDetails);
			if(obj!=null) {
				//update bill status to auction
				PawnBill billObjTemp = pawnBillDAO.getPawnBill(pawnBillAuctionDetails.getPawnBill().getPawnBillId());
				
				if(billObjTemp!=null) {
					billObjTemp.setPawnBillStatusId(pawnBillDAO.getPawnBillStatusByCode(ConstantUtils.PAWN_BILL_STATUS_AUCTION));
					billObjTemp = pawnBillDAO.saveOrUpdateBill(billObjTemp);
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	@Override
	public PawnBillAuctionDetails getPawnBillAuctionDetails(Integer pawnBillAuctionDetailsId) {
		// TODO Auto-generated method stub
		return pawnBillDAO.getPawnBillAuctionDetails(pawnBillAuctionDetailsId);
	}

	@Override
	public Integer savePawnBulkAuction(List<Integer> pawnBillIdList,Date auctionDate,String auctionNumber,String auctionNarration) {
		// TODO Auto-generated method stub
		try {
			List<PawnBill> pawnBillList = null;
			List<PawnBillAuctionDetails> bulkAuctionList = null;
			
			if(pawnBillIdList.size()>0) {
				System.out.println("pawnBillIdList size "+pawnBillIdList.size());
				pawnBillList = new ArrayList<PawnBill>();
				bulkAuctionList = new ArrayList<PawnBillAuctionDetails>();
				
				pawnBillList = pawnBillIdList.stream()
				.map(pawnBill -> getPawnBill(pawnBill))
				.collect(Collectors.toList())
				;
				
				
				System.out.println("pawnBillList size "+pawnBillList.size());
				
				bulkAuctionList = pawnBillList.stream()
				.map(bill -> getAuctionObject(bill,auctionDate,auctionNumber,auctionNarration))
				.collect(Collectors.toList());
				System.out.println("bulkAuctionList size "+bulkAuctionList.size());
				
				bulkAuctionList = bulkAuctionList.stream().map(auction -> saveOrUpdatePawnBillAuctionDetails(auction)).collect(Collectors.toList());
				System.out.println("bulkAuctionList After Save "+bulkAuctionList.size());
			}
		}catch(Exception ex) {
			
		}
		return 1;
	}
	
	private PawnBillAuctionDetails getAuctionObject(PawnBill bill,Date auctionDate,String auctionNumber,String auctionNarration) {
		PawnBillAuctionDetails obj = null;
		try {
			obj = new PawnBillAuctionDetails();
			
			//obj.setAuctionAmountReceived(0.0);
			obj.setAuctionDate(auctionDate);
			obj.setAuctionNumber(auctionNumber);
			obj.setDeletedFlag(ConstantUtils.CONSTANT_N);
			obj.setNarration(auctionNarration);
			obj.setPawnBill(bill);
			//obj.setRedeemInterest(0.0);
			//obj.setRedeemTotalAmount(0.0);
			//obj.setTotalNumberOfMonths(0);
			
			
			Integer nMonth=0;
			Double redeemAmount=0.0;
			
			nMonth = Calculator.monthDifference(bill.getBillDate(),auctionDate);
			
			obj.setTotalNumberOfMonths(nMonth);
			double interest = Calculator.getTwoDigitDecimal(nMonth*bill.getRateOfInterestPerMonth());
			obj.setRedeemInterest(interest);
			
			redeemAmount = Calculator.getTwoDigitDecimal(bill.getPledgeAmount()+interest);
			obj.setRedeemTotalAmount(redeemAmount);
			obj.setAuctionAmountReceived(redeemAmount);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return obj;
	}

	@Override
	public Integer deletePawnBillEntry(Integer pawnBillId) {
		// TODO Auto-generated method stub
		return pawnBillDAO.deletePawnBillEntry(pawnBillId);
	}

	@Override
	public Integer deletePawnRedeemEntry(Integer pawnBillId) {
		// TODO Auto-generated method stub
		return pawnBillDAO.deletePawnRedeemEntry(pawnBillId);
	}

	@Override
	public Integer deletePawnAuctionEntry(Integer pawnBillId) {
		// TODO Auto-generated method stub
		return pawnBillDAO.deletePawnAuctionEntry(pawnBillId);
	}

	@Override
	public Integer updatePawnBillStatus(Integer pawnBillId, Integer pawnBillStatusId) {
		// TODO Auto-generated method stub
		return pawnBillDAO.updatePawnBillStatus(pawnBillId, pawnBillStatusId);
	}

	@Override
	public PawnDailyReportDTO getPawnDailyReport(Date fromDate, Date toDate, Integer licenceId) {
		// TODO Auto-generated method stub
		PawnDailyReportDTO pawnDailyReportDTO = null;
		List<PawnBill> pawnBillList = null;
		//List<PawnDailyReportDTOList> pawnDailyReportDTOList = null;
		try {
			pawnDailyReportDTO = new PawnDailyReportDTO();
			pawnBillList = pawnBillDAO.getPawnBillList(fromDate, toDate, licenceId, null, null,null,null);
			List<PawnDailyReportDTOList> pawnDailyReportDTOList = new ArrayList<PawnDailyReportDTOList>();
			
			pawnBillList.forEach(bill ->{
				PawnDailyReportDTOList dto = new PawnDailyReportDTOList();
				
				if(bill.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_PLEDGE)) {
					dto.setPawnBillId(bill.getPawnBillId());
					dto.setDate(bill.getBillDate());
					dto.setDescription(bill.getBillNumber());
					dto.setPledgeAmount(bill.getPledgeAmount());
					dto.setBillCharges(bill.getBillCharges());
					dto.setInterest(bill.getRateOfInterestPerMonth());
					pawnDailyReportDTOList.add(dto);
				}else if(bill.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_REDEEM)) {
					dto.setPawnBillId(bill.getPawnBillId());
					dto.setDate(bill.getPawnBillRedeemDetails().getRedeemDate());
					dto.setDescription(bill.getPawnBillRedeemDetails().getDeliveryNumber());
					dto.setRedeemPledgeAmount(bill.getPledgeAmount());
					dto.setRedeemAmountReceived(bill.getPawnBillRedeemDetails().getRedeemAmountReceived());
					dto.setRedeemInterestAmount(Calculator.getTwoDigitDecimal(bill.getPawnBillRedeemDetails().getRedeemAmountReceived() - bill.getPledgeAmount()));
					pawnDailyReportDTOList.add(dto);
				}else if(bill.getPawnBillStatusId().getPawnBillStatus().equalsIgnoreCase(ConstantUtils.PAWN_BILL_STATUS_AUCTION)) {
					dto.setPawnBillId(bill.getPawnBillId());
					dto.setDate(bill.getPawnBillAuctionDetails().getAuctionDate());
					dto.setDescription("Auction - "+bill.getPawnBillAuctionDetails().getAuctionNumber());
					dto.setRedeemPledgeAmount(bill.getPledgeAmount());
					dto.setRedeemAmountReceived(bill.getPawnBillAuctionDetails().getAuctionAmountReceived());
					pawnDailyReportDTOList.add(dto);
				}
			});
			pawnDailyReportDTO.setPawnDailyReportDTOList(pawnDailyReportDTOList);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return pawnDailyReportDTO;
	}

}
