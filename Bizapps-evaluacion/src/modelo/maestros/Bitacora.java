package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;


/**
 * The persistent class for the evaluacion database table.
 * 
 */
@Entity
@Table(name="bitacora")
public class Bitacora implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bitacora", unique = true, nullable = false)
	private long idBitacora;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evaluacion")
	private Evaluacion evaluacion;

	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario idUsuario;
	
	@Column(name="estado_evaluacion")
	private String estadoEvaluacion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	public Bitacora() {
	}
	

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public long getIdBitacora() {
		return idBitacora;
	}


	public void setIdBitacora(long idBitacora) {
		this.idBitacora = idBitacora;
	}


	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEstadoEvaluacion() {
		return this.estadoEvaluacion;
	}

	public void setEstadoEvaluacion(String estadoEvaluacion) {
		this.estadoEvaluacion = estadoEvaluacion;
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
	
}