package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Actividad;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CActividad extends CGenerico {

	@Wire
	private Window wdwVActividad;
	@Wire
	private Div botoneraActividad;
	@Wire
	private Groupbox gpxRegistroActividad;
	@Wire
	private Textbox txtDescripcionActividad;
	@Wire
	private Groupbox gpxDatosActividad;
	@Wire
	private Div catalogoActividad;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idActividad = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Actividad> catalogo;
	protected List<Actividad> listaGeneral = new ArrayList<Actividad>();

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

		txtDescripcionActividad.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Actividad actividad = catalogo
								.objetoSeleccionadoDelCatalogo();
						idActividad = actividad.getId();
						txtDescripcionActividad.setValue(actividad
								.getDescripcion());
						txtDescripcionActividad.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

				if (validar()) {
					String descripcion = txtDescripcionActividad.getValue();
					String usuario = nombreUsuarioSesion();
					Timestamp fechaAuditoria = new Timestamp(
							new Date().getTime());
					Actividad actividad = new Actividad(idActividad,
							descripcion, fechaAuditoria, horaAuditoria, usuario);
					servicioActividad.guardar(actividad);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					listaGeneral = servicioActividad.buscarTodas();
					catalogo.actualizarLista(listaGeneral);
					abrirCatalogo();

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
				cerrarVentana(wdwVActividad, titulo, tabs);
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosActividad.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Actividad> eliminarLista = catalogo
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
													servicioActividad
															.eliminarVariasActividades(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioActividad
															.buscarTodas();
													catalogo.actualizarLista(listaGeneral);
													
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idActividad != 0) {
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
													servicioActividad
															.eliminarUnaActividad(idActividad);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioActividad
															.buscarTodas();
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
		botoneraActividad.appendChild(botonera);

	}

	public void limpiarCampos() {
		idActividad = 0;
		txtDescripcionActividad.setValue("");
		catalogo.limpiarSeleccion();
		txtDescripcionActividad.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionActividad.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroActividad")
	public void abrirRegistro() {
		gpxDatosActividad.setOpen(false);
		gpxRegistroActividad.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosActividad")
	public void abrirCatalogo() {
		gpxDatosActividad.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosActividad.setOpen(false);
								gpxRegistroActividad.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosActividad.setOpen(true);
									gpxRegistroActividad.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosActividad.setOpen(true);
			gpxRegistroActividad.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Actividad> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtDescripcionActividad.getText().compareTo("") == 0) {
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

		listaGeneral = servicioActividad.buscarTodas();
		catalogo = new Catalogo<Actividad>(catalogoActividad,
				"Catalogo de Actividades", listaGeneral, false, false, false,
				"Descripci�n") {

			@Override
			protected List<Actividad> buscar(List<String> valores) {
				List<Actividad> lista = new ArrayList<Actividad>();

				for (Actividad actividad : listaGeneral) {
					if (actividad.getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())) {
						lista.add(actividad);

					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Actividad actividad) {
				String[] registros = new String[1];
				registros[0] = actividad.getDescripcion();

				return registros;
			}

		};
		catalogo.setParent(catalogoActividad);

	}
}
