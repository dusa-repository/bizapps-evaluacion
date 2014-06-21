package servicio.maestros;

import interfacedao.maestros.IRevisionDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Revision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRevision")
public class SRevision {

	@Autowired
	private IRevisionDAO revisionDAO;

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
	
	
	/* Servicio que permite buscar las revisiones de acuerdo a un estado */
	public List<Revision> buscarPorEstado(String estado) {
		List<Revision> revisiones;
		revisiones = revisionDAO.findByEstadoRevision(estado);
		return revisiones;
	}
	
	/* Servicio que permite eliminar una revision */
	public void eliminarUnaRevision(int id) {
		revisionDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias revisiones */
	public void eliminarVariasRevisiones(List<Revision> eliminar) {
		revisionDAO.delete(eliminar);
	}
	

}