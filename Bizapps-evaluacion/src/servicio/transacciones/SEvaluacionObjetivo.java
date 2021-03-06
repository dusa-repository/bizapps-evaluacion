package servicio.transacciones;

import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelo.maestros.EvaluacionObjetivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacionObjetivo")
public class SEvaluacionObjetivo {

	@Autowired
	private IEvaluacionObjetivoDAO evaluacionObjetivoDAO;

	/* Servicio que permite guardar los datos de la evaluacion de los objetivos de un trabajador*/
	public void guardar(EvaluacionObjetivo objetivo) {
		evaluacionObjetivoDAO.save(objetivo);
	}
	
	public List<EvaluacionObjetivo> buscarObjetivos (String ficha, Integer evaluacion) {
		return evaluacionObjetivoDAO.buscarObjetivos(ficha, evaluacion); 
	}
	
	public List<EvaluacionObjetivo> buscarObjetivosEvaluar (Integer idEvaluacion) {
		return evaluacionObjetivoDAO.findByIdEvaluacion(idEvaluacion); 
	}

	public EvaluacionObjetivo buscarObjetivosId (Integer idObjetivo) {
		return evaluacionObjetivoDAO.findByIdObjetivo(idObjetivo);
	}
	
	public void eliminarUno(Integer id) {
		System.out.println("I"+id);
		evaluacionObjetivoDAO.delete(id);
	}

	// Servicio que busca el maximo id
	public Integer buscarId() {
		return evaluacionObjetivoDAO.buscar();
	}
	

	public void eliminarVarios(List<EvaluacionObjetivo> evaluacionObjetivo) {
		evaluacionObjetivoDAO.delete(evaluacionObjetivo);
	}

	public EvaluacionObjetivo buscarUltimo() {
		Integer in = evaluacionObjetivoDAO.buscar();
		return evaluacionObjetivoDAO.findOne(in);
	}
	
	
}