package servicio.maestros;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.IUrgenciaDAO;

import java.util.List;

import modelos.Area;
import modelos.Urgencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUrgencia")
public class SUrgencia {

	@Autowired
	private IUrgenciaDAO urgenciaDAO;

	/* Servicio que permite guardar los datos de la urgencia*/
	public void guardar(Urgencia urgencia) {
		urgenciaDAO.save(urgencia);
	}

	/* Servicio que permite buscar una urgencia de acuerdo al id */
	public Urgencia buscarUrgencia(int id) {
		return urgenciaDAO.findOne(id);
	}
	
	/* Servicio que permite buscar una urgencia de acuerdo al nombre */
	public Urgencia buscarPorNombre(String descripcion) {
		Urgencia urgencia;
		urgencia = urgenciaDAO.findByDescripcionUrgencia(descripcion);
		return urgencia;
	}
	
	/* Servicio que permite buscar todas las urgencias */
	public List<Urgencia> buscarTodas() {
		return urgenciaDAO.findAll();
	}
	
	/* Servicio que permite eliminar una urgencia */
	public void eliminarUnaUrgencia(int id) {
		urgenciaDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias urgencias */
	public void eliminarVariasUrgencias(List<Urgencia> eliminar) {
		urgenciaDAO.delete(eliminar);
	}
	

}