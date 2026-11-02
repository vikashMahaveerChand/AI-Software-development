package com.inventory.dao;

import java.util.Date;
import java.util.List;

import com.inventory.model.PawnBill;
import com.inventory.model.PawnBillAuctionDetails;
import com.inventory.model.PawnBillRedeemDetails;
import com.inventory.model.PawnBillStatus;
import com.inventory.model.pbold.OldBillDescription;
import com.inventory.model.pbold.OldBillDetails;

public interface PawnBillDAO {
	
	public PawnBill saveOrUpdateBill(PawnBill bill);
	public List<PawnBill> getPawnBillList(Date fromDate,Date toDate,Integer licenceId,Integer pawnBillStatusId,Integer contactId,String pawnBillSource,Integer metalId);
	public PawnBill getPawnBill(Integer pawnBillId);
	public PawnBill getPawnBillByBillNumber(String billNumber,Integer licenceId,String pawnBillSource);
	
	public PawnBillStatus getPawnBillStatusByCode(String pawnBillStatusCode);
	public List<PawnBillStatus> getPawnBillStatusList();
	
	public PawnBillRedeemDetails saveOrUpdatePawnBillRedeemDetails(PawnBillRedeemDetails pawnBillRedeemDetails);
	public PawnBillRedeemDetails getPawnBillRedeemDetails(Integer pawnBillRedeemDetailsId);
	
	
	public List<OldBillDetails> getPawnOldBillDetails();
	public List<OldBillDescription> getPawnOldBillDescription();
	
	public Integer updatePawnBillDetails(Integer pawnBillId,String pawnBillNumber,Date pawnBillDate);
	public Integer updatePawnBillAuctionStatus(Integer pawnBillId,String auctionStatus);
	public Integer updatePawnBillRedeemDetails(Integer pawnBillId, String deliveryNumber, Date redeemDate);
	
	public PawnBillAuctionDetails saveOrUpdatePawnBillAuctionDetails(PawnBillAuctionDetails pawnBillAuctionDetails);
	public PawnBillAuctionDetails getPawnBillAuctionDetails(Integer pawnBillAuctionDetailsId);
	
	public Integer deletePawnBillEntry(Integer pawnBillId);
	public Integer deletePawnRedeemEntry(Integer pawnBillId);
	public Integer deletePawnAuctionEntry(Integer pawnBillId);
	
	public Integer updatePawnBillStatus(Integer pawnBillId,Integer pawnBillStatusId);
}
