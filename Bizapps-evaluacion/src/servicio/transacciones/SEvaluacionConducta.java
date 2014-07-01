package servicio.transacciones;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;
import interfacedao.transacciones.IEvaluacionConductaDAO;
import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionConducta;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.TipoFormacion;

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

}