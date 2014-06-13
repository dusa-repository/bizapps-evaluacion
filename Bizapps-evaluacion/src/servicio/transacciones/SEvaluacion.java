package servicio.transacciones;



import interfacedao.transacciones.IEvaluacionDAO;

import java.util.List;
import modelos.Evaluacion;
import modelos.EvaluacionObjetivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacion")
public class SEvaluacion {

	@Autowired
	private IEvaluacionDAO evaluacionDAO;

	public List<Evaluacion> buscar(String ficha) {
		return evaluacionDAO.findByFicha(ficha);
	}
	
	/* Servicio que permite guardar los datos de la evaluacion de un trabajador*/
	public void guardar(Evaluacion evaluacion) {
		evaluacionDAO.save(evaluacion);
	}
	
	public Integer buscarId() {
		return evaluacionDAO.buscar();
	}
}
