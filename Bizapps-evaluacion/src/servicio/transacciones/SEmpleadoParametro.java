package servicio.transacciones;


import interfacedao.transacciones.IEmpleadoCursoDAO;
import interfacedao.transacciones.IEmpleadoParametroDAO;
import interfacedao.transacciones.IEvaluacionDAO;
import interfacedao.transacciones.INivelCompetenciaCargoDAO;

import java.util.List;

import modelo.maestros.EmpleadoParametro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpleadoParametro")
public class SEmpleadoParametro {

	@Autowired
	private IEmpleadoParametroDAO empleadoParametroDAO;

	/* Servicio que permite guardar los datos de los empleados de acuerdo a un curso*/
	public void guardar(EmpleadoParametro empleado) {
		empleadoParametroDAO.save(empleado);
	}
	
	
}