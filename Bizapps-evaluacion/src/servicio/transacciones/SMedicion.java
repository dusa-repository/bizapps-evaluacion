package servicio.transacciones;


import interfacedao.transacciones.IMedicionDAO;
import interfacedao.transacciones.IPerspectivaDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelos.Empleado;
import modelos.Medicion;
import modelos.Perspectiva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SMedicion")
public class SMedicion {

	@Autowired
	private IMedicionDAO medicionDAO;

	public List<Medicion> buscar() {
		return medicionDAO.findAll();
	}
}
