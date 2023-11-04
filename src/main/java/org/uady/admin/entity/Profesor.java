package org.uady.admin.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Profesor {
	private Long id;
	private int numeroEmpleado;
	private String nombres;
	private String apellidos;
	private int horasClase;
}
