package servicio.transacciones;


import interfacedao.transacciones.INivelCompetenciaCargoDAO;

import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.NivelCompetenciaCargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SNivelCompetenciaCargo")
public class SNivelCompetenciaCargo {

	@Autowired
	private INivelCompetenciaCargoDAO nivelCompetenciaCargoDAO;

	/* Servicio que permite guardar los datos de un nivel de competencia por cargo*/
	public void guardar(NivelCompetenciaCargo nivel) {
		nivelCompetenciaCargoDAO.save(nivel);
	}
	
	public void eliminar(NivelCompetenciaCargo nivel) {
		nivelCompetenciaCargoDAO.delete(nivel);
	}

	
	public void eliminarVarias(List<NivelCompetenciaCargo> lista) {
		nivelCompetenciaCargoDAO.delete(lista);
	}

	
	public List<NivelCompetenciaCargo> buscar(Cargo cargo) {
		return nivelCompetenciaCargoDAO.findByIdCargo(cargo);
	}
	public NivelCompetenciaCargo buscarPorDominio(Dominio dominio) {
		return nivelCompetenciaCargoDAO.findByIdDominio(dominio);
	}
}
