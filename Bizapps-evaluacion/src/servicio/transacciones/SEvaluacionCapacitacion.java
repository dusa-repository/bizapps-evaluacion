package servicio.transacciones;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;
import interfacedao.transacciones.IEvaluacionCapacitacionDAO;
import interfacedao.transacciones.IEvaluacionObjetivoDAO;

import java.util.List;

import modelo.maestros.EvaluacionCapacitacion;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.TipoFormacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEvaluacionCapacitacion")
public class SEvaluacionCapacitacion {

	@Autowired
	private IEvaluacionCapacitacionDAO evaluacionCapacitacionDAO;

	public List<EvaluacionCapacitacion> buscarPorEvaluacion(Integer idEva) {
		return evaluacionCapacitacionDAO.findByIdEvaluacion(idEva);
	}

	public void guardar(EvaluacionCapacitacion capacitacion) {
		evaluacionCapacitacionDAO.save(capacitacion);
		
	}

	public void eliminarUno(int idCapacitacionEliminar) {
		evaluacionCapacitacionDAO.delete(idCapacitacionEliminar);
		
	}

}