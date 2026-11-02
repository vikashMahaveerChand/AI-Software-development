package com.inventory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "LICENCE")
public class Licence {
	
	@Override
	public String toString(){
		return licenceName;
	}
	
	@Id @GeneratedValue
	@Column(name = "LICENCE_ID")
	private Integer licenceId;
	
	@Column(name = "LICENCE_NAME")
	private String licenceName;
	
	@Column(name = "OWNER_NAME")
	private String ownerName;
	
	@Column(name = "LICENCE_NUMBER")
	private String licenceNumber;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "LAND_LINE")
	private String landLine;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "WEBSITE")
	private String website;
	
	@Column(name = "GST_NUMBER")
	private String gstNumber;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name="RETURN_ITEM_CONFIG")
	private String returnItemConfig;
	
	@OneToOne
	@JoinColumn(name="LICENCE_TYPE_ID")
	private LicenceType licenceType;
	
	@OneToOne
	@JoinColumn(name="STATE_ID")
	private State state;
	
	@OneToOne
	@JoinColumn(name="BANK_ID")
	private Bank bank;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="licence")
	@OrderBy("OpeningBalanceDetailsLicenceId ASC")
    private List<OpeningBalanceDetailsLicence> openingBalanceDetailsLicenceList = new ArrayList<OpeningBalanceDetailsLicence>();
	
	@Transient
    private List<OpeningBalanceDetailsLicence> openingBalanceDetailsLicence;

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public String getLicenceName() {
		return licenceName;
	}

	public void setLicenceName(String licenceName) {
		this.licenceName = licenceName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandLine() {
		return landLine;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public LicenceType getLicenceType() {
		return licenceType;
	}

	public void setLicenceType(LicenceType licenceType) {
		this.licenceType = licenceType;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<OpeningBalanceDetailsLicence> getOpeningBalanceDetailsLicence() {
		return openingBalanceDetailsLicence;
	}

	public void setOpeningBalanceDetailsLicence(List<OpeningBalanceDetailsLicence> openingBalanceDetailsLicence) {
		this.openingBalanceDetailsLicence = openingBalanceDetailsLicence;
	}

	public void addOpeningBalanceDetailsLicence(OpeningBalanceDetailsLicence openingBalanceDetailsLicence){
		this.openingBalanceDetailsLicenceList.add(openingBalanceDetailsLicence);
		openingBalanceDetailsLicence.setLicence(this);
	}

	public List<OpeningBalanceDetailsLicence> getOpeningBalanceDetailsLicenceList() {
		return openingBalanceDetailsLicenceList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, bank, deletedFlag, email, gstNumber, landLine, licenceId, licenceName,
				licenceNumber, licenceType, mobile, openingBalanceDetailsLicence, openingBalanceDetailsLicenceList,
				ownerName, state, website);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Licence other = (Licence) obj;
		return Objects.equals(address, other.address) && Objects.equals(bank, other.bank)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(email, other.email)
				&& Objects.equals(gstNumber, other.gstNumber) && Objects.equals(landLine, other.landLine)
				&& Objects.equals(licenceId, other.licenceId) && Objects.equals(licenceName, other.licenceName)
				&& Objects.equals(licenceNumber, other.licenceNumber) && Objects.equals(licenceType, other.licenceType)
				&& Objects.equals(mobile, other.mobile)
				&& Objects.equals(openingBalanceDetailsLicence, other.openingBalanceDetailsLicence)
				&& Objects.equals(openingBalanceDetailsLicenceList, other.openingBalanceDetailsLicenceList)
				&& Objects.equals(ownerName, other.ownerName) && Objects.equals(state, other.state)
				&& Objects.equals(website, other.website);
	}

	public String getReturnItemConfig() {
		return returnItemConfig;
	}

	public void setReturnItemConfig(String returnItemConfig) {
		this.returnItemConfig = returnItemConfig;
	}

	
	

}
