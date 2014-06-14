package servicio.transacciones;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;
import interfacedao.transacciones.IEvaluacionCompetenciaDAO;
import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelos.EvaluacionCompetencia;
import modelos.EvaluacionObjetivo;
import modelos.TipoFormacion;
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
	
	
}