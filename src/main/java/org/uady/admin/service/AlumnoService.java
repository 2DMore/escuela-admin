package org.uady.admin.service;

import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uady.admin.entity.Alumno;
import org.uady.admin.entity.Sesion;
import org.uady.admin.repository.AlumnoRepository;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

@Service
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository AlumnoRepository;
	
	@Autowired
	private DynamoDBMapper DynamoDBMapper;
	
	public Alumno createAlumno(Alumno alumno) {
		try {
			if(alumno.getNombres().isBlank()){
				throw new Exception("POST: Nombres no válido");
			}
					
			if(alumno.getApellidos().isBlank()) {
				throw new Exception("POST: Apellido no válido");
			}
			if(!alumno.getMatricula().startsWith("A")) {
				throw new Exception("POST: Matricula no válido");
			}
			if(alumno.getPromedio().isNaN()) {
				throw new Exception("POST: Promedio no válido");
			}		
			return AlumnoRepository.save(alumno);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	public String updateFoto(String fotoUrl, Long ID) throws Exception{
		URL u=new URL(fotoUrl);
		u.toURI();
		System.out.println("URL verificada: "+ fotoUrl);
		Alumno alumnoUpdate=AlumnoRepository.findById(ID).get();
		alumnoUpdate.setFotoPerfilUrl(fotoUrl);
		AlumnoRepository.save(alumnoUpdate);
		return fotoUrl;
	}
	
	public Alumno updateAlumnoConID(Map<String,Object> dato, Long ID) {
		try {
			if (dato.containsKey("id") && Integer.parseInt(dato.get("id").toString())<=0) {
				throw new Exception("PUT: ID no válido");
			}
			if(dato.containsKey("nombres") && dato.get("nombres").toString().isBlank()){
				throw new Exception("PUT: Nombres no válido");
			}
					
			if(dato.containsKey("apellidos") && dato.get("apellidos").toString().isBlank()) {
				throw new Exception("PUT: Apellido no válido");
			}
			if(dato.containsKey("matricula") && !dato.get("matricula").toString().startsWith("A")) {
				throw new Exception("PUT: Matricula no válido");
			}
			if(dato.containsKey("promedio") && Double.isNaN(Double.parseDouble(dato.get("promedio").toString()))) {
				throw new Exception("PUT: Promedio no válido");
			}		
			if(AlumnoRepository.findById(ID).get()==null) {
				throw new Exception("PUT: Alumno no encontrado" + ID);
			}
			Alumno alumnoUpdate=AlumnoRepository.findById(ID).get();
			if (dato.containsKey("nombres")) {
				alumnoUpdate.setNombres(dato.get("nombres").toString());
			}
			if (dato.containsKey("apellidos")) {
				alumnoUpdate.setApellidos(dato.get("apellidos").toString());
			}
			if (dato.containsKey("matricula")) {
				alumnoUpdate.setMatricula(dato.get("matricula").toString());
			}
			if (dato.containsKey("promedio")) {
				alumnoUpdate.setPromedio(Double.parseDouble(dato.get("promedio").toString()));
			}
			
			return AlumnoRepository.save(alumnoUpdate);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	public List<Alumno> getAllAlumnos() {
		
		return AlumnoRepository.findAll();
	}
	
	public Alumno getAlumnoByID(Long ID) {
		try {
			if(AlumnoRepository.findById(ID).get()==null) {
				throw new Exception("GET: Alumno no encontrado" + ID);
			}
			return AlumnoRepository.findById(ID).get();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteAlumno(Long ID) {
		try {
			if(AlumnoRepository.findById(ID).get()==null) {
				throw new Exception("DELETE: Alumno no encontrado" + ID);
			}
			AlumnoRepository.deleteById(ID);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void enviarEmail(Long ID) throws Exception{
		Alumno alumnoEmail= AlumnoRepository.findById(ID).get();
		AmazonSNSClient snsClient =(AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(
						"ASIAVNYK52H4Q37WQM5I",
						"82EA5DGpuM+BNbszhKlXaa+z2ptlaMSJHIyq//ty",
						"FwoGZXIvYXdzEM3//////////wEaDLsnvEXMdQVhRZzkJCLLAZdJInHo9xFMGdz+dWyQOvpHWMtFRggNK5m4Y0d5EyNlPMTFZKSP8ZmPxdFWf+32I/Q0+yKA+vmLDulo8InCPDP6EXyPhERlWF8nTfSqHHnzvzJ7RpCDMuG+/cxfYabfxdLvk63XEdCDonfd3BvnV/8F1SUlI3hdpvZBSbtMBgTL7MbaGAeOVR6fu3jgqTis1KcKCxA5Y5cjikFmh6FpJ3yf/d4f4f0y8+dDuxE3IFKsWXhsp7WFw9jcMrCLWeG+BAd3JcMWmV7r3Y+qKLr44qsGMi04XpmibuQyGKUSxDHQ7P4JDsEWjbyKQITCdvUo5C3eaRxsSqMA+K+bvTtZeHI=")))
				.build();//Editar con nuevas keys
		PublishRequest email=new PublishRequest("arn:aws:sns:us-east-1:373148209657:uady-topic",
				"Alumno: "+alumnoEmail.getNombres() +" "+ alumnoEmail.getApellidos()+"\n Calificacion: "+ alumnoEmail.getPromedio().toString());
		snsClient.publish(email);
	}
	
	public String iniciarSesion(Long ID, String password) throws Exception{
		Alumno alumnoRDS=AlumnoRepository.findById(ID).get();
		if(alumnoRDS.getPassword().equals(password)) {
			Sesion login=new Sesion();
			login.setId(UUID.randomUUID().toString());
			login.setFecha(System.currentTimeMillis() / 1000);
			login.setAlumnoId(ID);
			login.setActive(true);
			String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			SecureRandom random = new SecureRandom();
		    StringBuilder sb = new StringBuilder(128);
		    for(int i = 0; i < 128; i++) {
		    	sb.append(AB.charAt(random.nextInt(AB.length())));
		    }
			login.setSessionString(sb.toString());
			DynamoDBMapper.save(login);
			return login.getSessionString();
		}else {
			throw new Exception("Contrasena incorrecta");
		}
	}
	
	public boolean verificarSesion(Long ID, Map<String, Object> sesion) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(ID.toString()));
        eav.put(":val2", new AttributeValue().withS(sesion.get("sessionString").toString()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("alumnoId = :val1 and sessionString = :val2").withExpressionAttributeValues(eav);

        List<Sesion> scanResult = DynamoDBMapper.scan(Sesion.class, scanExpression);
        if(!scanResult.isEmpty()) {
        	for (Sesion sesionElement : scanResult) {
        		if(sesionElement.isActive()) {
        			return true;
        		}
                System.out.println(sesionElement);
            }
        }else {
        	System.out.println("Verify: NO SE ENCONTRO NINGUN ELEMENTO");
        }
		return false;
	}
	
	public boolean cerrarSesion(Long ID, Map<String, Object> sesion) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(ID.toString()));
        eav.put(":val2", new AttributeValue().withS(sesion.get("sessionString").toString()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("alumnoId = :val1 and sessionString = :val2").withExpressionAttributeValues(eav);

        List<Sesion> scanResult = DynamoDBMapper.scan(Sesion.class, scanExpression);
        if(!scanResult.isEmpty()) {
        	for (Sesion sesionElement : scanResult) {
        		if(sesionElement.isActive()) {
        			System.out.println("LOGOUT: Elemento encontrado");
        			sesionElement.setActive(false);
        			DynamoDBMapper.save(sesionElement);
        			return true;
        		}
                System.out.println(sesionElement);
            }
        }else {
        	System.out.println("LOGOUT: No se encontro ningun elemento");
        }
        return false;
	}
	
	
}
