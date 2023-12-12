package org.uady.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.uady.admin.entity.Alumno;
import org.uady.admin.service.AlumnoService;
import org.uady.admin.service.S3Service;

@RestController
@RequestMapping(value="/alumnos")
public class AlumnoController {
	@Autowired 
	private AlumnoService alumnoService;
	
	@Autowired
	private S3Service s3service;
	
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
	
	@PostMapping("/{id}/fotoPerfil")
	public ResponseEntity<?> subirFotoPerfil(@RequestParam("foto") MultipartFile file, @PathVariable(value="id")Long id){
		try {
			System.out.println(id);
			System.out.println(file.getOriginalFilename());
			if(!file.isEmpty()) {
				String fotoUrl=s3service.subirFotoPerfil("lis20216399", id.toString(),file);
				Map<String,String> map=new HashMap<>();
				map.put("url", alumnoService.updateFoto(fotoUrl, id));
				return ResponseEntity.status(HttpStatus.OK).body(alumnoService.getAlumnoByID(id));
			}else {
				System.out.println("No se pudo obtener ningun archivo");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/{id}/email")
	public ResponseEntity<?> enviarEmail(@PathVariable(value="id") Long id){
		try {
			alumnoService.enviarEmail(id);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping("/{id}/session/login")
	public ResponseEntity<?> iniciarSesion(@PathVariable(value="id") Long id, @RequestBody Alumno alumno){
		try {
			Map<String,String> map=new HashMap<>();
			map.put("sessionString", alumnoService.iniciarSesion(id, alumno.getPassword()));
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/{id}/session/verify")
	public ResponseEntity<?> verificarSesion(@PathVariable(value="id") Long id, @RequestBody Map<String, Object> sesion){
		try {
			if(alumnoService.verificarSesion(id, sesion)){
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).build();
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).build();
		}
	}
	
	@PostMapping("/{id}/session/logout")
	public ResponseEntity<?> cerrarSesion(@PathVariable(value="id") Long id, @RequestBody Map<String, Object> sesion){
		try {
			if(alumnoService.cerrarSesion(id, sesion)){
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).build();
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAlumno(@RequestBody Map<String,Object> dato , @PathVariable(value="id")Long id) {
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
