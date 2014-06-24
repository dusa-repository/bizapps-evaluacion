package servicio.reportes;

import interfacedao.reportes.IReporteDAO;

import java.util.List;
import java.util.Map;

import modelo.reportes.BeanDataGeneralCsv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.chart.model.CategoryModel;

@Service
public class SReporte {

	@Autowired
	private IReporteDAO servicioReporteDAO;

	@SuppressWarnings("unchecked")
	public CategoryModel getDataResumenMacro(Map parametros) {

		CategoryModel datos = servicioReporteDAO
				.getDataResumenMacroPeriodo(parametros);
		
		return datos;

	}
	
	
	@SuppressWarnings("unchecked")
	public CategoryModel getDataCumplimientoObjetivo(Map parametros) {

		CategoryModel datos = servicioReporteDAO
				.getDataCumplimientoObjetivoPeriodo(parametros);
		
		return datos;

	}

	
	@SuppressWarnings("unchecked")
	public CategoryModel getDataResumenGeneralBrecha(Map parametros) {

		CategoryModel datos = servicioReporteDAO
				.getDataResumenGeneralBrechaPeriodo(parametros);
		
		return datos;

	}
	

	@SuppressWarnings("unchecked")
	public CategoryModel getDataEvaluadosBrecha(Map parametros) {

		CategoryModel datos = servicioReporteDAO
				.getDataEvaluadosBrecha(parametros);
		
		return datos;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<BeanDataGeneralCsv> getDataGeneralCsv(Map parametros) {

		List<BeanDataGeneralCsv> datos = servicioReporteDAO
				.getDataGeneralCsv(parametros);
		
		return datos;

	}

	
}
