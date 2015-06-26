package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.ActividadCursoPK;

/**
 * The persistent class for the nivel_competencia_cargo database table.
 * 
 */
@Entity
@Table(name = "actividad_curso")
public class ActividadCurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ActividadCursoPK id;
	
	@Column(name = "valor")
	private String valor;
	
	@Column(name="fecha")
	private Timestamp fecha;

	public ActividadCurso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActividadCurso(ActividadCursoPK id, String valor,
			Timestamp fecha) {
		super();
		this.id = id;
		this.valor = valor;
		this.fecha = fecha;
	}

	public ActividadCursoPK getId() {
		return id;
	}

	public void setId(ActividadCursoPK id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
}