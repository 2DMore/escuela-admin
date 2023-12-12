package org.uady.admin.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="alumnos")
@Data
@NoArgsConstructor
public class Alumno {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="nombres")
	private String nombres;
	@Column(name="apellidos")
	private String apellidos;
	@Column(name="matricula")
	private String matricula;
	@Column(name="promedio")
	private Double promedio;
	@Column(name="password")
	private String password;
	@Column(name="fotoPerfilUrl")
	private String fotoPerfilUrl;
}
