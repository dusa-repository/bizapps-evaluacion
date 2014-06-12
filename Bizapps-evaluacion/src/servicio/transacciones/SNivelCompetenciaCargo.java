package servicio.transacciones;


import interfacedao.transacciones.IEvaluacionDAO;
import interfacedao.transacciones.INivelCompetenciaCargoDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelos.Cargo;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.NivelCompetenciaCargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SNivelCompetenciaCargo")
public class SNivelCompetenciaCargo {

	@Autowired
	private INivelCompetenciaCargoDAO nivelCompetenciaCargoDAO;

	public List<NivelCompetenciaCargo> buscar(Cargo cargo) {
		return nivelCompetenciaCargoDAO.findByCargo(cargo);
	}
}
