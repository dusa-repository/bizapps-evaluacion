package modelos;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;


/**
 * The persistent class for the unidad_organizativa database table.
 * 
 */
@Entity
@Table(name="unidad_organizativa")
public class UnidadOrganizativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_unidad_organizativa")
	private int idUnidadOrganizativa;

	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="id_empresa_auxiliar")
	private String idEmpresaAuxiliar;

	@Column(name="id_unidad_organizativa_auxiliar")
	private String idUnidadOrganizativaAuxiliar;

	private int nivel;

	@Column(name="sub_nivel")
	private int subNivel;

	//bi-directional many-to-one association to Gerencia
	@ManyToOne
	@JoinColumn(name="id_gerencia")
	private Gerencia gerencia;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public UnidadOrganizativa() {
	}

	public int getIdUnidadOrganizativa() {
		return this.idUnidadOrganizativa;
	}

	public void setIdUnidadOrganizativa(int idUnidadOrganizativa) {
		this.idUnidadOrganizativa = idUnidadOrganizativa;
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

	public String getIdEmpresaAuxiliar() {
		return this.idEmpresaAuxiliar;
	}

	public void setIdEmpresaAuxiliar(String idEmpresaAuxiliar) {
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
	}

	public String getIdUnidadOrganizativaAuxiliar() {
		return this.idUnidadOrganizativaAuxiliar;
	}

	public void setIdUnidadOrganizativaAuxiliar(String idUnidadOrganizativaAuxiliar) {
		this.idUnidadOrganizativaAuxiliar = idUnidadOrganizativaAuxiliar;
	}

	public int getNivel() {
		return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getSubNivel() {
		return this.subNivel;
	}

	public void setSubNivel(int subNivel) {
		this.subNivel = subNivel;
	}

	public Gerencia getGerencia() {
		return this.gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}