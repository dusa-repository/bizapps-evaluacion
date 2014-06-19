package servicio.maestros;

import interfacedao.maestros.IGerenciaDAO;

import java.util.List;

import modelos.Gerencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SGerencia")
public class SGerencia {

	@Autowired
	private IGerenciaDAO gerenciaDAO;

	/* Servicio que permite guardar los datos de una gerencia*/
	public void guardar(Gerencia gerencia) {
		gerenciaDAO.save(gerencia);
	}

	/* Servicio que permite buscar una gerencia de acuerdo al id */
	public Gerencia buscarGerencia(int id) {
		return gerenciaDAO.findOne(id);
	}
	
	/* Servicio que permite buscar una gerencia de acuerdo al nombre */
	public Gerencia buscarPorNombre(String descripcion) {
		Gerencia gerencia;
		gerencia = gerenciaDAO.findByDescripcion(descripcion);
		return gerencia;
	}
	
	/* Servicio que permite buscar todas las gerencias */
	public List<Gerencia> buscarTodas() {
		return gerenciaDAO.findAll();
	}
	
	/* Servicio que permite eliminar una gerencia */
	public void eliminarUnaGerencia(int id) {
		gerenciaDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias gerencias */
	public void eliminarVariasGerencias(List<Gerencia> eliminar) {
		gerenciaDAO.delete(eliminar);
	}
	

}