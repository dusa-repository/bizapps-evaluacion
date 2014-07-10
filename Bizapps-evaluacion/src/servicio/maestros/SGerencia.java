package servicio.maestros;

import interfacedao.maestros.IGerenciaDAO;

import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.Gerencia;

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
	
	/*
	 * Servicio que permite filtrar las gerencias de una lista de acuerdo al
	 * id
	 */
	public List<Gerencia> filtroId(String valor) {
		return gerenciaDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las gerencias de una lista de acuerdo al
	 * id
	 */
	public List<Gerencia> filtroDescripcion(String valor) {
		return gerenciaDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public List<Gerencia> buscarPorNombres(String descripcion) {
		List<Gerencia> gerencias;
		gerencias = gerenciaDAO.findByDescripcionAllIgnoreCase(descripcion);
		return gerencias;
	}

	

}