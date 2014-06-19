package servicio.maestros;

import interfacedao.maestros.IPeriodoDAO;

import java.util.List;

import modelos.Area;
import modelos.Periodo;
import modelos.Revision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPeriodo")
public class SPeriodo {

	@Autowired
	private IPeriodoDAO periodoDAO;

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
	
	

	public String  buscarPorEstado(String estado) {
		return  periodoDAO.findByEstadoPeriodo(estado);
	
	}
	
	
	/* Servicio que permite eliminar un periodo */
	public void eliminarUnPeriodo(int id) {
		periodoDAO.delete(id);
	}
	
	
	/* Servicio que permite eliminar varios periodos */
	public void eliminarVariosPeriodos(List<Periodo> eliminar) {
		periodoDAO.delete(eliminar);
	}
	
	
	

}