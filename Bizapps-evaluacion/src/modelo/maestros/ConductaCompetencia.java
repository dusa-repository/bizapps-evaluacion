package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the conducta_competencia database table.
 * 
 */
@Entity
@Table(name="conducta_competencia")
public class ConductaCompetencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_conducta")
	private int idConducta;

	private String descripcion;

	@Column(name="id_competencia")
	private int idCompetencia;

	@Column(name="id_dominio")
	private int idDominio;

	private int orden;

	public ConductaCompetencia() {
	}

	public int getIdConducta() {
		return this.idConducta;
	}

	public void setIdConducta(int idConducta) {
		this.idConducta = idConducta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdCompetencia() {
		return this.idCompetencia;
	}

	public void setIdCompetencia(int idCompetencia) {
		this.idCompetencia = idCompetencia;
	}

	public int getIdDominio() {
		return this.idDominio;
	}

	public void setIdDominio(int idDominio) {
		this.idDominio = idDominio;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

}