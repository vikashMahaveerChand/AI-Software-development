package com.inventory.controller;

import java.util.List;

import com.inventory.dto.LicenceOpeningStockDetailsDTO;
import com.inventory.dto.OpeningStockDetailsLicenceDTO;
import com.inventory.dto.StreetDTO;
import com.inventory.model.Area;
import com.inventory.model.Bank;
import com.inventory.model.BillResetMaster;
import com.inventory.model.BillSource;
import com.inventory.model.BillType;
import com.inventory.model.City;
import com.inventory.model.Contact;
import com.inventory.model.ContactType;
import com.inventory.model.CoverWeight;
import com.inventory.model.DayMaster;
import com.inventory.model.EntryType;
import com.inventory.model.Licence;
import com.inventory.model.LicenceOpeningStockDetails;
import com.inventory.model.LicenceType;
import com.inventory.model.Metal;
import com.inventory.model.MonthMaster;
import com.inventory.model.OpeningStockDetailsLicence;
import com.inventory.model.Ornament;
import com.inventory.model.State;
import com.inventory.model.Street;
import com.inventory.model.User;
import com.inventory.model.UserType;
import com.inventory.model.WeekMaster;
import com.inventory.model.pbold.CustomerDetails;
import com.inventory.service.MasterService;
import com.inventory.service.MasterServiceImpl;

public class MasterController {
	private static MasterController myInstance = null;
	MasterService masterService = null;
	
