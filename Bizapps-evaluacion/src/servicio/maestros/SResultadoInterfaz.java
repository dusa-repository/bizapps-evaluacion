package servicio.maestros;


import interfacedao.maestros.IErrorInterfazDAO;

import java.util.List;

import modelo.maestros.ErrorInterfaz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SResultadoInterfaz")
public class SResultadoInterfaz {

	@Autowired
	private IErrorInterfazDAO errorInterfazDAO;


	/* Servicio que permite buscar un area de acuerdo al nombre */
	public List<ErrorInterfaz> buscar() {
		return errorInterfazDAO.buscar();
	}
	
	
}
