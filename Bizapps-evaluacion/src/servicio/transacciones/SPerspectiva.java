package servicio.transacciones;


import interfacedao.transacciones.IPerspectivaDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelos.Empleado;
import modelos.Perspectiva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPerspectiva")
public class SPerspectiva {

	@Autowired
	private IPerspectivaDAO perspectivaDAO;

	public List<Perspectiva> buscar() {
		return perspectivaDAO.findAll();
	}
}
