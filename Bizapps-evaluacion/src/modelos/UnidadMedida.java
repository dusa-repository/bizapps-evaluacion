package modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the unidad_medida database table.
 * 
 */
@Entity
@Table(name="unidad_medida")
public class UnidadMedida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_unidad")
	private int idUnidad;

	private String descripcion;

	public UnidadMedida() {
	}

	public int getIdUnidad() {
		return this.idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}