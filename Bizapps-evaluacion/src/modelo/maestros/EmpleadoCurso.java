package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.EmpleadoCursoPK;

/**
 * The persistent class for the nivel_competencia_cargo database table.
 * 
 */
@Entity
@Table(name = "empleado_curso")
public class EmpleadoCurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpleadoCursoPK id;

	@Column(name = "estado_curso")
	private String estadoCurso;
	
	@Column(name = "asistio")
	private String asistio;

	public EmpleadoCurso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpleadoCurso(EmpleadoCursoPK id, String estadoCurso,String asistio) {
		super();
		this.id = id;
		this.estadoCurso = estadoCurso;
		this.asistio= asistio;
	}

	public EmpleadoCursoPK getId() {
		return id;
	}

	public void setId(EmpleadoCursoPK id) {
		this.id = id;
	}

	public String getEstadoCurso() {
		return estadoCurso;
	}

	public void setEstadoCurso(String estadoCurso) {
		this.estadoCurso = estadoCurso;
	}

	public String getAsistio() {
		return asistio;
	}

	public void setAsistio(String asistio) {
		this.asistio = asistio;
	}

}