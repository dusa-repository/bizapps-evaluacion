package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelos.Area;
import modelos.Cargo;
import modelos.Empleado;
import modelos.Empresa;
import modelos.UnidadOrganizativa;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CEmpleado extends CGenerico {

	@Wire
	private Div divVEmpleado;
	@Wire
	private Div botoneraEmpleado;
	@Wire
	private Groupbox gpxRegistroEmpleado;
	@Wire
	private Textbox txtNombreEmpleado;
	@Wire
	private Textbox txtEmpresaEmpleado;
	@Wire
	private Button btnBuscarEmpresa;
	@Wire
	private Label lblEmpresaEmpleado;
	@Wire
	private Textbox txtCargoEmpleado;
	@Wire
	private Button btnBuscarCargo;
	@Wire
	private Label lblCargoEmpleado;
	@Wire
	private Textbox txtUnidadEmpleado;
	@Wire
	private Button btnBuscarUnidad;
	@Wire
	private Label lblUnidadEmpleado;
	@Wire
	private Textbox txtFichaEmpleado;
	@Wire
	private Textbox txtFichaSupervisorEmpleado;
	@Wire
	private Textbox txtGradoAuxiliarEmpleado;
	@Wire
	private Groupbox gpxDatosEmpleado;
	@Wire
	private Div catalogoEmpleado;
	@Wire
	private Div divCatalogoEmpresa;
	@Wire
	private Div divCatalogoCargo;
	@Wire
	private Div divUnidadOrganizativa;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idEmpleado = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Empleado> catalogo;
	Catalogo<Empresa> catalogoEmpresa;
	Catalogo<Cargo> catalogoCargo;
	Catalogo<UnidadOrganizativa> catalogoUnidad;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtNombreEmpleado.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Empleado empleado = catalogo
								.objetoSeleccionadoDelCatalogo();
						idEmpleado = empleado.getIdEmpleado();
						txtEmpresaEmpleado.setValue(String.valueOf(empleado
								.getEmpresa().getIdEmpresa()));
						lblEmpresaEmpleado.setValue(empleado.getEmpresa()
								.getNombre());
						txtCargoEmpleado.setValue(String.valueOf(empleado
								.getCargo().getIdCargo()));
						lblCargoEmpleado.setValue(empleado.getCargo()
								.getDescripcion());
						txtUnidadEmpleado.setValue(String.valueOf(empleado
								.getUnidadOrganizativa()
								.getIdUnidadOrganizativa()));
						lblUnidadEmpleado.setValue(empleado
								.getUnidadOrganizativa().getDescripcion());
						txtNombreEmpleado.setValue(empleado.getNombre());
						txtFichaEmpleado.setValue(empleado.getFicha());
						txtFichaSupervisorEmpleado.setValue(empleado
								.getFichaSupervisor());
						txtGradoAuxiliarEmpleado.setValue(String
								.valueOf(empleado.getGradoAuxiliar()));
						txtEmpresaEmpleado.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

				boolean guardar = true;
				guardar = validar();
				if (guardar) {

					Empresa empresa = servicioEmpresa.buscarEmpresa(Integer
							.valueOf(txtEmpresaEmpleado.getValue()));
					Cargo cargo = servicioCargo.buscarCargo(Integer
							.valueOf(txtCargoEmpleado.getValue()));
					UnidadOrganizativa unidadOrganizativa = servicioUnidadOrganizativa
							.buscarUnidad(Integer.valueOf(txtUnidadEmpleado
									.getValue()));

					if (empresa != null && cargo != null
							&& unidadOrganizativa != null) {
						String nombre = txtNombreEmpleado.getValue();
						String ficha = txtFichaEmpleado.getValue();
						String fichaSupervisor = txtFichaSupervisorEmpleado
								.getValue();
						int gradoAuxiliar = 0;
						if (txtGradoAuxiliarEmpleado.getText().compareTo("") != 0)
							gradoAuxiliar = Integer
									.valueOf(txtGradoAuxiliarEmpleado
											.getValue());
						String usuario = "JDE";
						Timestamp fechaAuditoria = new Timestamp(
								new Date().getTime());
						Empleado empleado = new Empleado(idEmpleado,
								fechaAuditoria, ficha, fichaSupervisor,
								gradoAuxiliar, horaAuditoria, nombre, usuario,
								cargo, empresa, unidadOrganizativa);
						servicioEmpleado.guardar(empleado);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						catalogo.actualizarLista(servicioEmpleado.buscarTodos());
					} else {

						msj.mensajeAlerta(Mensaje.claveRTNoEsta);
						txtEmpresaEmpleado.setFocus(true);

					}

				}

			}

			@Override
			public void limpiar() {
				// TODO Auto-generated method stub
				mostrarBotones(false);
				limpiarCampos();
			}

			@Override
			public void salir() {
				// TODO Auto-generated method stub
				cerrarVentana(divVEmpleado, "Empleado");
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosEmpleado.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Empleado> eliminarLista = catalogo
								.obtenerSeleccionados();
						Messagebox
								.show("¿Desea Eliminar los "
										+ eliminarLista.size() + " Registros?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioEmpleado
															.eliminarVariosEmpleados(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioEmpleado
															.buscarTodos());
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idEmpleado != 0) {
						Messagebox
								.show(Mensaje.deseaEliminar,
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioEmpleado
															.eliminarUnEmpleado(idEmpleado);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioEmpleado
															.buscarTodos());
												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}

		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botoneraEmpleado.appendChild(botonera);

	}

	public void limpiarCampos() {
		idEmpleado = 0;
		txtNombreEmpleado.setValue("");
		txtEmpresaEmpleado.setConstraint("");
		txtEmpresaEmpleado.setValue("");
		txtEmpresaEmpleado
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la empresa debe ser numérico");
		lblEmpresaEmpleado.setValue("");
		txtCargoEmpleado.setConstraint("");
		txtCargoEmpleado.setValue("");
		txtCargoEmpleado
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código del cargo debe ser numérico");
		lblCargoEmpleado.setValue("");
		txtUnidadEmpleado.setConstraint("");
		txtUnidadEmpleado.setValue("");
		txtUnidadEmpleado
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la unidad organizativa debe ser numérico");
		lblUnidadEmpleado.setValue("");
		txtNombreEmpleado.setValue("");
		txtFichaEmpleado.setValue("");
		txtFichaSupervisorEmpleado.setValue("");
		txtGradoAuxiliarEmpleado.setConstraint("");
		txtGradoAuxiliarEmpleado.setValue("");
		txtGradoAuxiliarEmpleado
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El grado auxiliar debe ser numérico");
		catalogo.limpiarSeleccion();
		txtEmpresaEmpleado.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtEmpresaEmpleado.getText().compareTo("") != 0
				|| txtCargoEmpleado.getText().compareTo("") != 0
				|| txtUnidadEmpleado.getText().compareTo("") != 0
				|| txtNombreEmpleado.getText().compareTo("") != 0
				|| txtFichaEmpleado.getText().compareTo("") != 0
				|| txtFichaSupervisorEmpleado.getText().compareTo("") != 0
				|| txtGradoAuxiliarEmpleado.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroEmpleado")
	public void abrirRegistro() {
		gpxDatosEmpleado.setOpen(false);
		gpxRegistroEmpleado.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosEmpleado")
	public void abrirCatalogo() {
		txtEmpresaEmpleado.setConstraint("");
		txtCargoEmpleado.setConstraint("");
		txtUnidadEmpleado.setConstraint("");
		txtGradoAuxiliarEmpleado.setConstraint("");
		gpxDatosEmpleado.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosEmpleado.setOpen(false);
								gpxRegistroEmpleado.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosEmpleado.setOpen(true);
									gpxRegistroEmpleado.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosEmpleado.setOpen(true);
			gpxRegistroEmpleado.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Empleado> seleccionados = catalogo.obtenerSeleccionados();
		if (seleccionados == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (seleccionados.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoItem);
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean camposLLenos() {
		if (txtEmpresaEmpleado.getText().compareTo("") == 0
				|| txtCargoEmpleado.getText().compareTo("") == 0
				|| txtUnidadEmpleado.getText().compareTo("") == 0) {
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

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(3).setVisible(!bol);

	}

	public void mostrarCatalogo() {

		final List<Empleado> listEmpleado = servicioEmpleado.buscarTodos();
		catalogo = new Catalogo<Empleado>(catalogoEmpleado,
				"Catalogo de Empleados", listEmpleado, "Código empleado",
				"Código Empresa", "Código Cargo", "Código Unidad Organizativa",
				"Nombre", "Ficha", "Ficha Supervisor", "Grado Auxiliar") {

			@Override
			protected List<Empleado> buscarCampos(List<String> valores) {
				List<Empleado> lista = new ArrayList<Empleado>();

				for (Empleado empleado : listEmpleado) {
					if (String.valueOf(empleado.getIdEmpleado()).toLowerCase()
							.startsWith(valores.get(0))
							&& String
									.valueOf(
											empleado.getEmpresa()
													.getIdEmpresa())
									.toLowerCase().startsWith(valores.get(1))
							&& String.valueOf(empleado.getCargo().getIdCargo())
									.toLowerCase().startsWith(valores.get(2))
							&& String
									.valueOf(
											empleado.getUnidadOrganizativa()
													.getIdUnidadOrganizativa())
									.toLowerCase().startsWith(valores.get(3))
							&& empleado.getNombre().toLowerCase()
									.startsWith(valores.get(4))
							&& empleado.getFicha().toLowerCase()
									.startsWith(valores.get(5))
							&& empleado.getFichaSupervisor().toLowerCase()
									.startsWith(valores.get(6))
							&& String.valueOf(empleado.getGradoAuxiliar())
									.toLowerCase().startsWith(valores.get(7))) {
						lista.add(empleado);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Empleado empleado) {
				String[] registros = new String[8];
				registros[0] = String.valueOf(empleado.getIdEmpleado());
				registros[1] = String.valueOf(empleado.getEmpresa()
						.getIdEmpresa());
				registros[2] = String.valueOf(empleado.getCargo().getIdCargo());
				registros[3] = String.valueOf(empleado.getUnidadOrganizativa()
						.getIdUnidadOrganizativa());
				registros[4] = empleado.getNombre();
				registros[5] = empleado.getFicha();
				registros[6] = empleado.getFichaSupervisor();
				registros[7] = String.valueOf(empleado.getGradoAuxiliar());

				return registros;
			}

			@Override
			protected List<Empleado> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		catalogo.setParent(catalogoEmpleado);

	}

	@Listen("onChange = #txtEmpresaEmpleado")
	public void buscarEmpresa() {
		Empresa empresa = servicioEmpresa.buscarEmpresa(Integer
				.valueOf(txtEmpresaEmpleado.getValue()));
		if (empresa == null) {
			msj.mensajeAlerta(Mensaje.claveRTNoEsta);
			txtEmpresaEmpleado.setFocus(true);
		}

	}

	@Listen("onChange = #txtCargoEmpleado")
	public void buscarCargo() {
		Cargo cargo = servicioCargo.buscarCargo(Integer
				.valueOf(txtCargoEmpleado.getValue()));
		if (cargo == null) {
			msj.mensajeAlerta(Mensaje.claveRTNoEsta);
			txtEmpresaEmpleado.setFocus(true);
		}

	}

	@Listen("onChange = #txtUnidadEmpleado")
	public void buscarUnidadOrganizativa() {
		UnidadOrganizativa unidad = servicioUnidadOrganizativa
				.buscarUnidad(Integer.valueOf(txtUnidadEmpleado.getValue()));
		if (unidad == null) {
			msj.mensajeAlerta(Mensaje.claveRTNoEsta);
			txtEmpresaEmpleado.setFocus(true);
		}

	}

}
