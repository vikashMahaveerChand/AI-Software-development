package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MONTH_MASTER")
public class MonthMaster {
	
	@Override
	public String toString(){
		return monthName;
	}
	
	@Id @GeneratedValue
	@Column(name = "MONTH_MASTER_ID")
	private Integer monthMasterId;
	
	@Column(name = "MONTH_NAME")
	private String monthName;
	
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "DISPLAY_ORDER")
	private Integer displayOrder;

	public Integer getMonthMasterId() {
		return monthMasterId;
	}

	public void setMonthMasterId(Integer monthMasterId) {
		this.monthMasterId = monthMasterId;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
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
		return Objects.hash(deletedFlag, displayOrder, monthMasterId, monthName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonthMaster other = (MonthMaster) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(displayOrder, other.displayOrder)
				&& Objects.equals(monthMasterId, other.monthMasterId) && Objects.equals(monthName, other.monthName);
	}
	
	

}
