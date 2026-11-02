package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAWN_BILL_STATUS")
public class PawnBillStatus {
	
	@Id @GeneratedValue
	@Column(name = "PAWN_BILL_STATUS_ID")
	private Integer pawnBillStatusId;

	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "PAWN_BILL_STATUS")
	private String pawnBillStatus;

	public Integer getPawnBillStatusId() {
		return pawnBillStatusId;
	}

	public void setPawnBillStatusId(Integer pawnBillStatusId) {
		this.pawnBillStatusId = pawnBillStatusId;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getPawnBillStatus() {
		return pawnBillStatus;
	}

	public void setPawnBillStatus(String pawnBillStatus) {
		this.pawnBillStatus = pawnBillStatus;
	}
	
	@Override
	public String toString() {
		return pawnBillStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deletedFlag, pawnBillStatus, pawnBillStatusId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PawnBillStatus other = (PawnBillStatus) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(pawnBillStatus, other.pawnBillStatus)
				&& Objects.equals(pawnBillStatusId, other.pawnBillStatusId);
	}
	
	


}
