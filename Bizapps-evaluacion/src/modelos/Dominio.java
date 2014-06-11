package modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dominio database table.
 * 
 */
@Entity
@Table(name="dominio")
public class Dominio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dominio")
	private int idDominio;

	private String comentario;

	@Column(name="descripcion_dominio")
	private String descripcionDominio;

	private String tipo;

	public Dominio() {
	}

	public int getIdDominio() {
		return this.idDominio;
	}

	public void setIdDominio(int idDominio) {
		this.idDominio = idDominio;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getDescripcionDominio() {
		return this.descripcionDominio;
	}

	public void setDescripcionDominio(String descripcionDominio) {
		this.descripcionDominio = descripcionDominio;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}