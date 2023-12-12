package org.uady.admin.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uady.admin.entity.Profesor;
import org.uady.admin.repository.ProfesorRepository;

@Service
public class ProfesorService {
	
	
	@Autowired
	private ProfesorRepository ProfesorRepository;

	public Profesor createProfesor(Profesor profesor) {
		try {
			if(profesor.getNombres().isBlank()){
				throw new Exception("POST: Prof Nombres no válido" + profesor.getNombres());
			}
					
			if(profesor.getApellidos().isBlank()) {
				throw new Exception("POST: Prof Apellido no válido" + profesor.getApellidos());
			}
			if(profesor.getNumeroEmpleado()<=0) {
				throw new Exception("POST: Num empleado no válido" + String.valueOf(profesor.getNumeroEmpleado()));
			}
			if(profesor.getHorasClase()<0) {
				throw new Exception("POST: Horas de clase no válido" + String.valueOf(profesor.getHorasClase()));
			}		
			return ProfesorRepository.save(profesor);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Profesor updateProfesorConID(Map<String,Object> dato, Long ID) {
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
			if(dato.containsKey("numEmpleado") && Integer.parseInt(dato.get("numEmpleado").toString())<0) {
				throw new Exception("PUT: Numero de empleado no válido");
			}
			if(dato.containsKey("horasClase") && Integer.parseInt(dato.get("horasClase").toString())<0) {
				throw new Exception("PUT: Horas Clase no válido");
			}
			if(ProfesorRepository.findById(ID).get()==null) {
				throw new Exception("PUT: Profesor no encontrado" + ID);
			}
			Profesor profesorUpdate=ProfesorRepository.findById(ID).get();
			if (dato.containsKey("nombres")) {
				profesorUpdate.setNombres(dato.get("nombres").toString());
			}
			if (dato.containsKey("apellidos")) {
				profesorUpdate.setApellidos(dato.get("apellidos").toString());
			}
			if (dato.containsKey("numeroEmpleado")) {
				profesorUpdate.setNumeroEmpleado(Integer.valueOf(dato.get("numeroEmpleado").toString()));
			}
			if (dato.containsKey("horasClase")) {
				profesorUpdate.setHorasClase(Integer.valueOf(dato.get("horasClase").toString()));
			}
			return ProfesorRepository.save(profesorUpdate);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Profesor> getAllProfesores() {
		return ProfesorRepository.findAll();
	}
	
	public Profesor getProfesorByID(Long ID) {
		try {
			
			if(ProfesorRepository.findById(ID).get()==null) {
				throw new Exception("GET: Profesor no encontrado" + ID);
			}
			return ProfesorRepository.findById(ID).get();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteProfesor(Long ID) {
		try {
			if(ProfesorRepository.findById(ID).get()==null) {
				throw new Exception("DELETE: Profesor no encontrado" + ID);
			}
			ProfesorRepository.deleteById(ID);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
