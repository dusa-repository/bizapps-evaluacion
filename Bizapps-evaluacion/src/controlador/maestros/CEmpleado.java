package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Cargo;
import modelo.maestros.Empleado;
import modelo.maestros.Empresa;
import modelo.maestros.UnidadOrganizativa;

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
	private Div divCatalogoUnidad;
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

		txtEmpresaEmpleado.setFocus(true);
		txtEmpresaEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la empresa debe ser numérico");
		txtCargoEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código del cargo debe ser numérico");
		txtUnidadEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la unidad organizativa debe ser numérico");
		txtGradoAuxiliarEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El grado auxiliar debe ser numérico");
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
						idEmpleado = empleado.getId();
						txtEmpresaEmpleado.setValue(String.valueOf(empleado
								.getEmpresa().getId()));
						lblEmpresaEmpleado.setValue(empleado.getEmpresa()
								.getNombre());
						txtCargoEmpleado.setValue(String.valueOf(empleado
								.getCargo().getId()));
						lblCargoEmpleado.setValue(empleado.getCargo()
								.getDescripcion());
						txtUnidadEmpleado.setValue(String.valueOf(empleado
								.getUnidadOrganizativa().getId()));
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
						String usuario = nombreUsuarioSesion();
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

						if (empresa == null) {
							msj.mensajeAlerta(Mensaje.codigoEmpresa);
							txtEmpresaEmpleado.setFocus(true);
						} else if (cargo == null) {
							msj.mensajeAlerta(Mensaje.codigoCargo);
							txtCargoEmpleado.setFocus(true);
						} else if (unidadOrganizativa == null) {
							msj.mensajeAlerta(Mensaje.codigoUnidad);
							txtUnidadEmpleado.setFocus(true);
						}

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
		txtEmpresaEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la empresa debe ser numérico");
		txtCargoEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código del cargo debe ser numérico");
		txtUnidadEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la unidad organizativa debe ser numérico");
		txtGradoAuxiliarEmpleado
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El grado auxiliar debe ser numérico");
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosEmpleado")
	public void abrirCatalogo() {
		txtEmpresaEmpleado.setConstraint("");
		txtEmpresaEmpleado.setValue("");
		txtCargoEmpleado.setConstraint("");
		txtCargoEmpleado.setValue("");
		txtUnidadEmpleado.setConstraint("");
		txtUnidadEmpleado.setValue("");
		txtGradoAuxiliarEmpleado.setConstraint("");
		txtGradoAuxiliarEmpleado.setValue("");
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
				"Código Empresa", "Código Cargo", "Código Unidad", "Nombre",
				"Ficha", "Ficha Supervisor", "Grado Auxiliar") {

			@Override
			protected List<Empleado> buscarCampos(List<String> valores) {
				List<Empleado> lista = new ArrayList<Empleado>();

				for (Empleado empleado : listEmpleado) {
					if (String.valueOf(empleado.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& String.valueOf(empleado.getEmpresa().getId())
									.toLowerCase().startsWith(valores.get(1))
							&& String.valueOf(empleado.getCargo().getId())
									.toLowerCase().startsWith(valores.get(2))
							&& String
									.valueOf(
											empleado.getUnidadOrganizativa()
													.getId()).toLowerCase()
									.startsWith(valores.get(3))
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
				registros[0] = String.valueOf(empleado.getId());
				registros[1] = String.valueOf(empleado.getEmpresa().getId());
				registros[2] = String.valueOf(empleado.getCargo().getId());
				registros[3] = String.valueOf(empleado.getUnidadOrganizativa()
						.getId());
				registros[4] = empleado.getNombre();
				registros[5] = empleado.getFicha();
				registros[6] = empleado.getFichaSupervisor();
				registros[7] = String.valueOf(empleado.getGradoAuxiliar());

				return registros;
			}

			@Override
			protected List<Empleado> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código empleado"))
					return servicioEmpleado.filtroId(valor);
				else if (combo.equals("Código Empresa"))
					return servicioEmpleado.filtroEmpresa(valor);
				else if (combo.equals("Código Cargo"))
					return servicioEmpleado.filtroCargo(valor);
				else if (combo.equals("Código"))
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
		catalogo.setParent(catalogoEmpleado);

	}

	@Listen("onChange = #txtEmpresaEmpleado")
	public void buscarEmpresa() {
		Empresa empresa = servicioEmpresa.buscarEmpresa(Integer
				.valueOf(txtEmpresaEmpleado.getValue()));
		if (empresa == null) {
			msj.mensajeAlerta(Mensaje.codigoEmpresa);
			txtEmpresaEmpleado.setFocus(true);
		}

	}

	@Listen("onChange = #txtCargoEmpleado")
	public void buscarCargo() {
		Cargo cargo = servicioCargo.buscarCargo(Integer
				.valueOf(txtCargoEmpleado.getValue()));
		if (cargo == null) {
			msj.mensajeAlerta(Mensaje.codigoCargo);
			txtCargoEmpleado.setFocus(true);
		}

	}

	@Listen("onChange = #txtUnidadEmpleado")
	public void buscarUnidadOrganizativa() {
		UnidadOrganizativa unidad = servicioUnidadOrganizativa
				.buscarUnidad(Integer.valueOf(txtUnidadEmpleado.getValue()));
		if (unidad == null) {
			msj.mensajeAlerta(Mensaje.codigoUnidad);
			txtUnidadEmpleado.setFocus(true);
		}

	}

	@Listen("onClick = #btnBuscarEmpresa")
	public void mostrarCatalogoEmpresa() {
		final List<Empresa> listEmpresa = servicioEmpresa.buscarTodas();
		catalogoEmpresa = new Catalogo<Empresa>(divCatalogoEmpresa,
				"Catalogo de Empresas", listEmpresa, "Código empresa",
				"Nombre", "Dirección", "Teléfono 1", "Teléfono 2",
				"Empresa Auxiliar") {

			@Override
			protected List<Empresa> buscarCampos(List<String> valores) {
				List<Empresa> lista = new ArrayList<Empresa>();

				for (Empresa empresa : listEmpresa) {
					if (String.valueOf(empresa.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& empresa.getNombre().toLowerCase()
									.startsWith(valores.get(1))
							&& empresa.getDireccion().toLowerCase()
									.startsWith(valores.get(2))
							&& empresa.getTelefono1().toLowerCase()
									.startsWith(valores.get(3))
							&& empresa.getTelefono2().toLowerCase()
									.startsWith(valores.get(4))
							&& empresa.getIdEmpresaAuxiliar().toLowerCase()
									.startsWith(valores.get(5))) {
						lista.add(empresa);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Empresa empresa) {
				String[] registros = new String[6];
				registros[0] = String.valueOf(empresa.getId());
				registros[1] = empresa.getNombre();
				registros[2] = empresa.getDireccion();
				registros[3] = empresa.getTelefono1();
				registros[4] = empresa.getTelefono2();
				registros[5] = empresa.getIdEmpresaAuxiliar();

				return registros;
			}

			@Override
			protected List<Empresa> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código empresa"))
					return servicioEmpresa.filtroId(valor);
				else if (combo.equals("Nombre"))
					return servicioEmpresa.filtroNombre(valor);
				else if (combo.equals("Dirección"))
					return servicioEmpresa.filtroDireccion(valor);
				else if (combo.equals("Teléfono 1"))
					return servicioEmpresa.filtroTelefono1(valor);
				else if (combo.equals("Teléfono 2"))
					return servicioEmpresa.filtroTelefono2(valor);
				else if (combo.equals("Empresa Auxiliar"))
					return servicioEmpresa.filtroEmpresaAuxiliar(valor);
				else
					return servicioEmpresa.buscarTodas();
			}

		};

		catalogoEmpresa.setParent(divCatalogoEmpresa);
		catalogoEmpresa.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEmpresa")
	public void seleccionEmpresa() {
		Empresa empresa = catalogoEmpresa.objetoSeleccionadoDelCatalogo();
		txtEmpresaEmpleado.setValue(String.valueOf(empresa.getId()));
		lblEmpresaEmpleado.setValue(empresa.getNombre());
		catalogoEmpresa.setParent(null);
	}

	@Listen("onClick = #btnBuscarCargo")
	public void mostrarCatalogoCargo() {
		final List<Cargo> listCargo = servicioCargo.buscarTodos();
		catalogoCargo = new Catalogo<Cargo>(divCatalogoCargo,
				"Catalogo de Cargos", listCargo, "Código cargo", "Descripción",
				"Nómina", "Cargo Auxiliar", "Empresa Auxiliar") {

			@Override
			protected List<Cargo> buscarCampos(List<String> valores) {
				List<Cargo> lista = new ArrayList<Cargo>();

				for (Cargo cargo : listCargo) {
					if (String.valueOf(cargo.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& cargo.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))
							&& cargo.getNomina().toLowerCase()
									.startsWith(valores.get(2))
							&& cargo.getIdCargoAuxiliar().toLowerCase()
									.startsWith(valores.get(3))
							&& cargo.getIdEmpresaAuxiliar().toLowerCase()
									.startsWith(valores.get(4))) {
						lista.add(cargo);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Cargo cargo) {
				String[] registros = new String[5];
				registros[0] = String.valueOf(cargo.getId());
				registros[1] = cargo.getDescripcion();
				registros[2] = cargo.getNomina();
				registros[3] = cargo.getIdCargoAuxiliar();
				registros[4] = cargo.getIdEmpresaAuxiliar();

				return registros;
			}

			@Override
			protected List<Cargo> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código cargo"))
					return servicioCargo.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioCargo.filtroDescripcion(valor);
				else if (combo.equals("Nómina"))
					return servicioCargo.filtroNomina(valor);
				else if (combo.equals("Cargo Auxiliar"))
					return servicioCargo.filtroCargoAuxiliar(valor);
				else if (combo.equals("Empresa Auxiliar"))
					return servicioCargo.filtroEmpresaAuxiliar(valor);
				else
					return servicioCargo.buscarTodos();
			}

		};

		catalogoCargo.setParent(divCatalogoCargo);
		catalogoCargo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoCargo")
	public void seleccionCargo() {
		Cargo cargo = catalogoCargo.objetoSeleccionadoDelCatalogo();
		txtCargoEmpleado.setValue(String.valueOf(cargo.getId()));
		lblCargoEmpleado.setValue(cargo.getDescripcion());
		catalogoCargo.setParent(null);
	}

	@Listen("onClick = #btnBuscarUnidad")
	public void mostrarCatalogoUnidad() {
		final List<UnidadOrganizativa> listUnidadOrganizativa = servicioUnidadOrganizativa
				.buscarTodas();
		catalogoUnidad = new Catalogo<UnidadOrganizativa>(divCatalogoUnidad,
				"Catalogo de UnidadOrganizativas", listUnidadOrganizativa,
				"Código Unidad", "Código Gerencia", "Descripción", "Nivel",
				"Sub-Nivel", "Empresa Auxiliar", "Unidad Auxiliar") {

			@Override
			protected List<UnidadOrganizativa> buscarCampos(List<String> valores) {
				List<UnidadOrganizativa> lista = new ArrayList<UnidadOrganizativa>();

				for (UnidadOrganizativa unidad : listUnidadOrganizativa) {
					if (String.valueOf(unidad.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& String.valueOf(unidad.getGerencia().getId())
									.toLowerCase().startsWith(valores.get(1))
							&& unidad.getDescripcion().toLowerCase()
									.startsWith(valores.get(2))
							&& String.valueOf(unidad.getNivel()).toLowerCase()
									.startsWith(valores.get(3))
							&& String.valueOf(unidad.getSubNivel())
									.toLowerCase().startsWith(valores.get(4))
							&& unidad.getIdEmpresaAuxiliar().toLowerCase()
									.startsWith(valores.get(5))
							&& unidad.getIdUnidadOrganizativaAuxiliar()
									.toLowerCase().startsWith(valores.get(6))) {
						lista.add(unidad);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(UnidadOrganizativa unidad) {
				String[] registros = new String[7];
				registros[0] = String.valueOf(unidad.getId());
				registros[1] = String.valueOf(unidad.getGerencia().getId());
				registros[2] = unidad.getDescripcion();
				registros[3] = String.valueOf(unidad.getNivel());
				registros[4] = String.valueOf(unidad.getSubNivel());
				registros[5] = String.valueOf(unidad.getIdEmpresaAuxiliar());
				registros[6] = String.valueOf(unidad
						.getIdUnidadOrganizativaAuxiliar());
				return registros;
			}

			@Override
			protected List<UnidadOrganizativa> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código Unidad"))
					return servicioUnidadOrganizativa.filtroId(valor);
				else if (combo.equals("Código Gerencia"))
					return servicioUnidadOrganizativa.filtroGerencia(valor);
				else if (combo.equals("Descripción"))
					return servicioUnidadOrganizativa.filtroDescripcion(valor);
				else if (combo.equals("Nivel"))
					return servicioUnidadOrganizativa.filtroNivel(valor);
				else if (combo.equals("Sub-Nivel"))
					return servicioUnidadOrganizativa.filtroSubNivel(valor);
				else if (combo.equals("Empresa Auxiliar"))
					return servicioUnidadOrganizativa
							.filtroEmpresaAuxiliar(valor);
				else if (combo.equals("Unidad Auxiliar"))
					return servicioUnidadOrganizativa
							.filtroUnidadAuxiliar(valor);
				else
					return servicioUnidadOrganizativa.buscarTodas();
			}

		};

		catalogoUnidad.setParent(divCatalogoUnidad);
		catalogoUnidad.doModal();
	}

	@Listen("onSeleccion = #divCatalogoUnidad")
	public void seleccionUnidad() {
		UnidadOrganizativa unidad = catalogoUnidad
				.objetoSeleccionadoDelCatalogo();
		txtUnidadEmpleado.setValue(String.valueOf(unidad.getId()));
		lblUnidadEmpleado.setValue(unidad.getDescripcion());
		catalogoUnidad.setParent(null);
	}

}
