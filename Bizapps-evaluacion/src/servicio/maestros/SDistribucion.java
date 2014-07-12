package servicio.maestros;

import interfacedao.maestros.IDistribucionDAO;

import java.util.List;

import modelo.maestros.Distribucion;
import modelo.seguridad.Grupo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDistribucion")
public class SDistribucion {

	@Autowired
	private IDistribucionDAO distribucionDAO;

	/* Servicio que permite guardar los datos de un distribucion */
	public void guardar(Distribucion distribucion) {
		distribucionDAO.save(distribucion);
	}

	/* Servicio que permite buscar un distribucion de acuerdo al id */
	public Distribucion buscarDistribucion(int id) {
		return distribucionDAO.findOne(id);
	}

	/* Servicio que permite buscar un distribucion de acuerdo al nombre */
	public Distribucion buscarPorNombre(String descripcion) {
		Distribucion distribucion;
		distribucion = distribucionDAO.findByDescripcion(descripcion);
		return distribucion;
	}

	/* Servicio que permite buscar todos las distribuciones */
	public List<Distribucion> buscarTodas() {
		return distribucionDAO.findAll();
	}

	/* Servicio que permite eliminar un distribucion */
	public void eliminarUnaDistribucion(int id) {
		distribucionDAO.delete(id);
	}

	/* Servicio que permite eliminar varias distribuciones */
	public void eliminarVariasDistribuciones(List<Distribucion> eliminar) {
		distribucionDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar las distribuciones de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Distribucion> filtroNombre(String valor) {
		return distribucionDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las distribuciones de una lista de acuerdo al
	 * porcentaje
	 */
	public List<Distribucion> filtroPorcentaje(String valor) {
		return distribucionDAO.findByPorcentajeStartingWithAllIgnoreCase(valor);
	}
	
	
	
}
