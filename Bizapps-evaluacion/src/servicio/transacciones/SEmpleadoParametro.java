package servicio.transacciones;

import interfacedao.transacciones.IEmpleadoParametroDAO;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoParametro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleadoParametro")
public class SEmpleadoParametro {

	@Autowired
	private IEmpleadoParametroDAO empleadoParametroDAO;

	/*
	 * Servicio que permite guardar los datos de los parametros evaluados por un
	 * empleado
	 */
	public void guardar(EmpleadoParametro empleado) {
		empleadoParametroDAO.save(empleado);
	}

	/*
	 * Servicio que permite guardar los datos de los parametros evaluados por un
	 * empleado
	 */
	public List<EmpleadoParametro> buscarParametros(Empleado empleado, Curso curso) {
		List<EmpleadoParametro> parametros;
		parametros = empleadoParametroDAO.findByEmpleadoAndCurso(empleado, curso);
		return parametros;
	}

}