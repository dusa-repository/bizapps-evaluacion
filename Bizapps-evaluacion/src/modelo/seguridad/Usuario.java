package modelo.seguridad;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.Empresa;
import modelo.maestros.Gerencia;
import modelo.maestros.Periodo;
import modelo.maestros.Revision;
import modelo.maestros.UnidadOrganizativa;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario")
	private int idUsuario;

	private String apellido;

	private String cedula;

	private String email;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="id_rol")
	private int idRol;

	private String login;

	private String nombre;

	private String password;
	

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean estado;

	//bi-directional many-to-one association to Cargo
	@OneToMany(mappedBy="usuario")
	private List<Cargo> cargos;

	//bi-directional many-to-one association to Competencia
	@OneToMany(mappedBy="usuario")
	private List<Competencia> competencias;

	//bi-directional many-to-one association to Empresa
	@OneToMany(mappedBy="usuario")
	private List<Empresa> empresas;

	//bi-directional many-to-one association to Gerencia
	@OneToMany(mappedBy="usuario")
	private List<Gerencia> gerencias;

	//bi-directional many-to-one association to Periodo
	@OneToMany(mappedBy="usuario")
	private List<Periodo> periodos;

	//bi-directional many-to-one association to Revision
	@OneToMany(mappedBy="usuario")
	private List<Revision> revisions;

	//bi-directional many-to-one association to UnidadOrganizativa
	@OneToMany(mappedBy="usuario")
	private List<UnidadOrganizativa> unidadOrganizativas;

	@ManyToMany
	@JoinTable(
		name="grupo_usuario"
		, joinColumns={
			@JoinColumn(name="id_usuario", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_grupo", nullable=false)
			}
		)
	private Set<Grupo> grupos;
	public Usuario() {
	}
	
	@Column(name="ficha")
	private String ficha;


	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getIdRol() {
		return this.idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Cargo> getCargos() {
		return this.cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo addCargo(Cargo cargo) {
		getCargos().add(cargo);
		

		return cargo;
	}

	public Cargo removeCargo(Cargo cargo) {
		getCargos().remove(cargo);
		return cargo;
	}

	public List<Competencia> getCompetencias() {
		return this.competencias;
	}

	public void setCompetencias(List<Competencia> competencias) {
		this.competencias = competencias;
	}

	public Competencia addCompetencia(Competencia competencia) {
		getCompetencias().add(competencia);
	

		return competencia;
	}

	public Competencia removeCompetencia(Competencia competencia) {
		getCompetencias().remove(competencia);
	

		return competencia;
	}

	public List<Empresa> getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa addEmpresa(Empresa empresa) {
		getEmpresas().add(empresa);


		return empresa;
	}

	public Empresa removeEmpresa(Empresa empresa) {
		getEmpresas().remove(empresa);
	

		return empresa;
	}

	public List<Gerencia> getGerencias() {
		return this.gerencias;
	}

	public void setGerencias(List<Gerencia> gerencias) {
		this.gerencias = gerencias;
	}

	public Gerencia addGerencia(Gerencia gerencia) {
		getGerencias().add(gerencia);
	

		return gerencia;
	}

	public Gerencia removeGerencia(Gerencia gerencia) {
		getGerencias().remove(gerencia);
	

		return gerencia;
	}

	public List<Periodo> getPeriodos() {
		return this.periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public Periodo addPeriodo(Periodo periodo) {
		getPeriodos().add(periodo);
		

		return periodo;
	}

	public Periodo removePeriodo(Periodo periodo) {
		getPeriodos().remove(periodo);
		

		return periodo;
	}

	public List<Revision> getRevisions() {
		return this.revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

	public Revision addRevision(Revision revision) {
		getRevisions().add(revision);
		

		return revision;
	}

	public Revision removeRevision(Revision revision) {
		getRevisions().remove(revision);
	

		return revision;
	}

	public List<UnidadOrganizativa> getUnidadOrganizativas() {
		return this.unidadOrganizativas;
	}

	public void setUnidadOrganizativas(List<UnidadOrganizativa> unidadOrganizativas) {
		this.unidadOrganizativas = unidadOrganizativas;
	}

	public UnidadOrganizativa addUnidadOrganizativa(UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().add(unidadOrganizativa);
	
		return unidadOrganizativa;
	}

	public UnidadOrganizativa removeUnidadOrganizativa(UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().remove(unidadOrganizativa);
		

		return unidadOrganizativa;
	}

}