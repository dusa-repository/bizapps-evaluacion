package servicio.transacciones;

import java.util.Map;

import interfacedao.reportes.IReporteDAO;
import interfacedao.transacciones.IUtilidadDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.chart.model.CategoryModel;

@Service
public class SUtilidad {

	@Autowired
	private IUtilidadDAO servicioUtilidadDAO;
	
	@SuppressWarnings("unchecked")
	public void eliminarConductaPorCompetencia(Integer eva, Integer com)  {

		servicioUtilidadDAO.eliminarConductaPorCompetencia(eva, com);
		
	}
	
	@SuppressWarnings("unchecked")
	public String obtenerValoracionFinal(Integer resultado) {

		return servicioUtilidadDAO.obtenerValoracionFinal(resultado);
		
	}
	
	
	
	
}
