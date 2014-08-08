package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Actividad;
import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;
import modelo.maestros.ItemEvaluacion;
import modelo.maestros.PerfilCargo;
import modelo.maestros.Periodo;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
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
	private Button btnBuscarCurso;
	@Wire
	private Listbox lsbEmpleadoEvaluacionEficacia;
	@Wire
	private Listbox lsbItemEvaluacionCualitativa;
	@Wire
	private Listbox lsbItemEvaluacionCuantitativa;
	@Wire
	private Textbox txtPorcentajeEvaluacionEficacia;
	@Wire
	private Div divCatalogoEmpleado;
	@Wire
	private Div divCatalogoCurso;
	List<Empleado> empleadoDatos = new ArrayList<Empleado>();
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

		txtCursoEvaluacionEficacia.setFocus(true);

	}

	@Listen("onClick = #btnBuscarCurso")
	public void mostrarCatalogoCurso() {
		final List<Curso> listCurso = servicioCurso.buscarTodos();
		catalogoCurso = new Catalogo<Curso>(divCatalogoCurso,
				"Catalogo de Cursos", listCurso, "Área", "Nombre", "Duración") {

			@Override
			protected List<Curso> buscarCampos(List<String> valores) {
				List<Curso> lista = new ArrayList<Curso>();

				for (Curso curso : listCurso) {
					if (curso.getArea().getDescripcion().toLowerCase()
							.startsWith(valores.get(0))
							&& curso.getNombre().toLowerCase()
									.startsWith(valores.get(1))

							&& String.valueOf(curso.getDuracion())
									.toLowerCase().startsWith(valores.get(2))) {
						lista.add(curso);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Curso curso) {
				String[] registros = new String[3];
				registros[0] = curso.getArea().getDescripcion();
				registros[1] = curso.getNombre();
				registros[2] = String.valueOf(mostrarDuracion(curso)) + " "
						+ curso.getMedidaDuracion();

				return registros;
			}

			@Override
			protected List<Curso> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Área"))
					return servicioCurso.filtroArea(valor);
				else if (combo.equals("Nombre"))
					return servicioCurso.filtroNombre(valor);
				else if (combo.equals("Duración"))
					return servicioCurso.filtroDuracion(valor);
				else
					return servicioCurso.buscarTodos();
			}

		};

		catalogoCurso.setClosable(true);
		catalogoCurso.setWidth("80%");
		catalogoCurso.setParent(divCatalogoCurso);
		catalogoCurso.doModal();
	}

	@Listen("onSeleccion = #divCatalogoCurso")
	public void seleccionCurso() {
		Curso curso = catalogoCurso.objetoSeleccionadoDelCatalogo();
		idCurso = curso.getId();
		txtCursoEvaluacionEficacia.setValue(curso.getNombre());
		catalogoCurso.setParent(null);
		// llenarLista();
	}

	@Listen("onChange = #txtCursoEvaluacionEficacia")
	public void buscarCurso() {
		List<Curso> cursos = servicioCurso
				.buscarPorNombres(txtCursoEvaluacionEficacia.getValue());

		if (cursos.size() == 0) {
			msj.mensajeAlerta(Mensaje.codigoCurso);
			txtCursoEvaluacionEficacia.setFocus(true);
		} else {
			idCurso = cursos.get(0).getId();
		}

	}

	@Listen("onClick = #btnBuscar")
	public void mostrarCatalogoEmpleado() {
		final List<Empleado> listEmpleado = servicioEmpleado.buscarTodos();
		catalogoEmpleado = new Catalogo<Empleado>(divCatalogoEmpleado,
				"Catalogo de Empleados", listEmpleado, "Empresa", "Cargo",
				"Unidad Organizativa", "Nombre", "Ficha", "Ficha Supervisor",
				"Grado Auxiliar") {

			@Override
			protected List<Empleado> buscarCampos(List<String> valores) {
				List<Empleado> lista = new ArrayList<Empleado>();

				for (Empleado empleado : listEmpleado) {
					if (empleado.getEmpresa().getNombre().toLowerCase()
							.startsWith(valores.get(0))
							&& empleado.getCargo().getDescripcion()
									.toLowerCase().startsWith(valores.get(1))
							&& empleado.getUnidadOrganizativa()
									.getDescripcion().toLowerCase()
									.startsWith(valores.get(2))
							&& empleado.getNombre().toLowerCase()
									.startsWith(valores.get(3))
							&& empleado.getFicha().toLowerCase()
									.startsWith(valores.get(4))
							&& empleado.getFichaSupervisor().toLowerCase()
									.startsWith(valores.get(5))
							&& String.valueOf(empleado.getGradoAuxiliar())
									.toLowerCase().startsWith(valores.get(6))) {
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

			@Override
			protected List<Empleado> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Empresa"))
					return servicioEmpleado.filtroEmpresa(valor);
				else if (combo.equals("Cargo"))
					return servicioEmpleado.filtroCargo(valor);
				else if (combo.equals("Unidad Organizativa"))
					return servicioEmpleado.filtroUnidadOrganizativa(valor);
				else if (combo.equals("Nombre"))
					return servicioEmpleado.filtroNombre(valor);
				else if (combo.equals("Ficha"))
					return servicioEmpleado.filtroFicha(valor);
				else if (combo.equals("Ficha Supervisor"))
					return servicioEmpleado.filtroFichaSupervisor(valor);
				else if (combo.equals("Grado Auxiliar"))
					return servicioEmpleado.filtroGradoAuxiliar(valor);
				else
					return servicioEmpleado.buscarTodos();
			}

		};

		catalogoEmpleado.setClosable(true);
		catalogoEmpleado.setWidth("80%");
		catalogoEmpleado.setParent(divCatalogoEmpleado);
		catalogoEmpleado.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEmpleado")
	public void seleccionEmpleado() {
		Empleado empleado = catalogoEmpleado.objetoSeleccionadoDelCatalogo();
		idEmpleado = empleado.getId();
		catalogoEmpleado.setParent(null);
		llenarLista();
	}

	@Listen("onClick = #btnLimpiar")
	public void limpiarCampos() {

		idEmpleado = 0;
		idCurso = 0;
		txtCursoEvaluacionEficacia.setValue("");
		lsbEmpleadoEvaluacionEficacia.setModel(new ListModelList<Empleado>());
		lsbItemEvaluacionCualitativa
				.setModel(new ListModelList<ItemEvaluacion>());
		lsbItemEvaluacionCuantitativa
				.setModel(new ListModelList<ItemEvaluacion>());
		txtPorcentajeEvaluacionEficacia.setValue("");
		txtCursoEvaluacionEficacia.setFocus(true);
	}

	public boolean camposLLenos() {
		if (txtCursoEvaluacionEficacia.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	protected boolean validar() {

		if (!camposLLenos()) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else
			return true;

	}

	@Listen("onClick = #btnSalir")
	public void salir() {

		cerrarVentana1(wdwVEvaluacionEficacia, "Evaluacion de Eficacia");
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

		Empleado empleado = servicioEmpleado.buscar(idEmpleado);
		empleadoDatos.add(empleado);
		lsbEmpleadoEvaluacionEficacia.setModel(new ListModelList<Empleado>(
				empleadoDatos));

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

	}

}
