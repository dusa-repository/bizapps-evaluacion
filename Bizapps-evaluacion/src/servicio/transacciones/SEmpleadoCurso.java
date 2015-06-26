package servicio.transacciones;

import interfacedao.transacciones.IEmpleadoCursoDAO;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleadoCurso")
public class SEmpleadoCurso {

	@Autowired
	private IEmpleadoCursoDAO empleadoCursoDAO;

	private String[] estado = { "APROBADO", "REPROBADO" , "ASISTIO"};

	/*
	 * Servicio que permite guardar los datos de los empleados de acuerdo a un
	 * curso
	 */
	public void guardar(EmpleadoCurso empleado) {
		empleadoCursoDAO.save(empleado);
	}

	public List<EmpleadoCurso> buscar(Curso curso) {
		return empleadoCursoDAO.findByIdCurso(curso);
	}

	public List<EmpleadoCurso> buscarCursos(Empleado empleado) {
		return empleadoCursoDAO.findByIdEmpleado(empleado);
	}

	public List<EmpleadoCurso> buscarCursosAsistidosYFinalizados(Empleado empleado) {
		return empleadoCursoDAO.findByIdEmpleadoAndAsistioAndEstadoCursoLike(empleado, "SI", "%ADO");
	}

	public EmpleadoCurso buscarPorempleadoYCurso(Empleado empleado, Curso curso) {

		return empleadoCursoDAO.findByIdEmpleadoAndIdCurso(empleado, curso);
	}

	public void eliminar(EmpleadoCurso curso) {
		empleadoCursoDAO.delete(curso);
	}

	/* Servicio que permite buscar todos los DNA */
	public List<EmpleadoCurso> buscarTodos() {
		return empleadoCursoDAO.findAll();
	}

	public void guardarVarios(List<EmpleadoCurso> guardadas) {
		empleadoCursoDAO.save(guardadas);
	}

	public void remover(List<Curso> procesadas, Empleado empleado) {
		List<EmpleadoCurso> lista = empleadoCursoDAO.findByIdEmpleadoAndIdCursoIn(
				empleado, procesadas);
		if (!lista.isEmpty())
			empleadoCursoDAO.delete(lista);
	}

	public Double sumarHoras(Empleado trabajador) {
		return empleadoCursoDAO.sumHours(trabajador);
	}

}