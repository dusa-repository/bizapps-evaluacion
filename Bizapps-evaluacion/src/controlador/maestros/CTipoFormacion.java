package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Gerencia;
import modelo.maestros.TipoFormacion;

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
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CTipoFormacion extends CGenerico {

	@Wire
	private Window wdwVTipoFormacion;
	@Wire
	private Div botoneraTipoFormacion;
	@Wire
	private Groupbox gpxRegistroTipoFormacion;
	@Wire
	private Textbox txtDescripcionTipoFormacion;
	@Wire
	private Textbox txtAreaTipoFormacion;
	@Wire
	private Button btnBuscarArea;
	@Wire
	private Label lblAreaTipoFormacion;
	@Wire
	private Groupbox gpxDatosTipoFormacion;
	@Wire
	private Div catalogoTipoFormacion;
	@Wire
	private Div divCatalogoArea;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idTipoFormacion = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<TipoFormacion> catalogo;
	Catalogo<Area> catalogoArea;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtAreaTipoFormacion.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						TipoFormacion tipoFormacion = catalogo
								.objetoSeleccionadoDelCatalogo();
						idTipoFormacion = tipoFormacion.getId();
						txtDescripcionTipoFormacion.setValue(tipoFormacion
								.getDescripcion());
						txtAreaTipoFormacion.setValue(String
								.valueOf(tipoFormacion.getArea().getId()));
						lblAreaTipoFormacion.setValue(servicioArea.buscarArea(
								tipoFormacion.getArea().getId())
								.getDescripcion());
						txtDescripcionTipoFormacion.setFocus(true);
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
					String descripcion = txtDescripcionTipoFormacion.getValue();
					Area area = servicioArea.buscarArea(Integer
							.valueOf(txtAreaTipoFormacion.getValue()));
					if (area != null) {

						String usuario = nombreUsuarioSesion();
						Timestamp fechaAuditoria = new Timestamp(
								new Date().getTime());
						TipoFormacion tipoFormacion = new TipoFormacion(
								idTipoFormacion, descripcion, fechaAuditoria,
								horaAuditoria, area, usuario);
						servicioTipoFormacion.guardar(tipoFormacion);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						catalogo.actualizarLista(servicioTipoFormacion
								.buscarTodos());
						abrirCatalogo();
					} else {
						msj.mensajeAlerta(Mensaje.codigoArea);
						txtAreaTipoFormacion.setFocus(true);
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
				cerrarVentana1(wdwVTipoFormacion, "Tipo de Formacion");
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosTipoFormacion.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<TipoFormacion> eliminarLista = catalogo
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
													servicioTipoFormacion
															.eliminarVariosTipos(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioTipoFormacion
															.buscarTodos());
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idTipoFormacion != 0) {
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
													servicioTipoFormacion
															.eliminarUnTipo(idTipoFormacion);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioTipoFormacion
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
		botoneraTipoFormacion.appendChild(botonera);

	}

	public void limpiarCampos() {
		idTipoFormacion = 0;
		txtDescripcionTipoFormacion.setValue("");
		txtAreaTipoFormacion.setConstraint("");
		txtAreaTipoFormacion.setValue("");
		txtAreaTipoFormacion
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código del área debe ser numérico");
		lblAreaTipoFormacion.setValue("");
		catalogo.limpiarSeleccion();
		txtAreaTipoFormacion.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionTipoFormacion.getText().compareTo("") != 0
				|| txtAreaTipoFormacion.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroTipoFormacion")
	public void abrirRegistro() {
		gpxDatosTipoFormacion.setOpen(false);
		gpxRegistroTipoFormacion.setOpen(true);
		txtAreaTipoFormacion
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código del área debe ser numérico");
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosTipoFormacion")
	public void abrirCatalogo() {
		txtAreaTipoFormacion.setConstraint("");
		txtAreaTipoFormacion.setValue("");
		gpxDatosTipoFormacion.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosTipoFormacion.setOpen(false);
								gpxRegistroTipoFormacion.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosTipoFormacion.setOpen(true);
									gpxRegistroTipoFormacion.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosTipoFormacion.setOpen(true);
			gpxRegistroTipoFormacion.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<TipoFormacion> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtAreaTipoFormacion.getText().compareTo("") == 0) {
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

		final List<TipoFormacion> listTipoFormacion = servicioTipoFormacion
				.buscarTodos();
		catalogo = new Catalogo<TipoFormacion>(catalogoTipoFormacion,
				"Catalogo de Tipos de Formacion", listTipoFormacion,
				"Código Tipo Formación", "Código Área", "Área", "Descripción") {

			@Override
			protected List<TipoFormacion> buscarCampos(List<String> valores) {
				List<TipoFormacion> lista = new ArrayList<TipoFormacion>();

				for (TipoFormacion tipoFormacion : listTipoFormacion) {
					if (String.valueOf(tipoFormacion.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& String.valueOf(tipoFormacion.getArea().getId())
									.toLowerCase().startsWith(valores.get(1))
							&& tipoFormacion.getArea().getDescripcion()
									.toLowerCase().startsWith(valores.get(2))
							&& tipoFormacion.getDescripcion().toLowerCase()
									.startsWith(valores.get(3))) {
						lista.add(tipoFormacion);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(TipoFormacion tipoFormacion) {
				String[] registros = new String[4];
				registros[0] = String.valueOf(tipoFormacion.getId());
				registros[1] = String.valueOf(tipoFormacion.getArea().getId());
				registros[2] = tipoFormacion.getArea().getDescripcion();
				registros[3] = tipoFormacion.getDescripcion();

				return registros;
			}

			@Override
			protected List<TipoFormacion> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código Tipo Formación"))
					return servicioTipoFormacion.filtroId(valor);
				else if (combo.equals("Código Área"))
					return servicioTipoFormacion.filtroArea(valor);
				else if (combo.equals("Área"))
					return servicioTipoFormacion.filtroArea(valor);
				else if (combo.equals("Descripción"))
					return servicioTipoFormacion.filtroDescripcion(valor);
				else
					return servicioTipoFormacion.buscarTodos();
			}

		};
		catalogo.setParent(catalogoTipoFormacion);

	}

	@Listen("onChange = #txtAreaTipoFormacion")
	public void buscarArea() {
		Area area = servicioArea.buscarArea(Integer
				.valueOf(txtAreaTipoFormacion.getValue()));
		if (area == null) {
			msj.mensajeAlerta(Mensaje.codigoArea);
			txtAreaTipoFormacion.setFocus(true);
		}

	}

	@Listen("onClick = #btnBuscarArea")
	public void mostrarCatalogoArea() {
		final List<Area> listArea = servicioArea.buscarTodas();
		catalogoArea = new Catalogo<Area>(divCatalogoArea, "Catalogo de Areas",
				listArea, "Código área", "Descripción") {

			@Override
			protected List<Area> buscarCampos(List<String> valores) {
				List<Area> lista = new ArrayList<Area>();

				for (Area area : listArea) {
					if (String.valueOf(area.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& area.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))) {
						lista.add(area);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Area area) {
				String[] registros = new String[2];
				registros[0] = String.valueOf(area.getId());
				registros[1] = area.getDescripcion();

				return registros;
			}

			@Override
			protected List<Area> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código área"))
					return servicioArea.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioArea.filtroNombre(valor);
				else
					return servicioArea.buscarTodas();
			}

		};

		catalogoArea.setParent(divCatalogoArea);
		catalogoArea.doModal();
	}

	@Listen("onSeleccion = #divCatalogoArea")
	public void seleccionArea() {
		Area area = catalogoArea.objetoSeleccionadoDelCatalogo();
		txtAreaTipoFormacion.setValue(String.valueOf(area.getId()));
		lblAreaTipoFormacion.setValue(area.getDescripcion());
		catalogoArea.setParent(null);
	}

}
