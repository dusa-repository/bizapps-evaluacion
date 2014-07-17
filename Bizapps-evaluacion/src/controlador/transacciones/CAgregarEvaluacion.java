package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Dominio;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionConducta;
import modelo.maestros.EvaluacionIndicador;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.Medicion;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.maestros.Perspectiva;
import modelo.maestros.Revision;
import modelo.maestros.UnidadMedida;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.lowagie.text.pdf.AcroFields.Item;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CAgregarEvaluacion extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Label txttotalIndicador;
	@Wire
	private Textbox txtFortalezas;
	@Wire
	private Textbox txtOportunidades;
	@Wire
	private Textbox txtResumen;
	@Wire
	private Textbox txtCompromisos;
	@Wire
	private Textbox txtIndicador;
	@Wire
	private Spinner txtPeso1;
	@Wire
	private Spinner txtValorMeta;
	@Wire
	private Button btnCalcular;
	@Wire
	private Spinner txtValorResultado;
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
	private Button btnCancelarO;
	@Wire
	private Button btnOk2;
	@Wire
	private Button btnEliminar;
	@Wire
	private Button btnEliminar2;
	@Wire
	private Button btnEliminarIndicador;
	@Wire
	private Button btnAgregarIndicador;
	@Wire
	private Button btnGuardarIndicador;
	@Wire
	private Button btnIr;
	@Wire
	private Listbox lbxIndicadoresAgregados;
	@Wire
	private Listbox lbxObjetivosGuardados;
	@Wire
	private Listbox lbxAgregarIndicador;
	@Wire
	private Listbox lbxCompetenciaEspecifica;
	@Wire
	private Combobox cmbObjetivos;
	@Wire
	private Window window;
	@Wire
	private Groupbox gpxAgregar;
	@Wire
	private Groupbox gpxAgregados;
	@Wire
	private Groupbox gpxObjetivosAgregados;
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
	private Button btnCancelarI;
	@Wire
	private Button btnCalculo;
	@Wire
	private Button btnCambiarEstado;
	@Wire
	private Button btnAgregarF;
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
	private Window wdwConductasRectoras;
	@Wire
	private Window wdwConductasEspecificas;
	@Wire
	private Window winEvaluacionEmpleado;
	@Wire
	private Groupbox gpxListaPersonal;
	@Wire
	private Groupbox gpxAgregarIndicador;
	@Wire
	private Combobox cmbPerspectiva;
	@Wire
	private Combobox cmbUnidad;
	@Wire
	private Combobox cmbMedicion;
	@Wire
	private Button btnCancelarIndicador;
	@Wire
	private Button btnSalirCompetenciaR;
	@Wire
	private Button btnSalirCompetenciaE;
	@Wire
	private Button btnCancelarEvaluacion;
	@Wire
	private Panel panBotones;
	@Wire
	private Tab tbIndicadores;
	@Wire
	private Tab tbEvaluacionObjetivos;
	String tipo = "EVIDENCIADO";

	ListModelList<Dominio> dominio;
	ListModelList<Perspectiva> perspectiva;
	List<EvaluacionObjetivo> objetivosG = new ArrayList<EvaluacionObjetivo>();
	List<EvaluacionIndicador> indicadores = new ArrayList<EvaluacionIndicador>();
	List<EvaluacionObjetivo> evaluacionObjetivoIndicadores = new ArrayList<EvaluacionObjetivo>();

	private static Integer idEva;
	private static boolean bool = false;
	private static boolean bool1 = false;
	private static int idObjetivo;
	private static int idIndicador;
	private static String pers;
	private static String unid;
	private static String medic;
	public static Revision revision;
	private static String fichaE;
	public static Integer numero = 0;
	public static Integer idO = 0;
	public static EvaluacionObjetivo ev;
	public static EvaluacionIndicador evi;
	private static Double valor = 0.0;
	private static Double total = 0.0;
	private static Double totalObjetivo = 0.0;
	private static Double totalInd = 0.0;
	Usuario u;
	private static int idIndicadorE;
	private static int idObjetivoE;
	private Empleado empleado;
	private static double tind = 0.0;

	@Override
	public void inicializar() throws IOException {
		revision = servicioRevision.buscarPorEstado("ACTIVO");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("idEva") != null) {
				Integer idEvaluacion = (Integer) map.get("idEva");
				idEva = idEvaluacion;

				System.out.println("viene" + idEva);
				List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
				cmbPerspectiva.setModel(new ListModelList<Perspectiva>(
						perspectiva));
				cmbPerspectiva.setValue(perspectiva.get(0).getDescripcion());

				List<Medicion> medicion = servicioMedicion.buscar();
				cmbMedicion.setModel(new ListModelList<Medicion>(medicion));

				List<UnidadMedida> unidad = servicioUnidadMedida.buscar();
				cmbUnidad.setModel(new ListModelList<UnidadMedida>(unidad));

				fichaE = ficha;
				Integer numeroEvaluacion = servicioEvaluacion
						.buscarIdSecundario(ficha);
				numero = numeroEvaluacion;
				lblEvaluacion.setValue(numeroEvaluacion.toString());
				lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
				lblRevision.setValue(revision.getDescripcion());
				String nombreTrabajador = u.getNombre() + " " + u.getApellido();
				empleado = servicioEmpleado.buscarPorFicha(ficha);
				String cargo = empleado.getCargo().getDescripcion();
				String unidadOrganizativa = empleado.getUnidadOrganizativa()
						.getDescripcion();
				String gerenciaReporte = empleado.getUnidadOrganizativa()
						.getGerencia().getDescripcion();
				lblFicha.setValue(ficha);
				lblNombreTrabajador.setValue(nombreTrabajador);
				lblCargo.setValue(cargo);
				lblUnidadOrganizativa.setValue(unidadOrganizativa);
				lblGerencia.setValue(gerenciaReporte);
				lblFicha.setValue(ficha);
				lblNombreTrabajador.setValue(nombreTrabajador);
				lblCargo.setValue(cargo);
				lblUnidadOrganizativa.setValue(unidadOrganizativa);
				lblGerencia.setValue(gerenciaReporte);
				lblEvaluacion.setValue(numeroEvaluacion.toString());
				lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
			}

			else {

				HashMap<String, Object> map1 = (HashMap<String, Object>) Sessions
						.getCurrent().getAttribute("itemsCatalogo");
				if (map1 != null) {
					if (map1.get("ficha") != null) {
						String ficha1 = (String) map1.get("ficha");
						Integer idEvaluacion1 = (Integer) map1.get("id");
						fichaE = ficha1;
						idEva = idEvaluacion1;
						List<Medicion> medicion = servicioMedicion.buscar();
						cmbMedicion.setModel(new ListModelList<Medicion>(
								medicion));

						List<UnidadMedida> unidad = servicioUnidadMedida
								.buscar();
						cmbUnidad.setModel(new ListModelList<UnidadMedida>(
								unidad));
						empleado = servicioEmpleado.buscarPorFicha(ficha1);
						String cargo = empleado.getCargo().getDescripcion();
						String unidadOrganizativa = empleado
								.getUnidadOrganizativa().getDescripcion();
						String gerenciaReporte = empleado
								.getUnidadOrganizativa().getGerencia()
								.getDescripcion();
						String nombreTrabajador = empleado.getNombre();
						Integer numeroEvaluacion = servicioEvaluacion
								.buscarIdSecundario(fichaE);
						numero = numeroEvaluacion;
						System.out.println(numeroEvaluacion);
						lblFicha.setValue(ficha1);
						lblNombreTrabajador.setValue(nombreTrabajador);
						lblCargo.setValue(cargo);
						lblUnidadOrganizativa.setValue(unidadOrganizativa);
						lblGerencia.setValue(gerenciaReporte);
						lblEvaluacion.setValue(numeroEvaluacion.toString());
						lblFechaCreacion.setValue(formatoFecha
								.format(fechaHora));
						lblRevision.setValue(revision.getDescripcion());

					}
				}

			}
			List<NivelCompetenciaCargo> nivel = new ArrayList<NivelCompetenciaCargo>();
			List<NivelCompetenciaCargo> nivel2 = new ArrayList<NivelCompetenciaCargo>();
			List<NivelCompetenciaCargo> nivel3 = new ArrayList<NivelCompetenciaCargo>();
			List<NivelCompetenciaCargo> nivel4 = new ArrayList<NivelCompetenciaCargo>();
			NivelCompetenciaCargo nivelRectoras = new NivelCompetenciaCargo();
			NivelCompetenciaCargo nivelEspecificas = new NivelCompetenciaCargo();

			nivel = servicioNivelCompetenciaCargo.buscar(empleado.getCargo());
			for (int j = 0; j < nivel.size(); j++) {
				if (nivel.get(j).getCompetencia().getNivel().equals("RECTORAS")) {
					nivelRectoras = nivel.get(j);
					nivel2.add(nivelRectoras);
				} else {
					nivel.remove(j);
				}
				lbxCompetenciaRectora
						.setModel(new ListModelList<NivelCompetenciaCargo>(
								nivel2));
			}
			nivel4 = servicioNivelCompetenciaCargo.buscar(empleado.getCargo());
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

			gpxAgregar.setOpen(false);
			gpxAgregarIndicador.setOpen(false);
			txttotalIndicador.setValue(String.valueOf(tind));

		}
	}

	public ListModelList<Dominio> getDominio() {
		dominio = new ListModelList<Dominio>(
				servicioDominio.buscarPorTipo(tipo));
		return dominio;
	}

	@Listen("onClick = #btnEliminarIndicador")
	public void eliminarI() {
		eliminarIndicador();
	}

	@Listen("onClick = #btnCancelarO")
	public void cerrarPanel() {
		gpxAgregar.setOpen(false);
	}

	@Listen("onClick = #btnCancelarI")
	public void cerrarPanelI() {
		idIndicador = 0;
		limpiarI();
		gpxAgregarIndicador.setOpen(false);
	}

	@Listen("onClick = #btnEliminar")
	public void eliminarObj() {
		eliminarObjetivo();
	}

	@Listen("onClick = #tbIndicadores")
	public void mostrarObjetivos() {

		List<EvaluacionObjetivo> evaluacionObjetivo = servicioEvaluacionObjetivo
				.buscarObjetivos(fichaE, numero);
		cmbObjetivos.setModel(new ListModelList<EvaluacionObjetivo>(
				evaluacionObjetivo));

		bool1 = true;

	}

	@Listen("onClick = #tbEvaluacionObjetivos")
	public void ir() {
		cmbObjetivos.setValue(null);
		txtIndicador.setValue("0.0");
	}

	@Listen("onSelect = #cmbObjetivos")
	public void mostrarIndicadores() {
		tind = 0;
		txttotalIndicador.setValue("0.0");
		gpxAgregados.setOpen(true);
		lbxIndicadoresAgregados.getItems().clear();
		List<EvaluacionIndicador> evaluacionObjetivoIndicador = new ArrayList<EvaluacionIndicador>();
		Integer idObjetivo = Integer.parseInt(cmbObjetivos.getSelectedItem()
				.getContext());
		evaluacionObjetivoIndicador = servicioEvaluacionIndicador
				.buscarIndicadores(idObjetivo);
		lbxIndicadoresAgregados
				.setModel(new ListModelList<EvaluacionIndicador>(
						evaluacionObjetivoIndicador));

		// if (cmbObjetivos.getValue() != ""){
		// String id = cmbObjetivos.getContext();
		// int id1 = Integer.valueOf(id);
		EvaluacionObjetivo eo = servicioEvaluacionObjetivo
				.buscarObjetivosId(idObjetivo);
		txttotalIndicador.setValue("0.0");
		txttotalIndicador.setValue(String.valueOf(eo.getTotalInd()));
	}

	@Listen("onClick = #btnCancelar")
	public void salir() {
		cerrarVentana1(winEvaluacionEmpleado, "Personal");
		winEvaluacionEmpleado.onClose();

	}

	@Listen("onClick = #btnAgregar")
	public void AgregarObjetivo() {
		gpxAgregar.setOpen(true);
		List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
		cmbPerspectiva.setModel(new ListModelList<Perspectiva>(perspectiva));
		cmbPerspectiva.setValue(perspectiva.get(0).getDescripcion());
	}

	@Listen("onClick = #btnOk")
	public void AgregarObjetivo2() {
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
			EvaluacionObjetivo objetivoLista = new EvaluacionObjetivo();
			Integer linea = objetivosG.size() + 1;
			// idObjetivo = servicioEvaluacionObjetivo.buscarId() + 1;
			System.out.println(servicioEvaluacionObjetivo.buscarId());
			// objetivoLista.setIdObjetivo(idObjetivo);
			System.out.println("idobjetivo" + idObjetivo);
			objetivoLista.setIdEvaluacion(idEva);
			objetivoLista.setDescripcionObjetivo(objetivo);
			objetivoLista.setPerspectiva(perspectiva);
			objetivoLista.setLinea(linea);
			objetivoLista.setPeso(peso);
			objetivoLista.setResultado(0);
			objetivoLista.setTotalInd(0);
			objetivoLista.setCorresponsables(corresponsables);
			ev = objetivoLista;
			//
			// if (objetivosG.size() == 0) {
			// servicioEvaluacionObjetivo.guardar(objetivoLista);
			// objetivosG =
			// servicioEvaluacionObjetivo.buscarObjetivosEvaluar(idEva);
			// lbxObjetivosGuardados
			// .setModel(new ListModelList<EvaluacionObjetivo>(
			// objetivosG));
			//
			// gpxAgregar.setOpen(false);
			// Messagebox.show("Objetivos Guardados Exitosamente",
			// "Información", Messagebox.OK, Messagebox.INFORMATION);
			// limpiar();
			// } else {
			objetivosG.add(objetivoLista);
			cambiarEstado1();

			objetivosG = servicioEvaluacionObjetivo
					.buscarObjetivosEvaluar(idEva);
			lbxObjetivosGuardados
					.setModel(new ListModelList<EvaluacionObjetivo>(objetivosG));

		}
		// }

		idObjetivo = 0;

	}

	public void limpiar() {
		txtObjetivo.setValue("");
		cmbPerspectiva.setValue(null);
		txtCorresponsables.setValue("");
		txtPeso.setValue(null);

	}

	public void limpiarI() {
		txtIndicador.setValue("");
		txtPesoPorc.setValue(0);
		txtResFy.setValue(0);
		txtResultadoPorc.setValue(0);
		txtValorMeta.setValue(null);
		txtValorResultado.setValue(null);
		cmbMedicion.setValue(null);
		cmbUnidad.setValue(null);
		txtPeso1.setValue(null);
	}

	@Listen("onClick = #btnAgregarIndicador")
	public void AgregarIndicador() {

		idIndicador = 0;
		limpiarI();
		gpxAgregarIndicador.setOpen(true);

	}

	@Listen("onClick = #btnOk2")
	public void AgregarIndicador1() {
		String idObjetivo;

		if (cmbObjetivos.getText().compareTo("") == 0
				|| cmbUnidad.getText().compareTo("") == 0
				|| cmbMedicion.getText().compareTo("") == 0
				|| txtPeso1.getText().compareTo("") == 0
				// || txtValorMeta.getText().compareTo("") == 0
				|| txtIndicador.getText().compareTo("") == 0
				// || txtResultadoPorc.getText().compareTo("") == 0
				// || txtPesoPorc.getText().compareTo("") == 0
				|| txtValorMeta.getText().compareTo("") == 0)

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

				if (bool1 == false) {
					idObjetivo = String.valueOf(idO);
				} else {
					idObjetivo = cmbObjetivos.getSelectedItem().getContext();
				}
				Double peso = Double.valueOf(txtPeso1.getValue());
				Double valorMeta = Double.valueOf(txtValorMeta.getValue());
				Double valorResultado = Double.valueOf(txtValorResultado
						.getValue());
				Double resFy = Double.valueOf(txtResFy.getValue());
				Double resultadoPorc = Double.valueOf(txtResultadoPorc
						.getValue());
				Double pesoPorc = Double.valueOf(txtPesoPorc.getValue());
				Integer linea = indicadores.size() + 1;

				EvaluacionIndicador indicadorLista = new EvaluacionIndicador();
				indicadorLista.setIdIndicador(servicioEvaluacionIndicador
						.buscarId() + 1);
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
				evi = indicadorLista;
				System.out.println("entroelseee");
				indicadores.add(indicadorLista);
				System.out.println("size" + indicadores.size());
				cambiarEstado1();
				indicadores.remove(indicadorLista);
				indicadores = servicioEvaluacionIndicador
						.buscarIndicadores(Integer.parseInt(idObjetivo));
				for (int i = 0; i < indicadores.size(); i++) {
					int id = indicadores.get(i).getIdObjetivo();
					System.out.println("ido" + idO);
					System.out.println("id" + id);

					if (Integer.valueOf(idObjetivo) == id) {
						lbxIndicadoresAgregados
								.setModel(new ListModelList<EvaluacionIndicador>(
										indicadores));
					} else {
						indicadores.remove(indicadorLista);
					}
				}
				// }
			}

			idIndicador = 0;

		}
		evaluarIndicadores();
		txttotalIndicador.setValue(String.valueOf(tind));
	}

	// indicadores.add(indicadorLista);
	// lbxIndicadoresAgregados
	// .setModel(new ListModelList<EvaluacionIndicador>(
	// indicadores));
	// servicioEvaluacionIndicador.guardar(indicadorLista);
	// Messagebox.show("Indicador para el objetivo" + " "
	// + cmbObjetivos.getValue() + " "
	// + "ha sido guardado exitosamente", "Información",
	// Messagebox.OK, Messagebox.INFORMATION);
	// gpxAgregarIndicador.setOpen(false);
	// idIndicador=0;
	// limpiarI();
	// }
	// }
	// evaluarIndicadores();
	// }

	@Listen("onSelect = #cmbMedicion")
	public void mostrarResAnterior() {
		txtResFy.setDisabled(true);
		if (cmbMedicion.getValue().equals("CONTINUA")) {
			txtResFy.setDisabled(false);
		}
	}

	public void validar() {
		Evaluacion evaluacion = servicioEvaluacion.buscarEvaluacion(idEva);
		String ficha = evaluacion.getFicha();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size();
		List<EvaluacionObjetivo> evaluacionObjetivoIndicadores = servicioEvaluacionObjetivo
				.buscarObjetivosEvaluar(idEva);
		List<EvaluacionIndicador> evaluacionObjetivoIndicador = new ArrayList<EvaluacionIndicador>();
		if (evaluacionObjetivoIndicadores != null) {
			for (int i = 0; i < evaluacionObjetivoIndicadores.size(); i++) {
				int idObjetivo = evaluacionObjetivoIndicadores.get(i)
						.getIdObjetivo();
				evaluacionObjetivoIndicador = servicioEvaluacionIndicador
						.buscarIndicadores(idObjetivo);
				Double sumaPeso = (double) 0;
				for (int j = 0; j < evaluacionObjetivoIndicador.size(); j++) {
					Double peso = evaluacionObjetivoIndicador.get(j).getPeso();
					sumaPeso = peso + sumaPeso;
				}

				if (sumaPeso != 100) {
					bool = true;
					i = 1000000000;
				}
			}

		}

	}

	// @Listen("onClick = #btnCambiarEstado")
	public void cambiarEstado() {
		Evaluacion evaluacion = servicioEvaluacion.buscarEvaluacion(idEva);
		validar();
		Double sumaPeso = (double) 0;
		for (int j = 0; j < objetivosG.size(); j++) {
			Double peso = objetivosG.get(j).getPeso();
			sumaPeso = sumaPeso + peso;
		}
		if (sumaPeso != 100) {
			Messagebox
					.show("La suma de los pesos de los objetivos debe ser igual a 100",
							"Información", Messagebox.OK,
							Messagebox.INFORMATION);

		}

		else if (bool == true) {
			Messagebox
					.show("La suma de los pesos de los indicadores debe ser igual a 100",
							"Información", Messagebox.OK,
							Messagebox.INFORMATION);

		} else {

			// String estado = "PENDIENTE";
			// evaluacion.setEstadoEvaluacion(estado);
			// servicioEvaluacion.guardar(evaluacion);
			Messagebox.show("Evaluación guardada con exito", "Información",
					Messagebox.OK, Messagebox.INFORMATION);
		}

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
				// cmbPerspectiva.setDisabled(true);
			}
		}
	}

	@Listen("onClick = #btnIr")
	public void mostrarPestannaIndicadores() {
		mostrarObjetivos();
		tbIndicadores.setSelected(true);
		gpxAgregados.setOpen(true);
		if (lbxObjetivosGuardados.getItemCount() != 0) {
			Listitem listItem = lbxObjetivosGuardados.getSelectedItem();
			if (listItem != null) {
				EvaluacionObjetivo evaluacionObjetivo = (EvaluacionObjetivo) listItem
						.getValue();
				idObjetivo = evaluacionObjetivo.getIdObjetivo();
				List<EvaluacionObjetivo> evaluacionObjetivo1 = servicioEvaluacionObjetivo
						.buscarObjetivos(fichaE, numero);
				evaluacionObjetivoIndicadores = servicioEvaluacionObjetivo
						.buscarObjetivosEvaluar(idEva);
				System.out.println("objeto" + evaluacionObjetivoIndicadores);
				if (evaluacionObjetivo1.size() == 1) {
					cmbObjetivos.setValue(evaluacionObjetivoIndicadores.get(0)
							.getDescripcionObjetivo());
					idO = evaluacionObjetivoIndicadores.get(0).getIdObjetivo();

				}
				for (int i = 0; i < evaluacionObjetivoIndicadores.size(); i++) {
					Integer idOb = evaluacionObjetivoIndicadores.get(i)
							.getIdObjetivo();
					if (idOb == idObjetivo) {
						cmbObjetivos.setValue(evaluacionObjetivo1.get(i)
								.getDescripcionObjetivo());
						idO = evaluacionObjetivoIndicadores.get(i)
								.getIdObjetivo();
					}
				}
			}
			indicadores = servicioEvaluacionIndicador
					.buscarIndicadores(idObjetivo);
			lbxIndicadoresAgregados
					.setModel(new ListModelList<EvaluacionIndicador>(
							indicadores));
		}
		idObjetivo = 0;
	}

	@Listen("onDoubleClick = #lbxIndicadoresAgregados")
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
				txtValorResultado.setValue(valorR);
				txtResultadoPorc.setValue(resulPorc);
				txtPesoPorc.setValue(resulP);
				// cmbMedicion.setDisabled(true);
				// cmbUnidad.setDisabled(true);
			}
		}
	}

	private void EvaluacionObjetivoActualizar() {
		System.out.println("entroooooo");
		Perspectiva perspectiva1 = servicioPerspectiva.buscarNombre(pers);
		String objetivo = txtObjetivo.getValue();
		String corresponsables = txtCorresponsables.getValue();
		Double peso = Double.valueOf(txtPeso.getValue());
		String perspectivaCombo = cmbPerspectiva.getSelectedItem().getContext();
		Perspectiva perspectiva = servicioPerspectiva.buscarId(Integer
				.parseInt(perspectivaCombo));
		EvaluacionObjetivo objetivoLista = servicioEvaluacionObjetivo
				.buscarObjetivosId(idObjetivo);
		objetivoLista.setDescripcionObjetivo(objetivo);
		objetivoLista.setPerspectiva(perspectiva);
		objetivoLista.setPeso(peso);
		objetivoLista.setCorresponsables(corresponsables);
		servicioEvaluacionObjetivo.guardar(objetivoLista);
		List<EvaluacionObjetivo> evaluacionObje = servicioEvaluacionObjetivo
				.buscarObjetivosEvaluar(idEva);
		lbxObjetivosGuardados.getItems().clear();
		lbxObjetivosGuardados.setModel(new ListModelList<EvaluacionObjetivo>(
				evaluacionObje));
		gpxAgregar.setOpen(false);
		limpiar();
		idObjetivo = 0;
	}

	private void EvaluacionIndicadorActualizar() {
		String UnidadCombo = cmbUnidad.getSelectedItem().getContext();
		UnidadMedida unidad = servicioUnidadMedida.buscarUnidad(Integer
				.parseInt(UnidadCombo));
		String medicionCombo = cmbMedicion.getSelectedItem().getContext();
		Medicion medicion = servicioMedicion.buscarMedicion(Integer
				.parseInt(medicionCombo));

		EvaluacionIndicador indicador = servicioEvaluacionIndicador
				.buscarIndicadorId(idIndicador);
		indicador.setDescripcionIndicador(txtIndicador.getValue());
		indicador.setPeso(txtPeso1.getValue());
		indicador.setValorMeta(txtValorMeta.getValue());
		indicador.setResultadoFyAnterior(txtResFy.getValue());
		indicador.setUnidadMedida(unidad);
		indicador.setMedicion(medicion);
		servicioEvaluacionIndicador.guardar(indicador);
		List<EvaluacionIndicador> evaluacionInd = servicioEvaluacionIndicador
				.buscarIndicadores(idObjetivo);
		lbxIndicadoresAgregados.getItems().clear();
		lbxIndicadoresAgregados
				.setModel(new ListModelList<EvaluacionIndicador>(evaluacionInd));
		gpxAgregarIndicador.setOpen(false);
	}

	// @Listen("onSelect = #cmbMedicion")
	// public void mostrarResAnterior() {
	// txtResFy.setDisabled(true);
	// if (cmbMedicion.getValue().equals("CONTINUA")) {
	// System.out.println("entroooooooooooo");
	// txtResFy.setDisabled(false);
	// }
	// }

	@Listen("onDoubleClick = #lbxCompetenciaEspecifica")
	public void mostrarDatosCatalogo() {

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
					map.put("idEva", idEva);
					map.put("id", competencia.getCompetencia().getId());
					map.put("idnivel", nivel);
					map.put("titulo", titulo);
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					wdwConductasEspecificas = (Window) Executions
							.createComponents(
									"/vistas/transacciones/VEvaluacionConductas.zul",
									null, map);
					wdwConductasEspecificas.doModal();
				}

			}
		}
	}

	@Listen("onDoubleClick = #lbxCompetenciaRectora")
	public void mostrarDatosCatalogo1() {

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

					NivelCompetenciaCargo competencia = (NivelCompetenciaCargo) listItem
							.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("idEva", idEva);
					System.out.println("va" + idEva);
					map.put("id", competencia.getCompetencia().getId());
					map.put("idnivel", nivel);
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

	@Listen("onClick = #btnCambiarEstado")
	public void Guardar() {
		String fortalezas = txtFortalezas.getValue();
		String oportunidades = txtOportunidades.getValue();
		String resumen = txtResumen.getValue();
		String compromisos = txtCompromisos.getValue();
		Integer idEvaluacion = Integer.parseInt(lblEvaluacion.getValue());
		Evaluacion evaluacionEmpleado = new Evaluacion();
		evaluacionEmpleado = servicioEvaluacion.buscarIdEvaluacion(
				idEvaluacion, fichaE);
		evaluacionEmpleado.setCompromisos(compromisos);
		evaluacionEmpleado.setFortalezas(fortalezas);
		evaluacionEmpleado.setOportunidades(oportunidades);
		evaluacionEmpleado.setResumen(resumen);

		servicioEvaluacion.guardar(evaluacionEmpleado);
		Messagebox.show("Feedback y Compromisos Registrados Exitosamente",
				"Información", Messagebox.OK, Messagebox.INFORMATION);
		limpiar();
	}

	private void eliminarIndicador() {
		if (lbxIndicadoresAgregados.getItemCount() != 0) {
			Listitem listItem = lbxIndicadoresAgregados.getSelectedItem();
			if (listItem != null) {
				EvaluacionIndicador evaluacionIndicador = (EvaluacionIndicador) listItem
						.getValue();
				idIndicadorE = evaluacionIndicador.getIdIndicador();
				idObjetivoE = evaluacionIndicador.getIdObjetivo();
				Messagebox.show("Desea Eliminar el Indicador", "Alerta",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {

							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									servicioEvaluacionIndicador
											.eliminarUno(idIndicadorE);
									msj.mensajeInformacion(Mensaje.eliminado);
									lbxIndicadoresAgregados.getItems().clear();
									indicadores = servicioEvaluacionIndicador
											.buscarIndicadores(idObjetivoE);
									lbxIndicadoresAgregados
											.setModel(new ListModelList<EvaluacionIndicador>(
													indicadores));
								}
								evaluarIndicadores();

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
				evaluacionObjetivo = listItem.getValue();

				idObjetivoE = evaluacionObjetivo.getIdObjetivo();
				System.out.println("idObjetivoE" + idObjetivoE);
				List<EvaluacionIndicador> evaluacionIndicador = servicioEvaluacionIndicador
						.buscarIndicadores(idObjetivoE);
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
														.buscarIndicadores(idObjetivoE);
												servicioEvaluacionIndicador
														.eliminarVarios(evaluacionIndicador);
												servicioEvaluacionObjetivo
														.eliminarUno(idObjetivoE);
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

					idObjetivoE = evaluacionObjetivo.getIdObjetivo();
					Messagebox.show("Desea Eliminar el Objetivo", "Alerta",
							Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										servicioEvaluacionObjetivo
												.eliminarUno(idObjetivoE);
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

	public void validar1() {
		System.out
				.println("enttroooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		Evaluacion evaluacion = servicioEvaluacion.buscarEvaluacion(idEva);
		String ficha = evaluacion.getFicha();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size();
		List<EvaluacionObjetivo> evaluacionObjetivoIndicadores = servicioEvaluacionObjetivo
				.buscarObjetivosEvaluar(idEva);
		List<EvaluacionIndicador> evaluacionObjetivoIndicador = new ArrayList<EvaluacionIndicador>();
		if (evaluacionObjetivoIndicadores != null) {
			for (int i = 0; i < evaluacionObjetivoIndicadores.size(); i++) {
				int idObjetivo = evaluacionObjetivoIndicadores.get(i)
						.getIdObjetivo();
				evaluacionObjetivoIndicador = servicioEvaluacionIndicador
						.buscarIndicadores(idObjetivo);
				Double sumaPeso = 0.0;
				for (int j = 0; j < indicadores.size(); j++) {
					System.out.println("ojoooooooooo");
					Double peso = indicadores.get(j).getPeso();
					System.out.println("pesooo" + peso);
					sumaPeso = peso + sumaPeso;
					System.out.println("sumapeso" + sumaPeso);
				}

				if (sumaPeso > 100) {
					bool = true;
					i = 1000000000;
				}
			}

		}

	}

	public void cambiarEstado1() {
		System.out.println("METODO");
		Evaluacion evaluacion = servicioEvaluacion.buscarEvaluacion(idEva);
		validar1();
		Double sumaPeso = (double) 0;
		for (int j = 0; j < objetivosG.size(); j++) {
			Double peso = objetivosG.get(j).getPeso();
			sumaPeso = sumaPeso + peso;
			System.out.println("SUMA" + sumaPeso);
		}
		if (sumaPeso > 100) {
			Messagebox
					.show("La suma de los pesos de los objetivos no debe ser mayor a 100",
							"Información", Messagebox.OK,
							Messagebox.INFORMATION);
			objetivosG.remove(ev);

		}

		else if (bool == true) {
			Messagebox
					.show("La suma de los pesos de los indicadores no debe ser mayor a 100",
							"Información", Messagebox.OK,
							Messagebox.INFORMATION);
			indicadores.remove(evi);

		} else {
			if (evi != null) {
				servicioEvaluacionIndicador.guardar(evi);
				gpxAgregarIndicador.setOpen(false);
				limpiarI();
				Messagebox.show("Guardado Exitosamente", "Información",
						Messagebox.OK, Messagebox.INFORMATION);
			} else {
				System.out.println("ojooooooooooooooooo");
				servicioEvaluacionObjetivo.guardar(ev);
				Messagebox.show("Guardado Exitosamente", "Información",
						Messagebox.OK, Messagebox.INFORMATION);
				gpxAgregar.setOpen(false);
				limpiar();

			}
		}
		bool = false;
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
				System.out.println(idIndicador);
				indicador.setValorResultado(valorResultado);
				indicador.setResultadoFyAnterior(resultadoFyAnterior);
				indicador.setResultadoPorc(Double.parseDouble(resultado));
				indicador.setResultadoPeso(Double.parseDouble(resultadoPeso));
				indicador.setTotal(total);
				servicioEvaluacionIndicador.guardar(indicador);

			}
			guardarObjetivos();
			guardarEvaluacion();
			// Messagebox.show("Datos guardados exitosamente", "Información",
			// Messagebox.OK, Messagebox.INFORMATION);
		}

	}

	public void evaluarIndicadores() {
		Integer resultado1 = 0;
		lbxIndicadoresAgregados.renderAll();
		for (int i = 0; i < lbxIndicadoresAgregados.getItems().size(); i++) {
			List<Listitem> listItem2 = lbxIndicadoresAgregados.getItems();
			EvaluacionIndicador EvaluacionI = listItem2.get(i).getValue();
			Listitem listItem = lbxIndicadoresAgregados.getItemAtIndex(i);
			Integer valorResultado = ((Spinner) ((listItem.getChildren().get(6)))
					.getFirstChild()).getValue();
			Integer resultadoFyAnterior = ((Spinner) ((listItem.getChildren()
					.get(7))).getFirstChild()).getValue();
			// String resultado = String.valueOf(txtResultadoPorc.getValue());
			String resultado = ((Textbox) ((listItem.getChildren().get(8)))
					.getFirstChild()).getValue();
			// String resultadoPeso = String.valueOf(txtPesoPorc.getValue());
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
				resultado1 = calcularPorcentajeMetaIndicado(valor1);
				System.out.println("resultado" + resultado);
			}

			resultadoPeso = String.valueOf((Double.parseDouble(resultado)
					* (EvaluacionI.getPeso()) / 100));
			double resul = ((Double.parseDouble(resultado)
					* (EvaluacionI.getPeso()) / 100));
			// Integer resul1 = Integer.
			// txtResultadoPorc.setValue(resultado1);
			// txtPesoPorc.setValue(resul);
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
		guardarIndicadores();
		totalObjetivo = 0.0;
		tind = totalInd;
		totalInd = 0.0;
		total = 0.0;
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

	public void guardarEvaluacion() {
		System.out.println("entrooo");
		Evaluacion evalua = servicioEvaluacion.buscarEvaluacion(idEva);
		System.out.println(evalua);
		System.out.println(idEva);
		evalua.setFechaRevision(fechaHora);
		evalua.setFichaEvaluador(u.getCedula());
		evalua.setIdUsuario(u.getIdUsuario());
		evalua.setResultadoObjetivos(totalObjetivo.intValue());
		servicioEvaluacion.guardar(evalua);
	}

	@Listen("onClick = #btnCancelar,  #btnCancelarIndicador, #btnSalirCompetenciaR, #btnSalirCompetenciaE, #btnCancelarEvaluacion")
	public void salir1() {
		winEvaluacionEmpleado.onClose();
	}

}
