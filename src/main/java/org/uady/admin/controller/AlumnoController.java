package org.uady.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uady.admin.entity.Alumno;
import org.uady.admin.service.AlumnoService;

@RestController
@RequestMapping(value="/alumnos")
public class AlumnoController {
	@Autowired 
	private AlumnoService alumnoService;
	
	@GetMapping
	public List<Alumno> getAllAlumnos(){
		return alumnoService.getAllAlumnos();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAlumnoByID(@PathVariable(value="id")Long id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(alumnoService.getAlumnoByID(id));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(404).body(id);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createAlumno(@RequestBody Alumno alumno) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.createAlumno(alumno));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(alumno);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAlumno(@RequestBody Map<String,Object> dato, @PathVariable(value="id")Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(alumnoService.updateAlumnoConID(dato, id));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAlumno(@PathVariable(value="id")Long id) {
		try {
			alumnoService.deleteAlumno(id);
			return ResponseEntity.status(HttpStatus.OK).body("Alumno borrado con exito");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
		}
	}
	
}
