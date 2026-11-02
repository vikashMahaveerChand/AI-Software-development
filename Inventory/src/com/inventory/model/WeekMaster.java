package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEEK_MASTER")
public class WeekMaster {
	
	@Override
	public String toString(){
		return weekName;
	}
	
	@Id @GeneratedValue
	@Column(name = "WEEK_MASTER_ID")
	private Integer weekMasterId;
	
	@Column(name = "WEEK_NAME")
	private String weekName;
	
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "DISPLAY_ORDER")
	private Integer displayOrder;

	public Integer getWeekMasterId() {
		return weekMasterId;
	}

	public void setWeekMasterId(Integer weekMasterId) {
		this.weekMasterId = weekMasterId;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
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
		return Objects.hash(deletedFlag, displayOrder, weekMasterId, weekName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeekMaster other = (WeekMaster) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(displayOrder, other.displayOrder)
				&& Objects.equals(weekMasterId, other.weekMasterId) && Objects.equals(weekName, other.weekName);
	}
	
	

}
