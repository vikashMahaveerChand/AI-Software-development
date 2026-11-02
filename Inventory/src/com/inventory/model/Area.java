package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARIA")
public class Area {
	
	@Override
	public String toString(){
		return areaName;
	}
	
	@Id @GeneratedValue
	@Column(name = "AREA_ID")
	private Integer areaId;
	
	@Column(name = "AREA_NAME")
	private String areaName;
	
	@OneToOne
	@JoinColumn(name="CITY_ID")
	private City city;
	
	@Column(name = "PIN_CODE")
	private String pincode;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaId, areaName, city, deletedFlag, pincode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		return Objects.equals(areaId, other.areaId) && Objects.equals(areaName, other.areaName)
				&& Objects.equals(city, other.city) && Objects.equals(deletedFlag, other.deletedFlag)
				&& Objects.equals(pincode, other.pincode);
	}
	
	

}
