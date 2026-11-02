package com.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_DETAILS")
public class EmployeeDetails {
	@Id @GeneratedValue
	@Column(name = "EMPLOYEE_DETAILS_ID")
	private Integer employeeDetailsId;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	/*@ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;*/
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

	public Integer getEmployeeDetailsId() {
		return employeeDetailsId;
	}

	public void setEmployeeDetailsId(Integer employeeDetailsId) {
		this.employeeDetailsId = employeeDetailsId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
