package org.uady.admin.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DynamoDBTable(tableName="sesiones-alumnos")
public class Sesion {
	
	@DynamoDBHashKey
	private String id;
	
	@DynamoDBAttribute
	private Long fecha;
	
	@DynamoDBAttribute
	private Long alumnoId;
	
	@DynamoDBAttribute
	private boolean active;
	
	@DynamoDBAttribute
	private String sessionString;
	
	
}
