package interfacedao.reportes;

import java.util.List;
import java.util.Map;

import org.zkoss.chart.model.CategoryModel;

public interface IReporteDAO {

	public abstract CategoryModel getDataResumenMacro(Map<String, String> parametros);
	public abstract CategoryModel getDataCumplimientoObjetivo(Map<String, String> parametros);
	public abstract CategoryModel getDataResumenGeneralBrecha(Map<String, String> parametros);

}
