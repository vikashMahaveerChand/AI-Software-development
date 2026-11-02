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
@Table(name = "ORNAMENT")
public class Ornament {
	
	@Override
	public String toString(){
		return ornamentName;
	}
	
	@Id @GeneratedValue
	@Column(name = "ORNAMENT_ID")
	private Integer ornamentId;
	
	@OneToOne
	@JoinColumn(name="METAL_ID")
	private Metal metalId;
	
	@OneToOne
	@JoinColumn(name="ORNAMENT_GROUP_ID")
	private OrnamentGroup ornamentGroupId;
	
	@OneToOne
	@JoinColumn(name="ORNAMENT_SUB_GROUP_ID")
	private OrnamentSubGroup ornamentSubGroupId;
	
	@Column(name = "ORNAMENT_NAME")
	private String ornamentName;
	
	@Column(name = "ORNAMENT_KEY_CODE")
	private String ornamentKeyCode;
	
	@Column(name = "GST_PERCENTAGE")
	private Double gstPercentage;
	
	@Column(name = "DELETED_FLAG")
	private String deletedFlag;
	
	@Column(name = "LICENCE_ID")
	private Integer licenceId;
	
	@Column(name = "PURITY")
	private Double purity;
	
	@Column(name = "MC_RATE")
	private Double mcRate;
	
	@Column(name = "MC_PER_PIECE")
	private Double mcPerPiece;
	
	@Column(name="HSNCODE")
	private String hsncode;
	/*@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="ORNAMENT_ID")
    private List<LicenceOpeningStockDetails> licenceOpeningStockDetailsList;*/
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER,mappedBy="ornament")
    //@JoinColumn(name="BILL_ID")
	//@OrderBy("sNo ASC")
    private List<OpeningStockDetailsLicence> openingStockDetailsList = new ArrayList<OpeningStockDetailsLicence>();
	
	
	/*@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="BILL_ID")*/
	@Transient
    private List<OpeningStockDetailsLicence> openingStockDetails;

	public Integer getOrnamentId() {
		return ornamentId;
	}

	public void setOrnamentId(Integer ornamentId) {
		this.ornamentId = ornamentId;
	}

	public Metal getMetalId() {
		return metalId;
	}

	public void setMetalId(Metal metalId) {
		this.metalId = metalId;
	}

	public OrnamentGroup getOrnamentGroupId() {
		return ornamentGroupId;
	}

	public void setOrnamentGroupId(OrnamentGroup ornamentGroupId) {
		this.ornamentGroupId = ornamentGroupId;
	}

	public OrnamentSubGroup getOrnamentSubGroupId() {
		return ornamentSubGroupId;
	}

	public void setOrnamentSubGroupId(OrnamentSubGroup ornamentSubGroupId) {
		this.ornamentSubGroupId = ornamentSubGroupId;
	}

	public String getOrnamentName() {
		return ornamentName;
	}

	public void setOrnamentName(String ornamentName) {
		this.ornamentName = ornamentName;
	}

	public String getOrnamentKeyCode() {
		return ornamentKeyCode;
	}

	public void setOrnamentKeyCode(String ornamentKeyCode) {
		this.ornamentKeyCode = ornamentKeyCode;
	}

	public Double getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(Double gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public Double getPurity() {
		return purity;
	}

	public void setPurity(Double purity) {
		this.purity = purity;
	}

	public Double getMcRate() {
		return mcRate;
	}

	public void setMcRate(Double mcRate) {
		this.mcRate = mcRate;
	}

	public Double getMcPerPiece() {
		return mcPerPiece;
	}

	public void setMcPerPiece(Double mcPerPiece) {
		this.mcPerPiece = mcPerPiece;
	}

	public String getHsncode() {
		return hsncode;
	}

	public void setHsncode(String hsncode) {
		this.hsncode = hsncode;
	}

	public List<OpeningStockDetailsLicence> getOpeningStockDetails() {
		return openingStockDetails;
	}

	public void setOpeningStockDetails(List<OpeningStockDetailsLicence> openingStockDetails) {
		this.openingStockDetails = openingStockDetails;
	}
	
	public void addBillDetailList(OpeningStockDetailsLicence stockDetails){
		this.openingStockDetailsList.add(stockDetails);
		stockDetails.setOrnament(this);
	}

	public List<OpeningStockDetailsLicence> getOpeningStockDetailsList() {
		return openingStockDetailsList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deletedFlag, gstPercentage, hsncode, licenceId, mcPerPiece, mcRate, metalId,
				openingStockDetails, openingStockDetailsList, ornamentGroupId, ornamentId, ornamentKeyCode,
				ornamentName, ornamentSubGroupId, purity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ornament other = (Ornament) obj;
		return Objects.equals(deletedFlag, other.deletedFlag) && Objects.equals(gstPercentage, other.gstPercentage)
				&& Objects.equals(hsncode, other.hsncode) && Objects.equals(licenceId, other.licenceId)
				&& Objects.equals(mcPerPiece, other.mcPerPiece) && Objects.equals(mcRate, other.mcRate)
				&& Objects.equals(metalId, other.metalId)
				&& Objects.equals(openingStockDetails, other.openingStockDetails)
				&& Objects.equals(openingStockDetailsList, other.openingStockDetailsList)
				&& Objects.equals(ornamentGroupId, other.ornamentGroupId)
				&& Objects.equals(ornamentId, other.ornamentId)
				&& Objects.equals(ornamentKeyCode, other.ornamentKeyCode)
				&& Objects.equals(ornamentName, other.ornamentName)
				&& Objects.equals(ornamentSubGroupId, other.ornamentSubGroupId) && Objects.equals(purity, other.purity);
	}

	/*public List<LicenceOpeningStockDetails> getLicenceOpeningStockDetailsList() {
		return licenceOpeningStockDetailsList;
	}

	public void setLicenceOpeningStockDetailsList(
			List<LicenceOpeningStockDetails> licenceOpeningStockDetailsList) {
		this.licenceOpeningStockDetailsList = licenceOpeningStockDetailsList;
	}*/
	

}
