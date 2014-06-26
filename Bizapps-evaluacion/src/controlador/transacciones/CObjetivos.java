package controlador.transacciones;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.Perspectiva;
import modelo.maestros.Revision;
import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import sun.util.calendar.BaseCalendar.Date;

import arbol.MArbol;
import arbol.Nodos;

import controlador.maestros.CGenerico;
import componentes.Mensaje;
import componentes.Validador;

public class CObjetivos extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

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
	private Button btnEliminar;
	@Wire
	private Button btnOk;
	@Wire
	private Button btnGuardar;
	@Wire
	private Listbox lbxEmpleado;
	@Wire
	private Listbox lbxObjetivos;
	@Wire
	private Listbox lbxObjetivosGuardados;
	@Wire
	private Label lblEvaluacion;
	@Wire
	private Label lblFechaCreacion;
	@Wire
	private Label lblRevision;
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
	private Combobox cmbPerspectiva;
	@Wire
	private Window window;
	@Wire
	private Groupbox gpxAgregar;
	@Wire
	private Groupbox gpxAgregados;
	
	List<EvaluacionObjetivo> objetivosG = new ArrayList<EvaluacionObjetivo>();
	ListModelList<Perspectiva> perspectiva;
	public static Revision revision;
	private static int idObjetivo ;
	private static String pers;
	private static int idEva;

	@Override
	public void inicializar() throws IOException {
		
		revision = servicioRevision.buscarPorEstado("ACTIVO");
		 List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
		 cmbPerspectiva.setModel(new ListModelList<Perspectiva>(perspectiva));
		 cmbPerspectiva.setValue(perspectiva.get(0).getDescripcion());
		 
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size() + 1;
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
		gpxAgregar.setOpen(false);
		gpxAgregados.setOpen(false);
		
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
			Integer idEvaluacion = servicioEvaluacion.buscarId();
			idEva = idEvaluacion;
			Integer evaluacion = Integer.parseInt(lblEvaluacion.getValue());
			Double peso = Double.valueOf(txtPeso.getValue());
			
			if (objetivosG.size() == 0) {
			idEvaluacion = idEvaluacion + 1; 
			idEva = idEvaluacion;
			Evaluacion evaluacionEmpleado = new Evaluacion();
			evaluacionEmpleado.setIdEvaluacion(idEvaluacion);
			evaluacionEmpleado.setEstadoEvaluacion("EN EDICION");
			evaluacionEmpleado.setFechaCreacion(fechaHora);
			evaluacionEmpleado.setFicha(ficha);
			evaluacionEmpleado.setIdEvaluacionSecundario(evaluacion);
			evaluacionEmpleado.setIdUsuario(idUsuario);
			evaluacionEmpleado.setPeso(peso);
			evaluacionEmpleado.setResultado(0);
			evaluacionEmpleado.setResultadoObjetivos(0);
			evaluacionEmpleado.setResultadoGeneral(0);
			evaluacionEmpleado.setResultadoFinal(0);
			evaluacionEmpleado.setRevision(revision);
			servicioEvaluacion.guardar(evaluacionEmpleado);
			}
			if (idObjetivo != 0) {
				EvaluacionObjetivoActualizar();
			} else {
			
		 String perspectivaCombo= cmbPerspectiva.getSelectedItem().getContext();
		 System.out.println(perspectivaCombo);
		 Perspectiva perspectiva = servicioPerspectiva.buscarId(Integer.parseInt(perspectivaCombo));
		 String objetivo =txtObjetivo.getValue();
		 String corresponsables = txtCorresponsables.getValue();
		 Double peso1 = Double.valueOf(txtPeso.getValue());
		 EvaluacionObjetivo objetivoLista = new EvaluacionObjetivo ();
		 Integer linea = objetivosG.size() + 1;
		 objetivoLista.setIdObjetivo(servicioEvaluacionObjetivo.buscarId()+1);
		 objetivoLista.setDescripcionObjetivo(objetivo);
		 objetivoLista.setIdEvaluacion(idEvaluacion);
		 objetivoLista.setPerspectiva(perspectiva);	 
		 objetivoLista.setLinea(linea);
		 objetivoLista.setPeso(peso1);
		 objetivoLista.setResultado(0);
		 objetivoLista.setTotalInd(0);
		 objetivoLista.setCorresponsables(corresponsables);
		 objetivosG.add(objetivoLista);
		 lbxObjetivosGuardados.setModel(new ListModelList<EvaluacionObjetivo>(objetivosG));
		 servicioEvaluacionObjetivo.guardar(objetivoLista);
			Messagebox.show("Objetivo Guardado Exitosamente",
					"Información", Messagebox.OK,
					Messagebox.INFORMATION);
			
			limpiar ();
		 gpxAgregar.setOpen(false);
		 limpiar ();
	
	}
	}

	public void limpiar() {
		 txtObjetivo.setValue("");
		 cmbPerspectiva.setValue(null);
		 txtCorresponsables.setValue("");
		 txtPeso.setValue(null);
	}
	
	@Listen("onClick = #lbxObjetivosGuardados")
	public void mostrarEvaluacion() {
		gpxAgregar.setOpen(true);
		if (lbxObjetivosGuardados.getItemCount() != 0) {
			Listitem listItem = lbxObjetivosGuardados.getSelectedItem();
			if (listItem != null) {
				EvaluacionObjetivo evaluacionObjetivo = (EvaluacionObjetivo) listItem
						.getValue();
				idObjetivo = evaluacionObjetivo.getIdObjetivo();
				System.out.println(evaluacionObjetivo.getDescripcionObjetivo() + evaluacionObjetivo.getIdObjetivo() +
						evaluacionObjetivo.getIdEvaluacion());
				System.out.println("PORFAAAAA"+evaluacionObjetivo.getIdObjetivo());
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
		System.out.println("entrooooooooooooooooooooooooooooooooooooooooooooo");
		Perspectiva perspectiva1 = servicioPerspectiva.buscarNombre(pers);
		String objetivo = txtObjetivo.getValue();
		String corresponsables = txtCorresponsables.getValue();
		Double peso = Double.valueOf(txtPeso.getValue());
		EvaluacionObjetivo objetivoLista = servicioEvaluacionObjetivo
				.buscarObjetivosId(idObjetivo);
		System.out.println(idObjetivo);
		System.out.println(objetivoLista);
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

}