package org.uady.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.uady.admin.entity.Alumno;

@Service
public class AlumnoService {
	private ArrayList<Alumno> listaAlumno=new ArrayList<Alumno>();
	
	public Alumno createAlumno(Alumno alumno) {
		String id=String.valueOf(alumno.getId());
		int i=0;
		int idArrayAlumno=0;
		try {
			if (alumno.getId()==0) {
				throw new Exception("POST: ID no válido");
			}
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
			for(Alumno alumnos: listaAlumno) {
				if(String.valueOf(alumnos.getId()).equals(id)) {
					idArrayAlumno=i;
				}
				i++;
			}
			listaAlumno.add(alumno);
			return listaAlumno.get(idArrayAlumno);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	public Alumno updateAlumnoConID(Map<String,Object> dato, Long ID) {
		int i=0;
		int idArrayAlumno=0;
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
			for(Alumno alumnos: listaAlumno) {
				if(String.valueOf(alumnos.getId()).equals(ID.toString())) {
					if (dato.containsKey("nombres")) {
						alumnos.setNombres(dato.get("nombres").toString());
					}
					if (dato.containsKey("apellidos")) {
						alumnos.setApellidos(dato.get("apellidos").toString());
					}
					if (dato.containsKey("matricula")) {
						alumnos.setMatricula(dato.get("matricula").toString());
					}
					if (dato.containsKey("promedio")) {
						alumnos.setPromedio(Double.parseDouble(dato.get("promedio").toString()));
					}
					idArrayAlumno=i;
				}
				i++;
			}
			if(idArrayAlumno==-1) {
				throw new Exception("PUT: Alumno no encontrado" + ID);
			}
			return listaAlumno.get(idArrayAlumno);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	public List<Alumno> getAllAlumnos() {
		
		return listaAlumno;
	}
	
	public Alumno getAlumnoByID(Long ID) {
		int i=0;
		int idArrayAlumno=-1;
		try {
			for(Alumno alumnos: listaAlumno) {
				if(String.valueOf(alumnos.getId()).equals(ID.toString())) {
					idArrayAlumno=i;
				}
				i++;
			}
			if(idArrayAlumno==-1) {
				throw new Exception("GET: Alumno no encontrado "+ ID);
			}
			return listaAlumno.get(idArrayAlumno);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteAlumno(Long ID) {
		int i=0;
		int idArrayAlumno=-1;
		try {
			for(Alumno alumnos: listaAlumno) {
				if(String.valueOf(alumnos.getId()).equals(ID.toString())) {
					idArrayAlumno=i;
				}
				i++;
			}
			if(idArrayAlumno==-1) {
				throw new Exception("DELETE: Alumno no encontrado " + idArrayAlumno);
			}
			listaAlumno.remove(idArrayAlumno);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	
}
