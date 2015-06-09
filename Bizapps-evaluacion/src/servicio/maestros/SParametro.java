package servicio.maestros;

import interfacedao.maestros.IParametroDAO;

import java.util.List;

import modelo.maestros.Parametro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SParametro")
public class SParametro {

	@Autowired
	private IParametroDAO parametroDAO;

	/* Servicio que permite guardar los datos de un parametro */
	public void guardar(Parametro parametro) {
		parametroDAO.save(parametro);
	}

	/* Servicio que permite buscar un parametro de acuerdo al id */
	public Parametro buscarParametro(int id) {
		return parametroDAO.findOne(id);
	}

	/* Servicio que permite buscar un parametro de acuerdo al nombre */
	public Parametro buscarPorNombre(String descripcion) {
		Parametro parametro;
		parametro = parametroDAO.findByDescripcion(descripcion);
		return parametro;
	}
	
	/* Servicio que permite buscar un parametro de acuerdo al tipo */
	public List<Parametro> buscarPorTipo(String tipo) {
		List<Parametro> parametros;
		parametros = parametroDAO.findByTipo(tipo);
		return parametros;
	}

	/* Servicio que permite buscar todos los parametros */
	public List<Parametro> buscarTodos() {
		return parametroDAO.findAll();
	}

	/* Servicio que permite eliminar un parametro */
	public void eliminarUnParametro(int id) {
		parametroDAO.delete(id);
	}

	/* Servicio que permite eliminar varios parametros */
	public void eliminarVariosParametros(List<Parametro> eliminar) {
		parametroDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar los parametros de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Parametro> filtroNombre(String valor) {
		return parametroDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los parametros de una lista de acuerdo al
	 * tipo
	 */
	public List<Parametro> filtroTipo(String valor) {
		return parametroDAO.findByTipoStartingWithAllIgnoreCase(valor);
	}

}
