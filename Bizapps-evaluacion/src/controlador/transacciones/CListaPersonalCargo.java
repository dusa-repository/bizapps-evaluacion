package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Bitacora;
import modelo.maestros.Cargo;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionCapacitacion;
import modelo.maestros.EvaluacionCompetencia;
import modelo.maestros.EvaluacionConducta;
import modelo.maestros.EvaluacionIndicador;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.maestros.Revision;
import modelo.maestros.UnidadOrganizativa;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CListaPersonalCargo extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Window winListaPersonal;
	@Wire
	private Window winEvaluacionEmpleado;
	@Wire
	private Listbox lbxEvaluacion;
	@Wire
	private Listbox lbxPersonalCargo;
	@Wire
	private Listheader header;
	@Wire
	private Include contenido;
	private  int numeroEvaluacion;
	private  Empleado empleado;
	Evaluacion evaluacion = new Evaluacion();
	private  int idEva;
	private  Evaluacion eva;
	public  Revision revision;

	@Override
	public void inicializar() throws IOException {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario usuario = servicioUsuario
				.buscarUsuarioPorNombre(auth.getName());
		Empleado emple = servicioEmpleado.buscarPorFicha(usuario.getCedula());
		header.setLabel(emple.getNombre());
		List<Empleado> empleados = servicioEmpleado.BuscarPorSupervisor(usuario
				.getCedula());
		if (!empleados.isEmpty()) {
			lbxPersonalCargo.setModel(new ListModelList<Empleado>(empleados));
			}
	}
	
	
	@Listen("onClick = #lbxPersonalCargo")
	public void mostrarEvaluaciones() {
		if(lbxPersonalCargo.getSelectedItem() != null){
		lbxEvaluacion.getItems().clear();
		empleado = lbxPersonalCargo.getSelectedItem().getValue();	
		String ficha = empleado.getFicha();
		List<Evaluacion> evaluacion = new ArrayList<Evaluacion>();
		evaluacion = servicioEvaluacion.buscar(ficha);		
		if(evaluacion.isEmpty()){
		Messagebox.show(
				"El empleado no tiene evaluaciones registradas",
				"Error", Messagebox.OK, Messagebox.ERROR);
		}else{		
			
		lbxEvaluacion.setModel(new ListModelList<Evaluacion>(
				evaluacion));		
		}
	}
	}
	
	public Empleado buscarNombre(){
		return empleado;
	}
	
	@Listen("onDoubleClick = #lbxEvaluacion")
	public void mostrarEvaluacion() {
			
			
			if (lbxEvaluacion.getItemCount() != 0) {
				
				Listitem listItem = lbxEvaluacion.getSelectedItem();	
				if (listItem != null) {
						
					Evaluacion evaluacion = (Evaluacion) listItem.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("modo", "EDITAR");
					map.put("id", evaluacion.getIdEvaluacion());
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					winEvaluacionEmpleado = (Window) Executions.createComponents("/vistas/transacciones/VAgregarEvaluacion.zul", null, map);				
					winEvaluacionEmpleado.doModal();
					winListaPersonal.onClose();
				}
				
			}
	}
	
	@Listen("onClick = #btnCopiar")
	public void copiar() {
		
		if (lbxEvaluacion.getItemCount() != 0) {

			Listitem listItem = lbxEvaluacion.getSelectedItem();
			if (listItem != null) {

				Evaluacion evaluacionE = (Evaluacion) listItem.getValue();
				eva = evaluacionE;
				Integer id = evaluacionE.getIdEvaluacion();
				idEva = id;
				Messagebox.show(Mensaje.deseaCopiar, "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									
									revision = servicioRevision.buscarPorEstado("ACTIVO");
									Authentication auth = SecurityContextHolder.getContext()
											.getAuthentication();
									Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
									String ficha = u.getCedula();
									Integer idUsuario = u.getIdUsuario();
									Integer numeroEvaluacion = servicioEvaluacion.buscarIdSecundario(eva.getFicha()) + 1;
									Integer numeroEvaluacionPrimaria = servicioEvaluacion.buscarId() + 1;
									Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
									String fichaEvaluador = empleado.getFichaSupervisor();
									Cargo cargo = empleado.getCargo();
									UnidadOrganizativa unidadOrganizativa = empleado.getUnidadOrganizativa();
									
									
									eva.setIdEvaluacion(numeroEvaluacionPrimaria);
									eva.setIdEvaluacionSecundario(numeroEvaluacion);
									eva.setCargo(cargo);
									eva.setUnidadOrganizativa(unidadOrganizativa);
									eva.setEstadoEvaluacion("EN EDICION");
									eva.setFechaCreacion(fechaHora);
									eva.setFechaAuditoria(fechaHora);
									eva.setRevision(revision);
									eva.setIdUsuario(idUsuario);
									eva.setFichaEvaluador(fichaEvaluador);
									eva.setPeso(0);
									eva.setResultado(0);
									eva.setResultadoObjetivos(0);
									eva.setResultadoGeneral(0);
									eva.setHoraAuditoria(horaAuditoria);
									
									Bitacora bitacora = new Bitacora();

									bitacora.setEvaluacion(eva);
									bitacora.setIdUsuario(u);
									bitacora.setFechaAuditoria(fechaHora);
									bitacora.setHoraAuditoria(horaAuditoria);
									bitacora.setEstadoEvaluacion("EN EDICION");
									System.out.println(bitacora.getEvaluacion().getIdEvaluacion());
									System.out.println(evaluacion + " " + u + " " + fechaHora
											+ horaAuditoria);

									servicioEvaluacion.guardar(eva);
									servicioBitacora.guardar(bitacora);
									
									Integer idEvaluacionObjetivoOriginal=0;
									
									List<EvaluacionObjetivo> listaEvaluacionObjetivo = servicioEvaluacionObjetivo
											.buscarObjetivosEvaluar(idEva);
									for (int i = 0; i < listaEvaluacionObjetivo
											.size(); i++) {
										
										EvaluacionObjetivo evaluacionObjetivo=listaEvaluacionObjetivo
												.get(i);
										idEvaluacionObjetivoOriginal=evaluacionObjetivo.getIdObjetivo();
										evaluacionObjetivo.setIdObjetivo(0);
										evaluacionObjetivo.setIdEvaluacion(numeroEvaluacionPrimaria);
										servicioEvaluacionObjetivo.guardar(evaluacionObjetivo);
										
										List<EvaluacionObjetivo> listaEvaluacionObjetivoGuardado = servicioEvaluacionObjetivo
												.buscarObjetivosEvaluar(numeroEvaluacionPrimaria);
										
										Integer idObjetivoGuardado=0;
										for (int x = 0; x < listaEvaluacionObjetivoGuardado
												.size(); x++) {
											
											EvaluacionObjetivo evaluacionObjetivoGuardardo=listaEvaluacionObjetivoGuardado
													.get(x);
											idObjetivoGuardado= evaluacionObjetivoGuardardo.getIdObjetivo();
											
										}
										
										List<EvaluacionIndicador> listaEvaluacionIndicador = servicioEvaluacionIndicador
												.buscarIndicadores(idEvaluacionObjetivoOriginal);
										for (int x = 0; x < listaEvaluacionIndicador
												.size(); x++) {
											
											EvaluacionIndicador evaluacionIndicador=listaEvaluacionIndicador
													.get(x);
											evaluacionIndicador.setIdIndicador(0);
											evaluacionIndicador.setIdObjetivo(idObjetivoGuardado);
											servicioEvaluacionIndicador.guardar(evaluacionIndicador);
										}
									
									}
									
									
									Evaluacion evaluacionAuxiliar = servicioEvaluacion.buscarEvaluacion(idEva);
									List<EvaluacionCompetencia> listaEvaluacionCompetencias = servicioEvaluacionCompetencia.buscar(evaluacionAuxiliar);
									
									for (int i = 0; i < listaEvaluacionCompetencias
											.size(); i++) {
										
										EvaluacionCompetencia evaluacionCompetencia=listaEvaluacionCompetencias
												.get(i);
										evaluacionCompetencia.setEvaluacion(eva);
										servicioEvaluacionCompetencia.guardar(evaluacionCompetencia);

									}
											
									
									List<EvaluacionConducta> listaEvaluacionConductas = servicioEvaluacionConducta.buscar(evaluacionAuxiliar);
									
									for (int i = 0; i < listaEvaluacionCompetencias
											.size(); i++) {
										
										EvaluacionConducta evaluacionConducta=listaEvaluacionConductas
												.get(i);
										evaluacionConducta.setEvaluacion(eva);
										servicioEvaluacionConducta.guardar(evaluacionConducta);

									}
									
									
									List<EvaluacionCapacitacion> listaEvaluacionCapacitacion = servicioEvaluacionCapacitacion.buscarPorEvaluacion(idEva);
									
									for (int i = 0; i < listaEvaluacionCapacitacion
											.size(); i++) {
										
										EvaluacionCapacitacion evaluacionCapacitacion=listaEvaluacionCapacitacion
												.get(i);
										evaluacionCapacitacion.setIdEvaluacion(numeroEvaluacionPrimaria);
										servicioEvaluacionCapacitacion.guardar(evaluacionCapacitacion);

									}
											
																	 

								}
							}
						});
			} else
				msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);

		}
	}
	
	
	
}