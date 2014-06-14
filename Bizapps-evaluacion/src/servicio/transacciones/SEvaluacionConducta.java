package servicio.transacciones;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;
import interfacedao.transacciones.IEvaluacionConductaDAO;
import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelos.EvaluacionConducta;
import modelos.EvaluacionObjetivo;
import modelos.TipoFormacion;
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
}