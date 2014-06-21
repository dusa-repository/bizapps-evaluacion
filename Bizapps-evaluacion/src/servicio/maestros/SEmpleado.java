package servicio.maestros;



import interfacedao.maestros.IEmpleadoDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Empleado;
import modelo.seguridad.Arbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleado")
public class SEmpleado {

	@Autowired
	private IEmpleadoDAO empleadoDAO;

	/* Servicio que permite guardar los datos de un empleado*/
	public void guardar(Empleado empleado) {
		empleadoDAO.save(empleado);
	}
	
	/* Servicio que permite buscar todos los empleados */
	public List<Empleado> buscarTodos() {
		return empleadoDAO.findAll();
	}
	
	public Empleado buscar(int id) {

		return empleadoDAO.findOne(id);
	}


	public Empleado buscarPorNombre(String nombre) {
		Empleado empleado;
		empleado = empleadoDAO.findByNombre(nombre);
		return empleado;
	}
	
	public Empleado buscarPorId(int integer) {

		Empleado empleado;
		empleado = empleadoDAO.findOne(integer);
		return empleado;
	}


	public Empleado buscarPorFicha(String ficha) {
		return empleadoDAO.findByFicha(ficha);
	}


	public List<Empleado> BuscarPorSupervisor(String ficha) {
		return empleadoDAO.findByFichaSupervisor(ficha);
	}
	
	
	/* Servicio que permite eliminar un empleado */
	public void eliminarUnEmpleado(int id) {
		empleadoDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varios empleados */
	public void eliminarVariosEmpleados(List<Empleado> eliminar) {
		empleadoDAO.delete(eliminar);
	}

}
