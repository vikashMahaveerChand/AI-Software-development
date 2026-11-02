package com.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COVER_WEIGHT")
public class CoverWeight {
	
	@Id @GeneratedValue
	@Column(name = "COVER_WEIGHT_ID")
	private Integer coverWeightId;
	
	@Column(name = "COVER_WEIGHT")
	private Double coverWeight;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getCoverWeightId() {
		return coverWeightId;
	}

	public void setCoverWeightId(Integer coverWeightId) {
		this.coverWeightId = coverWeightId;
	}

	public Double getCoverWeight() {
		return coverWeight;
	}

	public void setCoverWeight(Double coverWeight) {
		this.coverWeight = coverWeight;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	
	
	

}
