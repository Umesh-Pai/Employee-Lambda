package com.start.employee.service.impl;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.start.employee.domain.Employee;
import com.start.employee.service.EmployeeService;

/**
 * Created by umesh.pai on 12/21/2017.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	//@Value("${emloyee.database.uri}") private String databaseUri;
	//@Value("${emloyee.database.apikey}") private String apiKey;
	
	public String getEmployeeDetails(String empId) {
		JSONObject response = new JSONObject();
		JSONObject empJson = new JSONObject();
		Employee employee = new Employee();
		int ID = 0;
		DateTime dob = null;
		
		try {
			
			try {
				ID = Integer.parseInt(empId);
			} catch (Exception e) {
				System.out.println("Exception-->" + e.getMessage());
				//context.getLogger().log("Exception--> " + e.getMessage());
				response.put("Message", "Invalid Employee Id");
				return response.toString();
			}
			
			System.out.println("empId=" + ID);			
			HttpResponse httpResponse = Unirest.get("https://nfcapp-95e7.restdb.io/rest/employee")
					.header("x-apikey", "ad80d9dc091065f41889e0c712d6bf8cf137b")
					.header("cache-control", "no-cache")
					.queryString("q", "{\"EMP_ID\" : "+ ID + "}")
					.asJson();
			
			if (httpResponse != null && httpResponse.getBody() != null && httpResponse.getStatus() == 200) {
				System.out.println("httpResponse::" + httpResponse.getBody().toString());
		        JSONArray jsonArr = new JSONArray(httpResponse.getBody().toString());
		        
				if (jsonArr != null && jsonArr.length() != 0) {
					empJson = (JSONObject) jsonArr.get(0);
					
					//dob = DateTime.parse(empJson.getString("DOB"));
					System.out.println("dob=" + empJson.getString("DOB"));
					String dobStr = empJson.getString("DOB");
					if (dobStr.indexOf('T') != -1 ) {
						dobStr = dobStr.substring(0, dobStr.indexOf('T'));
						System.out.println("dobstr=" + dobStr);
					}
					
					//dob = dob.plusHours(11);
					//System.out.println("dob=" + dob.toString());
					
					employee.setFirstName(empJson.getString("FIRST_NAME"));
					employee.setLastName(empJson.getString("LAST_NAME"));
					employee.setEmpId(ID);
					//employee.setDob(dob.toString());
					employee.setDob(dobStr);
					employee.setIdCardType(empJson.getString("ID_CARD_TYPE"));
					employee.setIdCardNumber(empJson.getString("ID_CARD_NUMBER"));
					System.out.println("employee::" + employee.toString());
					
					response = new JSONObject(employee);
					
				} else {
					response.put("Message", "Invalid Employee Id");
				}
			} else {
				response.put("Message", "Internal Server Error");
			}
			
		} catch (Exception e) {
			System.out.println("Exception::" + e.getMessage());
			response.put("Message", "Internal Server Error");
		} 
		return response.toString();
	}
	
	public String createEmployee(Employee employee) {
		JSONObject response = new JSONObject();
		JSONObject empJson;
		int empId;
		
		System.out.println("Dob-->" + employee.getDob());
		/*
		DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-mm-dd");
		DateTime dt = formatter.parseDateTime(employee.getDob());
		
		String pattern = "YYYY-MM-DD hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(employee.getDob());
		System.out.println("date==" + date);
		
		String pattern1 = "YYYY-MM-DD hh:mm:ss";
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
		String date1 = simpleDateFormat1.format(employee.getDob());
		System.out.println("date1==" + date1);
		*/
		
		try {
			String payload = " {"
							+ " \"FIRST_NAME\" : \"" + employee.getFirstName() + "\", "
							+ "\"LAST_NAME\" : \"" + employee.getLastName() + "\","
							+ " \"DOB\" : \"" + employee.getDob() + "\", "
							//+ " \"DOB\" : \"" + dt + "\", "
							+ " \"ID_CARD_NUMBER\" : \"" + employee.getIdCardNumber() + "\", "
							+ " \"ID_CARD_TYPE\" : \"" + employee.getIdCardType() + "\" "
							+ "} ";
			System.out.println("payload=" + payload);	
			
			HttpResponse<JsonNode> httpResponse = Unirest.post("https://nfcapp-95e7.restdb.io/rest/employee")
					.header("x-apikey", "ad80d9dc091065f41889e0c712d6bf8cf137b")
					.header("cache-control", "no-cache")
			        .header("accept", "application/json")
			        .header("Content-Type", "application/json")
			        //.body(employee)
			        .body(payload)
			        .asJson();
			
			System.out.println("httpResponse::" + httpResponse.getBody().toString());
			System.out.println("Status::" + httpResponse.getStatus());

			if (httpResponse != null && httpResponse.getBody() != null && httpResponse.getStatus() == 201) {
				empJson = new JSONObject(httpResponse.getBody().toString());
				System.out.println("empJson::" + empJson.toString());
				empId = empJson.getInt("EMP_ID");
				response.put("EmployeeId", empId);
			} else {
				response.put("Message", "Internal Server Error");
			}
		} catch (Exception e) {
			System.out.println("Exception::" + e.getMessage());
			response.put("Message", "Internal Server Error");
		}
		return response.toString();
	}
	
	/*
	static {
		
		Unirest.setObjectMapper(new ObjectMapper() {
		    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
		                = new com.fasterxml.jackson.databind.ObjectMapper();

		    public <T> T readValue(String value, Class<T> valueType) {
		        try {
		            return jacksonObjectMapper.readValue(value, valueType);
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
		    }

		    public String writeValue(Object value) {
		        try {
		            return jacksonObjectMapper.writeValueAsString(value);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException(e);
		        }
		    }
		
		});
	} */
}
	