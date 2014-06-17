package servicio.reportes;

import interfacedao.reportes.IReporteDAO;

import java.util.Map;

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
				.getDataResumenMacro(parametros);

		/*
		 * if (listaBeanRe0001.size() == 0) {
		 * Messagebox.show(Mensaje.noExistenRegistrosParaEstaConsulta, "ALERTA",
		 * Messagebox.OK, Messagebox.EXCLAMATION); } return (List<BeanRe0001>)
		 * listaBeanRe0001;
		 */

		return datos;

	}
	
	
	@SuppressWarnings("unchecked")
	public CategoryModel getDataCumplimientoObjetivo(Map parametros) {

		CategoryModel datos = servicioReporteDAO
				.getDataCumplimientoObjetivo(parametros);
		
		return datos;

	}

	
	@SuppressWarnings("unchecked")
	public CategoryModel getDataResumenGeneralBrecha(Map parametros) {

		CategoryModel datos = servicioReporteDAO
				.getDataResumenGeneralBrecha(parametros);
		
		return datos;

	}

	
}
