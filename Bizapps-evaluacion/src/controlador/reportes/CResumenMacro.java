package controlador.reportes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.Empresa;
import modelo.maestros.Gerencia;
import modelo.maestros.Periodo;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import controlador.maestros.CGenerico;


public class CResumenMacro extends CGenerico {

    @Wire
    Charts chart;
    
    
    @Wire
	private Combobox cmbEmpresa;
    @Wire
	private Combobox cmbGerencia;
    @Wire
	private Combobox cmbPeriodo;
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
    	
    	chart.setTitle("Resultados de Desempeño / Resumen Macro");
    	String subtitulo = "Empresa: "+ cmbEmpresa.getSelectedItem().getLabel() +"/  Gerencia: " + cmbGerencia.getSelectedItem().getLabel() + "/  Periodo Actual: " + cmbPeriodo.getSelectedItem().getLabel() + " " ;
    	chart.setSubtitle(subtitulo);
    	
    	Map parametros = new HashMap();
		parametros.put("gerencia", cmbGerencia.getSelectedItem().getId().substring(0, cmbGerencia.getSelectedItem().getId().length()-1));

    	 chart.setModel(servicioReporte.getDataResumenMacro(parametros));
         
    	 chart.getXAxis().setMin(0);
         chart.getXAxis().getTitle().setText("Valoracion");
         
         chart.getYAxis().setMin(0);
         chart.getYAxis().getTitle().setText("Nro Evaluados");
         
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
		comboEmpresa();
		comboGerencia();
		comboPeriodo();
	}
	
	
	private void comboEmpresa() {
		List<Empresa> empresas = servicioEmpresa.buscarTodas();
		Empresa empresaAuxiliar = new Empresa();
		empresaAuxiliar.setId(0);
		empresaAuxiliar.setNombre("TODAS");
		empresas.add(empresaAuxiliar);
		cmbEmpresa.setModel(new ListModelList<Empresa>(empresas));
	}
	
	private void comboGerencia() {
		List<Gerencia> gerencias = servicioGerencia.buscarTodas();
		Gerencia gerenciaAuxiliar = new Gerencia();
		gerenciaAuxiliar.setId(0);
		gerenciaAuxiliar.setDescripcion("TODAS");
		gerencias.add(gerenciaAuxiliar);
		cmbGerencia.setModel(new ListModelList<Gerencia>(gerencias));
	}
	
	private void comboPeriodo() {
		List<Periodo> periodos = servicioPeriodo.buscarTodos();
		/*Periodo periodoAuxiliar = new Periodo();
		periodoAuxiliar.setId(0);
		periodoAuxiliar.setDescripcion("TODAS");
		periodos.add(periodoAuxiliar);*/
		cmbPeriodo.setModel(new ListModelList<Periodo>(periodos));
		cmbPeriodoComparar.setModel(new ListModelList<Periodo>(periodos));
	}
	
	
	
    
}