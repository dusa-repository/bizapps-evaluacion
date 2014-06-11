package servicio.transacciones;


import interfacedao.transacciones.ICompetenciaDAO;
import interfacedao.transacciones.IEmpleadoDAO;
import interfacedao.transacciones.IEvaluacionDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelos.Competencia;
import modelos.Empleado;
import modelos.Evaluacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCompetencia")
public class SCompetencia {

	@Autowired
	private ICompetenciaDAO competenciaDAO;

	public List<Competencia> buscarCompetenciasR() {
		return competenciaDAO.buscar();
	}
	

}
