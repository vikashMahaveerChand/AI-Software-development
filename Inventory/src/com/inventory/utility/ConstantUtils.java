package com.inventory.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConstantUtils {
	
	public static final String CONSTANT_N = "N";
	public static final String CONSTANT_Y = "Y";
	public static final String MENU = "MENU";
	public static final String LOGIN_PAGE = "LOGIN_PAGE";
	public static final String SELECT_BANK="==Select Bank==";
	public static final String DEFAULT = "DEFAULT";
	public static final String BLANK = "";
	public static final String SUCCESS = "Success";
	
	public static final String LICENCE_TYPE_SILVER_WHOLESALE = "SILVER_WHOLESALE";
	public static final String LICENCE_TYPE_GOLD_WHOLESALE = "GOLD_WHOLESALE";
	public static final String LICENCE_TYPE_JEWELLERY = "JEWELLERY";
	public static final String LICENCE_TYPE_PAWNBROKER = "PAWNBROKER";
	public static final String LICENCE_TYPE_GYM = "GYM";
	public static final String FINANCE = "FINANCE";
	public static final String HANDLOAN = "HANDLOAN";
	public static final String LICENCE_TYPE_WHOLESALE = "WHOLESALE";
	
	public static final String USER_TYPE_ADMIN = "ADMIN";
	public static final String USER_TYPE_GST = "GST";
	public static final String USER_TYPE_NON_GST = "NON_GST";
	
	public static final String LICENCE_REPORT = "LICENCE_REPORT";
	public static final String USER_REPORT = "USER_REPORT";
	
	public static final String SILVER="SILVER";
	public static final String GOLD="GOLD";
	public static final String OTHER="OTHER";
	public static final String ORNAMENT_REPORT = "ORNAMENT_REPORT";
	
	public static final String GST_ENTRY = "GST";
	public static final String NON_GST_ENTRY = "NON_GST";
	
	public static final String ADD_GST_OPENING_STOCK = "ADD_GST_OPENING_STOCK";
	public static final String ADD_NONGST_OPENING_STOCK = "ADD_NONGST_OPENING_STOCK";
	
	public static final String RETAIL_CONTACT_TYPE="RETAIL";
	public static final String WHOLESALE_CONTACT_TYPE="WHOLESALE";
	public static final String SUPPLIER_CONTACT_TYPE="SUPPLIER";
	public static final String PAWNCUSTOMER_CONTACT_TYPE="PAWNCUSTOMER";
	
	public static final String CONTACT_REPORT = "CONTACT_REPORT";
	public static final String BILLING_REPORT = "BILLING_REPORT";
	public static final String PAYMENT_FORM = "PAYMENT_FORM";
	public static final String PAWN_RETAIL_CUSTOMER_DETAILS = "PAWN_RETAIL_CUSTOMER_DETAILS";
	
	//Bill Source Starts
	public static final String GOLD_WHOLESALE_GST = "GOLD_WHOLESALE_GST";
	public static final String GOLD_WHOLESALE_NONGST = "GOLD_WHOLESALE_NONGST";
	public static final String SILVER_WHOLESALE_GST = "SILVER_WHOLESALE_GST";   
	public static final String SILVER_WHOLESALE_NONGST = "SILVER_WHOLESALE_NONGST";
	public static final String GOLD_SUPPLIER_GST = "GOLD_SUPPLIER_GST";      
	public static final String GOLD_SUPPLIER_NONGST = "GOLD_SUPPLIER_NONGST";  
	public static final String SILVER_SUPPLIER_GST = "SILVER_SUPPLIER_GST";    
	public static final String SILVER_SUPPLIER_NONGST = "SILVER_SUPPLIER_NONGST";
	public static final String PAWN_RETAIL = "PAWN_RETAIL";
	public static final String PAWN_RETAIL_DELIVERY = "PAWN_RETAIL_DELIVERY";
	public static final String PAWN_RETAIL_AUCTION = "PAWN_RETAIL_AUCTION";

	public static final String RETAIL_BILLING_GST="RETAIL_GST";
	public static final String RETAIL_BILLING_NONGST="RETAIL_NONGST";
	public static final String WHOLESALE_BILLING_GST="WHOLESALE_GST";
	public static final String WHOLESALE_BILLING_NONGST="WHOLESALE_NONGST";
	public static final String PURCHASE_BILLING_GST="PURCHASE_GST";
	public static final String PURCHASE_BILLING_NONGST="PURCHASE_NONGST";
	//Bill Source Ends
	
	
	public static final String GOLD_RETAIL_GST = "GOLD_RETAIL_GST";      
	public static final String GOLD_RETAIL_NONGST = "GOLD_RETAIL_NONGST"; 
	public static final String SILVER_RETAIL_GST = "SILVER_RETAIL_GST";    
	public static final String SILVER_RETAIL_NONGST = "SILVER_RETAIL_NONGST";
	
	public static final String ORNAMENT_MODE_SALE = "Sale";
	public static final String ORNAMENT_MODE_RETURN = "Return";
	public static final String ORNAMENT_MODE_OLD = "Old";
	
	public static final String CREATE = "CREATE";
	public static final String UPDATE = "UPDATE";
	
	public static final String BILL="BILL";
	public static final String PAYMENT="PAYMENT";
	public static final String OPENING="OPENING";
	public static final String TOTAL="TOTAL";
	public static final String OLD_BILL="OLD_BILL";
	public static final String OLD_PAYMENT="OLD_PAYMENT";
	
	public static final String PAWN_BILL_STATUS_PLEDGE="PLEDGE";
	public static final String PAWN_BILL_STATUS_REDEEM="REDEEM";
	public static final String PAWN_BILL_STATUS_AUCTION="AUCTION";
	
	public static final String FETCHTYPE_OPENING_STOCK="OPENING_STOCK";
	public static final String FETCHTYPE_USER_CRITERIA = "USER_CRETERIA";
	
	public static final String CONTACT_TYPE_RETAIL = "RETAIL";
	public static final String CONTACT_TYPE_WHOLESALE = "WHOLESALE";
	public static final String CONTACT_TYPE_SUPPLIER = "SUPPLIER";
	public static final String CONTACT_TYPE_PAWNCUSTOMER ="PAWNCUSTOMER";
	public static final String CONTACT_TYPE_GENERIC = "GENERIC";
	
	public static final List<String> PAYMENT_TYPE = Collections.unmodifiableList(
		    Arrays.asList("Cash", "Bank"));
	
	public static final List<String> CONVERSION_TYPE = Collections.unmodifiableList(
		    Arrays.asList("Cash to Metal (C-M)", "Metal to Cash (M-C)"));
	
	public static final String CASH="Cash";
	public static final String BANK="Bank";
	
}