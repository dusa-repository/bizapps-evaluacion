package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.Empleado;
import modelo.maestros.Empresa;
import modelo.maestros.UnidadOrganizativa;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CEmpleado extends CGenerico {

	@Wire
	private Window wdwVEmpleado;
	@Wire
	private Div botoneraEmpleado;
	@Wire
	private Groupbox gpxRegistroEmpleado;
	@Wire
	private Textbox txtNombreEmpleado;
	@Wire
	private Combobox cmbNivelAcademicoEmpleado;
	@Wire
	private Textbox txtEspecialidadEmpleado;
	@Wire
	private Textbox txtEspecializacionEmpleado;
	@Wire
	private Textbox txtEmpresaEmpleado;
	@Wire
	private Label lblEmpresaEmpleado;
	@Wire
	private Button btnBuscarEmpresa;
	@Wire
	private Textbox txtCargoEmpleado;
	@Wire
	private Label lblCargoEmpleado;
	@Wire
	private Button btnBuscarCargo;
	@Wire
	private Textbox txtUnidadEmpleado;
	@Wire
	private Label lblUnidadEmpleado;
	@Wire
	private Label lblNombreSupervisor;
	@Wire
	private Button btnBuscarUnidad;
	@Wire
	private Textbox txtFichaEmpleado;
	@Wire
	private Textbox txtFichaSupervisorEmpleado;
	@Wire
	private Button btnBuscarSupervisor;
	@Wire
	private Spinner spnGradoAuxiliarEmpleado;
	@Wire
	private Groupbox gpxDatosEmpleado;
	@Wire
	private Div catalogoEmpleado;
	@Wire
	private Div divCatalogoEmpresa;
	@Wire
	private Div divCatalogoCargo;
	@Wire
	private Div divCatalogoSupervisor;
	@Wire
	private Div divCatalogoUnidad;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idEmpleado = 0;
	private int idCargo = 0;
	private int idEmpresa = 0;
	private int idUnidad = 0;
	private int idFichaSupervisor = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Empleado> catalogo;
	Catalogo<Empresa> catalogoEmpresa;
	Catalogo<Cargo> catalogoCargo;
	Catalogo<UnidadOrganizativa> catalogoUnidad;
	Catalogo<Empleado> catalogoSupervisor;
	protected List<Empleado> listaGeneral = new ArrayList<Empleado>();

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				titulo = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}
		txtEmpresaEmpleado.setFocus(true);
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
						idCargo = empleado.getCargo().getId();
						idEmpresa = empleado.getEmpresa().getId();
						idUnidad = empleado.getUnidadOrganizativa().getId();
						idFichaSupervisor = empleado.getId();
						txtEmpresaEmpleado.setValue(String.valueOf(empleado.getEmpresa()
								.getId()));
						lblEmpresaEmpleado.setValue(empleado.getEmpresa()
								.getNombre());
						txtCargoEmpleado.setValue(String.valueOf(empleado.getCargo()
								.getId()));
						lblCargoEmpleado.setValue(empleado.getCargo()
								.getDescripcion());
						txtUnidadEmpleado.setValue(String.valueOf(empleado
								.getUnidadOrganizativa().getId()));
						lblUnidadEmpleado.setValue(empleado
								.getUnidadOrganizativa().getDescripcion());
						txtNombreEmpleado.setValue(empleado.getNombre());
						
						cmbNivelAcademicoEmpleado.setValue(empleado
								.getNivelAcademico());
						txtEspecialidadEmpleado.setValue(empleado
								.getEspecialidad());
						txtEspecializacionEmpleado.setValue(empleado
								.getEspecializacion());
						txtFichaEmpleado.setValue(empleado.getFicha());
						Empleado fichaSupervisor = servicioEmpleado
								.buscarPorFicha(empleado
										.getFichaSupervisor());
						if (fichaSupervisor != null)
						{
							idFichaSupervisor=fichaSupervisor.getId();
							txtFichaSupervisorEmpleado.setValue(fichaSupervisor.getFicha());
							lblNombreSupervisor.setValue(fichaSupervisor.getNombre());
						}
						else
						{
							idFichaSupervisor=0;
							txtFichaSupervisorEmpleado.setValue("0");
							lblNombreSupervisor.setValue("SIN SUPERVISOR");
						}
						spnGradoAuxiliarEmpleado.setValue(empleado
								.getGradoAuxiliar());
						txtEmpresaEmpleado.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {

				if (validar()) {

					Empresa empresa = servicioEmpresa.buscarEmpresa(idEmpresa);
					Cargo cargo = servicioCargo.buscarCargo(idCargo);
					UnidadOrganizativa unidadOrganizativa = servicioUnidadOrganizativa
							.buscarUnidad(idUnidad);
					Empleado fichaSupervisorEmpleado = servicioEmpleado
							.buscar(idFichaSupervisor);

					if (empresa != null && cargo != null
							&& unidadOrganizativa != null
							&& fichaSupervisorEmpleado != null) {
						String nombre = txtNombreEmpleado.getValue();
						String nivelAcademico = cmbNivelAcademicoEmpleado
								.getValue();
						String especialidad = txtEspecialidadEmpleado
								.getValue();
						String especializacion = txtEspecializacionEmpleado
								.getValue();
						String ficha = txtFichaEmpleado.getValue();
						String fichaSupervisor = String
								.valueOf(fichaSupervisorEmpleado.getFicha());
						int gradoAuxiliar = spnGradoAuxiliarEmpleado.getValue();
						String usuario = nombreUsuarioSesion();
						Timestamp fechaAuditoria = new Timestamp(
								new Date().getTime());
						Empleado empleado = new Empleado(idEmpleado,
								fechaAuditoria, ficha, fichaSupervisor,
								gradoAuxiliar, nivelAcademico, especialidad,
								especializacion, horaAuditoria, nombre,
								usuario, cargo, empresa, unidadOrganizativa);
						servicioEmpleado.guardar(empleado);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						listaGeneral = servicioEmpleado.buscarTodos();
						catalogo.actualizarLista(listaGeneral);
						abrirCatalogo();
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
						} else if (fichaSupervisorEmpleado == null) {
							msj.mensajeAlerta(Mensaje.codigoSupervisor);
							txtFichaSupervisorEmpleado.setFocus(true);
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
				cerrarVentana2(wdwVEmpleado, titulo, tabs);
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
								.show("�Desea Eliminar los "
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
													listaGeneral = servicioEmpleado.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
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
													listaGeneral = servicioEmpleado.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
													abrirCatalogo();
												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}

			@Override
			public void buscar() {
				abrirCatalogo();
				
			}

			@Override
			public void annadir() {
				abrirRegistro();
				mostrarBotones(false);
				
				
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub
				
			}

		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraEmpleado.appendChild(botonera);
	}

	public void limpiarCampos() {
		idEmpleado = 0;
		idCargo = 0;
		idEmpresa = 0;
		idUnidad = 0;
		idFichaSupervisor = 0;
		txtNombreEmpleado.setValue("");
		cmbNivelAcademicoEmpleado.setValue("");
		txtEspecialidadEmpleado.setValue("");
		txtEspecializacionEmpleado.setValue("");
		txtEmpresaEmpleado.setValue("");
		spnGradoAuxiliarEmpleado.setValue(0);
		txtCargoEmpleado.setValue("");
		txtUnidadEmpleado.setValue("");
		txtNombreEmpleado.setValue("");
		txtFichaEmpleado.setValue("");
		txtFichaSupervisorEmpleado.setValue("");
		lblCargoEmpleado.setValue("");
		lblEmpresaEmpleado.setValue("");
		lblNombreSupervisor.setValue("");
		lblUnidadEmpleado.setValue("");
		catalogo.limpiarSeleccion();
		txtEmpresaEmpleado.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtEmpresaEmpleado.getText().compareTo("") != 0
				|| txtCargoEmpleado.getText().compareTo("") != 0
				|| txtUnidadEmpleado.getText().compareTo("") != 0
				|| txtNombreEmpleado.getText().compareTo("") != 0
				|| cmbNivelAcademicoEmpleado.getText().compareTo("") != 0
				|| txtEspecialidadEmpleado.getText().compareTo("") != 0
				|| txtEspecializacionEmpleado.getText().compareTo("") != 0
				|| txtFichaEmpleado.getText().compareTo("") != 0
				|| txtFichaSupervisorEmpleado.getText().compareTo("") != 0
				|| spnGradoAuxiliarEmpleado.getText().compareTo("0") != 0) {
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
				|| txtUnidadEmpleado.getText().compareTo("") == 0
				|| txtFichaSupervisorEmpleado.getText().compareTo("") == 0) {
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

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(3).setVisible(!bol);
		botonera.getChildren().get(5).setVisible(!bol);
		botonera.getChildren().get(2).setVisible(bol);
		botonera.getChildren().get(4).setVisible(bol);
		botonera.getChildren().get(8).setVisible(false);
		

	}

	public void mostrarCatalogo() {

		listaGeneral= servicioEmpleado.buscarTodos();
		catalogo = new Catalogo<Empleado>(catalogoEmpleado,
				"Catalogo de Empleados", listaGeneral, false, false, false,
				"Empresa", "Cargo", "Unidad Organizativa", "Nombre", "Ficha",
				"Ficha Supervisor", "Grado Auxiliar") {

			@Override
			protected List<Empleado> buscar(List<String> valores) {
				List<Empleado> lista = new ArrayList<Empleado>();

				for (Empleado empleado : listaGeneral) {
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
		catalogo.setParent(catalogoEmpleado);

	}

//	@Listen("onChange = #txtEmpresaEmpleado")
//	public void buscarEmpresa() {
//		List<Empresa> empresas = servicioEmpresa
//				.buscarPorNombres(txtEmpresaEmpleado.getValue());
//		if (empresas.size() == 0) {
//			msj.mensajeAlerta(Mensaje.codigoEmpresa);
//			txtEmpresaEmpleado.setFocus(true);
//		} else {
//
//			idEmpresa = empresas.get(0).getId();
//		}
//
//	}
//
//	@Listen("onChange = #txtCargoEmpleado")
//	public void buscarCargo() {
//		List<Cargo> cargo = servicioCargo.buscarPorNombres(txtCargoEmpleado
//				.getValue());
//		if (cargo.size() == 0) {
//			msj.mensajeAlerta(Mensaje.codigoCargo);
//			txtCargoEmpleado.setFocus(true);
//		} else {
//
//			idCargo = cargo.get(0).getId();
//		}
//
//	}
//
//	@Listen("onChange = #txtUnidadEmpleado")
//	public void buscarUnidadOrganizativa() {
//		List<UnidadOrganizativa> unidades = servicioUnidadOrganizativa
//				.buscarPorNombres(txtUnidadEmpleado.getValue());
//		if (unidades.size() == 0) {
//			msj.mensajeAlerta(Mensaje.codigoUnidad);
//			txtUnidadEmpleado.setFocus(true);
//		} else {
//
//			idUnidad = unidades.get(0).getId();
//		}
//
//	}
//
//	@Listen("onChange = #txtFichaSupervisorEmpleado")
//	public void buscarSupervisor() {
//		List<Empleado> empleados = servicioEmpleado
//				.buscarPorNombres(txtFichaSupervisorEmpleado.getValue());
//		if (empleados.size() == 0) {
//			msj.mensajeAlerta(Mensaje.codigoSupervisor);
//			txtFichaSupervisorEmpleado.setFocus(true);
//		} else {
//
//			idFichaSupervisor = empleados.get(0).getId();
//		}
//
//	}

	@Listen("onClick = #btnBuscarEmpresa")
	public void mostrarCatalogoEmpresa() {
		final List<Empresa> listEmpresa = servicioEmpresa.buscarTodas();
		catalogoEmpresa = new Catalogo<Empresa>(divCatalogoEmpresa,
				"Catalogo de Empresas", listEmpresa, true, false, false,
				"Nombre", "Direcci�n", "Tel�fono 1", "Tel�fono 2",
				"Empresa Auxiliar") {

			@Override
			protected List<Empresa> buscar(List<String> valores) {
				List<Empresa> lista = new ArrayList<Empresa>();

				for (Empresa empresa : listEmpresa) {
					if (empresa.getNombre().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& empresa.getDireccion().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& empresa.getTelefono1().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& empresa.getTelefono2().toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& empresa.getIdEmpresaAuxiliar().toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						lista.add(empresa);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Empresa empresa) {
				String[] registros = new String[5];
				registros[0] = empresa.getNombre();
				registros[1] = empresa.getDireccion();
				registros[2] = empresa.getTelefono1();
				registros[3] = empresa.getTelefono2();
				registros[4] = empresa.getIdEmpresaAuxiliar();

				return registros;
			}

		};

		catalogoEmpresa.setClosable(true);
		catalogoEmpresa.setWidth("80%");
		catalogoEmpresa.setParent(divCatalogoEmpresa);
		catalogoEmpresa.doModal();
		catalogoEmpresa.setTitle("Catalogo de Empresas");
	}

	@Listen("onSeleccion = #divCatalogoEmpresa")
	public void seleccionEmpresa() {
		Empresa empresa = catalogoEmpresa.objetoSeleccionadoDelCatalogo();
		idEmpresa = empresa.getId();
		txtEmpresaEmpleado.setValue(String.valueOf(empresa.getId()));
		lblEmpresaEmpleado.setValue(empresa.getNombre());
		catalogoEmpresa.setParent(null);
	}

	@Listen("onClick = #btnBuscarCargo")
	public void mostrarCatalogoCargo() {
		final List<Cargo> listCargo = servicioCargo.buscarTodos();
		catalogoCargo = new Catalogo<Cargo>(divCatalogoCargo,
				"Catalogo de Cargos", listCargo, true, false, false,
				"Descripci�n", "N�mina", "Cargo Auxiliar", "Empresa Auxiliar") {

			@Override
			protected List<Cargo> buscar(List<String> valores) {
				List<Cargo> lista = new ArrayList<Cargo>();

				for (Cargo cargo : listCargo) {
					if (cargo.getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& cargo.getNomina().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& cargo.getIdCargoAuxiliar().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& cargo.getIdEmpresaAuxiliar().toLowerCase()
									.contains(valores.get(3).toLowerCase())) {
						lista.add(cargo);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Cargo cargo) {
				String[] registros = new String[4];
				registros[0] = cargo.getDescripcion();
				registros[1] = cargo.getNomina();
				registros[2] = cargo.getIdCargoAuxiliar();
				registros[3] = cargo.getIdEmpresaAuxiliar();

				return registros;
			}

		};

		catalogoCargo.setClosable(true);
		catalogoCargo.setWidth("80%");
		catalogoCargo.setParent(divCatalogoCargo);
		catalogoCargo.doModal();
		catalogoCargo.setTitle("Catalogo de Cargos");
	}

	@Listen("onSeleccion = #divCatalogoCargo")
	public void seleccionCargo() {
		Cargo cargo = catalogoCargo.objetoSeleccionadoDelCatalogo();
		idCargo = cargo.getId();
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
				true, false, false, "Gerencia", "Descripci�n", "Nivel",
				"Sub-Nivel", "Empresa Auxiliar", "Unidad Auxiliar") {

			@Override
			protected List<UnidadOrganizativa> buscar(List<String> valores) {
				List<UnidadOrganizativa> lista = new ArrayList<UnidadOrganizativa>();

				for (UnidadOrganizativa unidad : listUnidadOrganizativa) {
					if (unidad.getGerencia().getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& unidad.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& String.valueOf(unidad.getNivel()).toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& String.valueOf(unidad.getSubNivel())
									.toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& unidad.getIdEmpresaAuxiliar().toLowerCase()
									.contains(valores.get(4).toLowerCase())
							&& unidad.getIdUnidadOrganizativaAuxiliar()
									.toLowerCase()
									.contains(valores.get(5).toLowerCase())) {
						lista.add(unidad);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(UnidadOrganizativa unidad) {
				String[] registros = new String[6];
				registros[0] = unidad.getGerencia().getDescripcion();
				registros[1] = unidad.getDescripcion();
				registros[2] = String.valueOf(unidad.getNivel());
				registros[3] = String.valueOf(unidad.getSubNivel());
				registros[4] = String.valueOf(unidad.getIdEmpresaAuxiliar());
				registros[5] = String.valueOf(unidad
						.getIdUnidadOrganizativaAuxiliar());
				return registros;
			}

		};

		catalogoUnidad.setClosable(true);
		catalogoUnidad.setWidth("80%");
		catalogoUnidad.setParent(divCatalogoUnidad);
		catalogoUnidad.doModal();
		catalogoUnidad.setTitle("Catalogo de Unidades Organizativas");
	}

	@Listen("onSeleccion = #divCatalogoUnidad")
	public void seleccionUnidad() {
		UnidadOrganizativa unidad = catalogoUnidad
				.objetoSeleccionadoDelCatalogo();
		idUnidad = unidad.getId();
		txtUnidadEmpleado.setValue(String.valueOf(unidad.getId()));
		lblUnidadEmpleado.setValue(unidad.getDescripcion());
		catalogoUnidad.setParent(null);
	}

	@Listen("onClick = #btnBuscarSupervisor")
	public void mostrarCatalogoSupervisor() {
		final List<Empleado> listEmpleado = servicioEmpleado.buscarTodos();
		catalogoSupervisor = new Catalogo<Empleado>(divCatalogoSupervisor,
				"Catalogo Empleados", listEmpleado, true, false, false,
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
				String[] registros = new String[7];
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

		catalogoSupervisor.setClosable(true);
		catalogoSupervisor.setWidth("80%");
		catalogoSupervisor.setParent(divCatalogoSupervisor);
		catalogoSupervisor.doModal();
		catalogoSupervisor.setTitle("Catalogo de Empleados");
	}

	@Listen("onSeleccion = #divCatalogoSupervisor")
	public void seleccionSupervisor() {
		Empleado empleado = catalogoSupervisor.objetoSeleccionadoDelCatalogo();
		idFichaSupervisor = empleado.getId();
		txtFichaSupervisorEmpleado.setValue(empleado.getFicha());
		lblNombreSupervisor.setValue(empleado.getNombre());
		catalogoSupervisor.setParent(null);
	}

	
	@Listen("onChange = #txtFichaSupervisorEmpleado; onOK =  #txtFichaSupervisorEmpleado")
	public boolean buscarIdGerencia() {
		try {
		if (txtFichaSupervisorEmpleado.getText().compareTo("") != 0) {
			Empleado empleado = servicioEmpleado.buscarPorFicha(txtFichaSupervisorEmpleado.getValue());
			if (empleado != null) {
				txtFichaSupervisorEmpleado.setValue(empleado.getFicha());
				lblNombreSupervisor.setValue(empleado.getNombre());
				idFichaSupervisor = Integer.valueOf(empleado.getId());
				return false;
			} else {
				txtFichaSupervisorEmpleado.setFocus(true);
				lblNombreSupervisor.setValue("");
				msj.mensajeError(Mensaje.codigoEmpleado);
				return true;
			}

		} else
			return false;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public boolean buscarIdEmpresa() {
		try {
		if (txtEmpresaEmpleado.getText().compareTo("") != 0) {
			Empresa empresa = servicioEmpresa.buscarEmpresa(Integer.valueOf(txtEmpresaEmpleado.getValue()));
			if (empresa != null) {
				txtEmpresaEmpleado.setValue(String.valueOf(empresa.getId()));
				lblEmpresaEmpleado.setValue(empresa.getNombre());
				idEmpresa = empresa.getId();
				return false;
			} else {
				txtEmpresaEmpleado.setFocus(true);
				lblEmpresaEmpleado.setValue("");
				msj.mensajeError(Mensaje.codigoEmpresa);
				return true;
			}

		} else
			return false;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	
	@Listen("onChange = #txtCargoEmpleado; onOK =  #txtCargoEmpleado")
	public boolean buscarIdCargo() {
		try {
		if (txtCargoEmpleado.getText().compareTo("") != 0) {
			Cargo cargo = servicioCargo.buscarCargo(Integer.valueOf(txtEmpresaEmpleado.getValue()));
			if (cargo != null) {
				txtCargoEmpleado.setValue(String.valueOf(cargo.getId()));
				lblCargoEmpleado.setValue(cargo.getDescripcion());
				idCargo = cargo.getId();
				return false;
			} else {
				txtCargoEmpleado.setFocus(true);
				lblCargoEmpleado.setValue("");
				msj.mensajeError(Mensaje.codigoCargo);
				return true;
			}

		} else
			return false;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	@Listen("onChange = #txtUnidadEmpleado; onOK =  #txtUnidadEmpleado")
	public boolean buscarIdUnidadOrganizativa() {
		try {
		if (txtUnidadEmpleado.getText().compareTo("") != 0) {
			UnidadOrganizativa unidad = servicioUnidadOrganizativa.buscarUnidad(Integer.valueOf(txtEmpresaEmpleado.getValue()));
			if (unidad != null) {
				txtUnidadEmpleado.setValue(String.valueOf(unidad.getId()));
				lblUnidadEmpleado.setValue(unidad.getDescripcion());
				idUnidad = unidad.getId();
				return false;
			} else {
				txtUnidadEmpleado.setFocus(true);
				lblUnidadEmpleado.setValue("");
				msj.mensajeError(Mensaje.codigoUnidad);
				return true;
			}

		} else
			return false;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
