package servicio.maestros;

import interfacedao.maestros.IUnidadOrganizativaDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.UnidadOrganizativa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUnidadOrganizativa")
public class SUnidadOrganizativa {

	@Autowired
	private IUnidadOrganizativaDAO unidadOrganizativaDAO;

	/* Servicio que permite guardar los datos de una unidad organizativa */
	public void guardar(UnidadOrganizativa unidad) {
		unidadOrganizativaDAO.save(unidad);
	}

	/* Servicio que permite buscar una unidad organizativa de acuerdo al id */
	public UnidadOrganizativa buscarUnidad(int id) {
		return unidadOrganizativaDAO.findOne(id);
	}

	/* Servicio que permite buscar una unidad organizativa de acuerdo al nombre */
	public UnidadOrganizativa buscarPorNombre(String descripcion) {
		UnidadOrganizativa unidad;
		unidad = unidadOrganizativaDAO.findByDescripcion(descripcion);
		return unidad;
	}

	/* Servicio que permite buscar todas las unidades organizativas */
	public List<UnidadOrganizativa> buscarTodas() {
		return unidadOrganizativaDAO.findAll();
	}

	/* Servicio que permite eliminar una unidad organizativa */
	public void eliminarUnaUnidad(int id) {
		unidadOrganizativaDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias unidades organizativas */
	public void eliminarVariasUnidades(List<UnidadOrganizativa> eliminar) {
		unidadOrganizativaDAO.delete(eliminar);
	}

}
