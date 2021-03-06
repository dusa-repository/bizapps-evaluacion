package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.TipoFormacion;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CArea extends CGenerico {

	@Wire
	private Window wdwVArea;
	@Wire
	private Div botoneraArea;
	@Wire
	private Groupbox gpxRegistroArea;
	@Wire
	private Textbox txtDescripcionArea;
	@Wire
	private Textbox txtTipoFormacionArea;
	@Wire
	private Label lblTipoFormacionArea;
	@Wire
	private Button btnBuscarTipoFormacion;
	@Wire
	private Groupbox gpxDatosArea;
	@Wire
	private Div catalogoArea;
	@Wire
	private Div divCatalogoTipoFormacion;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idArea = 0;
	private int idTipoFormacion = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Area> catalogo;
	Catalogo<TipoFormacion> catalogoTipoFormacion;
	protected List<Area> listaGeneral = new ArrayList<Area>();

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
		txtTipoFormacionArea.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Area area = catalogo.objetoSeleccionadoDelCatalogo();
						idArea = area.getId();
						idTipoFormacion = area.getTipoFormacion().getId();
						txtTipoFormacionArea.setValue(String.valueOf(area.getTipoFormacion()
								.getId()));
						lblTipoFormacionArea.setValue(area.getTipoFormacion()
								.getDescripcion());
						txtDescripcionArea.setValue(area.getDescripcion());
						txtTipoFormacionArea.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub
				if (validar()) {
					String descripcion = txtDescripcionArea.getValue();
					String usuario = nombreUsuarioSesion();
					Timestamp fechaAuditoria = new Timestamp(
							new Date().getTime());
					TipoFormacion tipoFormacion = servicioTipoFormacion
							.buscarTipoFormacion(idTipoFormacion);
					if (tipoFormacion != null) {
						Area area = new Area(idArea, descripcion, usuario,
								fechaAuditoria, horaAuditoria, tipoFormacion);
						servicioArea.guardar(area);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						listaGeneral = servicioArea.buscarTodas();
						catalogo.actualizarLista(listaGeneral);
						abrirCatalogo();
					} else {

						msj.mensajeAlerta(Mensaje.codigoTipoFormacion);
						txtTipoFormacionArea.setFocus(true);

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
				cerrarVentana2(wdwVArea, titulo, tabs);
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosArea.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Area> eliminarLista = catalogo
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
													servicioArea
															.eliminarVariasAreas(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioArea.buscarTodas();
													catalogo.actualizarLista(listaGeneral);
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idArea != 0) {
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
													servicioArea
															.eliminarUnArea(idArea);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioArea.buscarTodas();
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
				// TODO Auto-generated method stub
				abrirCatalogo();
			}

			@Override
			public void annadir() {
				// TODO Auto-generated method stub
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
		botoneraArea.appendChild(botonera);

	}

	public void limpiarCampos() {
		idArea = 0;
		idTipoFormacion = 0;
		txtDescripcionArea.setValue("");
		txtTipoFormacionArea.setValue("");
		catalogo.limpiarSeleccion();
		txtTipoFormacionArea.setFocus(true);
		lblTipoFormacionArea.setValue("");

	}

	public boolean camposEditando() {
		if (txtTipoFormacionArea.getText().compareTo("") != 0
				|| txtDescripcionArea.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroArea")
	public void abrirRegistro() {
		gpxDatosArea.setOpen(false);
		gpxRegistroArea.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosArea")
	public void abrirCatalogo() {
		gpxDatosArea.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosArea.setOpen(false);
								gpxRegistroArea.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosArea.setOpen(true);
									gpxRegistroArea.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosArea.setOpen(true);
			gpxRegistroArea.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Area> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtTipoFormacionArea.getText().compareTo("") == 0) {
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

		listaGeneral = servicioArea.buscarTodas();
		catalogo = new Catalogo<Area>(catalogoArea, "Catalogo de Areas",
				listaGeneral,false,false,false, "Tipo de Formaci�n", "Descripci�n") {

			@Override
			protected List<Area> buscar(List<String> valores) {
				List<Area> lista = new ArrayList<Area>();

				for (Area area : listaGeneral) {
					if (area.getTipoFormacion().getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& area.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(area);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Area area) {
				String[] registros = new String[2];
				registros[0] = area.getTipoFormacion().getDescripcion();
				registros[1] = area.getDescripcion();

				return registros;
			}

		};
		catalogo.setParent(catalogoArea);

	}

	@Listen("onClick = #btnBuscarTipoFormacion")
	public void mostrarCatalogoTipoFormacion() {
		final List<TipoFormacion> listTipoFormacion = servicioTipoFormacion
				.buscarTodos();
		catalogoTipoFormacion = new Catalogo<TipoFormacion>(
				divCatalogoTipoFormacion, "Catalogo de Tipos de Formacion",
				listTipoFormacion,true,false,false, "Descripci�n") {

			@Override
			protected List<TipoFormacion> buscar(List<String> valores) {
				List<TipoFormacion> lista = new ArrayList<TipoFormacion>();

				for (TipoFormacion tipoFormacion : listTipoFormacion) {
					if (tipoFormacion.getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())) {
						lista.add(tipoFormacion);

					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(TipoFormacion tipoFormacion) {
				String[] registros = new String[1];
				registros[0] = tipoFormacion.getDescripcion();

				return registros;
			}

		};

		catalogoTipoFormacion.setClosable(true);
		catalogoTipoFormacion.setWidth("80%");
		catalogoTipoFormacion.setParent(divCatalogoTipoFormacion);
		catalogoTipoFormacion.doModal();
		catalogoTipoFormacion.setTitle("Catalogo de Tipo de Formaci�n");
	}

	@Listen("onSeleccion = #divCatalogoTipoFormacion")
	public void seleccionTipoFormacion() {
		TipoFormacion tipoFormacion = catalogoTipoFormacion
				.objetoSeleccionadoDelCatalogo();
		idTipoFormacion = tipoFormacion.getId();
		txtTipoFormacionArea.setValue(String.valueOf(tipoFormacion.getId()));
		lblTipoFormacionArea.setValue(tipoFormacion.getDescripcion());
		catalogoTipoFormacion.setParent(null);
	}

//	@Listen("onChange = #txtTipoFormacionArea")
//	public void buscarTipoFormacion() {
//		List<TipoFormacion> tipos = servicioTipoFormacion
//				.buscarPorNombres(txtTipoFormacionArea.getValue());
//
//		if (tipos.size() == 0) {
//			msj.mensajeAlerta(Mensaje.codigoTipoFormacion);
//			txtTipoFormacionArea.setFocus(true);
//		} else {
//
//			idTipoFormacion = tipos.get(0).getId();
//		}
//
//	}
	
	@Listen("onChange = #txtTipoFormacionArea; onOK =  #txtTipoFormacionArea")
	public boolean buscarTipoFormacion() {
		try {
		if (txtTipoFormacionArea.getText().compareTo("") != 0) {
			TipoFormacion tipo = servicioTipoFormacion.buscarTipoFormacion(Integer.valueOf(txtTipoFormacionArea.getValue()));
			if (tipo != null) {
				txtTipoFormacionArea.setValue(String.valueOf(tipo.getId()));
				lblTipoFormacionArea.setValue(tipo.getDescripcion());
				idTipoFormacion = tipo.getId();
				return false;
			} else {
				txtTipoFormacionArea.setFocus(true);
				lblTipoFormacionArea.setValue("");
				msj.mensajeError(Mensaje.codigoTipoFormacion);
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
