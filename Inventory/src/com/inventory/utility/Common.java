package com.inventory.utility;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.inventory.model.Contact;

public class Common {
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	
	public static String contactAddress(Contact contact) {
		String address ="";
		try {
			StringBuffer addressBuffer = new StringBuffer();
			if(contact.getDoorNo()!=null) {
				//address = "# "+contact.getDoorNo()+",";
				addressBuffer.append("# "+contact.getDoorNo()).append(" ");
			}
			if(contact.getStreet()!=null) {
				addressBuffer.append(StringUtils.capitalize(contact.getStreet().getStreetName()));
				
				if(contact.getStreet().getArea()!=null) {
				addressBuffer.append("\n").append(StringUtils.capitalize(contact.getStreet().getArea().getAreaName()));
				
				if(contact.getStreet().getArea().getCity()!=null) {
				addressBuffer.append("\n")
				.append(StringUtils.capitalize(contact.getStreet().getArea().getCity().getCityName()))
				.append(" - ")
				.append(contact.getStreet().getArea().getPincode());
				}
				if(contact.getStreet().getArea().getCity().getState()!=null) {
				addressBuffer.append("\n").append(StringUtils.capitalize(contact.getStreet().getArea().getCity().getState().getStateName()))
				;
				}
				}
			}
			
			address = addressBuffer.toString();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return address;
	}

}
