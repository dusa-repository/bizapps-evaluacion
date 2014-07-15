package servicio.transacciones;


import interfacedao.transacciones.IEmpleadoCursoDAO;
import interfacedao.transacciones.IEvaluacionDAO;
import interfacedao.transacciones.INivelCompetenciaCargoDAO;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.EmpleadoCurso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleadoCurso")
public class SEmpleadoCurso {

	@Autowired
	private IEmpleadoCursoDAO empleadoCursoDAO;

	/* Servicio que permite guardar los datos de los empleados de acuerdo a un curso*/
	public void guardar(EmpleadoCurso empleado) {
		empleadoCursoDAO.save(empleado);
	}
	
	public List<EmpleadoCurso> buscar(Curso curso) {
		return empleadoCursoDAO.findByCurso(curso);
	}
	
}