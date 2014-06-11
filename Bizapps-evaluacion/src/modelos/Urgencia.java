package modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the urgencia database table.
 * 
 */
@Entity
@Table(name="urgencia")
public class Urgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_urgencia")
	private int idUrgencia;

	@Column(name="descripcion_urgencia")
	private String descripcionUrgencia;

	public Urgencia() {
	}

	public int getIdUrgencia() {
		return this.idUrgencia;
	}

	public void setIdUrgencia(int idUrgencia) {
		this.idUrgencia = idUrgencia;
	}

	public String getDescripcionUrgencia() {
		return this.descripcionUrgencia;
	}

	public void setDescripcionUrgencia(String descripcionUrgencia) {
		this.descripcionUrgencia = descripcionUrgencia;
	}

}