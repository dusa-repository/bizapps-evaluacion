package servicio.transacciones;

import interfacedao.transacciones.IActividadCursoDAO;
import interfacedao.transacciones.IEmpleadoClaseDAO;

import java.util.List;


import modelo.maestros.ActividadCurso;
import modelo.maestros.Curso;
import modelo.maestros.EmpleadoCurso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SActividadCurso")
public class SActividadCurso {

	@Autowired
	private IActividadCursoDAO actividadCursoDAO;

	/* Servicio que permite guardar los datos de las actividades de acuerdo a una curso*/
	public void guardar(ActividadCurso actividad) {
		actividadCursoDAO.save(actividad);
	}
	
	public List<ActividadCurso> buscar(Curso curso) {
		return actividadCursoDAO.findByCurso(curso);
	}
	
	
}
