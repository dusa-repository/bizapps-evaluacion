package servicio.transacciones;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;
import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.TipoFormacion;

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
		evaluacionObjetivoDAO.delete(id);
	}
	

	public void eliminarVarios(List<EvaluacionObjetivo> evaluacionObjetivo) {
		evaluacionObjetivoDAO.delete(evaluacionObjetivo);
	}
	
}