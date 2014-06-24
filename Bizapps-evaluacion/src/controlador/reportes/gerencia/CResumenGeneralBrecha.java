package controlador.reportes.gerencia;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.Competencia;
import modelo.maestros.Empresa;
import modelo.maestros.Gerencia;
import modelo.maestros.Revision;
import modelo.maestros.UnidadOrganizativa;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import componentes.Mensaje;

import controlador.maestros.CGenerico;


public class CResumenGeneralBrecha extends CGenerico {

	@Wire
	Charts chart;
	@Wire
	private Combobox cmbPeriodo;
	@Wire
	private Combobox cmbPeriodoComparar;
	@Wire
	private Combobox cmbEmpresa;
	@Wire
	private Combobox cmbGerencia;
	@Wire
	private Combobox cmbUnidadOrganizativa;
	@Wire
	private Combobox cmbCompetencia;
	@Wire
	private Button btnGenerar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
	@Wire
	private Window winCumplimientoObjetivo;
    
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
    }
    
    
    @Listen("onClick = #btnGenerar")
	public void generarReporte() throws Exception {
    	
    	chart.setTitle("Resultados de Desempeño / Resumen General de Brechas");
    	String subtitulo = "Empresa: "
				+ cmbEmpresa.getSelectedItem().getLabel() + "/  Gerencia: "
				+ cmbGerencia.getSelectedItem().getLabel()
				+ "/  Periodo: "
				+ cmbPeriodo.getSelectedItem().getLabel() + " - " + cmbPeriodoComparar.getSelectedItem().getLabel() + " / Unidad Organizativa: " + cmbUnidadOrganizativa.getSelectedItem().getLabel() + " ";
    	chart.setSubtitle(subtitulo);
    
    	
    	Map parametros = new HashMap();
		parametros.put(
				"periodo",
				cmbPeriodo
						.getSelectedItem()
						.getId()
						.substring(
								0,
								cmbPeriodo.getSelectedItem().getId()
										.length() - 1));
		parametros.put(
				"periodo_comparar",
				cmbPeriodoComparar
						.getSelectedItem()
						.getId()
						.substring(
								0,
								cmbPeriodoComparar.getSelectedItem()
										.getId().length() - 1));
		parametros.put(
				"empresa",
				cmbEmpresa
						.getSelectedItem()
						.getId()
						.substring(
								0,
								cmbEmpresa.getSelectedItem().getId()
										.length() - 1));
		parametros.put(
				"gerencia",
				cmbGerencia
						.getSelectedItem()
						.getId()
						.substring(
								0,
								cmbGerencia.getSelectedItem().getId()
										.length() - 1));
		parametros.put(
				"unidad",
				cmbUnidadOrganizativa
						.getSelectedItem()
						.getId()
						.substring(
								0,
								cmbUnidadOrganizativa.getSelectedItem()
										.getId().length() - 1));
		parametros.put(
				"competencia",
				cmbCompetencia
						.getSelectedItem()
						.getId()
						.substring(
								0,
								cmbCompetencia.getSelectedItem()
										.getId().length() - 1));
		
		parametros.put("estado_evaluacion", "FINALIZADA");
		
    	
    	
    	 chart.setModel(servicioReporte.getDataResumenGeneralBrecha(parametros));
         
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
    
    public boolean validar() {
		boolean valido = true;
		
		
		if (cmbPeriodo.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarPeriodo, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		} else if (cmbPeriodoComparar.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarPeriodo, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		}else if (cmbEmpresa.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarEmpresa, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		} else if (cmbGerencia.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarGerencia, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		}  else if (cmbUnidadOrganizativa.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarUnidadOrganizativa, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		}  else if (cmbCompetencia.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarCompetencia, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		}  
	
		return valido;
	}


	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
		comboEmpresa();
		comboGerencia();
		comboPeriodo();
		comboUnidadOrganizativa();
		comboCompetencia(); 
		
	}
	
	private void comboEmpresa() {
		List<Empresa> empresas = new ArrayList<Empresa>();
		Empresa empresaAuxiliar = new Empresa();
		empresaAuxiliar.setId(0);
		empresaAuxiliar.setNombre("TODAS");
		empresas.add(empresaAuxiliar);
		empresas.addAll(servicioEmpresa.buscarTodas());
		cmbEmpresa.setModel(new ListModelList<Empresa>(empresas));
	}

	private void comboGerencia() {
		List<Gerencia> gerencias = new ArrayList<Gerencia>();
		Gerencia gerenciaAuxiliar = new Gerencia();
		gerenciaAuxiliar.setId(0);
		gerenciaAuxiliar.setDescripcion("TODAS");
		gerencias.add(gerenciaAuxiliar);
		gerencias.addAll(servicioGerencia.buscarTodas());
		cmbGerencia.setModel(new ListModelList<Gerencia>(gerencias));
	}

	private void comboPeriodo() {
		List<Revision> revisiones = servicioRevision.buscarTodas();
		/*
		 * Periodo periodoAuxiliar = new Periodo(); periodoAuxiliar.setId(0);
		 * periodoAuxiliar.setDescripcion("TODAS");
		 * periodos.add(periodoAuxiliar);
		 */
		cmbPeriodo.setModel(new ListModelList<Revision>(revisiones));
		cmbPeriodoComparar.setModel(new ListModelList<Revision>(revisiones));
	}

	private void comboUnidadOrganizativa() {
		List<UnidadOrganizativa> unidades = new ArrayList<UnidadOrganizativa>();
		UnidadOrganizativa unidadOrganizativa = new UnidadOrganizativa();
		unidadOrganizativa.setId(0);
		unidadOrganizativa.setDescripcion("TODAS");
		unidades.add(unidadOrganizativa);
		unidades.addAll(servicioUnidadOrganizativa.buscarTodas());
		cmbUnidadOrganizativa.setModel(new ListModelList<UnidadOrganizativa>(
				unidades));
	}
	
	private void comboCompetencia() {
		List<Competencia> compentencias = new ArrayList<Competencia>();
		Competencia competenciaAuxiliar = new Competencia();
		competenciaAuxiliar.setId(0);
		competenciaAuxiliar.setDescripcion("TODAS");
		compentencias.add(competenciaAuxiliar);
		compentencias.addAll(servicioCompetencia.buscarTodas());
		cmbCompetencia.setModel(new ListModelList<Competencia>(compentencias));
	}
    
}