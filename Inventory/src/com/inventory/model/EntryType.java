package com.inventory.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENTRY_TYPE")
public class EntryType {
	
	@Override
	public String toString(){
		return entryType;
	}
	
	@Id @GeneratedValue
	@Column(name = "ENTRY_TYPE_ID")
	private Integer entryTypeId;
	
	@Column(name = "ENTRY_TYPE")
	private String entryType;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;

	public Integer getEntryTypeId() {
		return entryTypeId;
	}

	public void setEntryTypeId(Integer entryTypeId) {
		this.entryTypeId = entryTypeId;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deletedFlag, entryType, entryTypeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntryType other = (EntryType) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(entryType, other.entryType)
				&& Objects.equals(entryTypeId, other.entryTypeId);
	}
	
	

}
