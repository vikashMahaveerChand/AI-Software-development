package com.inventory.service;

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

public interface MasterService {

	public LicenceType getLicenceType(Integer licenceTypeId);
	public List<LicenceType> getLicenceTypeList();
	
	public Integer saveOrUpdateLicence(Licence licence);
	public List<Licence> getLicenceList();
	public Licence getLicence(Integer licenceId);
	
	public Integer saveOrUpdateState(State state);
	public List<State> getStateList();
	public State getState(Integer stateId);
	
	public Integer saveOrUpdateCity(City city);
	public List<City> getCityList();
	public City getCity(Integer cityId);
	
	public Integer saveOrUpdateArea(Area area);
	public List<Area> getAreaList();
	public Area getArea(Integer areaId);
	
	public Integer saveOrUpdateStreet(Street street);
	public List<Street> getStreetList();
	public List<StreetDTO> getStreetDTOList();
	public Street getStreet(Integer streetId);
	
	public Integer saveOrUpdateBank(Bank bank);
	public List<Bank> getBankList();
	public Bank getBank(Integer bankId);
	
	public Integer saveOrUpdateUser(User user);
	public List<User> getUserList();
	public User getUser(Integer userId);
	public User validateUser(String userName,String password);
	
	public Integer saveOrUpdateUserType(UserType userType);
	public List<UserType> getUserTypeList();
	public UserType getUserType(Integer userTypeId);
	
	public Integer saveOrUpdateMetal(Metal metal);
	public List<Metal> getMetalList();
	public Metal getMetal(String metalCode);
	
	public Integer saveOrUpdateOrnament(Ornament ornament);
	public List<Ornament> getOrnamentList(Integer licenceId);
	public List<Ornament> getOrnamentListByMetalCode(Integer licenceId,String metalCode);
	public Ornament getOrnament(Integer ornamentId);
	public List<LicenceOpeningStockDetailsDTO> getLicenceOpeningStockDetailsList(String entryTypeCode,Integer licenceId);
	
	public Integer saveOrUpdateEntryType(EntryType entryType);
	public List<EntryType> getEntryTypeList();
	public EntryType getEntryType(String entryType);
	
	public Integer saveOrUpdateBillType(BillType entryType);
	public List<BillType> getBillTypeList();
	public BillType getBillType(String billType);
	
	public Integer saveOrUpdateLicenceOpeningStockDetails(LicenceOpeningStockDetails licenceOpeningStockDetails);
	public List<LicenceOpeningStockDetails> getLicenceOpeningStockDetailsList();
	public LicenceOpeningStockDetails getLicenceOpeningStockDetails(Integer licenceOpeningStockDetailsId);
	public List<OpeningStockDetailsLicenceDTO> getOpeningStockDetailsLicenceDTOList(Integer licenceId,String billTypeName);
	
	public ContactType getContactType(Integer contactTypeId);
	public List<ContactType> getContactTypeList();
	
	public Contact saveOrUpdateContact(Contact contact);
	public Contact getContact(Integer contactId);
	public List<Contact> getContactList(String contactType);
	public List<Contact> getEnabledContactList(String contactType,String enableFlag);
	
	public BillSource saveOrUpdateBillSource(BillSource billSource);
	public List<BillSource> getBillSourceList();
	public BillSource getBillSource(Integer billSourceId);
	public BillSource getBillSourceByCode(String billSourceCode);
	
	public OpeningStockDetailsLicence saveOrUpdateOpeningStockDetailsLicence(OpeningStockDetailsLicence openingStockDetailsLicence);
	public List<OpeningStockDetailsLicence> getOpeningStockDetailsLicenceList();
	public OpeningStockDetailsLicence getOpeningStockDetailsLicence(Integer openingStockDetailsLicenceId);
	
	public List<CustomerDetails> getCustomerDetailsList();
	
	public List<CoverWeight> coverWeightList();
	
	public List<BillResetMaster> getBillResetMaster();
	public List<DayMaster> getDayMasterList();	
	public List<WeekMaster> getWeekMasterList();	
	public List<MonthMaster> getMonthMasterList();
	
}
