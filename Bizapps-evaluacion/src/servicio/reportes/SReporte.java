package servicio.reportes;

import interfacedao.reportes.IReporteDAO;

import java.util.List;
import java.util.Map;

import modelo.reportes.BeanDataGeneralCsv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SReporte {

	@Autowired
	private IReporteDAO servicioReporteDAO;
	
	@SuppressWarnings("unchecked")
	public List<BeanDataGeneralCsv> getDataGeneralCsv(Map parametros) {

		List<BeanDataGeneralCsv> datos = servicioReporteDAO
				.getDataGeneralCsv(parametros);
		
		return datos;

	}

	
}
