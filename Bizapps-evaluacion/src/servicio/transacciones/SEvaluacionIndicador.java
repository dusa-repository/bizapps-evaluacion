package servicio.transacciones;

import interfacedao.transacciones.IEvaluacionIndicadorDAO;

import java.util.List;

import modelo.maestros.EvaluacionIndicador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacionIndicador")
public class SEvaluacionIndicador {

	@Autowired
	private IEvaluacionIndicadorDAO evaluacionIndicadorDAO;

	/* Servicio que permite guardar los datos de la evaluacion de los objetivos de un trabajador*/
	public void guardar(EvaluacionIndicador indicador) {
		evaluacionIndicadorDAO.save(indicador);
	}

	public List<EvaluacionIndicador> buscarIndicadores (Integer idObjetivo) {
		return evaluacionIndicadorDAO.findByIdObjetivo(idObjetivo); 
	}
	
	public EvaluacionIndicador buscarIndicadorId (Integer idIndicador) {
		return evaluacionIndicadorDAO.findByIdIndicador(idIndicador);
	}
	
	public void eliminarUno(Integer id) {
		evaluacionIndicadorDAO.delete(id);
	}
	
	public void eliminarVarios(List<EvaluacionIndicador> evaluacionIndicador) {
		evaluacionIndicadorDAO.delete(evaluacionIndicador);
	}
	
	// Servicio que busca el maximo id
		public Integer buscarId() {
			return evaluacionIndicadorDAO.buscar();
		}
		
}