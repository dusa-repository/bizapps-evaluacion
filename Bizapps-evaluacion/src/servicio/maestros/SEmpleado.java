package servicio.maestros;



import interfacedao.maestros.IEmpleadoDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelos.Empleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleado")
public class SEmpleado {

	@Autowired
	private IEmpleadoDAO empleadoDAO;

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

}
