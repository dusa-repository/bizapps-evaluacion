package controlador.reportes.exportar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.Empresa;
import modelo.maestros.Gerencia;
import modelo.maestros.Revision;
import modelo.maestros.UnidadOrganizativa;
import modelo.reportes.BeanDataGeneralCsv;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import componentes.Mensaje;

import controlador.maestros.CGenerico;

public class CDataGeneralCsv extends CGenerico {

	@Wire
	private Listbox listaData;
	@Wire
	private Combobox cmbPeriodo;
	@Wire
	private Combobox cmbEmpresa;
	@Wire
	private Combobox cmbGerencia;
	@Wire
	private Combobox cmbUnidadOrganizativa;
	@Wire
	private Button btnGenerar;
	@Wire
	private Button btnExportar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
	@Wire
	private Div divDataGeneralCsv;

	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@SuppressWarnings("rawtypes")
	@Listen("onClick = #btnGenerar")
	public void generarReporte() throws Exception {

		if (validar()) {
			Map parametros = new HashMap();
			parametros
					.put("periodo",
							cmbPeriodo
									.getSelectedItem()
									.getId()
									.substring(
											0,
											cmbPeriodo.getSelectedItem().getId()
													.length() - 1));
			parametros
					.put("empresa",
							cmbEmpresa
									.getSelectedItem()
									.getId()
									.substring(
											0,
											cmbEmpresa.getSelectedItem().getId()
													.length() - 1));
			parametros
					.put("gerencia",
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
									cmbUnidadOrganizativa.getSelectedItem().getId()
											.length() - 1));

			parametros.put("estado_evaluacion", "FINALIZADA");

			List<BeanDataGeneralCsv> listaExportarCSV = servicioReporte
					.getDataGeneralCsv(parametros);
			listaData.setModel(new ListModelList<BeanDataGeneralCsv>(
					listaExportarCSV));
		}

	}
	
	@SuppressWarnings("rawtypes")
	@Listen("onClick = #btnExportar")
	public void exportarReporte() throws Exception {

		if (validar()) {
			export_to_csv(listaData);
		}

	}

	@Listen("onClick = #btnLimpiar")
	public void limpiar() {
		cmbPeriodo.setText("Seleccione un Periodo");
		cmbEmpresa.setText("Seleccione una Empresa");
		cmbGerencia.setText("Seleccione una Gerencia");
		cmbUnidadOrganizativa.setText("Seleccione una Unidad Organizativa");
	}

	@Listen("onClick = #btnSalir")
	public void salir() {
		cerrarVentana(divDataGeneralCsv, "Resumen Macro");
	}

	public boolean validar() {
		boolean valido = true;

		if (cmbPeriodo.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarPeriodo, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		}  else if (cmbEmpresa.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarEmpresa, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		} else if (cmbGerencia.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarGerencia, alerta, Messagebox.OK,
					Messagebox.EXCLAMATION);
			valido = false;
		} else if (cmbUnidadOrganizativa.getSelectedItem() == null) {
			Messagebox.show(Mensaje.seleccionarUnidadOrganizativa, alerta,
					Messagebox.OK, Messagebox.EXCLAMATION);
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
		// comboCompetencia();
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
		List<Revision> revisiones = new ArrayList<Revision>();
		Revision revisionAuxiliar = new Revision();
		revisionAuxiliar.setId(0);
		revisionAuxiliar.setDescripcion("TODOS");
		revisiones.add(revisionAuxiliar);
		revisiones.addAll(servicioRevision.buscarTodas());
		cmbPeriodo.setModel(new ListModelList<Revision>(revisiones));		
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

	public void export_to_csv(Listbox listbox) {
		String s = ";";
		StringBuffer sb = new StringBuffer();

		for (Object head : listbox.getHeads()) {
			String h = "";
			for (Object header : ((Listhead) head).getChildren()) {
				h += ((Listheader) header).getLabel() + s;
			}
			sb.append(h + "\n");
		}
		for (Object item : listbox.getItems()) {
			String i = "";
			for (Object cell : ((Listitem) item).getChildren()) {
				i += ((Listcell) cell).getLabel() + s;
			}
			sb.append(i + "\n");
		}
		Filedownload.save(sb.toString().getBytes(), "text/plain", "datos.csv");
	}
	
	

	

}