package org.uady.admin.entity;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Alumno {
	private Long id;
	private String nombres;
	private String apellidos;
	private String matricula;
	private Double promedio;
}
