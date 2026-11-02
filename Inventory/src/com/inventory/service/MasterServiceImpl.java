package com.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.inventory.dao.MasterDAO;
import com.inventory.dao.MasterDAOImpl;
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
import com.inventory.model.OpeningBalanceDetailsContact;
import com.inventory.model.OpeningBalanceDetailsLicence;
import com.inventory.model.OpeningStockDetailsLicence;
import com.inventory.model.Ornament;
import com.inventory.model.State;
import com.inventory.model.Street;
import com.inventory.model.User;
import com.inventory.model.UserType;
import com.inventory.model.WeekMaster;
import com.inventory.model.pbold.CustomerDetails;

public class MasterServiceImpl implements MasterService{
	private static MasterServiceImpl myInstance = null;
	MasterDAO masterDAO = null;
	
	private MasterServiceImpl(){
		try{
			masterDAO = MasterDAOImpl.getInstace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static MasterServiceImpl getInstace(){
		if(myInstance == null){
			myInstance = new MasterServiceImpl();
		}
		return myInstance;
	}
	
	@Override
	public LicenceType getLicenceType(Integer licenceTypeId) {
		// TODO Auto-generated method stub
		return masterDAO.getLicenceType(licenceTypeId);
	}

	@Override
	public Integer saveOrUpdateLicence(Licence licence) {
		// TODO Auto-generated method stub
		Integer licenceId = null;
		try {
			
			if(licence!=null && licence.getOpeningBalanceDetailsLicence()!=null && licence.getOpeningBalanceDetailsLicence().size()>0) {
				for(OpeningBalanceDetailsLicence obj:licence.getOpeningBalanceDetailsLicence()) {
					licence.addOpeningBalanceDetailsLicence(obj);
				}
			}
			
			licenceId = masterDAO.saveOrUpdateLicence(licence);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return licenceId;//masterDAO.saveOrUpdateLicence(licence);
	}

	@Override
	public List<Licence> getLicenceList() {
		// TODO Auto-generated method stub
		return masterDAO.getLicenceList();
	}

	@Override
	public List<LicenceType> getLicenceTypeList() {
		// TODO Auto-generated method stub
		return masterDAO.getLicenceTypeList();
	}

	@Override
	public Integer saveOrUpdateState(State state) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateState(state);
	}

	@Override
	public List<State> getStateList() {
		// TODO Auto-generated method stub
		return masterDAO.getStateList();
	}

	@Override
	public State getState(Integer stateId) {
		// TODO Auto-generated method stub
		return masterDAO.getState(stateId);
	}

	@Override
	public Integer saveOrUpdateBank(Bank bank) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateBank(bank);
	}

	@Override
	public List<Bank> getBankList() {
		// TODO Auto-generated method stub
		return masterDAO.getBankList();
	}

	@Override
	public Bank getBank(Integer bankId) {
		// TODO Auto-generated method stub
		return masterDAO.getBank(bankId);
	}

	@Override
	public Integer saveOrUpdateUser(User user) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateUser(user);
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return masterDAO.getUserList();
	}

