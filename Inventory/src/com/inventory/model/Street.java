package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "STREET")
public class Street {
	
	@Override
	public String toString(){
		return streetName;
	}
	
	@Id @GeneratedValue
	@Column(name = "STREET_ID")
	private Integer streetId;
	
	@Column(name = "STREET_NAME")
	private String streetName;
	
	@OneToOne
	@JoinColumn(name="AREA_ID")
	private Area area;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	//@Formula("concat(streetName,' ',area.areaName)")
	//private String fullname;	
	
	public Integer getStreetId() {
		return streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}	

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, deletedFlag, streetId, streetName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Street other = (Street) obj;
		return Objects.equals(area, other.area) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(streetId, other.streetId) && Objects.equals(streetName, other.streetName);
	}
}
