package modelos;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the gerencia database table.
 * 
 */
@Entity
@Table(name="gerencia")
public class Gerencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_gerencia")
	private int idGerencia;

	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	//bi-directional many-to-one association to UnidadOrganizativa
	@OneToMany(mappedBy="gerencia")
	private List<UnidadOrganizativa> unidadOrganizativas;

	public Gerencia() {
	}

	public int getIdGerencia() {
		return this.idGerencia;
	}

	public void setIdGerencia(int idGerencia) {
		this.idGerencia = idGerencia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<UnidadOrganizativa> getUnidadOrganizativas() {
		return this.unidadOrganizativas;
	}

	public void setUnidadOrganizativas(List<UnidadOrganizativa> unidadOrganizativas) {
		this.unidadOrganizativas = unidadOrganizativas;
	}

	public UnidadOrganizativa addUnidadOrganizativa(UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().add(unidadOrganizativa);
		unidadOrganizativa.setGerencia(this);

		return unidadOrganizativa;
	}

	public UnidadOrganizativa removeUnidadOrganizativa(UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().remove(unidadOrganizativa);
		unidadOrganizativa.setGerencia(null);

		return unidadOrganizativa;
	}

}