package com.start.employee.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.start.employee.domain.ApiGatewayProxyResponse;
import com.start.employee.domain.Employee;
import com.start.employee.service.EmployeeService;
import com.start.employee.service.impl.EmployeeServiceImpl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class LambdaFunctionHandler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayProxyResponse> {

    //private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

    public LambdaFunctionHandler() {}
    // Test purpose only.
    //LambdaFunctionHandler(AmazonS3 s3) {
      //  this.s3 = s3;
    //}
    
    private EmployeeService employeeService;

    @Override
    public ApiGatewayProxyResponse handleRequest(APIGatewayProxyRequestEvent gatewayEvent, Context context) {
        context.getLogger().log("Id: " + gatewayEvent.getPathParameters().get("id"));
        context.getLogger().log("Method: " + gatewayEvent.getHttpMethod());
        context.getLogger().log("Path: " + gatewayEvent.getPath());
        //context.getLogger().log("Content: " + gatewayEvent.getHeaders().get("Content-Type"));
        
        String empId = null;
        try {
        	empId = gatewayEvent.getPathParameters().get("id");
        	employeeService = new EmployeeServiceImpl();
        	//String resp = "{\"firstName\": \"John\",\"lastName\": \"Rambo\",\"empId\": 101}";
        	//Employee emp = new Employee();
        	//emp.setEmpId(111); emp.setFirstName("Alan"); emp.setLastName("Don");
        	
        	String employeeDetails = employeeService.getEmployeeDetails(empId);
        	context.getLogger().log("employeeDetails: " + employeeDetails);  
        	
        	JSONObject body = new JSONObject(employeeDetails);
        	
        	//JSONObject headerJson = new JSONObject();
            //headerJson.put("x-custom-header", "my custom header value");
            
            Map<String, String> headers = new HashMap();
            headers.put("Content-Type", "application/json");
        	
        	ApiGatewayProxyResponse response = new ApiGatewayProxyResponse(200, headers, body.toString());
        	
        	
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            //context.getLogger().log(String.format(
              //  "Error getting object %s from bucket %s. Make sure they exist and"));
            throw e;
        }
    }
}