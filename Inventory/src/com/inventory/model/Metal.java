package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "METAL")
public class Metal {
	
	@Override
	public String toString(){
		return metalName;
	}
	
	@Id @GeneratedValue
	@Column(name = "METAL_ID")
	private Integer metalId;
	
	@Column(name = "METAL_CODE")
	private String metalCode;
	
	@Column(name = "METAL_NAME")
	private String metalName;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getMetalId() {
		return metalId;
	}

	public void setMetalId(Integer metalId) {
		this.metalId = metalId;
	}

	public String getMetalCode() {
		return metalCode;
	}

	public void setMetalCode(String metalCode) {
		this.metalCode = metalCode;
	}

	public String getMetalName() {
		return metalName;
	}

	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deletedFlag, metalCode, metalId, metalName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metal other = (Metal) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(metalCode, other.metalCode)
				&& Objects.equals(metalId, other.metalId) && Objects.equals(metalName, other.metalName);
	}
	

}
