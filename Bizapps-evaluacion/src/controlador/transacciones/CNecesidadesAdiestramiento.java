package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;
import modelo.maestros.PerfilCargo;
import modelo.maestros.Periodo;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Catalogo;
import componentes.Mensaje;

import controlador.maestros.CGenerico;

public class CNecesidadesAdiestramiento extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwVNecesidadesAdiestramiento;
	@Wire
	private Button btnBuscar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
	@Wire
	private Textbox txtHorasAcumuladas;
	@Wire
	private Button btnBuscarPeriodo;
	@Wire
	private Listbox lsbEmpleadoDatos;
	@Wire
	private Listbox lsbEmpleadoFormacion;
	@Wire
	private Listbox lsbPerfilCargo;
	@Wire
	private Listbox lsbCursosTransito;
	@Wire
	private Listbox lsbCursosRealizados;
	@Wire
	private Listbox lsbCursosDisponibles;
	@Wire
	private Div divCatalogoEmpleado;
	@Wire
	private Div divCatalogoPeriodo;
	@Wire
	private Textbox txtEmpleado;
	List<Empleado> empleadoDatos = new ArrayList<Empleado>();
	List<Empleado> empleadoFormacion = new ArrayList<Empleado>();
	List<EmpleadoCurso> cursosEmpleado = new ArrayList<EmpleadoCurso>();
	List<Curso> cursosRealizados = new ArrayList<Curso>();
	List<Curso> cursosTramite = new ArrayList<Curso>();
	List<Curso> cursosDisponibles = new ArrayList<Curso>();
	List<PerfilCargo> perfilCargo = new ArrayList<PerfilCargo>();
	private int idEmpleado = 0;
	private int idPeriodo = 0;

	Mensaje msj = new Mensaje();
	Catalogo<Empleado> catalogoEmpleado;
	Catalogo<Periodo> catalogoPeriodo;

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				titulo = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
			}
		}
		txtEmpleado.setFocus(true);
	}

	@Listen("onClick = #btnBuscar")
	public void mostrarCatalogoEmpleado() {
		final List<Empleado> listEmpleado = servicioEmpleado.buscarTodos();
		catalogoEmpleado = new Catalogo<Empleado>(divCatalogoEmpleado,
				"Catalogo de Empleados", listEmpleado, true, false, false,
				"Empresa", "Cargo", "Unidad Organizativa", "Nombre", "Ficha",
				"Ficha Supervisor", "Grado Auxiliar") {

			@Override
			protected List<Empleado> buscar(List<String> valores) {
				List<Empleado> lista = new ArrayList<Empleado>();

				for (Empleado empleado : listEmpleado) {
					if (empleado.getEmpresa().getNombre().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& empleado.getCargo().getDescripcion()
									.toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& empleado.getUnidadOrganizativa()
									.getDescripcion().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& empleado.getNombre().toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& empleado.getFicha().toLowerCase()
									.contains(valores.get(4).toLowerCase())
							&& empleado.getFichaSupervisor().toLowerCase()
									.contains(valores.get(5).toLowerCase())
							&& String.valueOf(empleado.getGradoAuxiliar())
									.toLowerCase()
									.contains(valores.get(6).toLowerCase())) {
						lista.add(empleado);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Empleado empleado) {
				String[] registros = new String[8];
				registros[0] = empleado.getEmpresa().getNombre();
				registros[1] = empleado.getCargo().getDescripcion();
				registros[2] = empleado.getUnidadOrganizativa()
						.getDescripcion();
				registros[3] = empleado.getNombre();
				registros[4] = empleado.getFicha();
				registros[5] = empleado.getFichaSupervisor();
				registros[6] = String.valueOf(empleado.getGradoAuxiliar());

				return registros;
			}
		};

		catalogoEmpleado.setClosable(true);
		catalogoEmpleado.setWidth("80%");
		catalogoEmpleado.setParent(divCatalogoEmpleado);
		catalogoEmpleado.setTitle("Catalogo de Empleados");
		catalogoEmpleado.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEmpleado")
	public void seleccionEmpleado() {
		Empleado empleado = catalogoEmpleado.objetoSeleccionadoDelCatalogo();
		idEmpleado = empleado.getId();
		txtEmpleado.setValue(empleado.getFicha());
		catalogoEmpleado.setParent(null);
		llenarLista();
	}

	@Listen("onChange = #txtEmpleado; onOK =  #txtEmpleado")
	public void buscarIdGerencia() {
		if (txtEmpleado.getValue() != null) {
			if (txtEmpleado.getText().compareTo("") != 0) {
				Empleado empleado = servicioEmpleado.buscarPorFicha(txtEmpleado
						.getValue());
				if (empleado != null) {
					txtEmpleado.setValue(empleado.getFicha());
					idEmpleado = empleado.getId();
					empleadoDatos.add(empleado);
					llenarLista();
				} else {
					txtEmpleado.setValue("");
					idEmpleado = 0;
					llenarLista();
					msj.mensajeError(Mensaje.noHayRegistros);
				}
			}
		}
	}

	@Listen("onClick = #btnLimpiar")
	public void limpiarCampos() {
		idEmpleado = 0;
		idPeriodo = 0;
		txtEmpleado.setValue("");
		txtHorasAcumuladas.setValue("");
		lsbEmpleadoDatos.setModel(new ListModelList<Empleado>());
		lsbEmpleadoFormacion.setModel(new ListModelList<Empleado>());
		lsbPerfilCargo.setModel(new ListModelList<PerfilCargo>());
		lsbCursosDisponibles.setModel(new ListModelList<Curso>());
		lsbCursosTransito.setModel(new ListModelList<Curso>());
		lsbCursosRealizados.setModel(new ListModelList<Curso>());
		lsbCursosDisponibles
				.setEmptyMessage("No se ha seleccionado un empleado");
		lsbCursosRealizados
				.setEmptyMessage("No se ha seleccionado un empleado");
		lsbCursosTransito.setEmptyMessage("No se ha seleccionado un empleado");
	}

	@Listen("onClick = #btnSalir")
	public void salir() {
		cerrarVentana2(wdwVNecesidadesAdiestramiento, titulo, tabs);

	}

	public void llenarLista() {
		empleadoDatos = new ArrayList<Empleado>();
		empleadoFormacion = new ArrayList<Empleado>();
		perfilCargo = new ArrayList<PerfilCargo>();
		cursosEmpleado = new ArrayList<EmpleadoCurso>();
		cursosRealizados = new ArrayList<Curso>();
		cursosTramite = new ArrayList<Curso>();
		cursosDisponibles = new ArrayList<Curso>();
		lsbCursosDisponibles.setModel(new ListModelList<Curso>());
		lsbCursosTransito.setModel(new ListModelList<Curso>());
		lsbCursosRealizados.setModel(new ListModelList<Curso>());
		float horasAcumuladas = 0;
		Empleado empleado = servicioEmpleado.buscar(idEmpleado);
		empleadoDatos.add(empleado);
		lsbEmpleadoDatos.setModel(new ListModelList<Empleado>(empleadoDatos));
		empleadoFormacion.add(empleado);
		lsbEmpleadoFormacion.setModel(new ListModelList<Empleado>(
				empleadoFormacion));
		PerfilCargo perfil = servicioPerfilCargo.buscarPorCargo(empleado
				.getCargo());
		perfilCargo.add(perfil);
		lsbPerfilCargo.setModel(new ListModelList<PerfilCargo>(perfilCargo));
		cursosEmpleado = servicioEmpleadoCurso.buscarCursos(empleado);
		for (int i = 0; i < cursosEmpleado.size(); i++) {
			if (cursosEmpleado.get(i).getEstadoCurso().equals("APROBADO")
					|| cursosEmpleado.get(i).getEstadoCurso()
							.equals("REPROBADO")) {
				Curso curso = cursosEmpleado.get(i).getCurso();
				horasAcumuladas = horasAcumuladas
						+ cursosEmpleado.get(i).getCurso().getDuracion();
				cursosRealizados.add(curso);
			}
			if (cursosEmpleado.get(i).getEstadoCurso().equals("EN TRAMITE")) {
				Curso curso = cursosEmpleado.get(i).getCurso();
				cursosTramite.add(curso);
			}

		}
		lsbCursosRealizados
				.setModel(new ListModelList<Curso>(cursosRealizados));
		lsbCursosTransito.setModel(new ListModelList<Curso>(cursosTramite));
		if (cursosTramite.isEmpty())
			lsbCursosTransito
					.setEmptyMessage("El empleado no posee cursos en Tramite");
		if (cursosRealizados.isEmpty())
			lsbCursosRealizados
					.setEmptyMessage("El empleado no posee cursos realizados");
		cursosDisponibles = servicioCurso.buscarDisponibles(cursosEmpleado);
		lsbCursosDisponibles.setModel(new ListModelList<Curso>(
				cursosDisponibles));
		if (idPeriodo != 0)
			lsbCursosDisponibles
					.setEmptyMessage("No se ha seleccionado un periodo");
		listasMultiples();
	}

	private boolean validarSeleccion(List<Curso> procesadas) {
		if (procesadas == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (procesadas.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoItem);
				return false;
			} else {
				return true;
			}
		}
	}

	@Listen("onClick= #btnAceptarCurso, #btnSalirCurso")
	public void procesar(Event evento) {
		String nombre = "";
		List<Curso> procesadas2 = new ArrayList<Curso>();
		switch (evento.getTarget().getId()) {
		case "btnAceptarCurso":
			nombre = "Agregar";
			procesadas2 = obtenerSeleccionados(lsbCursosDisponibles);
			break;
		case "btnSalirCurso":
			nombre = "Remover";
			procesadas2 = obtenerSeleccionados(lsbCursosTransito);
			break;
		}
		final List<Curso> procesadas = procesadas2;
		final String estado = nombre;
		if (validarSeleccion(procesadas)) {
			Messagebox.show("¿Desea " + nombre + " los " + procesadas.size()
					+ " Cursos al Empleado?", "Alerta", Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								Empleado empleado = servicioEmpleado
										.buscar(idEmpleado);
								if (estado.equals("Remover")) {
									servicioEmpleadoCurso.remover(procesadas,
											empleado);
								} else {
									List<EmpleadoCurso> guardadas = new ArrayList<EmpleadoCurso>();
									for (int i = 0; i < procesadas.size(); i++) {
										Curso curso = procesadas.get(i);
										EmpleadoCurso cursosEmpleado = new EmpleadoCurso(
												curso, empleado, "EN TRAMITE",
												"NO");
										guardadas.add(cursosEmpleado);
									}
									servicioEmpleadoCurso
											.guardarVarios(guardadas);
								}
								llenarLista();
							}
						}
					});
		}

	}

	public List<Curso> obtenerSeleccionados(Listbox lista) {
		List<Curso> valores = new ArrayList<Curso>();
		boolean entro = false;
		if (lista.getItemCount() != 0) {
			final List<Listitem> list1 = lista.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected()) {
					Curso curso = list1.get(i).getValue();
					entro = true;
					valores.add(curso);
				}
			}
			if (!entro) {
				valores.clear();
				return valores;
			}
			return valores;
		} else
			return null;
	}

	public void listasMultiples() {
		lsbCursosTransito.renderAll();
		lsbCursosTransito.setMultiple(false);
		lsbCursosTransito.setCheckmark(false);
		lsbCursosTransito.setMultiple(true);
		lsbCursosTransito.setCheckmark(true);
		lsbCursosDisponibles.renderAll();
		lsbCursosDisponibles.setMultiple(false);
		lsbCursosDisponibles.setCheckmark(false);
		lsbCursosDisponibles.setMultiple(true);
		lsbCursosDisponibles.setCheckmark(true);
	}

	@Listen("onClick = #btnImprimir")
	public void reporte() {
		if (idEmpleado != 0) {
			Empleado empleado = servicioEmpleado.buscar(idEmpleado);
			Usuario u = servicioUsuario
					.buscarUsuarioPorNombre(nombreUsuarioSesion());
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Impresion?par1=gna"
					+ "&par2="
					+ empleado.getFicha()
					+ "&par3="
					+ u.getFicha()
					+ ""
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		} else
			msj.mensajeAlerta("No se ha seleccionado un empleado");
	}

}
