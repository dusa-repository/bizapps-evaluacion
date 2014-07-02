package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Dominio;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionCompetencia;
import modelo.maestros.EvaluacionConducta;
import modelo.maestros.EvaluacionIndicador;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.Medicion;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.maestros.Perspectiva;
import modelo.maestros.UnidadMedida;
import modelo.seguridad.Usuario;

import org.hibernate.hql.internal.ast.tree.CaseNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CEvaluacionEmpleado extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Textbox txtFortalezas;
	@Wire
	private Textbox txtOportunidades;
	@Wire
	private Textbox txtResumen;
	@Wire
	private Textbox txtCompromisos;
	@Wire
	private Textbox txtValorResultado;
	@Wire
	private Textbox txtAnterior;
	@Wire
	private Spinner txtPeso1;
	@Wire
	private Spinner txtValorMeta;
	@Wire
	private Spinner txtValorResultado1;
	@Wire
	private Textbox txtIndicador;
	@Wire
	private Spinner txtResFy;
	@Wire
	private Spinner txtResultadoPorc;
	@Wire
	private Spinner txtPesoPorc;
	@Wire
	private Button btnGuardar;
	@Wire
	private Button btnGuardarObjetivo;
	@Wire
	private Button btnImprimir;
	@Wire
	private Button btnOk;
	@Wire
	private Button btnCalcular;
	@Wire
	private Button btnGuardarIndicador;
	@Wire
	private Button btnAgregarIndicador;
	@Wire
	private Button btnEliminarIndicador;
	@Wire
	private Button btnAgregarObjetivo;
	@Wire
	private Button btnEliminar;
	@Wire
	private Listbox lbxIndicadoresAgregados;
	@Wire
	private Listbox lbxObjetivosGuardados;
	@Wire
	private Listbox lbxCompetenciaEspecifica;
	@Wire
	private Combobox cmbObjetivos;
	@Wire
	private Combobox cmbPerspectiva;
	@Wire
	private Window window;
	@Wire
	private Groupbox gpxAgregar;
	@Wire
	private Groupbox gpxAgregados;
	@Wire
	private Groupbox gpxAgregarIndicador;
	@Wire
	private Label lblEvaluacion;
	@Wire
	private Label lblFechaCreacion;
	@Wire
	private Label lblRevision;
	@Wire
	private Textbox txtObjetivo;
	@Wire
	private Textbox txtCorresponsables;
	@Wire
	private Spinner txtPeso;
	@Wire
	private Textbox txtTotal;
	@Wire
	private Textbox txtResultados;
	@Wire
	private Button btnAgregar;
	@Wire
	private Button btnCancelar;
	@Wire
	private Button btnCancelarIndicador;
	@Wire
	private Button btnSalirCompetenciaR;
	@Wire
	private Button btnSalirCompetenciaE;
	@Wire
	private Button btnCancelarEvaluacion;
	@Wire
	private Button btnCancelarO;
	@Wire
	private Listbox lbxCompetenciaRectora;
	@Wire
	private Listbox lbxObjetivos;
	@Wire
	private Label lblFicha;
	@Wire
	private Label lblNombreTrabajador;
	@Wire
	private Label lblCargo;
	@Wire
	private Label lblUnidadOrganizativa;
	@Wire
	private Label lblGerencia;
	@Wire
	private Combobox cmbNivelRequerido;
	@Wire
	private Combobox cmbUnidad;
	@Wire
	private Combobox cmbMedicion;
	@Wire
	private Window wdwConductasRectoras;
	@Wire
	private Window winEvaluacionEmpleado;
	@Wire
	private Groupbox gpxListaPersonal;

	ListModelList<Perspectiva> perspectiva;
	List<EvaluacionObjetivo> objetivosG = new ArrayList<EvaluacionObjetivo>();
	List<EvaluacionIndicador> indicadores = new ArrayList<EvaluacionIndicador>();
	List<EvaluacionConducta> evaluacionconductas = new ArrayList<EvaluacionConducta>();

	String tipo = "EVIDENCIADO";
	ListModelList<Dominio> dominio;
	private static int idEva;
	private static Double valor = 0.0;
	private static Double total = 0.0;
	private static Double totalObjetivo = 0.0;
	private static Double totalInd = 0.0;
	private static Evaluacion evaluacion1;
	private Double cambio;
	private static int idObjetivo;
	private static int idIndicador;
	private static String pers;
	private static String unid;
	private static String medic;
	private static String fichaE;
	private static int num;

	@Override
	public void inicializar() throws IOException {

		List<Medicion> medicion = servicioMedicion.buscar();
		cmbMedicion.setModel(new ListModelList<Medicion>(medicion));

		List<UnidadMedida> unidad = servicioUnidadMedida.buscar();
		cmbUnidad.setModel(new ListModelList<UnidadMedida>(unidad));

		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				Integer idEvaluacion = (Integer) map.get("id");
				String fichaMap = (String) map.get("titulo");
				idEva = idEvaluacion;
				fichaE = fichaMap;

				System.out.println(idEvaluacion);
				List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
				cmbPerspectiva.setModel(new ListModelList<Perspectiva>(
						perspectiva));
				cmbPerspectiva.setValue(perspectiva.get(0).getDescripcion());

				Evaluacion evaluacion = servicioEvaluacion
						.buscarEvaluacion(idEvaluacion);
				evaluacion1 = evaluacion;
				txtCompromisos.setValue(evaluacion.getCompromisos());
				txtFortalezas.setValue(evaluacion.getFortalezas());
				txtOportunidades.setValue(evaluacion.getOportunidades());
				txtResumen.setValue(evaluacion.getResumen());
				Authentication auth = SecurityContextHolder.getContext()
						.getAuthentication();
				String ficha = evaluacion.getFicha();
				Integer numeroEvaluacion = evaluacion
						.getIdEvaluacionSecundario();
				num = numeroEvaluacion;
				System.out.println(num);
				System.out.println(fichaE);
				Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);

				List<EvaluacionObjetivo> evaluacionObjetivo = new ArrayList<EvaluacionObjetivo>();
				evaluacionObjetivo = servicioEvaluacionObjetivo
						.buscarObjetivosEvaluar(idEvaluacion);
				lbxObjetivosGuardados
						.setModel(new ListModelList<EvaluacionObjetivo>(
								evaluacionObjetivo));

				List<EvaluacionObjetivo> evaluacionObjetivoIndicadores = servicioEvaluacionObjetivo
						.buscarObjetivosEvaluar(idEvaluacion);
				cmbObjetivos.setModel(new ListModelList<EvaluacionObjetivo>(
						evaluacionObjetivoIndicadores));

				String cargo = empleado.getCargo().getDescripcion();
				String unidadOrganizativa = empleado.getUnidadOrganizativa()
						.getDescripcion();
				String gerenciaReporte = empleado.getUnidadOrganizativa()
						.getGerencia().getDescripcion();
				String nombreTrabajador = empleado.getNombre();

				// Listbox que contiene los objetivos
				objetivosG = servicioEvaluacionObjetivo
						.buscarObjetivosEvaluar(idEvaluacion);
				lbxObjetivosGuardados
						.setModel(new ListModelList<EvaluacionObjetivo>(
								objetivosG));

				// Listbox que contine los indicadores
				cmbObjetivos.setModel(new ListModelList<EvaluacionObjetivo>(
						evaluacionObjetivoIndicadores));

				List<NivelCompetenciaCargo> nivel = new ArrayList<NivelCompetenciaCargo>();
				List<NivelCompetenciaCargo> nivel2 = new ArrayList<NivelCompetenciaCargo>();
				List<NivelCompetenciaCargo> nivel3 = new ArrayList<NivelCompetenciaCargo>();
				List<NivelCompetenciaCargo> nivel4 = new ArrayList<NivelCompetenciaCargo>();
				NivelCompetenciaCargo nivelRectoras = new NivelCompetenciaCargo();
				NivelCompetenciaCargo nivelEspecificas = new NivelCompetenciaCargo();

				nivel = servicioNivelCompetenciaCargo.buscar(empleado
						.getCargo());
				for (int j = 0; j < nivel.size(); j++) {
					if (nivel.get(j).getCompetencia().getNivel()
							.equals("RECTORAS")) {
						nivelRectoras = nivel.get(j);
						nivel2.add(nivelRectoras);
					} else {
						nivel.remove(j);
					}
					lbxCompetenciaRectora
							.setModel(new ListModelList<NivelCompetenciaCargo>(
									nivel2));
				}
				nivel4 = servicioNivelCompetenciaCargo.buscar(empleado
						.getCargo());
				for (int j = 0; j < nivel4.size(); j++) {
					if (nivel4.get(j).getCompetencia().getNivel()
							.equals("ESPECIFICAS")) {
						nivelEspecificas = nivel4.get(j);
						nivel3.add(nivelEspecificas);
					} else {
						nivel4.remove(j);
					}
					lbxCompetenciaEspecifica
							.setModel(new ListModelList<NivelCompetenciaCargo>(
									nivel3));
				}

				lblFicha.setValue(ficha);
				lblNombreTrabajador.setValue(nombreTrabajador);
				lblCargo.setValue(cargo);
				lblUnidadOrganizativa.setValue(unidadOrganizativa);
				lblGerencia.setValue(gerenciaReporte);
				lblEvaluacion.setValue(numeroEvaluacion.toString());
				lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
				lblRevision.setValue(evaluacion.getRevision().getDescripcion());
				gpxAgregar.setOpen(false);
				gpxAgregarIndicador.setOpen(false);
				evaluacionconductas = servicioEvaluacionConducta
						.buscarConductas(idEva);
				if (evaluacionconductas.size() != 0) {
					mostrarDominioRectora();
					mostrarDominioEspecifica();
				}
			}
		}
	}

	// public Double getCambio() {
	// evaluarIndicadores ();
	// return cambio;
	//
	// }

	@Listen("onClick = #btnAgregarObjetivo")
	public void agregarObjetivo() {
		gpxAgregar.setOpen(true);
	}

	@Listen("onClick = #btnAgregarIndicador")
	public void agregarIndicador() {
		gpxAgregarIndicador.setOpen(true);
	}

	@Listen("onClick = #btnCancelarO")
	public void cerrarPanel() {
		gpxAgregar.setOpen(false);
	}

	@Listen("onClick = #btnEliminar")
	public void eliminarObj() {
		eliminarObjetivo();
	}

	public ListModelList<Dominio> getDominio() {
		dominio = new ListModelList<Dominio>(
				servicioDominio.buscarPorTipo(tipo));
		return dominio;
	}

	public void limpiar() {
		txtObjetivo.setValue("");
		List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
		cmbPerspectiva.setModel(new ListModelList<Perspectiva>(perspectiva));
		cmbPerspectiva.setDisabled(false);
		cmbPerspectiva.setValue(perspectiva.get(0).getDescripcion());
		txtCorresponsables.setValue("");
		txtPeso.setValue(null);

	}

	public void limpiarIndicador() {
		txtIndicador.setValue("");
		txtValorMeta.setValue(null);
		cmbMedicion.setValue(null);
		cmbUnidad.setValue(null);
		txtPeso1.setValue(null);
		cmbMedicion.setDisabled(false);
		cmbUnidad.setDisabled(false);
		List<Medicion> medicion = servicioMedicion.buscar();
		cmbMedicion.setModel(new ListModelList<Medicion>(medicion));
		List<UnidadMedida> unidad = servicioUnidadMedida.buscar();
		cmbUnidad.setModel(new ListModelList<UnidadMedida>(unidad));
	}

	@Listen("onDoubleClick = #lbxCompetenciaRectora")
	public void mostrarCompetenciasR() {

		if (lbxCompetenciaRectora.getItemCount() != 0) {

			Listitem listItem = lbxCompetenciaRectora.getSelectedItem();
			if (listItem != null) {
				if (((Combobox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue() == "") {
					Messagebox.show("Debe Seleccionar un nivel de dominio",
							"Error", Messagebox.OK, Messagebox.ERROR);
				} else {
					String nivel = ((Combobox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getSelectedItem()
							.getDescription();
					String titulo = "Competencias Rectoras";
					NivelCompetenciaCargo competencia = (NivelCompetenciaCargo) listItem
							.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", competencia.getCompetencia().getId());
					map.put("idnivel", nivel);
					map.put("titulo", titulo);
					map.put("idEva", idEva);
					map.put("conductas", evaluacionconductas);
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					wdwConductasRectoras = (Window) Executions
							.createComponents(
									"/vistas/transacciones/VEvaluacionConductas.zul",
									null, map);
					wdwConductasRectoras.doModal();
				}
			}

		}

	}

	@Listen("onSelect = #cmbObjetivos")
	public void mostrarIndicadores() {
		lbxIndicadoresAgregados.getItems().clear();
		Integer idObjetivo = Integer.parseInt(cmbObjetivos.getSelectedItem()
				.getContext());
		indicadores = servicioEvaluacionIndicador.buscarIndicadores(idObjetivo);
		lbxIndicadoresAgregados
				.setModel(new ListModelList<EvaluacionIndicador>(indicadores));

	}

	@Listen("onDoubleClick = #lbxCompetenciaEspecifica")
	public void mostrarCompetenciasE() {

		if (lbxCompetenciaEspecifica.getItemCount() != 0) {

			Listitem listItem = lbxCompetenciaEspecifica.getSelectedItem();
			if (listItem != null) {
				if (((Combobox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue() == "") {
					Messagebox.show("Debe Seleccionar un nivel de dominio",
							"Error", Messagebox.OK, Messagebox.ERROR);
				} else {
					String nivel = ((Combobox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getSelectedItem()
							.getDescription();

					NivelCompetenciaCargo competencia = (NivelCompetenciaCargo) listItem
							.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					String titulo = "Competencias Especificas";
					map.put("id", competencia.getCompetencia().getId());
					map.put("idnivel", nivel);
					map.put("titulo", titulo);
					map.put("idEva", idEva);
					map.put("conductas", evaluacionconductas);
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					wdwConductasRectoras = (Window) Executions
							.createComponents(
									"/vistas/transacciones/VEvaluacionConductas.zul",
									null, map);
					wdwConductasRectoras.doModal();
				}
			}

		}

	}

	@Listen("onClick = #btnGuardarObjetivo")
	public void guardarObjetivos() {

		boolean campoBlanco = false;

		for (int i = 0; i < lbxObjetivosGuardados.getItems().size(); i++) {
			List<Listitem> listItem2 = lbxObjetivosGuardados.getItems();

			Listitem listItem = lbxObjetivosGuardados.getItemAtIndex(i);
			if (((Textbox) ((listItem.getChildren().get(5))).getFirstChild())
					.getValue().equals("")
					&& ((Textbox) ((listItem.getChildren().get(6)))
							.getFirstChild()).getValue().equals("")) {
				campoBlanco = true;
			}
		}

		if (campoBlanco == true) {
			Messagebox.show("Debe ingresar la Evaluacion de los Objetivos",
					"Error", Messagebox.OK, Messagebox.ERROR);

		} else {

			List<EvaluacionObjetivo> evaluacionesO = new ArrayList<EvaluacionObjetivo>();

			for (int i = 0; i < lbxObjetivosGuardados.getItems().size(); i++) {
				List<Listitem> listItem2 = lbxObjetivosGuardados.getItems();
				EvaluacionObjetivo EvaluacionO = listItem2.get(i).getValue();

				Integer idObjetivo = EvaluacionO.getIdObjetivo();
				Listitem listItem = lbxObjetivosGuardados.getItemAtIndex(i);
				EvaluacionObjetivo objetivos = listItem2.get(i).getValue();
				String totalIndicador = ((Textbox) ((listItem.getChildren()
						.get(5))).getFirstChild()).getValue();
				String resultado = ((Textbox) ((listItem.getChildren().get(6)))
						.getFirstChild()).getValue();

				EvaluacionObjetivo evaluacion = servicioEvaluacionObjetivo
						.buscarObjetivosId(idObjetivo);
				evaluacion.setTotalInd(Double.parseDouble(totalIndicador));
				evaluacion.setResultado(Double.parseDouble(resultado));
				servicioEvaluacionObjetivo.guardar(evaluacion);
			}
		}

	}

	@Listen("onClick = #btnCalcular")
	public void prueba() {
		evaluarIndicadores();
	}

	@Listen("onClick = #btnEliminarIndicador")
	public void eliminarI() {
		eliminarIndicador();
	}

	@Listen("onClick = #btnGuardarIndicador")
	public void guardarIndicadores() {

		boolean campoBlanco = false;

		for (int i = 0; i < lbxIndicadoresAgregados.getItems().size(); i++) {
			List<Listitem> listItem2 = lbxIndicadoresAgregados.getItems();

			Listitem listItem = lbxIndicadoresAgregados.getItemAtIndex(i);
			if (((Spinner) ((listItem.getChildren().get(6))).getFirstChild())
					.getValue().equals("")
					&& ((Spinner) ((listItem.getChildren().get(7)))
							.getFirstChild()).getValue().equals("")
					&& ((Textbox) ((listItem.getChildren().get(8)))
							.getFirstChild()).getValue().equals("")
					&& ((Textbox) ((listItem.getChildren().get(9)))
							.getFirstChild()).getValue().equals("")) {
				campoBlanco = true;
			}
		}

		if (campoBlanco == true) {
			Messagebox.show("Debe ingresar la Evaluacion de los Indicadores",
					"Error", Messagebox.OK, Messagebox.ERROR);

		} else {
			List<EvaluacionIndicador> evaluacionesI = new ArrayList<EvaluacionIndicador>();

			for (int i = 0; i < lbxIndicadoresAgregados.getItems().size(); i++) {
				List<Listitem> listItem2 = lbxIndicadoresAgregados.getItems();
				EvaluacionIndicador EvaluacionI = listItem2.get(i).getValue();
				Integer idIndicador = EvaluacionI.getIdIndicador();
				Listitem listItem = lbxIndicadoresAgregados.getItemAtIndex(i);
				EvaluacionIndicador indicadores = listItem2.get(i).getValue();
				Integer valorResultado = ((Spinner) ((listItem.getChildren()
						.get(6))).getFirstChild()).getValue();
				Integer resultadoFyAnterior = ((Spinner) ((listItem
						.getChildren().get(7))).getFirstChild()).getValue();
				String resultado = ((Textbox) ((listItem.getChildren().get(8)))
						.getFirstChild()).getValue();
				String resultadoPeso = ((Textbox) ((listItem.getChildren()
						.get(9))).getFirstChild()).getValue();

				EvaluacionIndicador indicador = servicioEvaluacionIndicador
						.buscarIndicadorId(idIndicador);
				indicador.setValorResultado(valorResultado);
				indicador.setResultadoFyAnterior(resultadoFyAnterior);
				indicador.setResultadoPorc(Double.parseDouble(resultado));
				indicador.setResultadoPeso(Double.parseDouble(resultadoPeso));
				indicador.setTotal(total);
				servicioEvaluacionIndicador.guardar(indicador);

			}
			guardarObjetivos();
			guardarEvaluacion();
			Messagebox.show("Datos guardados exitosamente", "Información",
					Messagebox.OK, Messagebox.INFORMATION);
		}

	}

	@Listen("onClick = #btnCancelar,  #btnCancelarIndicador, #btnSalirCompetenciaR, #btnSalirCompetenciaE, #btnCancelarEvaluacion")
	public void salir() {
		winEvaluacionEmpleado.onClose();
	}

	public void evaluarIndicadores() {

		for (int i = 0; i < lbxIndicadoresAgregados.getItems().size(); i++) {
			List<Listitem> listItem2 = lbxIndicadoresAgregados.getItems();
			EvaluacionIndicador EvaluacionI = listItem2.get(i).getValue();
			Listitem listItem = lbxIndicadoresAgregados.getItemAtIndex(i);
			Integer valorResultado = ((Spinner) ((listItem.getChildren().get(6)))
					.getFirstChild()).getValue();
			Integer resultadoFyAnterior = ((Spinner) ((listItem.getChildren()
					.get(7))).getFirstChild()).getValue();
			String resultado = ((Textbox) ((listItem.getChildren().get(8)))
					.getFirstChild()).getValue();
			String resultadoPeso = ((Textbox) ((listItem.getChildren().get(9)))
					.getFirstChild()).getValue();
			if (EvaluacionI.getMedicion().getDescripcionMedicion()
					.equals("CONTINUA")) {
				if ((valorResultado) < EvaluacionI.getValorMeta()) {
					if ((valorResultado) >= (resultadoFyAnterior)) {
						resultado = "85";
					} else {
						resultado = "0";
					}
				} else {
					if ((valorResultado) > EvaluacionI.getValorMeta()) {
						resultado = "115";
					} else {
						if ((valorResultado) == EvaluacionI.getValorMeta()) {
							resultado = "100";
						}
					}
				}
			} else {
				((Spinner) ((listItem.getChildren().get(7))).getFirstChild())
						.setDisabled(true);
				valor = ((valorResultado / EvaluacionI.getValorMeta()) * 100);
				System.out.println("valor" + valor);
				Integer valor1 = valor.intValue();
				System.out.println("valor1" + valor1);
				resultado = calcularPorcentajeMetaIndicado(valor1).toString();
				System.out.println("resultado" + resultado);
			}

			resultadoPeso = String.valueOf((Double.parseDouble(resultado)
					* (EvaluacionI.getPeso()) / 100));
			((Textbox) ((listItem.getChildren().get(8))).getFirstChild())
					.setValue((resultado));
			((Textbox) ((listItem.getChildren().get(9))).getFirstChild())
					.setValue((resultadoPeso));
			total = total + Double.parseDouble(resultadoPeso);
			for (int j = 0; j < lbxObjetivosGuardados.getItems().size(); j++) {
				List<Listitem> listItem3 = lbxObjetivosGuardados.getItems();
				EvaluacionObjetivo EvaluacionO = listItem3.get(j).getValue();
				Double peso = EvaluacionO.getPeso();
				Integer idObjetivo = EvaluacionO.getIdObjetivo();
				Integer idObjetivo1 = EvaluacionI.getIdObjetivo();
				if (idObjetivo.equals(idObjetivo1)) {
					totalInd = 0.0;
					totalInd = (total * peso) / 100;
					String total = totalInd.toString();
					((Textbox) ((listItem3.get(j).getChildren().get(5)))
							.getFirstChild()).setValue(total);

				}
			}

		}
		int item = lbxObjetivosGuardados.getItems().size();
		System.out.println("k" + item);
		for (int k = 0; k < lbxObjetivosGuardados.getItems().size(); k++) {

			List<Listitem> listItem4 = lbxObjetivosGuardados.getItems();
			String resultadoO = ((Textbox) ((listItem4.get(k).getChildren()
					.get(5))).getFirstChild()).getValue();
			totalObjetivo = totalObjetivo + Double.parseDouble(resultadoO);
			String resultado = totalObjetivo.toString();
			if (k == item - 1) {
				for (int l = 0; l < lbxObjetivosGuardados.getItems().size(); l++) {
					List<Listitem> listItem5 = lbxObjetivosGuardados.getItems();
					((Textbox) ((listItem5.get(l).getChildren().get(6)))
							.getFirstChild()).setValue(resultado);
				}
			}
		}
	}

	public Integer calcularPorcentajeMetaIndicado(Integer valor) {
		if (valor < 0)
			valor = 0;
		if (valor >= 0 && valor <= 24)
			valor = 0;
		if (valor >= 25 && valor <= 49)
			valor = 25;
		if (valor >= 50 && valor <= 84)
			valor = 50;
		if (valor >= 85 && valor <= 94)
			valor = 85;
		if (valor >= 95 && valor <= 100)
			valor = 100;
		if (valor >= 101)
			valor = 115;
		System.out.println(valor);
		return valor;
	}

	@Listen("onClick = #btnOk")
	public void AgregarObjetivo2() {
		gpxAgregar.setOpen(false);
		gpxAgregados.setOpen(true);

		if (idObjetivo != 0) {
			EvaluacionObjetivoActualizar();
		} else {
			String perspectivaCombo = cmbPerspectiva.getSelectedItem()
					.getContext();
			System.out.println(perspectivaCombo);
			Perspectiva perspectiva = servicioPerspectiva.buscarId(Integer
					.parseInt(perspectivaCombo));
			String objetivo = txtObjetivo.getValue();
			String corresponsables = txtCorresponsables.getValue();
			Double peso = Double.valueOf(txtPeso.getValue());
			Integer linea = objetivosG.size() + 1;
			EvaluacionObjetivo objetivoLista = new EvaluacionObjetivo();
			idObjetivo = servicioEvaluacionObjetivo.buscarId() + 1;
			objetivoLista.setIdObjetivo(idObjetivo);
			objetivoLista.setIdEvaluacion(idEva);
			objetivoLista.setDescripcionObjetivo(objetivo);
			objetivoLista.setPerspectiva(perspectiva);
			objetivoLista.setLinea(linea);
			objetivoLista.setPeso(peso);
			objetivoLista.setResultado(0);
			objetivoLista.setTotalInd(0);
			objetivoLista.setCorresponsables(corresponsables);
			objetivosG.add(objetivoLista);
			lbxObjetivosGuardados
					.setModel(new ListModelList<EvaluacionObjetivo>(objetivosG));
			servicioEvaluacionObjetivo.guardar(objetivoLista);
			gpxAgregar.setOpen(false);
			Messagebox.show("Objetivos Guardados Exitosamente", "Información",
					Messagebox.OK, Messagebox.INFORMATION);

		}
		limpiar();
		idObjetivo = 0;
	}

	@Listen("onDoubleClick = #lbxObjetivosGuardados")
	public void mostrarEvaluacion() {
		gpxAgregar.setOpen(true);
		if (lbxObjetivosGuardados.getItemCount() != 0) {
			Listitem listItem = lbxObjetivosGuardados.getSelectedItem();
			if (listItem != null) {
				EvaluacionObjetivo evaluacionObjetivo = (EvaluacionObjetivo) listItem
						.getValue();
				idObjetivo = evaluacionObjetivo.getIdObjetivo();
				Integer linea = evaluacionObjetivo.getLinea();
				String objetivo = evaluacionObjetivo.getDescripcionObjetivo();
				String corresponsables = evaluacionObjetivo
						.getCorresponsables();
				Double peso = evaluacionObjetivo.getPeso();
				Perspectiva perspectiva = evaluacionObjetivo.getPerspectiva();
				txtObjetivo.setValue(objetivo);
				txtCorresponsables.setValue(corresponsables);
				int peso1 = peso.intValue();
				txtPeso.setValue(peso1);
				cmbPerspectiva.setValue(perspectiva.getDescripcion());
				pers = perspectiva.getDescripcion();
				cmbPerspectiva.setDisabled(true);
			}
		}
	}

	private void EvaluacionObjetivoActualizar() {
		Perspectiva perspectiva1 = servicioPerspectiva.buscarNombre(pers);
		String objetivo = txtObjetivo.getValue();
		String corresponsables = txtCorresponsables.getValue();
		Double peso = Double.valueOf(txtPeso.getValue());
		EvaluacionObjetivo objetivoLista = servicioEvaluacionObjetivo
				.buscarObjetivosId(idObjetivo);
		objetivoLista.setDescripcionObjetivo(objetivo);
		objetivoLista.setPerspectiva(perspectiva1);
		objetivoLista.setPeso(peso);
		objetivoLista.setCorresponsables(corresponsables);
		servicioEvaluacionObjetivo.guardar(objetivoLista);
		List<EvaluacionObjetivo> evaluacionObje = servicioEvaluacionObjetivo
				.buscarObjetivosEvaluar(idEva);
		lbxObjetivosGuardados.getItems().clear();
		lbxObjetivosGuardados.setModel(new ListModelList<EvaluacionObjetivo>(
				evaluacionObje));
		gpxAgregar.setOpen(false);
	}

	@Listen("onClick = #btnOk2")
	public void AgregarIndicador1() {

		if (cmbObjetivos.getText().compareTo("") == 0
				|| cmbUnidad.getText().compareTo("") == 0
				|| cmbMedicion.getText().compareTo("") == 0
				|| txtPeso1.getText().compareTo("") == 0
				|| txtValorMeta.getText().compareTo("") == 0
				|| txtResFy.getText().compareTo("") == 0
				|| txtIndicador.getText().compareTo("") == 0
				|| txtResultadoPorc.getText().compareTo("") == 0
				|| txtPesoPorc.getText().compareTo("") == 0)

		{
			Messagebox.show("Debe llenar todos los campos", "Advertencia",
					Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			if (idIndicador != 0) {
				System.out.println("entoooooooooooo");
				EvaluacionIndicadorActualizar();
			} else {
				gpxAgregados.setOpen(true);
				String indicador = txtIndicador.getValue();
				String unidadCombo = cmbUnidad.getSelectedItem().getContext();
				UnidadMedida unidad = servicioUnidadMedida.buscarUnidad(Integer
						.parseInt(unidadCombo));
				String medicionCombo = cmbMedicion.getSelectedItem()
						.getContext();
				Medicion medicion = servicioMedicion.buscarMedicion(Integer
						.parseInt(medicionCombo));
				String idObjetivo = cmbObjetivos.getSelectedItem().getContext();
				Double peso = Double.valueOf(txtPeso1.getValue());
				Double valorMeta = Double.valueOf(txtValorMeta.getValue());
				Double valorResultado = Double.valueOf(txtValorResultado1
						.getValue());
				Double resFy = Double.valueOf(txtResFy.getValue());
				Double resultadoPorc = Double.valueOf(txtResultadoPorc
						.getValue());
				Double pesoPorc = Double.valueOf(txtPesoPorc.getValue());
				Integer linea = indicadores.size() + 1;
				EvaluacionIndicador indicadorLista = new EvaluacionIndicador();
				indicadorLista.setIdObjetivo(Integer.parseInt(idObjetivo));
				indicadorLista.setDescripcionIndicador(indicador);
				indicadorLista.setMedicion(medicion);
				indicadorLista.setUnidadMedida(unidad);
				indicadorLista.setLinea(linea);
				indicadorLista.setPeso(peso);
				indicadorLista.setResultadoFyAnterior(resFy);
				indicadorLista.setResultadoPeso(pesoPorc);
				indicadorLista.setResultadoPorc(resultadoPorc);
				indicadorLista.setValorMeta(valorMeta);
				indicadorLista.setValorResultado(valorResultado);
				indicadorLista.setTotal(0);
				indicadores.add(indicadorLista);
				lbxIndicadoresAgregados
						.setModel(new ListModelList<EvaluacionIndicador>(
								indicadores));
				servicioEvaluacionIndicador.guardar(indicadorLista);

				Messagebox.show("Indicador para el objetivo" + " "
						+ cmbObjetivos.getValue() + " "
						+ "ha sido guardado exitosamente", "Información",
						Messagebox.OK, Messagebox.INFORMATION);
				gpxAgregar.setOpen(false);
			}

			limpiarIndicador();
			idIndicador = 0;
		}
	}

	@Listen("onDoubleClick  = #lbxIndicadoresAgregados")
	public void mostrarEvaluacionIndicadores() {
		gpxAgregarIndicador.setOpen(true);
		if (lbxIndicadoresAgregados.getItemCount() != 0) {

			Listitem listItem = lbxIndicadoresAgregados.getSelectedItem();
			if (listItem != null) {
				EvaluacionIndicador evaluacionIndicador = (EvaluacionIndicador) listItem
						.getValue();
				idObjetivo = evaluacionIndicador.getIdObjetivo();
				idIndicador = evaluacionIndicador.getIdIndicador();
				Integer linea = evaluacionIndicador.getLinea();
				String indicador = evaluacionIndicador
						.getDescripcionIndicador();
				String unidad = evaluacionIndicador.getUnidadMedida()
						.getDescripcion();
				unid = unidad;
				String medicion = evaluacionIndicador.getMedicion()
						.getDescripcionMedicion();
				medic = medicion;
				Double peso = evaluacionIndicador.getPeso();
				Double valorMeta = evaluacionIndicador.getValorMeta();
				Double valorResultado = evaluacionIndicador.getValorResultado();
				Double fyAnterior = evaluacionIndicador
						.getResultadoFyAnterior();
				Double resultadoPorc = evaluacionIndicador.getResultadoPorc();
				Double resultadoPeso = evaluacionIndicador.getResultadoPeso();
				Double total = evaluacionIndicador.getTotal();
				txtIndicador.setValue(indicador);
				cmbMedicion.setValue(medicion);
				cmbUnidad.setValue(unidad);
				int peso1 = peso.intValue();
				int valor = valorMeta.intValue();
				int valorR = valorResultado.intValue();
				int fyAnt = fyAnterior.intValue();
				int resulPorc = resultadoPorc.intValue();
				int resulP = resultadoPeso.intValue();
				int tot = total.intValue();
				txtPeso1.setValue(peso1);
				txtResFy.setValue(fyAnt);
				txtValorMeta.setValue(valor);
				txtValorResultado1.setValue(valorR);
				txtResultadoPorc.setValue(resulPorc);
				txtPesoPorc.setValue(resulP);
				cmbMedicion.setDisabled(true);
				cmbUnidad.setDisabled(true);
			}
		}
	}

	private void EvaluacionIndicadorActualizar() {
		UnidadMedida unidadMedida = servicioUnidadMedida.buscarPorNombre(unid);
		Medicion medicion = servicioMedicion.buscarPorNombre(medic);
		EvaluacionIndicador indicador = servicioEvaluacionIndicador
				.buscarIndicadorId(idIndicador);
		indicador.setDescripcionIndicador(txtIndicador.getValue());
		indicador.setPeso(txtPeso1.getValue());
		indicador.setValorMeta(txtValorMeta.getValue());
		servicioEvaluacionIndicador.guardar(indicador);
		List<EvaluacionIndicador> evaluacionInd = servicioEvaluacionIndicador
				.buscarIndicadores(idObjetivo);
		lbxIndicadoresAgregados.getItems().clear();
		lbxIndicadoresAgregados
				.setModel(new ListModelList<EvaluacionIndicador>(evaluacionInd));
		gpxAgregarIndicador.setOpen(false);
	}

	private void eliminarIndicador() {
		if (lbxIndicadoresAgregados.getItemCount() != 0) {
			Listitem listItem = lbxIndicadoresAgregados.getSelectedItem();
			if (listItem != null) {
				EvaluacionIndicador evaluacionIndicador = (EvaluacionIndicador) listItem
						.getValue();
				idIndicador = evaluacionIndicador.getIdIndicador();
				idObjetivo = evaluacionIndicador.getIdObjetivo();
				Messagebox.show("Desea Eliminar el Indicador", "Alerta",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {

							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									servicioEvaluacionIndicador
											.eliminarUno(idIndicador);
									msj.mensajeInformacion(Mensaje.eliminado);
									lbxIndicadoresAgregados.getItems().clear();
									indicadores = servicioEvaluacionIndicador
											.buscarIndicadores(idObjetivo);
									lbxIndicadoresAgregados
											.setModel(new ListModelList<EvaluacionIndicador>(
													indicadores));
								}
							}
						});
			} else
				msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
		}
	}

	public void eliminarObjetivo() {
		if (lbxObjetivosGuardados.getItemCount() != 0) {
			Listitem listItem = lbxObjetivosGuardados.getSelectedItem();
			if (listItem != null) {
				EvaluacionObjetivo evaluacionObjetivo = (EvaluacionObjetivo) listItem
						.getValue();
				idObjetivo = evaluacionObjetivo.getIdObjetivo();
				List<EvaluacionIndicador> evaluacionIndicador = servicioEvaluacionIndicador
						.buscarIndicadores(idObjetivo);
				if (evaluacionIndicador.size() != 0) {
					Messagebox
							.show("El objetivo tiene indicadores asocioados desea eliminarlo",
									"Alerta",
									Messagebox.OK | Messagebox.CANCEL,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event evt)
												throws InterruptedException {
											if (evt.getName().equals("onOK")) {
												List<EvaluacionIndicador> evaluacionIndicador = servicioEvaluacionIndicador
														.buscarIndicadores(idObjetivo);
												servicioEvaluacionIndicador
														.eliminarVarios(evaluacionIndicador);
												servicioEvaluacionObjetivo
														.eliminarUno(idObjetivo);
												msj.mensajeInformacion(Mensaje.eliminado);
												lbxObjetivosGuardados
														.getItems().clear();
												objetivosG = servicioEvaluacionObjetivo
														.buscarObjetivosEvaluar(idEva);
												lbxObjetivosGuardados
														.setModel(new ListModelList<EvaluacionObjetivo>(
																objetivosG));
											}
										}
									});
				} else {

					Messagebox.show("Desea Eliminar el Objetivo", "Alerta",
							Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										servicioEvaluacionObjetivo
												.eliminarUno(idObjetivo);
										msj.mensajeInformacion(Mensaje.eliminado);
										lbxObjetivosGuardados.getItems()
												.clear();
										objetivosG = servicioEvaluacionObjetivo
												.buscarObjetivosEvaluar(idEva);
										lbxObjetivosGuardados
												.setModel(new ListModelList<EvaluacionObjetivo>(
														objetivosG));
									}
								}
							});

				}
			} else
				msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);

		}
	}

	public void guardarEvaluacion() {
		System.out.println("entrooo");
		Evaluacion evalua = servicioEvaluacion.buscarEvaluacion(idEva);
		System.out.println(evalua);
		System.out.println(idEva);
		evalua.setFechaRevision(fechaHora);
		evalua.setFichaEvaluador(fichaE);
		evalua.setResultadoObjetivos(totalObjetivo.intValue());
		servicioEvaluacion.guardar(evalua);
	}

	private void mostrarDominioRectora() {
		lbxCompetenciaRectora.renderAll();
		for (int i = 0; i < lbxCompetenciaRectora.getItemCount(); i++) {
			Listitem listItem2 = lbxCompetenciaRectora.getItemAtIndex(i);
			NivelCompetenciaCargo nivel = (NivelCompetenciaCargo) listItem2
					.getValue();
			List<Listitem> listItem5 = lbxCompetenciaRectora.getItems();
			Integer idCompetencia = nivel.getCompetencia().getId();
			Evaluacion eva = servicioEvaluacion.buscarEvaluacion(idEva);
			Competencia competencia = servicioCompetencia
					.buscarCompetencia(idCompetencia);
			EvaluacionCompetencia evaluacion = servicioEvaluacionCompetencia
					.buscar(eva, competencia);
			Integer dominio = evaluacion.getIdDominio();
			Dominio dom = servicioDominio.buscarDominio(dominio);
			String descripcionDominio = dom.getDescripcionDominio();
			((Combobox) ((listItem5.get(i).getChildren().get(2)))
					.getFirstChild()).setValue(descripcionDominio);
			System.out.println(descripcionDominio);

		}

	}

	private void mostrarDominioEspecifica() {
		lbxCompetenciaEspecifica.renderAll();
		for (int i = 0; i < lbxCompetenciaEspecifica.getItemCount(); i++) {
			Listitem listItem2 = lbxCompetenciaEspecifica.getItemAtIndex(i);
			NivelCompetenciaCargo nivel = (NivelCompetenciaCargo) listItem2
					.getValue();
			List<Listitem> listItem5 = lbxCompetenciaEspecifica.getItems();
			Integer idCompetencia = nivel.getCompetencia().getId();
			Evaluacion eva = servicioEvaluacion.buscarEvaluacion(idEva);
			Competencia competencia = servicioCompetencia
					.buscarCompetencia(idCompetencia);
			EvaluacionCompetencia evaluacion = servicioEvaluacionCompetencia
					.buscar(eva, competencia);
			Integer dominio = evaluacion.getIdDominio();
			Dominio dom = servicioDominio.buscarDominio(dominio);
			String descripcionDominio = dom.getDescripcionDominio();
			((Combobox) ((listItem5.get(i).getChildren().get(2)))
					.getFirstChild()).setValue(descripcionDominio);
			System.out.println(descripcionDominio);

		}

	}
}