package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Actividad;
import modelo.maestros.ActividadCurso;
import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;
import modelo.maestros.EmpleadoItem;
import modelo.maestros.ItemEvaluacion;
import modelo.maestros.Parametro;
import modelo.maestros.PerfilCargo;
import modelo.maestros.Periodo;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Catalogo;
import componentes.Mensaje;
import controlador.maestros.CGenerico;

public class CEvaluacionEficacia extends CGenerico {

	@Wire
	private Window wdwVEvaluacionEficacia;
	@Wire
	private Button btnBuscar;
	@Wire
	private Button btnGuardar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
	@Wire
	private Datebox dbfecha;
	@Wire
	private Textbox txtCursoEvaluacionEficacia;
	@Wire
	private Textbox txtEmpleado;
	@Wire
	private Textbox txtPorcentajeItem;
	@Wire
	private Button btnBuscarCurso;
	@Wire
	private Listbox lsbEmpleadoEvaluacionEficacia;
	@Wire
	private Listbox lsbItemEvaluacionCualitativa;
	@Wire
	private Listbox lsbItemEvaluacionCuantitativa;
	@Wire
	private Doublebox txtPorcentajeEvaluacionEficacia;
	@Wire
	private Div divCatalogoEmpleado;
	@Wire
	private Div divCatalogoCurso;
	List<Empleado> empleadoDatos = new ArrayList<Empleado>();
	List<EmpleadoCurso> empleadosCurso = new ArrayList<EmpleadoCurso>();
	List<EmpleadoItem> empleadoItems = new ArrayList<EmpleadoItem>();
	List<ItemEvaluacion> itemPonderacionCualitativa = new ArrayList<ItemEvaluacion>();
	List<ItemEvaluacion> itemPonderacionCuantitativa = new ArrayList<ItemEvaluacion>();
	private int idCurso = 0;
	private int idEmpleado = 0;

	Mensaje msj = new Mensaje();
	Catalogo<Empleado> catalogoEmpleado;
	Catalogo<Curso> catalogoCurso;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

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

	public List<Curso> cursosEmpleado() {

		List<Curso> cursos = new ArrayList<Curso>();
		empleadosCurso = servicioEmpleadoCurso
				.buscarCursosAsistidosYFinalizados(empleadoDatos.get(0));

		for (int i = 0; i < empleadosCurso.size(); i++) {

			cursos.add(empleadosCurso.get(i).getCurso());
		}

		return cursos;
	}

