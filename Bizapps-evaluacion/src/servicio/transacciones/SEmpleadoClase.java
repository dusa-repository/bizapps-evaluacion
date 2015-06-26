package servicio.transacciones;

import interfacedao.transacciones.IEmpleadoClaseDAO;

import java.util.List;

import modelo.maestros.Clase;
import modelo.maestros.EmpleadoClase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleadoClase")
public class SEmpleadoClase {

	@Autowired
	private IEmpleadoClaseDAO empleadoClaseDAO;

	/* Servicio que permite guardar los datos de los empleados de acuerdo a una clase*/
	public void guardar(EmpleadoClase empleado) {
		empleadoClaseDAO.save(empleado);
	}
	
	public List<EmpleadoClase> buscar(Clase clase) {
		return empleadoClaseDAO.findByIdClase(clase);
	}
	
	
}
