package servicio.maestros;

import interfacedao.maestros.IRevisionDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Dominio;
import modelo.maestros.Periodo;
import modelo.maestros.Revision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRevision")
public class SRevision {

	@Autowired
	private IRevisionDAO revisionDAO;
	
	private String[] estadoRevision = { "ACTIVO", "INACTIVO" };

	/* Servicio que permite guardar los datos de una revision*/
	public void guardar(Revision revision) {
		revisionDAO.save(revision);
	}

	/* Servicio que permite buscar una revision de acuerdo al id */
	public Revision buscarRevision(int id) {
		return revisionDAO.findOne(id);
	}
	
	/* Servicio que permite buscar una revision de acuerdo al nombre */
	public Revision buscarPorNombre(String descripcion) {
		Revision revision;
		revision = revisionDAO.findByDescripcion(descripcion);
		return revision;
	}
	
	/* Servicio que permite buscar todas las revisiones */
	public List<Revision> buscarTodas() {
		return revisionDAO.findAll();
	}
	
	
	/* Servicio que permite buscar la revision de acuerdo a un estado */
	public Revision buscarPorEstado(String estado) {
		Revision revisiones;
		revisiones = revisionDAO.findByEstadoRevision(estado);
		return revisiones;
	}
	
	/* Servicio que permite buscar la revision de acuerdo a un estado y un periodo*/
	public Revision buscarRevisionPorPeriodo(Periodo periodo) {
		Revision revision;
		revision = revisionDAO.findByPeriodoAndEstadoRevision(periodo, estadoRevision[0]);
		return revision;

	}
	
	
	/* Servicio que permite buscar la revision de acuerdo a un estado y un periodo*/
	public Revision buscarRevisionActiva() {
		Revision revision;
		revision = revisionDAO.findByEstadoRevision(estadoRevision[0]);
		return revision;

	}
	
	
	/* Servicio que permite eliminar una revision */
	public void eliminarUnaRevision(int id) {
		revisionDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias revisiones */
	public void eliminarVariasRevisiones(List<Revision> eliminar) {
		revisionDAO.delete(eliminar);
	}
	
	
	/*
	 * Servicio que permite filtrar las revisiones de una lista de acuerdo al
	 * id
	 */
	public List<Revision> filtroId(String valor) {
		return revisionDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	
	/*
	 * Servicio que permite filtrar las revisiones de una lista de acuerdo al
	 * periodo
	 */
	public List<Revision> filtroPeriodo(String valor) {
		return revisionDAO.findByPeriodoDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las revisiones de una lista de acuerdo al
	 * periodo
	 */
	public List<Revision> filtroDescripcion(String valor) {
		return revisionDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las revisiones de una lista de acuerdo al
	 * estado
	 */
	public List<Revision> filtroEstado(String valor) {
		return revisionDAO.findByEstadoRevisionStartingWithAllIgnoreCase(valor);
	}
	
	
	
	

}