	@Listen("onClick = #btnBuscarCurso")
	public void mostrarCatalogoCurso() {

		if (empleadoDatos.size() != 0) {

			final List<Curso> listCurso = cursosEmpleado();
			catalogoCurso = new Catalogo<Curso>(divCatalogoCurso,
					"Catalogo de Cursos", listCurso, true, false, false,
					"Área", "Nombre", "Duración") {

				@Override
				protected List<Curso> buscar(List<String> valores) {
					List<Curso> lista = new ArrayList<Curso>();

					for (Curso curso : listCurso) {
						if (curso.getNombreCurso().getArea().getDescripcion()
								.toLowerCase()
								.contains(valores.get(0).toLowerCase())
								&& curso.getNombreCurso().getNombre()
										.toLowerCase()
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
						registros[2] = String.valueOf(mostrarDuracion(curso))
								+ " " + curso.getMedidaDuracion();
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
		} else {

			Messagebox.show("Debe seleccionar un empleado", "Advertencia",
					Messagebox.OK, Messagebox.EXCLAMATION);

		}

	}

	@Listen("onSeleccion = #divCatalogoCurso")
	public void seleccionCurso() {
		Curso curso = catalogoCurso.objetoSeleccionadoDelCatalogo();
		idCurso = curso.getId();
		txtCursoEvaluacionEficacia.setValue(curso.getNombreCurso().getNombre());
		llenarLista();
		catalogoCurso.setParent(null);

	}

	@Listen("onChange = #txtCursoEvaluacionEficacia")
	public void buscarCurso() {
		List<EmpleadoCurso> cursos = servicioEmpleadoCurso.buscar(servicioCurso
				.buscarPorNombre(txtCursoEvaluacionEficacia.getValue()));

		if (cursos.size() == 0) {
			msj.mensajeAlerta(Mensaje.codigoCurso);
			txtCursoEvaluacionEficacia.setFocus(true);
		} else {
			idCurso = cursos.get(0).getCurso().getId();
			llenarLista();
		}

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
		empleadoDatos = new ArrayList<Empleado>();
		empleadoDatos.add(empleado);
		lsbEmpleadoEvaluacionEficacia.setModel(new ListModelList<Empleado>(
				empleadoDatos));

	}

	@Listen("onChange = #txtEmpleado; onOK =  #txtEmpleado")
	public void buscarIdGerencia() {
		if (txtEmpleado.getValue() != null) {
			if (txtEmpleado.getText().compareTo("") != 0) {
				Empleado empleado = servicioEmpleado.buscarPorFicha(txtEmpleado
						.getValue());
				empleadoDatos = new ArrayList<Empleado>();
				if (empleado != null) {
					txtEmpleado.setValue(empleado.getFicha());
					idEmpleado = empleado.getId();
					empleadoDatos.add(empleado);
					lsbEmpleadoEvaluacionEficacia
							.setModel(new ListModelList<Empleado>(empleadoDatos));
				} else {
					txtEmpleado.setValue("");
					idEmpleado = 0;
					lsbEmpleadoEvaluacionEficacia
							.setModel(new ListModelList<Empleado>());
					msj.mensajeError(Mensaje.noHayRegistros);
				}
			}
		}
	}

	@Listen("onClick = #btnLimpiar")
	public void limpiarCampos() {

		idEmpleado = 0;
		idCurso = 0;
		empleadoDatos = new ArrayList<Empleado>();
		txtCursoEvaluacionEficacia.setValue("");
		txtEmpleado.setValue("");
		lsbEmpleadoEvaluacionEficacia.setModel(new ListModelList<Empleado>());
		lsbItemEvaluacionCualitativa
				.setModel(new ListModelList<ItemEvaluacion>());
		lsbItemEvaluacionCuantitativa
				.setModel(new ListModelList<ItemEvaluacion>());
		txtPorcentajeEvaluacionEficacia.setValue(0);
		txtEmpleado.setFocus(true);
	}

	public void cambio() {
		if (lsbItemEvaluacionCuantitativa.getItemCount() != 0) {
			double total = 0;
			for (int i = 0; i < lsbItemEvaluacionCuantitativa.getItemCount(); i++) {
				Listitem listItem = lsbItemEvaluacionCuantitativa
						.getItemAtIndex(i);
				double cantidad = 0;
				if (((Doublebox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue() != null)
					cantidad = ((Doublebox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getValue();
				total = total + cantidad;
			}
			txtPorcentajeEvaluacionEficacia.setValue(total);
		}
	}

	public boolean camposLLenos() {
		if (txtCursoEvaluacionEficacia.getText().compareTo("") == 0) {
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

		cerrarVentana(wdwVEvaluacionEficacia, titulo, tabs);

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

	public void llenarLista() {

		Curso curso = servicioCurso.buscarCurso(idCurso);

		itemPonderacionCualitativa = servicioItemEvaluacion
				.buscarPorTipoPonderacion("CUALITATIVA");
		lsbItemEvaluacionCualitativa
				.setModel(new ListModelList<ItemEvaluacion>(
						itemPonderacionCualitativa));

		itemPonderacionCuantitativa = servicioItemEvaluacion
				.buscarPorTipoPonderacion("CUANTITATIVA");
		lsbItemEvaluacionCuantitativa
				.setModel(new ListModelList<ItemEvaluacion>(
						itemPonderacionCuantitativa));

		empleadoItems = servicioEmpleadoItem.buscarItems(empleadoDatos.get(0),
				curso);

		lsbItemEvaluacionCualitativa.renderAll();
		lsbItemEvaluacionCuantitativa.renderAll();

		if (empleadoItems.size() != 0) {
			for (int i = 0; i < lsbItemEvaluacionCualitativa.getItems().size(); i++) {
				for (int j = 0; j < empleadoItems.size(); j++) {
					if (empleadoItems.get(j).getItem().getId() == itemPonderacionCualitativa
							.get(i).getId()) {
						Listitem listItem = lsbItemEvaluacionCualitativa
								.getItemAtIndex(i);
						if (empleadoItems.get(j).getValorEvaluacion()
								.equals("SI")) {
							((Radio) (listItem.getChildren().get(2))
									.getFirstChild().getChildren().get(0))
									.setChecked(true);
						} else if (empleadoItems.get(j).getValorEvaluacion()
								.equals("NO")) {
							((Radio) (listItem.getChildren().get(2))
									.getFirstChild().getChildren().get(1))
									.setChecked(true);
						}
					}
				}
			}
			double total = 0;
			for (int i = 0; i < lsbItemEvaluacionCuantitativa.getItems().size(); i++) {
				for (int j = 0; j < empleadoItems.size(); j++) {
					if (empleadoItems.get(j).getItem().getId() == itemPonderacionCuantitativa
							.get(i).getId()) {
						Listitem listItem = lsbItemEvaluacionCuantitativa
								.getItemAtIndex(i);
						String valor = empleadoItems.get(j)
								.getValorEvaluacion();
						((Doublebox) ((listItem.getChildren().get(2)))
								.getFirstChild()).setValue(Double
								.valueOf(valor));
						total = total + Double.valueOf(valor);
					}
				}
			}
			txtPorcentajeEvaluacionEficacia.setValue(total);
		}
	}

	@Listen("onClick = #btnGuardar")
	public void guardar() {

		boolean error = false;
		boolean datosGuardados = false;
		if (validar()) {

			Curso curso = servicioCurso.buscarCurso(idCurso);
			Timestamp fecha = new Timestamp(new Date().getTime());

			if (curso != null) {

				if (lsbItemEvaluacionCualitativa.getItemCount() != 0) {

					for (int i = 0; i < lsbItemEvaluacionCualitativa
							.getItemCount(); i++) {

						Listitem listItem = lsbItemEvaluacionCualitativa
								.getItemAtIndex(i);

						int codigoItem = ((Intbox) ((listItem.getChildren()
								.get(0))).getFirstChild()).getValue();

						String valorEvaluacion = null;

						Radiogroup a = ((Radiogroup) ((listItem.getChildren()
								.get(2))).getFirstChild());
						if (a.getSelectedItem() == null) {
							error = true;
							Messagebox
									.show("Debe seleccionar una respuesta por cada item de evaluacion",
											"Advertencia", Messagebox.OK,
											Messagebox.EXCLAMATION);
							i = lsbItemEvaluacionCualitativa.getItemCount();
						} else {

							a = ((Radiogroup) ((listItem.getChildren().get(2)))
									.getFirstChild());
							valorEvaluacion = a.getSelectedItem().getLabel();
						}
						if (!error) {
							ItemEvaluacion item = servicioItemEvaluacion
									.buscarItem(codigoItem);
							EmpleadoItem empleadoItem = new EmpleadoItem(
									empleadoDatos.get(0), item, curso, fecha,
									valorEvaluacion);
							servicioEmpleadoItem.guardar(empleadoItem);
							datosGuardados = true;
						}
					}
				} else
					Messagebox
							.show("Actualmente no existen items de evaluacion registrados",
									"Advertencia", Messagebox.OK,
									Messagebox.EXCLAMATION);

				if (!error) {

					if (lsbItemEvaluacionCuantitativa.getItemCount() != 0) {

						for (int i = 0; i < lsbItemEvaluacionCuantitativa
								.getItemCount(); i++) {

							Listitem listItem = lsbItemEvaluacionCuantitativa
									.getItemAtIndex(i);

							int codigoItem = ((Intbox) ((listItem.getChildren()
									.get(0))).getFirstChild()).getValue();

							Double valorEvaluacion = (double) 0;

							if (((Doublebox) ((listItem.getChildren().get(2)))
									.getFirstChild()).getValue() != null)
								valorEvaluacion = ((Doublebox) ((listItem
										.getChildren().get(2))).getFirstChild())
										.getValue();

							ItemEvaluacion item = servicioItemEvaluacion
									.buscarItem(codigoItem);
							EmpleadoItem empleadoItem = new EmpleadoItem(
									empleadoDatos.get(0), item, curso, fecha,
									valorEvaluacion.toString());
							servicioEmpleadoItem.guardar(empleadoItem);
							datosGuardados = true;

						}

						if (datosGuardados) {

							msj.mensajeInformacion(Mensaje.guardado);
							limpiarCampos();

						}

					} else
						Messagebox
								.show("Actualmente no existen items de evaluacion registrados",
										"Advertencia", Messagebox.OK,
										Messagebox.EXCLAMATION);

				}

			} else {

				msj.mensajeAlerta(Mensaje.codigoCurso);
				txtCursoEvaluacionEficacia.setFocus(true);

			}

		}

	}

}
