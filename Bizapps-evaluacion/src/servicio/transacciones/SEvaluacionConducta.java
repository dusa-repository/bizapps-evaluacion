package servicio.transacciones;

import interfacedao.transacciones.IEvaluacionConductaDAO;

import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionConducta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacionConducta")
public class SEvaluacionConducta {

	@Autowired
	private IEvaluacionConductaDAO evaluacionConductaDAO;

	/* Servicio que permite guardar los datos de la evaluacion de los objetivos de un trabajador*/
	public void guardar(EvaluacionConducta conducta) {
		evaluacionConductaDAO.save(conducta);
	}
	
	public List<EvaluacionConducta> buscarConductas (Integer id){
		return evaluacionConductaDAO.findByEvaluacionIdEvaluacion(id);
	}

	public EvaluacionConducta buscarConducta (Integer id){
		return evaluacionConductaDAO.buscar(id);
	}
	
	public EvaluacionConducta buscar (Evaluacion eva, ConductaCompetencia cc){
		return evaluacionConductaDAO.findByEvaluacionAndConductaCompetencia(eva, cc);
	}
	
	public List<EvaluacionConducta> buscar (Evaluacion eva, Competencia con){
		return evaluacionConductaDAO.findByEvaluacionAndCompetencia(eva, con);
	}
	
	public List<EvaluacionConducta> buscar (Evaluacion eva){
		return evaluacionConductaDAO.findByEvaluacion(eva);
	}
		

}