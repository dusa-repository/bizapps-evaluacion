package servicio.transacciones;



import interfacedao.transacciones.IBitacoraDAO;
import interfacedao.transacciones.IEvaluacionDAO;

import java.util.List;

import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionObjetivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacion")
public class SEvaluacion {

	@Autowired
	private IEvaluacionDAO evaluacionDAO;
	
	@Autowired
	private IBitacoraDAO bitacoraDAO;

	public List<Evaluacion> buscar(String ficha) {
		return evaluacionDAO.findByFichaOrderByIdEvaluacionSecundarioDesc(ficha);
	}
	
	public List<Evaluacion> buscarEstado(String ficha) {
		return evaluacionDAO.buscarEstado(ficha);
	}
	
	public List<Evaluacion> buscarPorEstado(String ficha, String estado) {
		return evaluacionDAO.findByFichaAndEstadoEvaluacion(ficha, estado);
	}
	
	/* Servicio que permite guardar los datos de la evaluacion de un trabajador*/
	public void guardar(Evaluacion evaluacion) {
		evaluacionDAO.save(evaluacion);
	}
	
	// Servicio que busca el maximo id
	public Integer buscarId() {
		return evaluacionDAO.buscar();
	}
	
	// Servicio que busca el maximo id Secundario	
		public Integer buscarIdSecundario(String ficha) {
			return evaluacionDAO.buscarIdSecundario(ficha);
		}
	
	// Servicio que busca el id de una evaluacion
	public Evaluacion buscarIdEvaluacion(Integer id, String ficha) {
		return evaluacionDAO.findByIdEvaluacionSecundarioAndFicha(id, ficha);
	}
	
	public Evaluacion buscarEvaluacion (Integer idEvaluacion) {
		return evaluacionDAO.findByIdEvaluacion(idEvaluacion);
	}
	
	public void eliminarUno(Integer id) {
		evaluacionDAO.delete(id);
	}
}
