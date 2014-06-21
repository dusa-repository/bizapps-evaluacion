package servicio.transacciones;


import interfacedao.transacciones.IEvaluacionDAO;
import interfacedao.transacciones.INivelCompetenciaCargoDAO;

import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.seguridad.Arbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SNivelCompetenciaCargo")
public class SNivelCompetenciaCargo {

	@Autowired
	private INivelCompetenciaCargoDAO nivelCompetenciaCargoDAO;

	public List<NivelCompetenciaCargo> buscar(Cargo cargo) {
		return nivelCompetenciaCargoDAO.findByCargo(cargo);
	}
	public NivelCompetenciaCargo buscarPorDominio(Dominio dominio) {
		return nivelCompetenciaCargoDAO.findByDominio(dominio);
	}
}
