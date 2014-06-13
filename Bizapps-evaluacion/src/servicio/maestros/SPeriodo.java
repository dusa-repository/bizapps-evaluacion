package servicio.maestros;

import interfacedao.maestros.IPeriodoDAO;

import java.util.List;

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
	
	
//	/* Servicio que permite buscar los periodos de acuerdo a un estado */
//	public List<Periodo> buscarPorEstado(String estado) {
//		List<Periodo> periodos;
//		periodos = periodoDAO.findByEstadoPeriodo(estado);
//		return periodos;
//	}
	
	public String  buscarPorEstado(String estado) {
		return  periodoDAO.findByEstadoPeriodo(estado);
	
	}
	
	
	/* Servicio que permite eliminar un periodo */
	public void eliminarUnPeriodo(int id) {
		periodoDAO.delete(id);
	}
	
	
	
	

}