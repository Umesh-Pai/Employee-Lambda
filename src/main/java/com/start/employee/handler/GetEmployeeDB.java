package com.start.employee.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GetEmployeeDB {

	public static void main(String[] args) {
		
		String table = "Employee";
		String name = "101";
		
		HashMap<String,AttributeValue> key_to_get = new HashMap<String,AttributeValue>();
		key_to_get.put("Name", new AttributeValue(name));
		
		GetItemRequest request = new GetItemRequest().withKey(key_to_get).withTableName(table);

		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
		
		try {
			Map<String,AttributeValue> returned_item = ddb.getItem(request).getItem();
			if (returned_item != null) {
                Set<String> keys = returned_item.keySet();
                for (String key : keys) {
                    System.out.format("%s: %s\n", key, returned_item.get(key).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", name);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
            System.err.println(e.getMessage());
        }
	}
}
