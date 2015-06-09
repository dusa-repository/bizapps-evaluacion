package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;
import modelo.maestros.EmpleadoParametro;
import modelo.maestros.Parametro;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Catalogo;
import componentes.Mensaje;

import controlador.maestros.CGenerico;

public class CEvaluacionSatisfaccion extends CGenerico {

	@Wire
	private Window wdwVEvaluacionSatisfaccion;
	@Wire
	private Button btnGuardar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
	@Wire
	private Textbox txtCursoEvaluacionSatisfaccion;
	@Wire
	private Datebox dbfecha;
	@Wire
	private Button btnBuscarCurso;
	@Wire
	private Listbox lsbParametroInformacionPrevia;
	@Wire
	private Listbox lsbParametroContenidoInformacion;
	@Wire
	private Listbox lsbParametroFacilitadorActividad;
	@Wire
	private Listbox lsbParametroEquipos;
	@Wire
	private Listbox lsbParametroResumen;
	@Wire
	private Listbox lsbEmpleadoEvaluacionSatisfaccion;
	@Wire
	private Div divCatalogoCurso;
	List<EmpleadoCurso> empleadosCurso = new ArrayList<EmpleadoCurso>();
	List<EmpleadoParametro> empleadoParametros = new ArrayList<EmpleadoParametro>();
	List<Empleado> empleadoMap = new ArrayList<Empleado>();
	List<Listbox> listas = new ArrayList<Listbox>();
	private int idCurso = 0;
	private int idEmpleado = 0;
	private Empleado empleado;
	private Curso curso;

