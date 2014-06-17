package controlador.reportes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.chart.Charts;
import org.zkoss.chart.Legend;
import org.zkoss.chart.Tooltip;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import controlador.maestros.CGenerico;


public class CResumenGeneralBrecha extends CGenerico {

    @Wire
    Charts chart;
    
    
    @Wire
	private Combobox cmbEmpresa;
    @Wire
	private Combobox cmbGerencia;
    @Wire
	private Combobox cmbPeriodoActual;
    @Wire
	private Combobox cmbPeriodoComparar;
    @Wire
	private Button btnGenerar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
    
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
    }
    
    
    @Listen("onClick = #btnGenerar")
	public void generarReporte() throws Exception {
    	
    	chart.setTitle("Resultados de Desempeño / Resumen General de Brechas");
    	String subtitulo = "Empresa: "+ cmbEmpresa.getSelectedItem().getLabel() +"/  Gerencia: " + cmbGerencia.getSelectedItem().getLabel() + "/  Periodo Actual: " + cmbPeriodoActual.getSelectedItem().getLabel() + " " ;
    	chart.setSubtitle(subtitulo);
    
    	 chart.setModel(servicioReporte.getDataResumenGeneralBrecha(new HashMap<>()));
         
    	 chart.getXAxis().setMin(0);
         chart.getXAxis().getTitle().setText("Brechas");
         
         chart.getYAxis().setMin(0);
         chart.getYAxis().getTitle().setText("Nro Personas");
         
         Tooltip tooltip = chart.getTooltip();
         tooltip.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
         tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>"
             + "<td style=\"padding:0\"><b>{point.y:.1f} </b></td></tr>");
         tooltip.setFooterFormat("</table>");
         tooltip.setShared(true);
         tooltip.setUseHTML(true);
         
         chart.getLegend().setEnabled(true);
         
         chart.getPlotOptions().getSeries().setBorderWidth(0);
         chart.getPlotOptions().getSeries().getDataLabels().setEnabled(true);
         //chart.getPlotOptions().getSeries().getDataLabels().setFormat("{point.y:.2f}%");
         
         chart.getPlotOptions().getColumn().setPointPadding(0.2);
         chart.getPlotOptions().getColumn().setBorderWidth(0);
         
        
    	
		
	}


	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
	}
    
}