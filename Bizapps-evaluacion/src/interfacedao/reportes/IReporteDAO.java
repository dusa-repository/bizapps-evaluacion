package interfacedao.reportes;

import java.util.List;
import java.util.Map;

import modelo.reportes.BeanDataGeneralCsv;

public interface IReporteDAO {
	
	public abstract List<BeanDataGeneralCsv> getDataGeneralCsv(Map<String, String> parametros);
	
	

}
