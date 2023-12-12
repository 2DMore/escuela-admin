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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.uady.admin.entity.Profesor;
import org.uady.admin.service.ProfesorService;

@RestController
@RequestMapping(value="/profesores")
public class ProfesorController {

	@Autowired 
	private ProfesorService profesorService;
	
	@GetMapping
	public List<Profesor> getAllProfesors(){
		return profesorService.getAllProfesores();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProfesorByID(@PathVariable(value="id")Long id){
		try {
			return ResponseEntity.status(200).body(profesorService.getProfesorByID(id));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(404).body(id);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createProfesor(@RequestBody Profesor profesor) {
		try {
			return ResponseEntity.status(201).body(profesorService.createProfesor(profesor));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(profesor);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProfesor(@RequestBody  Map<String,Object> dato, @PathVariable(value="id")Long id) {
		try {
			return ResponseEntity.status(200).body(profesorService.updateProfesorConID(dato, id));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfesor(@PathVariable(value="id")Long id) {
		try {
			profesorService.deleteProfesor(id);
			return ResponseEntity.status(HttpStatus.OK).body("Profesor borrado con exito");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
		}
		
	}
}
