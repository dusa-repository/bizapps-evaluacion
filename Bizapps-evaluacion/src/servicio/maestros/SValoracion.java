package servicio.maestros;

import interfacedao.maestros.IAreaDAO;
import interfacedao.maestros.IValoracionDAO;

import java.util.List;

import modelo.maestros.Valoracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SValoracion")
public class SValoracion {

	@Autowired
	private IValoracionDAO valoracionDAO;

	/* Servicio que permite guardar los datos de una valoracion */
	public void guardar(Valoracion valoracion) {
		valoracionDAO.save(valoracion);
	}

	/* Servicio que permite buscar una valoracion de acuerdo al id */
	public Valoracion buscarValoracion(int id) {
		return valoracionDAO.findOne(id);
	}

	/* Servicio que permite buscar una valoracion de acuerdo al nombre */
	public Valoracion buscarPorNombre(String nombre) {
		Valoracion valoracion;
		valoracion = valoracionDAO.findByNombre(nombre);
		return valoracion;
	}

	/* Servicio que permite buscar todas las valoraciones */
	public List<Valoracion> buscarTodas() {
		return valoracionDAO.findAll();
	}

	/* Servicio que permite eliminar una valoracion */
	public void eliminarUnaValoracion(int id) {
		valoracionDAO.delete(id);
	}

	/* Servicio que permite eliminar varias areas */
	public void eliminarVariasValoraciones(List<Valoracion> eliminar) {
		valoracionDAO.delete(eliminar);
	}
	
	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo al
	 * id
	 */
	public List<Valoracion> filtroId(String valor) {
		return valoracionDAO.findByIdStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo al
	 * nombre
	 */
	public List<Valoracion> filtroNombre(String valor) {
		return valoracionDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Valoracion> filtroDescripcion(String valor) {
		return valoracionDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo al
	 * orden
	 */
	public List<Valoracion> filtroOrden(String valor) {
		return valoracionDAO.findByOrdenStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo al
	 * rango inferior
	 */
	public List<Valoracion> filtroRangoInferior(String valor) {
		return valoracionDAO.findByRangoInferiorStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo al
	 * rango superior
	 */
	public List<Valoracion> filtroRangoSuperior(String valor) {
		return valoracionDAO.findByRangoSuperiorStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las valoraciones de una lista de acuerdo al
	 * rango superior
	 */
	public List<Valoracion> filtroValor(String valor) {
		return valoracionDAO.findByValorStartingWithAllIgnoreCase(valor);
	}
	
	
}

