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
import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CAgregarEvaluacion extends CGenerico {

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
	private Textbox txtIndicador;
	@Wire
	private Spinner txtPeso1;
	@Wire
	private Spinner txtValorMeta;
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
	private Panel panBotones;
	@Wire
	private Tab tbIndicadores;
	String tipo = "EVIDENCIADO";

	ListModelList<Dominio> dominio;
	ListModelList<Perspectiva> perspectiva;
	List<EvaluacionObjetivo> objetivosG = new ArrayList<EvaluacionObjetivo>();
	List<EvaluacionIndicador> indicadores = new ArrayList<EvaluacionIndicador>();

	private static Integer idEva;
	private static boolean bool = false;
	private static int idObjetivo;
	private static int idIndicador;
	private static String pers;
	private static String unid;
	private static String medic;
	public static Revision revision;
	private static String fichaE;
	public static Integer numero = 0;


	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("idEva") != null) {
				Integer idEvaluacion = (Integer) map.get("idEva");
				idEva = idEvaluacion;
			}
		}
		
		System.out.println("viene" +idEva);
		List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
		cmbPerspectiva.setModel(new ListModelList<Perspectiva>(perspectiva));
		cmbPerspectiva.setValue(perspectiva.get(0).getDescripcion());
		revision = servicioRevision.buscarPorEstado("ACTIVO");
		List<Medicion> medicion = servicioMedicion.buscar();
		cmbMedicion.setModel(new ListModelList<Medicion>(medicion));

		List<UnidadMedida> unidad = servicioUnidadMedida.buscar();
		cmbUnidad.setModel(new ListModelList<UnidadMedida>(unidad));

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		fichaE = ficha;
		Integer numeroEvaluacion = servicioEvaluacion.buscarIdSecundario(ficha);
		numero = numeroEvaluacion;
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
		lblRevision.setValue(revision.getDescripcion());
		String nombreTrabajador = u.getNombre() + " " + u.getApellido();
		Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
		String cargo = empleado.getCargo().getDescripcion();
		String unidadOrganizativa = empleado.getUnidadOrganizativa()
				.getDescripcion();
		String gerenciaReporte = empleado.getUnidadOrganizativa().getGerencia()
				.getDescripcion();
		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);
		// Combo de Objetivos

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
					.setModel(new ListModelList<NivelCompetenciaCargo>(nivel2));
		}
		nivel4 = servicioNivelCompetenciaCargo.buscar(empleado.getCargo());
		for (int j = 0; j < nivel4.size(); j++) {
			if (nivel4.get(j).getCompetencia().getNivel().equals("ESPECIFICAS")) {
				nivelEspecificas = nivel4.get(j);
				nivel3.add(nivelEspecificas);
			} else {
				nivel4.remove(j);
			}
			lbxCompetenciaEspecifica
					.setModel(new ListModelList<NivelCompetenciaCargo>(nivel3));
		}

		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
		gpxAgregar.setOpen(false);
		gpxAgregarIndicador.setOpen(false);
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

	@Listen("onClick = #btnEliminar")
	public void eliminarObj() {
		eliminarObjetivo ();
	}
	
	@Listen("onClick = #cmbObjetivos")
	public void mostrarObjetivos() {
		List<EvaluacionObjetivo> evaluacionObjetivo = servicioEvaluacionObjetivo
				.buscarObjetivos(fichaE, numero);
		System.out.println(fichaE);
		System.out.println(numero);
		System.out.println(evaluacionObjetivo);
		cmbObjetivos.setModel(new ListModelList<EvaluacionObjetivo>(
				evaluacionObjetivo));
	}

	@Listen("onSelect = #cmbObjetivos")
	public void mostrarIndicadores() {
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

	}

	@Listen("onClick = #btnCancelar")
	public void salir() {
		cerrarVentana1(winEvaluacionEmpleado, "Personal");
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
			objetivoLista.setIdObjetivo(servicioEvaluacionObjetivo.buscarId() + 1);
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
			

		}
		gpxAgregar.setOpen(false);
		Messagebox.show("Objetivos Guardados Exitosamente", "Información",
				Messagebox.OK, Messagebox.INFORMATION);

		limpiar();
		
	}


	public void limpiar() {
		txtObjetivo.setValue("");
		cmbPerspectiva.setValue(null);
		txtCorresponsables.setValue("");
		txtPeso.setValue(null);
		
	}
	
	public void limpiarI (){
		txtIndicador.setValue("");
		txtPesoPorc.setValue(0);
		txtResFy.setValue(0);
		txtResultadoPorc.setValue(0);
		txtValorMeta.setValue(null);
		txtValorResultado.setValue(0);
		cmbMedicion.setValue(null);
		cmbUnidad.setValue(null);
		txtPeso1.setValue(null);
	}

	@Listen("onClick = #btnAgregarIndicador")
	public void AgregarIndicador() {
		gpxAgregarIndicador.setOpen(true);
	}

	@Listen("onClick = #btnOk2")
	public void AgregarIndicador1() {
		
		if (cmbObjetivos.getText().compareTo("") == 0
				|| cmbUnidad.getText().compareTo("") == 0
				|| cmbMedicion.getText().compareTo("") == 0
				|| txtPeso1.getText().compareTo("") == 0
				|| txtValorMeta.getText().compareTo("") == 0
				|| txtIndicador.getText().compareTo("") == 0
				|| txtResultadoPorc.getText().compareTo("") == 0
				|| txtPesoPorc.getText().compareTo("") == 0
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
				String idObjetivo = cmbObjetivos.getSelectedItem().getContext();
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
				indicadorLista.setIdIndicador(servicioEvaluacionIndicador.buscarId()+1);
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
				gpxAgregarIndicador.setOpen(false);

				limpiarI();
			}
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

	@Listen("onClick = #btnCambiarEstado")
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

			String estado = "PENDIENTE";
			evaluacion.setEstadoEvaluacion(estado);
			servicioEvaluacion.guardar(evaluacion);
			Messagebox.show("La evaluación ahora esta pendiente por revisar",
					"Información", Messagebox.OK, Messagebox.INFORMATION);
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
				cmbPerspectiva.setDisabled(true);
			}
		}
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
				cmbMedicion.setDisabled(true);
				cmbUnidad.setDisabled(true);
			}
		}
	}

	private void EvaluacionObjetivoActualizar() {
		System.out.println("entroooooo");
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

	@Listen("onSelect = #cmbMedicion")
	public void mostrarResAnterior() {
		txtResFy.setDisabled(true);
		if (cmbMedicion.getValue().equals("CONTINUA")) {
			System.out.println("entroooooooooooo");
			txtResFy.setDisabled(false);
		}
	}

	
	public void mostrarPestannaIndicadores() {
		tbIndicadores.setSelected(true);
		gpxAgregados.setOpen(true);
		if (lbxObjetivosGuardados.getItemCount() != 0) {
			Listitem listItem = lbxObjetivosGuardados.getSelectedItem();
			if (listItem != null) {
				EvaluacionObjetivo evaluacionObjetivo = (EvaluacionObjetivo) listItem
						.getValue();
				idObjetivo = evaluacionObjetivo.getIdObjetivo();
				indicadores = servicioEvaluacionIndicador
						.buscarIndicadores(idObjetivo);
				lbxIndicadoresAgregados
						.setModel(new ListModelList<EvaluacionIndicador>(
								indicadores));
			}
		}
	}

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
	
	@Listen("onClick = #btnAgregarF")
	public void Guardar() {	
		String fortalezas = txtFortalezas.getValue();
		String oportunidades = txtOportunidades.getValue();
		String resumen = txtResumen.getValue();
		String compromisos = txtCompromisos.getValue();
		Integer idEvaluacion = Integer.parseInt(lblEvaluacion.getValue());
		Evaluacion evaluacionEmpleado = new Evaluacion();
		evaluacionEmpleado = servicioEvaluacion.buscarIdEvaluacion(idEvaluacion, fichaE);
		evaluacionEmpleado.setCompromisos(compromisos);
		evaluacionEmpleado.setFortalezas(fortalezas);
		evaluacionEmpleado.setOportunidades(oportunidades);
		evaluacionEmpleado.setResumen(resumen);

		servicioEvaluacion.guardar(evaluacionEmpleado);
		Messagebox.show("Feedback y Compromisos Registrados Exitosamente",
				"Información", Messagebox.OK,
					Messagebox.INFORMATION);
			limpiar ();
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
									indicadores = servicioEvaluacionIndicador.buscarIndicadores(idObjetivo);
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
												objetivosG = servicioEvaluacionObjetivo.buscarObjetivosEvaluar(idEva);
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
										objetivosG = servicioEvaluacionObjetivo.buscarObjetivosEvaluar(idEva);
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
	

}

