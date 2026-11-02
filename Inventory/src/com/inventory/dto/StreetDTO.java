package com.inventory.dto;

import com.inventory.model.Area;


public class StreetDTO {
	
	public String toString(){
		return streetName+" - "+area.getAreaName()+" - "+area.getCity().getCityName()+" - "+area.getPincode()+
				" - "+area.getCity().getState().getStateName();
		//return streetName;
	}
	
	private Integer streetId;
	
	private String streetName;
	
	private Area area;
	
	private String deletedFlag;

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
	
		

}
