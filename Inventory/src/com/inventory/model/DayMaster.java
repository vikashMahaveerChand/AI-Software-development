package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DAY_MASTER")
public class DayMaster {

	@Override
	public String toString(){
		return day;
	}
	
	@Id @GeneratedValue
	@Column(name = "DAY_MASTER_ID")
	private Integer dayMasterId;
	
	@Column(name = "DAY")
	private String day;
	
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "DISPLAY_ORDER")
	private Integer displayOrder;

	public Integer getDayMasterId() {
		return dayMasterId;
	}

	public void setDayMasterId(Integer dayMasterId) {
		this.dayMasterId = dayMasterId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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
		return Objects.hash(day, dayMasterId, deletedFlag, displayOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DayMaster other = (DayMaster) obj;
		return Objects.equals(day, other.day) && Objects.equals(dayMasterId, other.dayMasterId)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(displayOrder, other.displayOrder);
	}
	
	

}
