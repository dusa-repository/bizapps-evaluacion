package servicio.transacciones;


import interfacedao.transacciones.IPerspectivaDAO;
import interfacedao.transacciones.IUnidadMedidaDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelos.Empleado;
import modelos.Perspectiva;
import modelos.UnidadMedida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUnidadMedida")
public class SUnidadMedida {

	@Autowired
	private IUnidadMedidaDAO unidadMedidaDAO;

	public List<UnidadMedida> buscar() {
		return unidadMedidaDAO.findAll();
	}
}
