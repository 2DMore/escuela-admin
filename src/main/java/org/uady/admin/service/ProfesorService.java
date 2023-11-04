package org.uady.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.uady.admin.entity.Profesor;

@Service
public class ProfesorService {
private ArrayList<Profesor> listaProfesor=new ArrayList<Profesor>();
	
	public Profesor createProfesor(Profesor profesor) {
		String id=String.valueOf(profesor.getId());
		int i=0;
		int idArrayProfesores=0;
		for(Profesor profesores: listaProfesor) {
			if(String.valueOf(profesores.getId()).equals(id)) {
				idArrayProfesores=i;
			}
			i++;
		}
		try {
			if (profesor.getId()==0) {
				throw new Exception("POST: Prof ID no válido" + profesor.getId().toString());
			}
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
			for(Profesor profesores: listaProfesor) {
				if(String.valueOf(profesores.getId()).equals(id)) {
					idArrayProfesores=i;
				}
				i++;
			}
			listaProfesor.add(profesor);
			return listaProfesor.get(idArrayProfesores);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Profesor updateProfesorConID(Map<String,Object> dato, Long ID) {
		int i=0;
		int idArrayProfesores=-1;
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
			for(Profesor profesores: listaProfesor) {
				if(String.valueOf(profesores.getId()).equals(ID.toString())) {
					if (dato.containsKey("nombres")) {
						profesores.setNombres(dato.get("nombres").toString());
					}
					if (dato.containsKey("apellidos")) {
						profesores.setApellidos(dato.get("apellidos").toString());
					}
					if (dato.containsKey("numeroEmpleado")) {
						profesores.setNumeroEmpleado(Integer.valueOf(dato.get("numeroEmpleado").toString()));
					}
					if (dato.containsKey("horasClase")) {
						profesores.setHorasClase(Integer.valueOf(dato.get("horasClase").toString()));
					}
					idArrayProfesores=i;
				}
				i++;
			}
			if(idArrayProfesores==-1) {
				throw new Exception("PUT: Profesor no encontrado" + ID);
			}
			return listaProfesor.get(idArrayProfesores);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Profesor> getAllProfesores() {
		return listaProfesor;
	}
	
	public Profesor getProfesorByID(Long ID) {
		int i=0;
		int idArrayProfesores=-1;
		try {
			for(Profesor profesores: listaProfesor) {
				if(String.valueOf(profesores.getId()).equals(ID.toString())) {
					idArrayProfesores=i;
				}
				i++;
			}
			if(idArrayProfesores==-1) {
				throw new Exception("GET: Profesor no encontrado " + ID);
			}
			return listaProfesor.get(idArrayProfesores);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteProfesor(Long ID) {
		int i=0;
		int idArrayProfesores=-1;
		try {
			for(Profesor profesores: listaProfesor) {
				if(String.valueOf(profesores.getId()).equals(ID.toString())) {
					idArrayProfesores=i;
				}
				i++;
			}
			if(idArrayProfesores==-1) {
				throw new Exception("DELETE: Profesor no encontrado " +ID);
			}
			listaProfesor.remove(idArrayProfesores);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
