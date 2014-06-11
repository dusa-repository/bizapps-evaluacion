package modelos;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;


/**
 * The persistent class for the cargo database table.
 * 
 */
@Entity
@Table(name="cargo")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cargo")
	private int idCargo;

	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="id_cargo_auxiliar")
	private String idCargoAuxiliar;

	@Column(name="id_empresa_auxiliar")
	private String idEmpresaAuxiliar;

	private String nomina;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Cargo() {
	}

	public int getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
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

	public String getIdCargoAuxiliar() {
		return this.idCargoAuxiliar;
	}

	public void setIdCargoAuxiliar(String idCargoAuxiliar) {
		this.idCargoAuxiliar = idCargoAuxiliar;
	}

	public String getIdEmpresaAuxiliar() {
		return this.idEmpresaAuxiliar;
	}

	public void setIdEmpresaAuxiliar(String idEmpresaAuxiliar) {
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
	}

	public String getNomina() {
		return this.nomina;
	}

	public void setNomina(String nomina) {
		this.nomina = nomina;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}