package servicio.transacciones;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;
import interfacedao.transacciones.IEvaluacionIndicadorDAO;
import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelos.EvaluacionIndicador;
import modelos.EvaluacionObjetivo;
import modelos.TipoFormacion;
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


}