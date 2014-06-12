package servicio.transacciones;



import interfacedao.transacciones.IEvaluacionDAO;

import java.util.List;
import modelos.Evaluacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacion")
public class SEvaluacion {

	@Autowired
	private IEvaluacionDAO evaluacionDAO;

	public List<Evaluacion> buscar(String ficha) {
		return evaluacionDAO.findByFicha(ficha);
	}
}