	private MasterController(){
		try{
			masterService = MasterServiceImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static MasterController getInstace(){
		if(myInstance == null){
			myInstance = new MasterController();
		}
		return myInstance;
	}
	
	public LicenceType getLicenceType(Integer licenceTypeId){
		return masterService.getLicenceType(licenceTypeId);
	}
	
	public Integer saveOrUpdateLicence(Licence licence){
		return masterService.saveOrUpdateLicence(licence);
	}
	public List<Licence> getLicenceList(){
		return masterService.getLicenceList();
	}
	public List<LicenceType> getLicenceTypeList(){
		return masterService.getLicenceTypeList();
	}
	
	public Integer saveOrUpdateState(State state){
		return masterService.saveOrUpdateState(state);
	}
	public List<State> getStateList(){
		return masterService.getStateList();
	}
	public State getState(Integer stateId){
		return masterService.getState(stateId);
	}
	
	public Integer saveOrUpdateBank(Bank bank){
		return masterService.saveOrUpdateBank(bank);
	}
	public List<Bank> getBankList(){
		return masterService.getBankList();
	}
	public Bank getBank(Integer bankId){
		return masterService.getBank(bankId);
	}
	
	public Integer saveOrUpdateUser(User user){
		return masterService.saveOrUpdateUser(user);
	}
	public List<User> getUserList(){
		return masterService.getUserList();
	}
	public User getUser(Integer userId){
		return masterService.getUser(userId);
	}
	
	public Integer saveOrUpdateUserType(UserType userType){
		return masterService.saveOrUpdateUserType(userType);
	}
	public List<UserType> getUserTypeList(){
		return masterService.getUserTypeList();
	}
	public UserType getUserType(Integer userTypeId){
		return masterService.getUserType(userTypeId);
	}
	
	public User validatedUser(String userName,String password){
		return masterService.validateUser(userName, password);
	}
	public Licence getLicence(Integer licenceId){
		return masterService.getLicence(licenceId);
	}
	
	public Integer saveOrUpdateMetal(Metal metal){
		return masterService.saveOrUpdateMetal(metal);
	}
	public List<Metal> getMetalList(){
		return masterService.getMetalList();
	}
	public Metal getMetal(String metalCode){
		return masterService.getMetal(metalCode);
	}
	
	public Integer saveOrUpdateOrnament(Ornament ornament){
		return masterService.saveOrUpdateOrnament(ornament);
	}
	public List<Ornament> getOrnamentList(Integer licenceId){
		return masterService.getOrnamentList(licenceId);
	}
	public List<Ornament> getOrnamentListByMetalCode(Integer licenceId,String metalCode){
		return masterService.getOrnamentListByMetalCode(licenceId, metalCode);
	}
	public Ornament getOrnament(Integer ornamentId){
		return masterService.getOrnament(ornamentId);
	}
	
	/*public Integer saveOrUpdateEntryType(EntryType entryType){
		return masterService.saveOrUpdateEntryType(entryType);
	}
	public List<EntryType> getEntryTypeList(){
		return masterService.getEntryTypeList();
	}
	public EntryType getEntryType(String entryType){
		return masterService.getEntryType(entryType);
	}*/
	
	public Integer saveOrUpdateBillType(BillType billType){
		return masterService.saveOrUpdateBillType(billType);
	}
	public List<BillType> getBillTypeList(){
		return masterService.getBillTypeList();
	}
	public BillType getBillType(String billType){
		return masterService.getBillType(billType);
	}
	
	
	
	public List<LicenceOpeningStockDetailsDTO> getLicenceOpeningStockDetailsList(String entryTypeCode,Integer licenceId){
		return masterService.getLicenceOpeningStockDetailsList(entryTypeCode,licenceId);
	}
	
	public Integer saveOrUpdateLicenceOpeningStockDetails(LicenceOpeningStockDetails licenceOpeningStockDetails){
		return masterService.saveOrUpdateLicenceOpeningStockDetails(licenceOpeningStockDetails);
	}
	public List<LicenceOpeningStockDetails> getLicenceOpeningStockDetailsList(){
		return masterService.getLicenceOpeningStockDetailsList();
	}
	public LicenceOpeningStockDetails getLicenceOpeningStockDetails(Integer licenceOpeningStockDetailsId){
		return masterService.getLicenceOpeningStockDetails(licenceOpeningStockDetailsId);
	}
	public ContactType getContactType(Integer contactTypeId){
		return masterService.getContactType(contactTypeId);
	}
	public List<ContactType> getContactTypeList(){
		return masterService.getContactTypeList();
	}
	
	public Integer saveOrUpdateCity(City city){
		return masterService.saveOrUpdateCity(city);
	}
	public List<City> getCityList(){
		return masterService.getCityList();
	}
	public City getCity(Integer cityId){
		return masterService.getCity(cityId);
	}
	public Integer saveOrUpdateArea(Area area){
		return masterService.saveOrUpdateArea(area);
	}
	public List<Area> getAreaList(){
		return masterService.getAreaList();
	}
	public Area getArea(Integer areaId){
		return masterService.getArea(areaId);
	}
	public Integer saveOrUpdateStreet(Street street){
		return masterService.saveOrUpdateStreet(street);
	}
	public List<Street> getStreetList(){
		return masterService.getStreetList();
	}
	public Street getStreet(Integer streetId){
		return masterService.getStreet(streetId);
	}
	public List<StreetDTO> getStreetDTOList(){
		return masterService.getStreetDTOList();
	}
	public Contact saveOrUpdateContact(Contact contact){
		return masterService.saveOrUpdateContact(contact);
	}
	public Contact getContact(Integer contactId){
		return masterService.getContact(contactId);
	}
	
	public List<Contact> getContactList(String contactType){
		return masterService.getContactList(contactType);
	}
	public List<Contact> getEnabledContactList(String contactType,String enableFlag){
		return masterService.getEnabledContactList(contactType, enableFlag);
	}
	
	public BillSource saveOrUpdateBillSource(BillSource billSource){
		return masterService.saveOrUpdateBillSource(billSource);
	}
	public List<BillSource> getBillSourceList(){
		return masterService.getBillSourceList();
	}
	public BillSource getBillSource(Integer billSourceId){
		return masterService.getBillSource(billSourceId);
	}
	public BillSource getBillSourceByCode(String billSourceCode){
		return masterService.getBillSourceByCode(billSourceCode);
	}
	public List<OpeningStockDetailsLicenceDTO> getOpeningStockDetailsLicenceDTOList(Integer licenceId,String billTypeName){
		return masterService.getOpeningStockDetailsLicenceDTOList(licenceId, billTypeName);
	}
	
	public OpeningStockDetailsLicence saveOrUpdateOpeningStockDetailsLicence(OpeningStockDetailsLicence openingStockDetailsLicence){
		return masterService.saveOrUpdateOpeningStockDetailsLicence(openingStockDetailsLicence);
	}
	public List<OpeningStockDetailsLicence> getOpeningStockDetailsLicenceList(){
		return masterService.getOpeningStockDetailsLicenceList();
	}
	public OpeningStockDetailsLicence getOpeningStockDetailsLicence(Integer openingStockDetailsLicenceId){
		return masterService.getOpeningStockDetailsLicence(openingStockDetailsLicenceId);
	}
	
	public List<CustomerDetails> getCustomerDetailsList(){
		return masterService.getCustomerDetailsList();
	}
	
	public List<CoverWeight> coverWeightList(){
		return masterService.coverWeightList();
	}
	
	public List<BillResetMaster> getBillResetMaster(){
		return masterService.getBillResetMaster();
	}
	
	public List<DayMaster> getDayMasterList(){
		return masterService.getDayMasterList();
	}
	
	public List<WeekMaster> getWeekMasterList(){
		return masterService.getWeekMasterList();
	}
	
	public List<MonthMaster> getMonthMasterList(){
		return masterService.getMonthMasterList();
	}
	
}