	Mensaje msj = new Mensaje();
	Catalogo<Curso> catalogoCurso;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) wdwVEvaluacionSatisfaccion.getParent();
		Tabbox tabox = (Tabbox) wdwVEvaluacionSatisfaccion.getParent()
				.getParent().getParent().getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
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
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			// if (map.get("id") != null) {
			if (map.get("idCurso") != null) {
				idCurso = (Integer) map.get("idCurso");
				idEmpleado = (Integer) map.get("idEmpleado");
				empleado = servicioEmpleado.buscarPorId(idEmpleado);
				curso = servicioCurso.buscarCurso(idCurso);
				map.clear();
				map = null;

				EmpleadoCurso cursoEmpleado = servicioEmpleadoCurso
						.buscarPorempleadoYCurso(empleado, curso);

				if (cursoEmpleado.getEstadoCurso().equals("APROBADO")
						|| cursoEmpleado.getEstadoCurso().equals("REPROBADO")) {

					txtCursoEvaluacionSatisfaccion.setValue(curso
							.getNombreCurso().getNombre());
					llenarLista();

				}

				empleadoMap.add(empleado);
				lsbEmpleadoEvaluacionSatisfaccion
						.setModel(new ListModelList<Empleado>(empleadoMap));
				lsbEmpleadoEvaluacionSatisfaccion.setVisible(true);

			}
		} else {

			txtCursoEvaluacionSatisfaccion.setFocus(true);
			String nombreUsuario = nombreUsuarioSesion();
			empleado = servicioEmpleado.buscarPorFicha(servicioUsuario
					.buscarUsuarioPorNombre(nombreUsuario).getFicha());

		}
		listas.add(lsbParametroEquipos);
		listas.add(lsbParametroContenidoInformacion);
		listas.add(lsbParametroFacilitadorActividad);
		listas.add(lsbParametroInformacionPrevia);
		listas.add(lsbParametroResumen);
	}

	public List<Curso> cursosEmpleado() {

		List<Curso> cursos = new ArrayList<Curso>();
		empleadosCurso = servicioEmpleadoCurso
				.buscarCursosAsistidosYFinalizados(empleado);

		for (int i = 0; i < empleadosCurso.size(); i++) {
			cursos.add(empleadosCurso.get(i).getCurso());
		}

		return cursos;
	}

	@Listen("onClick = #btnBuscarCurso")
	public void mostrarCatalogoCurso() {
		final List<Curso> listCurso = cursosEmpleado();
		catalogoCurso = new Catalogo<Curso>(divCatalogoCurso,
				"Catalogo de Cursos", listCurso, true, false, false, "Área",
				"Nombre", "Duración") {

			@Override
			protected List<Curso> buscar(List<String> valores) {
				List<Curso> lista = new ArrayList<Curso>();

				for (Curso curso : listCurso) {
					if (curso.getNombreCurso().getArea().getDescripcion()
							.toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& curso.getNombreCurso().getNombre().toLowerCase()
									.contains(valores.get(1).toLowerCase())

							&& String.valueOf(curso.getDuracion())
									.toLowerCase()
									.contains(valores.get(2).toLowerCase())) {
						lista.add(curso);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Curso curso) {
				String[] registros = new String[3];
				registros[0] = curso.getNombreCurso().getArea()
						.getDescripcion();
				registros[1] = curso.getNombreCurso().getNombre();
				if (curso.getDuracion() != 0)
					registros[2] = String.valueOf(mostrarDuracion(curso)) + " "
							+ curso.getMedidaDuracion();
				else
					registros[2] = String.valueOf(curso.getDuracion());

				return registros;
			}

		};

		catalogoCurso.setClosable(true);
		catalogoCurso.setWidth("80%");
		catalogoCurso.setParent(divCatalogoCurso);
		catalogoCurso.setTitle("Catalogo de Cursos");
		catalogoCurso.doModal();
	}

	@Listen("onSeleccion = #divCatalogoCurso")
	public void seleccionCurso() {
		Curso curso = catalogoCurso.objetoSeleccionadoDelCatalogo();
		idCurso = curso.getId();
		txtCursoEvaluacionSatisfaccion.setValue(curso.getNombreCurso()
				.getNombre());
		llenarLista();
		catalogoCurso.setParent(null);

	}

	@Listen("onChange = #txtCursoEvaluacionSatisfaccion")
	public void buscarCurso() {
		List<EmpleadoCurso> cursos = servicioEmpleadoCurso.buscar(servicioCurso
				.buscarPorNombre(txtCursoEvaluacionSatisfaccion.getValue()));

		if (cursos.size() == 0) {
			msj.mensajeAlerta(Mensaje.codigoCurso);
			txtCursoEvaluacionSatisfaccion.setFocus(true);
		} else {
			idCurso = cursos.get(0).getCurso().getId();
			llenarLista();
		}

	}

	@Listen("onClick = #btnLimpiar")
	public void limpiarCampos() {
		idCurso = 0;
		txtCursoEvaluacionSatisfaccion.setValue("");
		lsbParametroInformacionPrevia.setModel(new ListModelList<Parametro>());
		lsbParametroContenidoInformacion
				.setModel(new ListModelList<Parametro>());
		lsbParametroFacilitadorActividad
				.setModel(new ListModelList<Parametro>());
		lsbParametroEquipos.setModel(new ListModelList<Parametro>());
		lsbParametroResumen.setModel(new ListModelList<Parametro>());
		lsbEmpleadoEvaluacionSatisfaccion
				.setModel(new ListModelList<Empleado>());
		lsbEmpleadoEvaluacionSatisfaccion.setVisible(false);
		txtCursoEvaluacionSatisfaccion.setFocus(true);
	}

	public boolean camposLLenos() {
		if (txtCursoEvaluacionSatisfaccion.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	protected boolean validar() {

		if (!camposLLenos()) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;

	}

	@Listen("onClick = #btnSalir")
	public void salir() {
		cerrarVentana(wdwVEvaluacionSatisfaccion, titulo, tabs);
	}

	public void llenarLista() {

		Curso curso = servicioCurso.buscarCurso(idCurso);
		empleadoParametros = servicioEmpleadoParametro.buscarParametros(
				empleado, curso);

		List<Parametro> parametrosTipo1 = new ArrayList<Parametro>();
		parametrosTipo1 = servicioParametro
				.buscarPorTipo("EVALUACION INFORMACION PREVIA");

		List<Parametro> parametrosTipo2 = new ArrayList<Parametro>();
		parametrosTipo2 = servicioParametro
				.buscarPorTipo("EVALUACION CONTENIDO");

		List<Parametro> parametrosTipo3 = new ArrayList<Parametro>();
		parametrosTipo3 = servicioParametro
				.buscarPorTipo("EVALUACION FACILITADOR");

		List<Parametro> parametrosTipo4 = new ArrayList<Parametro>();
		parametrosTipo4 = servicioParametro
				.buscarPorTipo("EVALUACION EQUIPOS E INFRAESTRUCTURA");

		List<Parametro> parametrosTipo5 = new ArrayList<Parametro>();
		parametrosTipo5 = servicioParametro.buscarPorTipo("EVALUACION RESUMEN");

		lsbParametroInformacionPrevia.setModel(new ListModelList<Parametro>(
				parametrosTipo1));
		lsbParametroContenidoInformacion.setModel(new ListModelList<Parametro>(
				parametrosTipo2));
		lsbParametroFacilitadorActividad.setModel(new ListModelList<Parametro>(
				parametrosTipo3));
		lsbParametroEquipos.setModel(new ListModelList<Parametro>(
				parametrosTipo4));
		lsbParametroResumen.setModel(new ListModelList<Parametro>(
				parametrosTipo5));

		lsbParametroInformacionPrevia.renderAll();
		lsbParametroContenidoInformacion.renderAll();
		lsbParametroFacilitadorActividad.renderAll();
		lsbParametroEquipos.renderAll();
		lsbParametroResumen.renderAll();

		if (empleadoParametros.size() != 0) {

			for (int i = 0; i < lsbParametroInformacionPrevia.getItems().size(); i++) {
				for (int j = 0; j < empleadoParametros.size(); j++) {
					if (empleadoParametros.get(j).getParametro().getId() == parametrosTipo1
							.get(i).getId()) {
						Listitem listItem = lsbParametroInformacionPrevia
								.getItemAtIndex(i);
						Radiogroup grupo = ((Radiogroup) ((listItem
								.getChildren().get(2))).getFirstChild());
						if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY BUENO"))
							grupo.getItemAtIndex(0).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("BUENO"))
							grupo.getItemAtIndex(1).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("REGULAR"))
							grupo.getItemAtIndex(2).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MALO"))
							grupo.getItemAtIndex(3).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY MALO"))
							grupo.getItemAtIndex(4).setChecked(true);
					}
				}
			}
			for (int i = 0; i < lsbParametroContenidoInformacion.getItems()
					.size(); i++) {

				for (int j = 0; j < empleadoParametros.size(); j++) {

					if (empleadoParametros.get(j).getParametro().getId() == parametrosTipo2
							.get(i).getId()) {

						Listitem listItem = lsbParametroContenidoInformacion
								.getItemAtIndex(i);
						Radiogroup grupo = ((Radiogroup) ((listItem
								.getChildren().get(2))).getFirstChild());
						if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY BUENO"))
							grupo.getItemAtIndex(0).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("BUENO"))
							grupo.getItemAtIndex(1).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("REGULAR"))
							grupo.getItemAtIndex(2).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MALO"))
							grupo.getItemAtIndex(3).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY MALO"))
							grupo.getItemAtIndex(4).setChecked(true);

					}

				}

			}

			for (int i = 0; i < lsbParametroFacilitadorActividad.getItems()
					.size(); i++) {

				for (int j = 0; j < empleadoParametros.size(); j++) {

					if (empleadoParametros.get(j).getParametro().getId() == parametrosTipo3
							.get(i).getId()) {

						Listitem listItem = lsbParametroFacilitadorActividad
								.getItemAtIndex(i);
						Radiogroup grupo = ((Radiogroup) ((listItem
								.getChildren().get(2))).getFirstChild());
						if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY BUENO"))
							grupo.getItemAtIndex(0).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("BUENO"))
							grupo.getItemAtIndex(1).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("REGULAR"))
							grupo.getItemAtIndex(2).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MALO"))
							grupo.getItemAtIndex(3).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY MALO"))
							grupo.getItemAtIndex(4).setChecked(true);

					}

				}

			}

			for (int i = 0; i < lsbParametroEquipos.getItems().size(); i++) {

				for (int j = 0; j < empleadoParametros.size(); j++) {

					if (empleadoParametros.get(j).getParametro().getId() == parametrosTipo4
							.get(i).getId()) {

						Listitem listItem = lsbParametroEquipos
								.getItemAtIndex(i);
						Radiogroup grupo = ((Radiogroup) ((listItem
								.getChildren().get(2))).getFirstChild());
						if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY BUENO"))
							grupo.getItemAtIndex(0).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("BUENO"))
							grupo.getItemAtIndex(1).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("REGULAR"))
							grupo.getItemAtIndex(2).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MALO"))
							grupo.getItemAtIndex(3).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY MALO"))
							grupo.getItemAtIndex(4).setChecked(true);
					}

				}

			}

			for (int i = 0; i < lsbParametroResumen.getItems().size(); i++) {

				for (int j = 0; j < empleadoParametros.size(); j++) {

					if (empleadoParametros.get(j).getParametro().getId() == parametrosTipo5
							.get(i).getId()) {
						Listitem listItem = lsbParametroResumen
								.getItemAtIndex(i);
						Radiogroup grupo = ((Radiogroup) ((listItem
								.getChildren().get(2))).getFirstChild());
						if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY BUENO"))
							grupo.getItemAtIndex(0).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("BUENO"))
							grupo.getItemAtIndex(1).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("REGULAR"))
							grupo.getItemAtIndex(2).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MALO"))
							grupo.getItemAtIndex(3).setChecked(true);
						else if (empleadoParametros.get(j).getValorEvaluacion()
								.equals("MUY MALO"))
							grupo.getItemAtIndex(4).setChecked(true);
					}

				}

			}

		}

	}

	private boolean validarVacios() {
		for (int i = 0; i < listas.size(); i++) {
			Listbox lista = listas.get(i);
			for (int j = 0; j < lista.getItemCount(); j++) {
				Listitem listItem = lista.getItemAtIndex(j);
				Radiogroup grupo = ((Radiogroup) ((listItem.getChildren()
						.get(2))).getFirstChild());
				if (grupo.getSelectedItem() == null) {
					Messagebox
							.show("Debe seleccionar la respuesta de su preferencia en cada uno de los parametros de las listas",
									"Advertencia", Messagebox.OK,
									Messagebox.EXCLAMATION);
					return false;
				}
			}
		}
		return true;
	}

	@Listen("onClick = #btnGuardar")
	public void guardar() {
		if (validar()) {
			if (validarVacios()) {
				Curso curso = servicioCurso.buscarCurso(idCurso);
				List<EmpleadoCurso> cursoEmpleado = servicioEmpleadoCurso
						.buscar(curso);
				if (cursoEmpleado.size() != 0) {
					if (lsbParametroInformacionPrevia.getItemCount() != 0) {
						for (int i = 0; i < lsbParametroInformacionPrevia
								.getItemCount(); i++) {
							Listitem listItem = lsbParametroInformacionPrevia
									.getItemAtIndex(i);
							int codigoParametro1 = ((Intbox) ((listItem
									.getChildren().get(0))).getFirstChild())
									.getValue();
							Radiogroup grupo = ((Radiogroup) ((listItem
									.getChildren().get(2))).getFirstChild());
							String valorEvaluacion = null;
							if (grupo.getSelectedItem() != null)
								valorEvaluacion = grupo.getSelectedItem()
										.getValue();
							Parametro parametro1 = servicioParametro
									.buscarParametro(codigoParametro1);
							EmpleadoParametro empleadoParametro1 = new EmpleadoParametro(
									empleado, parametro1, curso,
									valorEvaluacion);
							servicioEmpleadoParametro
									.guardar(empleadoParametro1);
						}
					}
					if (lsbParametroContenidoInformacion.getItemCount() != 0) {
						for (int i = 0; i < lsbParametroContenidoInformacion
								.getItemCount(); i++) {
							Listitem listItem = lsbParametroContenidoInformacion
									.getItemAtIndex(i);
							int codigoParametro2 = ((Intbox) ((listItem
									.getChildren().get(0))).getFirstChild())
									.getValue();
							Radiogroup grupo = ((Radiogroup) ((listItem
									.getChildren().get(2))).getFirstChild());
							String valorEvaluacion = null;
							if (grupo.getSelectedItem() != null)
								valorEvaluacion = grupo.getSelectedItem()
										.getValue();
							Parametro parametro2 = servicioParametro
									.buscarParametro(codigoParametro2);
							EmpleadoParametro empleadoParametro2 = new EmpleadoParametro(
									empleado, parametro2, curso,
									valorEvaluacion);
							servicioEmpleadoParametro
									.guardar(empleadoParametro2);
						}
					}
					if (lsbParametroFacilitadorActividad.getItemCount() != 0) {
						for (int i = 0; i < lsbParametroFacilitadorActividad
								.getItemCount(); i++) {
							Listitem listItem = lsbParametroFacilitadorActividad
									.getItemAtIndex(i);
							int codigoParametro3 = ((Intbox) ((listItem
									.getChildren().get(0))).getFirstChild())
									.getValue();
							Radiogroup grupo = ((Radiogroup) ((listItem
									.getChildren().get(2))).getFirstChild());
							String valorEvaluacion = null;
							if (grupo.getSelectedItem() != null)
								valorEvaluacion = grupo.getSelectedItem()
										.getValue();
							Parametro parametro3 = servicioParametro
									.buscarParametro(codigoParametro3);
							EmpleadoParametro empleadoParametro3 = new EmpleadoParametro(
									empleado, parametro3, curso,
									valorEvaluacion);
							servicioEmpleadoParametro
									.guardar(empleadoParametro3);
						}
					}

					if (lsbParametroEquipos.getItemCount() != 0) {
						for (int i = 0; i < lsbParametroEquipos.getItemCount(); i++) {
							Listitem listItem = lsbParametroEquipos
									.getItemAtIndex(i);
							int codigoParametro4 = ((Intbox) ((listItem
									.getChildren().get(0))).getFirstChild())
									.getValue();
							Radiogroup grupo = ((Radiogroup) ((listItem
									.getChildren().get(2))).getFirstChild());
							String valorEvaluacion = null;
							if (grupo.getSelectedItem() != null)
								valorEvaluacion = grupo.getSelectedItem()
										.getValue();
							Parametro parametro4 = servicioParametro
									.buscarParametro(codigoParametro4);
							EmpleadoParametro empleadoParametro4 = new EmpleadoParametro(
									empleado, parametro4, curso,
									valorEvaluacion);
							servicioEmpleadoParametro
									.guardar(empleadoParametro4);
						}
					}

					if (lsbParametroResumen.getItemCount() != 0) {
						for (int i = 0; i < lsbParametroResumen.getItemCount(); i++) {
							Listitem listItem = lsbParametroResumen
									.getItemAtIndex(i);
							int codigoParametro5 = ((Intbox) ((listItem
									.getChildren().get(0))).getFirstChild())
									.getValue();
							Radiogroup grupo = ((Radiogroup) ((listItem
									.getChildren().get(2))).getFirstChild());
							String valorEvaluacion = null;
							if (grupo.getSelectedItem() != null)
								valorEvaluacion = grupo.getSelectedItem()
										.getValue();
							Parametro parametro5 = servicioParametro
									.buscarParametro(codigoParametro5);
							EmpleadoParametro empleadoParametro5 = new EmpleadoParametro(
									empleado, parametro5, curso,
									valorEvaluacion);

							servicioEmpleadoParametro
									.guardar(empleadoParametro5);

						}

					}
					msj.mensajeInformacion(Mensaje.guardado);
					limpiarCampos();
				} else {

					msj.mensajeAlerta(Mensaje.codigoCurso);
					txtCursoEvaluacionSatisfaccion.setFocus(true);
				}
			}
		}

	}

	public float mostrarDuracion(Curso curso) {

		float duracionTransformada = 0;

		if (curso.getMedidaDuracion().equals("HORAS")) {

			duracionTransformada = curso.getDuracion();
			return duracionTransformada;

		} else if (curso.getMedidaDuracion().equals("DIAS")) {

			duracionTransformada = curso.getDuracion() / 24;
			return duracionTransformada;
		}

		return duracionTransformada;

	}

}
