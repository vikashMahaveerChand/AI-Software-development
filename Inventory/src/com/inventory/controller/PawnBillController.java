package com.inventory.controller;

import java.util.Date;
import java.util.List;

import com.inventory.dto.PawnBillDTO;
import com.inventory.dto.PawnDailyReportDTO;
import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillAuctionDetails;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.model.PawnBillStatus;
import com.inventory.model.pbold.OldBillDescription;
import com.inventory.model.pbold.OldBillDetails;
import com.inventory.service.PawnBillService;
import com.inventory.service.PawnBillServiceImpl;

public class PawnBillController {
	
	private static PawnBillController myInstance = null;
	PawnBillService pawnBillService = null;
	
	private PawnBillController(){
		try{
			pawnBillService = PawnBillServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static PawnBillController getInstace(){
		if(myInstance == null){
			myInstance = new PawnBillController();
		}
		return myInstance;
	}
	
	public PawnBill saveOrUpdateBill(PawnBill bill) {
		return pawnBillService.saveOrUpdateBill(bill);
	}
	public PawnBillDTO getPawnBillList(Date fromDate,Date toDate,Integer licenceId,Integer pawnBillStatusId,Integer contactId,String pawnBillSource,Integer metalId){
		return pawnBillService.getPawnBillList(fromDate, toDate, licenceId, pawnBillStatusId, contactId,pawnBillSource,metalId);
	}
	public PawnBill getPawnBill(Integer pawnBillId) {
		return pawnBillService.getPawnBill(pawnBillId);
	}
	
	public PawnBillStatus getPawnBillStatusByCode(String pawnBillStatusCode) {
		return pawnBillService.getPawnBillStatusByCode(pawnBillStatusCode);
	}
	public PawnBill getPawnBillByBillNumber(String billNumber,Integer licenceId,String pawnBillSource) {
		return pawnBillService.getPawnBillByBillNumber(billNumber, licenceId, pawnBillSource);
	}
	
	public PawnBillRedeemDetails saveOrUpdatePawnBillRedeemDetails(PawnBillRedeemDetails pawnBillRedeemDetails) {
		return pawnBillService.saveOrUpdatePawnBillRedeemDetails(pawnBillRedeemDetails);
	}
	public PawnBillRedeemDetails getPawnBillRedeemDetails(Integer pawnBillRedeemDetailsId) {
		return pawnBillService.getPawnBillRedeemDetails(pawnBillRedeemDetailsId);
	}
	
	public List<OldBillDetails> getPawnOldBillDetails(){
		return pawnBillService.getPawnOldBillDetails();
	}
	
	public List<OldBillDescription> getPawnOldBillDescription(){
		return pawnBillService.getPawnOldBillDescription();
	}
	
	public List<PawnBillStatus> getPawnBillStatusList(){
		return pawnBillService.getPawnBillStatusList();
	}
	
	public Integer updatePawnBillDetails(Integer pawnBillId,String pawnBillNumber,Date pawnBillDate) {
		return pawnBillService.updatePawnBillDetails(pawnBillId, pawnBillNumber, pawnBillDate);
	}
	public Integer updatePawnBillRedeemDetails(Integer pawnBillId, String deliveryNumber, Date redeemDate) {
		return pawnBillService.updatePawnBillRedeemDetails(pawnBillId, deliveryNumber, redeemDate);
	}
	
	public Integer updatePawnBillAuctionStatus(Integer pawnBillId,String auctionStatus) {
		return pawnBillService.updatePawnBillAuctionStatus(pawnBillId, auctionStatus);
	}
	
	public PawnBillAuctionDetails saveOrUpdatePawnBillAuctionDetails(PawnBillAuctionDetails pawnBillAuctionDetails) {
		return pawnBillService.saveOrUpdatePawnBillAuctionDetails(pawnBillAuctionDetails);
	}
	public PawnBillAuctionDetails getPawnBillAuctionDetails(Integer pawnBillAuctionDetailsId) {
		return pawnBillService.getPawnBillAuctionDetails(pawnBillAuctionDetailsId);
	}
	
	public Integer savePawnBulkAuction(List<Integer> pawnBillId,Date auctionDate,String auctionNumber,String auctionNarration) {
		return pawnBillService.savePawnBulkAuction(pawnBillId, auctionDate, auctionNumber, auctionNarration);
	}
	
	public Integer deletePawnBillEntry(Integer pawnBillId) {
		return pawnBillService.deletePawnBillEntry(pawnBillId);
	}
	public Integer deletePawnRedeemEntry(Integer pawnBillId) {
		return pawnBillService.deletePawnRedeemEntry(pawnBillId);
	}
	public Integer deletePawnAuctionEntry(Integer pawnBillId) {
		return pawnBillService.deletePawnAuctionEntry(pawnBillId);
	}
	
	public Integer updatePawnBillStatus(Integer pawnBillId,Integer pawnBillStatusId) {
		return pawnBillService.updatePawnBillStatus(pawnBillId, pawnBillStatusId);
	}
	
	public PawnDailyReportDTO getPawnDailyReport(Date fromDate,Date toDate,Integer licenceId) {
		return pawnBillService.getPawnDailyReport(fromDate, toDate, licenceId);
	}

}
