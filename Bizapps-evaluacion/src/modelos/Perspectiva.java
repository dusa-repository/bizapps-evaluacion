package modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the perspectiva database table.
 * 
 */
@Entity
@Table(name="perspectiva")
public class Perspectiva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_perspectiva")
	private int idPerspectiva;

	private String descripcion;

	public Perspectiva() {
	}

	public int getIdPerspectiva() {
		return this.idPerspectiva;
	}

	public void setIdPerspectiva(int idPerspectiva) {
		this.idPerspectiva = idPerspectiva;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}