	@Override
	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		return masterDAO.getUser(userId);
	}

	@Override
	public Integer saveOrUpdateUserType(UserType userType) {
		// TODO Auto-generated method stub
		return saveOrUpdateUserType(userType);
	}

	@Override
	public List<UserType> getUserTypeList() {
		// TODO Auto-generated method stub
		return masterDAO.getUserTypeList();
	}

	@Override
	public UserType getUserType(Integer userTypeId) {
		// TODO Auto-generated method stub
		return masterDAO.getUserType(userTypeId);
	}

	@Override
	public User validateUser(String userName, String password) {
		// TODO Auto-generated method stub
		User user = null;
		try{
			List<User> userList = masterDAO.getUserList();
			
			if(userList!=null && userList.size()>0){
				for(User obj : userList){
					if(userName.equals(obj.getUserName()) && password.equals(obj.getPassword())){
						user = obj;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return user;
	}

	@Override
	public Licence getLicence(Integer licenceId) {
		// TODO Auto-generated method stub
		Licence licence = null;
		try {
			licence = masterDAO.getLicence(licenceId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return licence;//masterDAO.getLicence(licenceId);
	}

	@Override
	public Integer saveOrUpdateMetal(Metal metal) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateMetal(metal);
	}

	@Override
	public List<Metal> getMetalList() {
		// TODO Auto-generated method stub
		return masterDAO.getMetalList();
	}

	@Override
	public Metal getMetal(String metalCode) {
		// TODO Auto-generated method stub
		return masterDAO.getMetal(metalCode);
	}

	@Override
	public Integer saveOrUpdateOrnament(Ornament ornament) {
		// TODO Auto-generated method stub
		Integer ornamentId = null;
		try {
			if(ornament!=null && ornament.getOpeningStockDetails()!=null) {
				if(ornament.getOpeningStockDetails().size()>0) {
					for(OpeningStockDetailsLicence stock : ornament.getOpeningStockDetails()) {
						ornament.addBillDetailList(stock);
					}
				}
			}
			ornamentId = masterDAO.saveOrUpdateOrnament(ornament);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return ornamentId;
	}

	@Override
	public List<Ornament> getOrnamentList(Integer licenceId) {
		// TODO Auto-generated method stub
		List<Ornament> ornamentList = null;
		try {
			ornamentList = masterDAO.getOrnamentList(licenceId);
			List<OpeningStockDetailsLicence> openingStockDetails = new ArrayList<OpeningStockDetailsLicence>();
			
			if(ornamentList!=null && ornamentList.size()>0) {
				for(Ornament ornament : ornamentList) {
					if(ornament.getOpeningStockDetailsList()!=null && ornament.getOpeningStockDetailsList().size()>0) {
						for(OpeningStockDetailsLicence stock : ornament.getOpeningStockDetailsList()) {
							openingStockDetails.add(stock);
						}
						ornament.setOpeningStockDetails(openingStockDetails);
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return ornamentList;
	}

	@Override
	public Ornament getOrnament(Integer ornamentId) {
		// TODO Auto-generated method stub
		Ornament ornament = null;
		try {
			ornament = masterDAO.getOrnament(ornamentId);
			List<OpeningStockDetailsLicence> openingStockDetails = new ArrayList<OpeningStockDetailsLicence>();
			if(ornament!=null && ornament.getOpeningStockDetailsList()!=null && ornament.getOpeningStockDetailsList().size()>0) {
				for(OpeningStockDetailsLicence stock : ornament.getOpeningStockDetailsList()) {
					openingStockDetails.add(stock);
				}
				ornament.setOpeningStockDetails(openingStockDetails);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return ornament;//masterDAO.getOrnament(ornamentId);
	}

	@Override
	public Integer saveOrUpdateEntryType(EntryType entryType) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateEntryType(entryType);
	}

	@Override
	public List<EntryType> getEntryTypeList() {
		// TODO Auto-generated method stub
		return masterDAO.getEntryTypeList();
	}

	@Override
	public EntryType getEntryType(String entryType) {
		// TODO Auto-generated method stub
		return masterDAO.getEntryType(entryType);
	}

	@Override
	public List<LicenceOpeningStockDetailsDTO> getLicenceOpeningStockDetailsList(String entryTypeCode,Integer licenceId) {
		// TODO Auto-generated method stub
		return masterDAO.getLicenceOpeningStockDetailsList(entryTypeCode,licenceId);
	}

	@Override
	public Integer saveOrUpdateLicenceOpeningStockDetails(
			LicenceOpeningStockDetails licenceOpeningStockDetails) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateLicenceOpeningStockDetails(licenceOpeningStockDetails);
	}

	@Override
	public List<LicenceOpeningStockDetails> getLicenceOpeningStockDetailsList() {
		// TODO Auto-generated method stub
		return masterDAO.getLicenceOpeningStockDetailsList();
	}

	@Override
	public LicenceOpeningStockDetails getLicenceOpeningStockDetails(
			Integer licenceOpeningStockDetailsId) {
		// TODO Auto-generated method stub
		return masterDAO.getLicenceOpeningStockDetails(licenceOpeningStockDetailsId);
	}

	@Override
	public ContactType getContactType(Integer contactTypeId) {
		// TODO Auto-generated method stub
		return masterDAO.getContactType(contactTypeId);
	}

	@Override
	public List<ContactType> getContactTypeList() {
		// TODO Auto-generated method stub
		return masterDAO.getContactTypeList();
	}

	@Override
	public Integer saveOrUpdateCity(City city) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateCity(city);
	}

	@Override
	public List<City> getCityList() {
		// TODO Auto-generated method stub
		return masterDAO.getCityList();
	}

	@Override
	public City getCity(Integer cityId) {
		// TODO Auto-generated method stub
		return masterDAO.getCity(cityId);
	}

	@Override
	public Integer saveOrUpdateArea(Area area) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateArea(area);
	}

	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		return masterDAO.getAreaList();
	}

	@Override
	public Area getArea(Integer areaId) {
		// TODO Auto-generated method stub
		return masterDAO.getArea(areaId);
	}

	@Override
	public Integer saveOrUpdateStreet(Street street) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateStreet(street);
	}

	@Override
	public List<Street> getStreetList() {
		// TODO Auto-generated method stub
		return masterDAO.getStreetList();
	}

	@Override
	public Street getStreet(Integer streetId) {
		// TODO Auto-generated method stub
		return masterDAO.getStreet(streetId);
	}

	@Override
	public List<StreetDTO> getStreetDTOList() {
		// TODO Auto-generated method stub
		List<StreetDTO> streetDTOList = null;
		try{
			List<Street> streetList = masterDAO.getStreetList();
			streetDTOList = new ArrayList<StreetDTO>();
			
			for(Street street:streetList){
				StreetDTO dto = new StreetDTO();
				dto.setArea(street.getArea());
				dto.setDeletedFlag(street.getDeletedFlag());
				dto.setStreetId(street.getStreetId());
				dto.setStreetName(street.getStreetName());
				
				streetDTOList.add(dto);
			}
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return streetDTOList;
	}

	@Override
	public Contact saveOrUpdateContact(Contact contact) {
		// TODO Auto-generated method stub
		Contact contactObj = null;
		try {
			if(contact!=null && contact.getOpeningBalanceDetailsList()!=null && contact.getOpeningBalanceDetailsList().size()>0) {
				for(OpeningBalanceDetailsContact balance : contact.getOpeningBalanceDetailsList()) {
					contact.addOpeningBalanceDetailsListDB(balance);
				}
			}
			
			contactObj = masterDAO.saveOrUpdateContact(contact);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return contactObj;//masterDAO.saveOrUpdateContact(contact);
	}

	@Override
	public Contact getContact(Integer contactId) {
		// TODO Auto-generated method stub
		Contact contact = null;
		try {
			contact = masterDAO.getContact(contactId);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return contact;
	}

	@Override
	public List<Contact> getContactList(String contactType) {
		// TODO Auto-generated method stub
		return masterDAO.getContactList(contactType);
	}

	@Override
	public List<Contact> getEnabledContactList(String contactType, String enableFlag) {
		// TODO Auto-generated method stub
		return masterDAO.getEnabledContactList(contactType, enableFlag);
	}

	@Override
	public List<Ornament> getOrnamentListByMetalCode(Integer licenceId, String metalCode) {
		// TODO Auto-generated method stub
		List<Ornament> ornamentList = null;
		try {
			List<Ornament> ornamentListFull = masterDAO.getOrnamentListByMetalCode(licenceId, metalCode);
			ornamentList = ornamentListFull.stream().distinct().collect(Collectors.toList());
		}catch(Exception ex) {
			
		}
		return ornamentList;
	}

	@Override
	public BillSource saveOrUpdateBillSource(BillSource billSource) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateBillSource(billSource);
	}

	@Override
	public List<BillSource> getBillSourceList() {
		// TODO Auto-generated method stub
		return masterDAO.getBillSourceList();
	}

	@Override
	public BillSource getBillSource(Integer billSourceId) {
		// TODO Auto-generated method stub
		return masterDAO.getBillSource(billSourceId);
	}

	@Override
	public BillSource getBillSourceByCode(String billSourceCode) {
		// TODO Auto-generated method stub
		return masterDAO.getBillSourceByCode(billSourceCode);
	}

	@Override
	public Integer saveOrUpdateBillType(BillType entryType) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateBillType(entryType);
	}

	@Override
	public List<BillType> getBillTypeList() {
		// TODO Auto-generated method stub
		return masterDAO.getBillTypeList();
	}

	@Override
	public BillType getBillType(String billType) {
		// TODO Auto-generated method stub
		return masterDAO.getBillType(billType);
	}

	@Override
	public List<OpeningStockDetailsLicenceDTO> getOpeningStockDetailsLicenceDTOList(Integer licenceId,
			String billTypeName) {
		// TODO Auto-generated method stub
		return masterDAO.getOpeningStockDetailsLicenceDTOList(licenceId, billTypeName);
	}

	@Override
	public OpeningStockDetailsLicence saveOrUpdateOpeningStockDetailsLicence(
			OpeningStockDetailsLicence openingStockDetailsLicence) {
		// TODO Auto-generated method stub
		return masterDAO.saveOrUpdateOpeningStockDetailsLicence(openingStockDetailsLicence);
	}

	@Override
	public List<OpeningStockDetailsLicence> getOpeningStockDetailsLicenceList() {
		// TODO Auto-generated method stub
		return masterDAO.getOpeningStockDetailsLicenceList();
	}

	@Override
	public OpeningStockDetailsLicence getOpeningStockDetailsLicence(Integer openingStockDetailsLicenceId) {
		// TODO Auto-generated method stub
		return masterDAO.getOpeningStockDetailsLicence(openingStockDetailsLicenceId);
	}

	@Override
	public List<CustomerDetails> getCustomerDetailsList() {
		// TODO Auto-generated method stub
		return masterDAO.getCustomerDetailsList();
	}

	@Override
	public List<CoverWeight> coverWeightList() {
		// TODO Auto-generated method stub
		return masterDAO.coverWeightList();
	}

	@Override
	public List<BillResetMaster> getBillResetMaster() {
		// TODO Auto-generated method stub
		return masterDAO.getBillResetMaster();
	}

	@Override
	public List<DayMaster> getDayMasterList() {
		// TODO Auto-generated method stub
		return masterDAO.getDayMasterList();
	}

	@Override
	public List<WeekMaster> getWeekMasterList() {
		// TODO Auto-generated method stub
		return masterDAO.getWeekMasterList();
	}

	@Override
	public List<MonthMaster> getMonthMasterList() {
		// TODO Auto-generated method stub
		return masterDAO.getMonthMasterList();
	}
	

}
