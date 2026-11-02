package com.inventory.reports;

import java.util.ArrayList;

public class DataBeanList {
	public ArrayList<DataBean> getDataBeanList() {
	      ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();
	      /*
	      dataBeanList.add(produce("Manisha", "India"));
	      dataBeanList.add(produce("Dennis Ritchie", "USA"));
	      dataBeanList.add(produce("V.Anand", "India"));
	      dataBeanList.add(produce("Shrinath", "California"));
	      */
	      dataBeanList.add(addOrnament("1","Gold Chain","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("2","Gold Ring","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("3","Gold Ear Ring","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("4","Gold Neclance","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("5","Gold Biscuite 99 per","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("6","Gold Nanokola","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("7","Gold Nanokola","4582","14.85","3","1,00,250"));
	      dataBeanList.add(addOrnament("8","Gold Nanokola","4582","14.85","3","1,00,250"));
	      return dataBeanList;
	   }

	   /**
	    * This method returns a DataBean object,
	    * with name and country set in it.
	    */
	   private DataBean produce(String name, String country) {
	      DataBean dataBean = new DataBean();
	      dataBean.setName(name);
	      dataBean.setCountry(country);
	      
	      return dataBean;
	   }
	   
	   private DataBean addOrnament(String sNo, String description,String rate,String weight,String gstPercentage,String amount) {
		      DataBean dataBean = new DataBean();
		      dataBean.setsNo(sNo);
		      dataBean.setDescription(description);
		      dataBean.setRate(rate);
		      dataBean.setWeight(weight);
		      dataBean.setGstPercentage(gstPercentage);
		      dataBean.setAmount(amount);
		      
		      return dataBean;
		   }

}
