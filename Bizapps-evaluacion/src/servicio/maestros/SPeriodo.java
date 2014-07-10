package servicio.maestros;

import interfacedao.maestros.IPeriodoDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.Periodo;
import modelo.maestros.Revision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPeriodo")
public class SPeriodo {

	@Autowired
	private IPeriodoDAO periodoDAO;
	
	private String[] estadoPeriodo = { "ACTIVO", "INACTIVO" };

	/* Servicio que permite guardar los datos de un cargo*/
	public void guardar(Periodo periodo) {
		periodoDAO.save(periodo);
	}

	/* Servicio que permite buscar un periodo de acuerdo al id */
	public Periodo buscarPeriodo(int id) {
		return periodoDAO.findOne(id);
	}
	
	/* Servicio que permite buscar un periodo de acuerdo al nombre */
	public Periodo buscarPorNombre(String nombre) {
		Periodo periodo;
		periodo = periodoDAO.findByNombre(nombre);
		return periodo;
	}
	
	/* Servicio que permite buscar todos los periodos */
	public List<Periodo> buscarTodos() {
		return periodoDAO.findAll();
	}
	
	
	/* Servicio que permite buscar un periodo activo */
	public Periodo buscarPeriodoActivo() {
		Periodo periodo;
		periodo = periodoDAO.findByEstadoPeriodo(estadoPeriodo[0]);
		return periodo;
	}

	/* Servicio que permite eliminar un periodo */
	public void eliminarUnPeriodo(int id) {
		periodoDAO.delete(id);
	}
	
	
	/* Servicio que permite eliminar varios periodos */
	public void eliminarVariosPeriodos(List<Periodo> eliminar) {
		periodoDAO.delete(eliminar);
	}
	
	/*
	 * Servicio que permite filtrar los periodos de una lista de acuerdo al
	 * id
	 */
	public List<Periodo> filtroId(String valor) {
		return periodoDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar los periodos de una lista de acuerdo al
	 * nombre
	 */
	public List<Periodo> filtroNombre(String valor) {
		return periodoDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar los periodos de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Periodo> filtroDescripcion(String valor) {
		return periodoDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar los periodos de una lista de acuerdo a la
	 * fecha de inicio
	 */
	public List<Periodo> filtroFechaInicio(String valor) {
		return periodoDAO.findByFechaInicioStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar los periodos de una lista de acuerdo a la
	 * fecha fin
	 */
	public List<Periodo> filtroFechaFin(String valor) {
		return periodoDAO.findByFechaFinStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar los periodos de una lista de acuerdo al
	 * estado
	 */
	public List<Periodo> filtroEstado(String valor) {
		return periodoDAO.findByEstadoPeriodoStartingWithAllIgnoreCase(valor);
	}
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public List<Periodo> buscarPorNombres(String nombre) {
		List<Periodo> periodos;
		periodos = periodoDAO.findByNombreAllIgnoreCase(nombre);
		return periodos;
	}


	
	
	

}