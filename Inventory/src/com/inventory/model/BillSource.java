package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_SOURCE")
public class BillSource {
	
	@Override
	public String toString(){
		return billSourceName;
	}
	
	@Id @GeneratedValue
	@Column(name = "BILL_SOURCE_ID")
	private Integer billSourceId;
	
	@Column(name = "BILL_SOURCE_NAME")
	private String billSourceName;

	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getBillSourceId() {
		return billSourceId;
	}

	public void setBillSourceId(Integer billSourceId) {
		this.billSourceId = billSourceId;
	}

	public String getBillSourceName() {
		return billSourceName;
	}

	public void setBillSourceName(String billSourceName) {
		this.billSourceName = billSourceName;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(billSourceId, billSourceName, deletedFlag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillSource other = (BillSource) obj;
		return Objects.equals(billSourceId, other.billSourceId) && Objects.equals(billSourceName, other.billSourceName)
				&& Objects.equals(deletedFlag, other.deletedFlag);
	}
	
}
