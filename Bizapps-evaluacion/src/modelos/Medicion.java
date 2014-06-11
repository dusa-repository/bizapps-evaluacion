package modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medicion database table.
 * 
 */
@Entity
@Table(name="medicion")
public class Medicion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_medicion")
	private int idMedicion;

	@Column(name="descripcion_medicion")
	private String descripcionMedicion;

	public Medicion() {
	}

	public int getIdMedicion() {
		return this.idMedicion;
	}

	public void setIdMedicion(int idMedicion) {
		this.idMedicion = idMedicion;
	}

	public String getDescripcionMedicion() {
		return this.descripcionMedicion;
	}

	public void setDescripcionMedicion(String descripcionMedicion) {
		this.descripcionMedicion = descripcionMedicion;
	}

}