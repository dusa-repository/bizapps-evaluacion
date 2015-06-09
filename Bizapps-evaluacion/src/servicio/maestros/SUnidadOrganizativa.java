package servicio.maestros;

import interfacedao.maestros.IUnidadOrganizativaDAO;

import java.util.List;

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
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * id
	 */
	public List<UnidadOrganizativa> filtroId(String valor) {
		return unidadOrganizativaDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * codigo de gerencia
	 */
	public List<UnidadOrganizativa> filtroDescripcion(String valor) {
		return unidadOrganizativaDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * codigo de gerencia
	 */
	public List<UnidadOrganizativa> filtroGerencia(String valor) {
		return unidadOrganizativaDAO.findByGerenciaDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * codigo de gerencia
	 */
	public List<UnidadOrganizativa> filtroNivel(String valor) {
		return unidadOrganizativaDAO.findByNivelStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * codigo de gerencia
	 */
	public List<UnidadOrganizativa> filtroSubNivel(String valor) {
		return unidadOrganizativaDAO.findBySubNivelStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * codigo de gerencia
	 */
	public List<UnidadOrganizativa> filtroEmpresaAuxiliar(String valor) {
		return unidadOrganizativaDAO.findByIdEmpresaAuxiliarStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las unidades organizativas de una lista de acuerdo al
	 * codigo de gerencia
	 */
	public List<UnidadOrganizativa> filtroUnidadAuxiliar(String valor) {
		return unidadOrganizativaDAO.findByIdUnidadOrganizativaAuxiliarStartingWithAllIgnoreCase(valor);
	}
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public List<UnidadOrganizativa> buscarPorNombres(String descripcion) {
		List<UnidadOrganizativa> unidad;
		unidad = unidadOrganizativaDAO.findByDescripcionAllIgnoreCase(descripcion);
		return unidad;
	}


}
