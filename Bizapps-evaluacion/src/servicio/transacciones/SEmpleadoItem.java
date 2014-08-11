package servicio.transacciones;

import interfacedao.transacciones.IEmpleadoItemDAO;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleadoItem")
public class SEmpleadoItem {

	@Autowired
	private IEmpleadoItemDAO empleadoItemDAO;

	/*
	 * Servicio que permite guardar los datos de los items evaluados por un
	 * empleado
	 */
	public void guardar(EmpleadoItem empleado) {
		empleadoItemDAO.save(empleado);
	}

	/*
	 * Servicio que permite guardar los datos de los items evaluados por un
	 * empleado
	 */
	public List<EmpleadoItem> buscarItems(Empleado empleado, Curso curso) {
		List<EmpleadoItem> items;
		items = empleadoItemDAO.findByEmpleadoAndCurso(empleado, curso);
		return items;
	}

}
