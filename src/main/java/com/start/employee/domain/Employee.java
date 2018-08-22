package com.start.employee.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by umesh.pai on 12/20/2017.
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class Employee {
	
    private int empId;
    
	@JsonProperty("firstName")
    private String firstName;

	@JsonProperty("lastName")
    private String lastName;
	
	@JsonProperty("dob")
    private String dob;
	
	@JsonProperty("idCardType")
    private String idCardType;
	
	@JsonProperty("idCardNumber")
    private String idCardNumber;
    
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	
	@Override
	public String toString()
	{
		return "Employee{" +
				"firstName=" + firstName +
				", lastName='" + lastName + '\'' +
				", dob='" + dob + '\'' +
				", idCardType='" + idCardType + '\'' +
				", idCardNumber='" + idCardNumber + '\'' +
				"}";
	}


}
