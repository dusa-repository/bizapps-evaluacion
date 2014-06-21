package servicio.transacciones;


import interfacedao.maestros.ICompetenciaDAO;
import interfacedao.transacciones.IConductaCompetenciaDAO;

import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConductaCompetencia")
public class SConductaCompetencia {

	@Autowired
	private IConductaCompetenciaDAO conductaCompetenciaDAO;

	public List<ConductaCompetencia> buscarConductaCompetencias(int id) {
		return conductaCompetenciaDAO.findByIdCompetencia(id);
	}
	
	

}
