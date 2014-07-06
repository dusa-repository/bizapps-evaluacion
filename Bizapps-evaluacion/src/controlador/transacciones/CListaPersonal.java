package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.maestros.Bitacora;
import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionIndicador;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.NivelCompetenciaCargo;
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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
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

public class CListaPersonal extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	private Button btnAgregar;
	@Wire
	private Button btnEliminar;
	@Wire
	private Window winListaPersonal;
	@Wire
	private Listbox lbxEvaluacion;
	@Wire
	private Listbox lbxUsuario;
	@Wire
	private Window winEvaluacionEmpleado;
	@Wire
	private Groupbox gpxListaPersonal;
	private static int idEva;
	private static String fichaE;
	private static Evaluacion eva;
	public static Revision revision;
	List<Evaluacion> evaluacion = new ArrayList<Evaluacion>();
	List<Usuario> usuarios = new ArrayList<Usuario>();
	Evaluacion evaluacionN;

	@Override
	public void inicializar() throws IOException {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		fichaE = ficha;
		gpxListaPersonal.setTitle("(" + "  " + ficha + ")" + "   "
				+ u.getNombre() + "   " + u.getApellido());
		evaluacion = servicioEvaluacion.buscar(ficha);
		lbxEvaluacion.setModel(new ListModelList<Evaluacion>(evaluacion));
		revision = servicioRevision.buscarPorEstado("ACTIVO");
		idEva = servicioEvaluacion.buscarId() + 1;
		lbxEvaluacion.renderAll();
		for (int j = 0; j < lbxEvaluacion.getItems().size(); j++) {
			Listitem listItem = lbxEvaluacion.getItemAtIndex(j);
			List<Listitem> listItem2 = lbxEvaluacion.getItems();
			Evaluacion eva = listItem2.get(j).getValue();
			String fichaS = eva.getFichaEvaluador();
			Empleado empleado = servicioEmpleado.buscarPorFicha(fichaS);
			String nombre = empleado.getNombre();
			((Label) ((listItem.getChildren().get(5))).getFirstChild())
					.setValue(nombre);
		}
	}

	@Listen("onClick = #btnSalir")
	public void salir() {
		cerrarVentana1(winListaPersonal, "Personal");
	}

	@Listen("onClick = #btnAgregar")
	public void AgregarEvaluacion() {
		winListaPersonal.onClose();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		Integer idUsuario = u.getIdUsuario();
		Integer numeroEvaluacion = servicioEvaluacion.buscarIdSecundario(ficha) + 1;
		idEva = servicioEvaluacion.buscarId() + 1;
		Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
		String fichaEvaluador = empleado.getFichaSupervisor();
		Cargo cargo = empleado.getCargo();
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setIdEvaluacion(idEva);
		evaluacion.setEstadoEvaluacion("EN EDICION");
		evaluacion.setFechaCreacion(fechaHora);
		evaluacion.setFechaAuditoria(fechaHora);
		evaluacion.setFicha(ficha);
		evaluacion.setRevision(revision);
		evaluacion.setIdEvaluacionSecundario(numeroEvaluacion);
		evaluacion.setIdUsuario(idUsuario);
		evaluacion.setFichaEvaluador(fichaEvaluador);
		evaluacion.setPeso(0);
		evaluacion.setResultado(0);
		evaluacion.setResultadoObjetivos(0);
		evaluacion.setResultadoGeneral(0);
		evaluacion.setCargo(cargo);
		evaluacion.setHoraAuditoria(horaAuditoria);
		Bitacora bitacora = new Bitacora();

		bitacora.setEvaluacion(evaluacion);
		bitacora.setIdUsuario(u);
		bitacora.setFechaAuditoria(fechaHora);
		bitacora.setHoraAuditoria(horaAuditoria);
		bitacora.setEstadoEvaluacion("EN EDICION");
		System.out.println(bitacora.getEvaluacion().getIdEvaluacion());
		System.out.println(evaluacion + " " + u + " " + fechaHora
				+ horaAuditoria);

		servicioEvaluacion.guardar(evaluacion);
		//servicioBitacora.guardar(bitacora);
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idEva", idEva);
		System.out.println("va" + idEva);
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		winEvaluacionEmpleado = (Window) Executions.createComponents(
				"/vistas/transacciones/VAgregarEvaluacion.zul", null, map);
		winEvaluacionEmpleado.doModal();
	}

	@Listen("onClick = #btnEliminar")
	public void eliminar() {
		if (lbxEvaluacion.getItemCount() != 0) {

			Listitem listItem = lbxEvaluacion.getSelectedItem();
			if (listItem != null) {

				Evaluacion evaluacionE = (Evaluacion) listItem.getValue();
				eva = evaluacionE;
				Integer id = evaluacionE.getIdEvaluacion();
				idEva = id;
				Messagebox.show(Mensaje.deseaEliminar, "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									if (eva.getEstadoEvaluacion().equals(
											"EN EDICION")) {
										List<EvaluacionObjetivo> evaluacionObjetivo = servicioEvaluacionObjetivo
												.buscarObjetivosEvaluar(idEva);
										for (int i = 0; i < evaluacionObjetivo
												.size(); i++) {
											Integer idObjetivo = evaluacionObjetivo
													.get(i).getIdObjetivo();
											List<EvaluacionIndicador> evaluacionIndicador = servicioEvaluacionIndicador
													.buscarIndicadores(idObjetivo);
											servicioEvaluacionIndicador
													.eliminarVarios(evaluacionIndicador);
										}
										servicioEvaluacion.eliminarUno(idEva);
										servicioEvaluacionObjetivo
												.eliminarVarios(evaluacionObjetivo);
										msj.mensajeInformacion(Mensaje.eliminado);
										lbxEvaluacion.getItems().clear();
										evaluacion = servicioEvaluacion
												.buscar(fichaE);
										lbxEvaluacion
												.setModel(new ListModelList<Evaluacion>(
														evaluacion));
									} else {
										Messagebox
												.show("No puede Eliminar la Evaluación",
														"Alerta",
														Messagebox.OK,
														Messagebox.EXCLAMATION);
									}

								}
							}
						});
			} else
				msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);

		}
	}

	@Listen("onDoubleClick = #lbxEvaluacion")
	public void mostrarEvaluacion() {

		if (lbxEvaluacion.getItemCount() != 0) {

			Listitem listItem = lbxEvaluacion.getSelectedItem();
			if (listItem != null) {

				Evaluacion evaluacion = (Evaluacion) listItem.getValue();
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", evaluacion.getIdEvaluacion());
				map.put("titulo", evaluacion.getFicha());
				Sessions.getCurrent().setAttribute("itemsCatalogo", map);
				winEvaluacionEmpleado = (Window) Executions.createComponents(
						"/vistas/transacciones/VEvaluacionEnEdicion.zul", null,
						map);
				winEvaluacionEmpleado.doModal();
				winListaPersonal.onClose();
			}

		}

	}
}