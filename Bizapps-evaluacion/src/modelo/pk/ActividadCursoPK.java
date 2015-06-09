package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

import modelo.maestros.Actividad;
import modelo.maestros.Curso;

/**
 * The primary key class for the actividad_curso database table.
 * 
 */
@Embeddable
public class ActividadCursoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Actividad actividad;

	private Curso curso;

	public ActividadCursoPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
