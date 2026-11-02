package com.inventory.model;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.inventory.utility.Common;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.Session;

@Entity
@Table(name = "CONTACT")
public class Contact {
	
	@Override
	public String toString(){
		//System.out.println("contact toString"+Session.licence+" "+Session.licence.getLicenceType());
		if(Session.licence==null) {
		return contactName;
		}else if(Session.licence.getLicenceType().getLicenceTypeName().equals(ConstantUtils.LICENCE_TYPE_PAWNBROKER)){
			//System.out.println("else if contact tostring");
			return contactName+" "+secondName+" "+Common.contactAddress(this).replaceAll("\n", " ");
			//return contactName;
		}else {
			return contactName;
		}
	}
	
	@Id @GeneratedValue
	@Column(name = "CONTACT_ID")
	private Integer contactId;
	
	@Column(name = "CONTACT_NAME")
	private String contactName;
	
	@Column(name = "SECOND_NAME")
	private String secondName;
	
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name = "DOOR_NO")
	private String doorNo;
	
	@OneToOne
	@JoinColumn(name="STREET_ID")
	private Street street;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "IS_ENABLED")
	private String isEnabled;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;	
	
	@OneToOne
	@JoinColumn(name="CONTACT_TYPE_ID")
	private ContactType contactType;
	
	@Column(name = "GST_NUMBER")
	private String gstNumber;
	
	
	//@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,mappedBy="contact")
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="contact")
    private List<OpeningBalanceDetailsContact> openingBalanceDetailsListDB = new ArrayList<OpeningBalanceDetailsContact>();
	
	@Column(name="OLD_CONTACT_ID")
	private Integer oldContactId;
		
	@Column(name="AADHAR_CARD")
	private String aadharCard;
	
	@Column(name="PAN_CARD")
	private String panCard;
	
	@Column(name="VOTER_ID")
	private String voterId;
	
	@Column(name="RATION_CARD")
	private String rationCard;
	
	@Transient
    private List<OpeningBalanceDetailsContact> openingBalanceDetailsList;
	
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
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

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getDoorNo() {
		return doorNo;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	public void addOpeningBalanceDetailsListDB(OpeningBalanceDetailsContact openingBalanceDetails){
		this.openingBalanceDetailsListDB.add(openingBalanceDetails);
		openingBalanceDetails.setContact(this);
	}

	public List<OpeningBalanceDetailsContact> getOpeningBalanceDetailsList() {
		return openingBalanceDetailsList;
	}

	public void setOpeningBalanceDetailsList(List<OpeningBalanceDetailsContact> openingBalanceDetailsList) {
		this.openingBalanceDetailsList = openingBalanceDetailsList;
	}

	public List<OpeningBalanceDetailsContact> getOpeningBalanceDetailsListDB() {
		return openingBalanceDetailsListDB;
	}

	public Integer getOldContactId() {
		return oldContactId;
	}

	public void setOldContactId(Integer oldContactId) {
		this.oldContactId = oldContactId;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getRationCard() {
		return rationCard;
	}

	public void setRationCard(String rationCard) {
		this.rationCard = rationCard;
	}

	public void setOpeningBalanceDetailsListDB(List<OpeningBalanceDetailsContact> openingBalanceDetailsListDB) {
		this.openingBalanceDetailsListDB = openingBalanceDetailsListDB;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aadharCard, contactId, contactName, contactType, createdDate, dateOfBirth, deletedFlag,
				doorNo, email, gstNumber, isEnabled, mobile, oldContactId, openingBalanceDetailsList,
				openingBalanceDetailsListDB, panCard, rationCard, secondName, street, voterId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return Objects.equals(aadharCard, other.aadharCard) && Objects.equals(contactId, other.contactId)
				&& Objects.equals(contactName, other.contactName) && Objects.equals(contactType, other.contactType)
				&& Objects.equals(createdDate, other.createdDate) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(doorNo, other.doorNo)
				&& Objects.equals(email, other.email) && Objects.equals(gstNumber, other.gstNumber)
				&& Objects.equals(isEnabled, other.isEnabled) && Objects.equals(mobile, other.mobile)
				&& Objects.equals(oldContactId, other.oldContactId)
				&& Objects.equals(openingBalanceDetailsList, other.openingBalanceDetailsList)
				&& Objects.equals(openingBalanceDetailsListDB, other.openingBalanceDetailsListDB)
				&& Objects.equals(panCard, other.panCard) && Objects.equals(rationCard, other.rationCard)
				&& Objects.equals(secondName, other.secondName) && Objects.equals(street, other.street)
				&& Objects.equals(voterId, other.voterId);
	}
	
	

}
