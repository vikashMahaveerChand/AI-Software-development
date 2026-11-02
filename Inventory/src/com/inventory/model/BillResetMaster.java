package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_RESET_MASTER")
public class BillResetMaster {
	
	@Override
	public String toString(){
		return resetType;
	}
	
	@Id @GeneratedValue
	@Column(name = "BILL_RESET_MASTER_ID")
	private Integer billResetMasterId;
	
	@Column(name = "RESET_TYPE")
	private String resetType;
	
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "DISPLAY_ORDER")
	private Integer displayOrder;

	public Integer getBillResetMasterId() {
		return billResetMasterId;
	}

	public void setBillResetMasterId(Integer billResetMasterId) {
		this.billResetMasterId = billResetMasterId;
	}

	public String getResetType() {
		return resetType;
	}

	public void setResetType(String resetType) {
		this.resetType = resetType;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(billResetMasterId, deletedFlag, displayOrder, resetType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillResetMaster other = (BillResetMaster) obj;
		return Objects.equals(billResetMasterId, other.billResetMasterId)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(displayOrder, other.displayOrder)
				&& Objects.equals(resetType, other.resetType);
	}

}
