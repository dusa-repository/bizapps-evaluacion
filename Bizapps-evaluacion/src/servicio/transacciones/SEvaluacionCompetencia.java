package servicio.transacciones;

import interfacedao.transacciones.IEvaluacionCompetenciaDAO;

import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionCompetencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacionCompetencia")
public class SEvaluacionCompetencia {

	@Autowired
	private IEvaluacionCompetenciaDAO evaluacionCompetenciaDAO;

	/* Servicio que permite guardar los datos de la evaluacion de los las competencia de un trabajador*/
	public void guardar(EvaluacionCompetencia competencia) {
		evaluacionCompetenciaDAO.save(competencia);
	}
	
	public EvaluacionCompetencia buscar (Evaluacion evaluacion, Competencia competencia){
		return evaluacionCompetenciaDAO.findByIdEvaluacionAndIdCompetencia(evaluacion, competencia);
	}
	
	public List<EvaluacionCompetencia> buscar (Evaluacion evaluacion){
		return  evaluacionCompetenciaDAO.findByIdEvaluacion(evaluacion);
	}

}