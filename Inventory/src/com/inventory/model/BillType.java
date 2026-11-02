package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_TYPE")
public class BillType {
	
	@Override
	public String toString(){
		return billTypeName;
	}
	
	@Id @GeneratedValue
	@Column(name = "BILL_TYPE_ID")
	private Integer billTypeId;
	
	@Column(name = "BILL_TYPE_NAME")
	private String billTypeName;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(Integer billTypeId) {
		this.billTypeId = billTypeId;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(billTypeId, billTypeName, deletedFlag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillType other = (BillType) obj;
		return Objects.equals(billTypeId, other.billTypeId) && Objects.equals(billTypeName, other.billTypeName)
				&& Objects.equals(deletedFlag, other.deletedFlag);
	}
}
