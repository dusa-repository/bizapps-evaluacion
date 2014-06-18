package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import modelo.seguridad.Usuario;
import modelos.Competencia;
import modelos.ConductaCompetencia;
import modelos.Dominio;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.EvaluacionConducta;
import modelos.EvaluacionIndicador;
import modelos.EvaluacionObjetivo;
import modelos.Medicion;
import modelos.NivelCompetenciaCargo;
import modelos.Perspectiva;
import modelos.UnidadMedida;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
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
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CEvaluacionEnEdicion extends CGenerico {

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
	private Textbox txtPeso;
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
	String tipo = "EVIDENCIADO";

	ListModelList<Dominio> dominio;
	ListModelList<Perspectiva> perspectiva;
	List<EvaluacionObjetivo> objetivosG = new ArrayList<EvaluacionObjetivo>();
	List<EvaluacionIndicador> indicadores = new ArrayList<EvaluacionIndicador>();

	@Override
	public void inicializar() throws IOException {

		List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
		cmbPerspectiva.setModel(new ListModelList<Perspectiva>(perspectiva));
		
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

				System.out.println(idEvaluacion);

				Evaluacion evaluacion = servicioEvaluacion
						.buscarEvaluacion(idEvaluacion);
				txtCompromisos.setValue(evaluacion.getCompromisos());
				txtFortalezas.setValue(evaluacion.getFortalezas());
				txtOportunidades.setValue(evaluacion.getOportunidades());
				txtResumen.setValue(evaluacion.getResumen());
				Authentication auth = SecurityContextHolder.getContext()
						.getAuthentication();
				String ficha = evaluacion.getFicha();
				Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha)
						.size();
				Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
				String cargo = empleado.getCargo().getDescripcion();
				String unidadOrganizativa = empleado.getUnidadOrganizativa()
						.getDescripcion();
				String gerenciaReporte = empleado.getUnidadOrganizativa()
						.getGerencia().getDescripcion();
				String nombreTrabajador = empleado.getNombre();
				
				// Combo de Objetivos 
				List<EvaluacionObjetivo> evaluacionObjetivo = servicioEvaluacionObjetivo.buscarObjetivos(ficha, numeroEvaluacion);
				cmbObjetivos.setModel(new ListModelList<EvaluacionObjetivo>(evaluacionObjetivo));

				// Listbox que contiene los objetivos
				objetivosG = servicioEvaluacionObjetivo
						.buscarObjetivosEvaluar(idEvaluacion);
				lbxObjetivosGuardados
						.setModel(new ListModelList<EvaluacionObjetivo>(
								objetivosG));

				// Listbox que contine los indicadores
				List<EvaluacionObjetivo> evaluacionObjetivoIndicadores = servicioEvaluacionObjetivo
						.buscarObjetivosEvaluar(idEvaluacion);
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
				lblFechaCreacion.setValue(fechaHora.toString());
			}
		}
		gpxAgregar.setOpen(false);
		gpxObjetivosAgregados.setOpen(true);
		gpxAgregarIndicador.setOpen(false);
		gpxAgregados.setOpen(false);
	}

	public ListModelList<Dominio> getDominio() {
		dominio = new ListModelList<Dominio>(
				servicioDominio.buscarPorTipo(tipo));
		return dominio;
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

		winEvaluacionEmpleado.onClose();
	}

	@Listen("onClick = #btnAgregar")
	public void AgregarObjetivo() {
		gpxAgregar.setOpen(true);
	}

	@Listen("onClick = #btnOk")
	public void AgregarObjetivo2() {
		gpxAgregados.setOpen(true);
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		Integer idUsuario = u.getIdUsuario();
		Integer numeroEvaluacion = Integer.parseInt(lblEvaluacion.getValue());
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				Integer idEvaluacion = (Integer) map.get("id");
				String fichaMap = (String) map.get("titulo");

				Evaluacion evaluacion = servicioEvaluacion
						.buscarEvaluacion(idEvaluacion);
				evaluacion.setIdEvaluacion(idEvaluacion);
				evaluacion.setEstadoEvaluacion("EN EDICION");
				evaluacion.setFechaCreacion(fechaHora);
				evaluacion.setFicha(ficha);
				evaluacion.setIdEvaluacionSecundario(numeroEvaluacion);
				evaluacion.setIdUsuario(idUsuario);
				evaluacion.setPeso(0);
				evaluacion.setResultado(0);
				evaluacion.setResultadoObjetivos(0);
				evaluacion.setResultadoGeneral(0);
				evaluacion.setResultadoFinal(0);
				if (objetivosG.size() == 0) {
					Messagebox.show("Debe agregar sus objetivos",
							"Advertencia", Messagebox.OK,
							Messagebox.EXCLAMATION);
				} else {
					String perspectivaCombo = cmbPerspectiva.getSelectedItem()
							.getContext();
					System.out.println(perspectivaCombo);
					Perspectiva perspectiva = servicioPerspectiva
							.buscarId(Integer.parseInt(perspectivaCombo));
					String objetivo = txtObjetivo.getValue();
					String corresponsables = txtCorresponsables.getValue();
					Double peso = Double.parseDouble(txtPeso.getValue());
					EvaluacionObjetivo objetivoLista = new EvaluacionObjetivo();
					Integer linea = objetivosG.size() + 1;
					objetivoLista.setIdEvaluacion(idEvaluacion);
					objetivoLista.setDescripcionObjetivo(objetivo);
					objetivoLista.setPerspectiva(perspectiva);
					objetivoLista.setLinea(linea);
					objetivoLista.setPeso(peso);
					objetivoLista.setResultado(0);
					objetivoLista.setTotalInd(0);
					objetivoLista.setCorresponsables(corresponsables);
					objetivosG.add(objetivoLista);
					lbxObjetivosGuardados
							.setModel(new ListModelList<EvaluacionObjetivo>(
									objetivosG));
					servicioEvaluacionObjetivo.guardar(objetivoLista);
					gpxAgregar.setOpen(false);

				}
				servicioEvaluacion.guardar(evaluacion);
				Messagebox.show("Objetivos Guardados Exitosamente",
						"Informaci�n", Messagebox.OK, Messagebox.INFORMATION);

				limpiar();
			}
		}
	}

	public void limpiar() {
		txtObjetivo.setValue("");
		cmbPerspectiva.setValue(null);
		txtCorresponsables.setValue("");
		txtIndicador.setValue("");
		txtPeso.setValue(null);
		txtPesoPorc.setValue(null);
		txtResFy.setValue(null);
		txtResultadoPorc.setValue(null);
		txtValorMeta.setValue(null);
		txtValorResultado.setValue(null);
		cmbMedicion.setValue(null);
		cmbUnidad.setValue(null);
	}
	
	@Listen("onClick = #btnAgregarIndicador")
	public void AgregarIndicador() {
		gpxAgregarIndicador.setOpen(true);
	}

	@Listen("onClick = #btnOk2")
	public void AgregarIndicador1() {
		
		if (cmbObjetivos.getText().compareTo("")==0 || cmbUnidad.getText().compareTo("")==0 
		|| cmbMedicion.getText().compareTo("")==0 || txtPeso1.getText().compareTo("")==0 
		|| txtValorMeta.getText().compareTo("")==0 || txtResFy.getText().compareTo("")==0 || txtIndicador.getText().compareTo("")==0
		|| txtResultadoPorc.getText().compareTo("")==0 || txtPesoPorc.getText().compareTo("")==0 || txtValorMeta.getText().compareTo("")==0)
		
		{
			Messagebox.show("Debe llenar todos los campos",
					"Advertencia", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}	else {
			gpxAgregados.setOpen(true);
		String indicador = txtIndicador.getValue();
		String unidadCombo= cmbUnidad.getSelectedItem().getContext();
		UnidadMedida  unidad = servicioUnidadMedida.buscarUnidad(Integer.parseInt(unidadCombo));
		String medicionCombo= cmbMedicion.getSelectedItem().getContext();
		Medicion medicion = servicioMedicion.buscarMedicion(Integer.parseInt(medicionCombo));
		String idObjetivo = cmbObjetivos.getSelectedItem().getContext();
		Double peso = Double.valueOf(txtPeso1.getValue());
		Double valorMeta = Double.valueOf(txtValorMeta.getValue());
		Double valorResultado = Double.valueOf(txtValorResultado.getValue());
		Double resFy = Double.valueOf(txtResFy.getValue());
		Double resultadoPorc = Double.valueOf(txtResultadoPorc.getValue());
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
		lbxIndicadoresAgregados.setModel(new ListModelList<EvaluacionIndicador>(
				indicadores));
		servicioEvaluacionIndicador.guardar(indicadorLista);
		Messagebox.show("Indicador para el objetivo" + " " + cmbObjetivos.getValue() + " " + "ha sido guardado exitosamente",
				"Informaci�n", Messagebox.OK,
				Messagebox.INFORMATION);
		gpxAgregar.setOpen(false);
		
		limpiar ();
		}
	}
	
	

}