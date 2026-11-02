package com.inventory.migration;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.inventory.controller.MasterController;
import com.inventory.model.Area;
import com.inventory.model.City;
import com.inventory.model.Contact;
import com.inventory.model.ContactType;
import com.inventory.model.Street;
import com.inventory.model.pbold.CustomerDetails;
import com.inventory.utility.ConstantUtils;
import com.inventory.utility.StringUtils;

public class CustomerDetailsMigration {
	
	public static void migrateCustomerDetails() {
		
		try {
			System.out.println("migrateCustomeDetails");
			List<CustomerDetails> customerDetailsList = MasterController.getInstace().getCustomerDetailsList();
			if(customerDetailsList!=null && customerDetailsList.size()>0) {
				System.out.println(customerDetailsList.size());
				createUniqueCity(customerDetailsList);
				createUniqueArea(customerDetailsList);
				createUniqueStreet(customerDetailsList);
				createCustomer(customerDetailsList);
			}else {
				System.out.println("==No Data ==");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public static void createUniqueCity(List<CustomerDetails> customerDetailsList) {
		try {
			System.out.println("createUniqueCity");
			List<CustomerDetails> customerDetailsListFiltered = customerDetailsList.stream() 
					  .filter(distinctByKey(p -> p.getAddress6().trim().toUpperCase())) 
					  .collect(Collectors.toList());
			
			
			System.out.println("createUniqueCity count - "+customerDetailsListFiltered.size());
			
			customerDetailsListFiltered.stream().forEach(
					cd -> {
						System.out.println("->"+cd.getAddress6()+"<-");
						City city = new City();
						city.setCityName(StringUtils.capitalize(cd.getAddress6().trim()).trim());
						city.setDeletedFlag(ConstantUtils.CONSTANT_N);
						city.setState(MasterController.getInstace().getState(1));
						
						MasterController.getInstace().saveOrUpdateCity(city);
						}
					);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void createUniqueArea(List<CustomerDetails> customerDetailsList) {
		try {
			System.out.println("createUniqueArea");
			List<CustomerDetails> customerDetailsListFiltered = customerDetailsList.stream() 
					  .filter(distinctByKey(p -> p.getAddress4().trim()))
					  .sorted(Comparator.comparing(CustomerDetails::getAddress4))
					  .collect(Collectors.toList());
			
			
			System.out.println("createUniqueArea count - "+customerDetailsListFiltered.size());
			
			List<City> cityList = MasterController.getInstace().getCityList();
			
			customerDetailsListFiltered.stream().forEach(
					cd -> {
						System.out.println("->"+cd.getAddress4()+"<-"+cd.getAddress5()+"<-"+cd.getAddress6());
						Area area = new Area();
						area.setAreaName(StringUtils.capitalize(cd.getAddress4().trim()).trim());
						cityList.stream().forEach(city -> {
							System.out.println("Compare :->"+city.getCityName()+"<->"+cd.getAddress6()+"<-");
							if(city.getCityName().toUpperCase().equalsIgnoreCase(cd.getAddress6().trim().toUpperCase())) {
								area.setCity(city);
								System.out.println("Compare success : "+city.getCityName()+" - "+cd.getAddress6());
							}
						});
						area.setDeletedFlag(ConstantUtils.CONSTANT_N);
						area.setPincode(cd.getAddress5());
						MasterController.getInstace().saveOrUpdateArea(area);
						}
					);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void createUniqueStreet(List<CustomerDetails> customerDetailsList) {
		try {
			System.out.println("createUniqueStreet");
			List<CustomerDetails> customerDetailsListFiltered = customerDetailsList.stream() 
					  .filter(distinctByKey(p -> (StringUtils.capitalize(p.getAddress2().trim())+" "+StringUtils.capitalize(p.getAddress3().trim()))))
					  .sorted(Comparator.comparing(CustomerDetails::getAddress2))
					  .collect(Collectors.toList());
			
			
			System.out.println("createUniqueStreet count - "+customerDetailsListFiltered.size());
			
			List<Area> areaList = MasterController.getInstace().getAreaList();
			
			customerDetailsListFiltered.stream().forEach(
					cd -> {
						System.out.println("->"+cd.getAddress2()+"<->"+cd.getAddress3()+"<->"+cd.getAddress4()+"<-");
						
						Street street = new Street();
						String streetName = StringUtils.capitalize(cd.getAddress2().trim())+" "+StringUtils.capitalize(cd.getAddress3().trim());
						
						street.setStreetName(streetName);
						areaList.stream().forEach(area -> {
							if(area.getAreaName().toUpperCase().equalsIgnoreCase(cd.getAddress4().trim().toUpperCase())) {
								street.setArea(area);
							}
							street.setDeletedFlag(ConstantUtils.CONSTANT_N);
							MasterController.getInstace().saveOrUpdateStreet(street);
						});
						
					}
					);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void createCustomer(List<CustomerDetails> customerDetailsList) {
		try {
			System.out.println("createCustomer");
			
			List<CustomerDetails> customerDetailsListFiltered = customerDetailsList.stream() 
					  //.filter(distinctByKey(p -> p.getAddress2().trim()))
					//.filter(cd -> cd.getFirstownername().trim().equalsIgnoreCase("Banu.r"))
					  .sorted(Comparator.comparing(CustomerDetails::getFirstownername))
					  .collect(Collectors.toList());
			
			System.out.println(customerDetailsListFiltered.size());
			
			
			List<Street> streetList = MasterController.getInstace().getStreetList();
			ContactType contactType = MasterController.getInstace().getContactType(1);
			customerDetailsListFiltered.stream().forEach(cd -> {
				System.out.println(cd.getFirstownername()+" "+cd.getSecondownername());
				
				Contact contact = new Contact();
				
				contact.setContactName(StringUtils.capitalize(cd.getFirstownername().trim()));
				contact.setContactType(contactType);
				contact.setDeletedFlag(ConstantUtils.CONSTANT_N);
				contact.setDoorNo(cd.getAddress1().trim());
				if(cd.getEmail()!=null) {
					contact.setEmail(cd.getEmail().trim());
				}
				contact.setIsEnabled(ConstantUtils.CONSTANT_Y);
				contact.setMobile(cd.getPhone());
				contact.setSecondName(StringUtils.capitalize(cd.getSecondownername().trim()));
				contact.setOldContactId(cd.getId());
				
				streetList.stream()
				//.filter(st-> st.getStreetName().contains("Thirvallur"))
				.forEach(street -> {
					String streetName = StringUtils.capitalize(cd.getAddress2().trim())+" "+StringUtils.capitalize(cd.getAddress3().trim());
					System.out.println("->"+streetName+"<-"+" : ->"+street.getStreetName()+"<-");
					if(street.getStreetName().toUpperCase().equalsIgnoreCase(streetName.toUpperCase())) {
						contact.setStreet(street);
					}
				});
				
				MasterController.getInstace().saveOrUpdateContact(contact);
				
			});
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public static <T> Predicate<T> distinctByKey(
		    Function<? super T, ?> keyExtractor) {
		  
		    Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
		    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; 
		}
	

}
