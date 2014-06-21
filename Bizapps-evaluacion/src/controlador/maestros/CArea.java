package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Area;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CArea extends CGenerico {

	@Wire
	private Div divVArea;
	@Wire
	private Div botoneraArea;
	@Wire
	private Groupbox gpxRegistroArea;
	@Wire
	private Textbox txtDescripcionArea;
	@Wire
	private Groupbox gpxDatosArea;
	@Wire
	private Div catalogoArea;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idArea = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Area> catalogo;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtDescripcionArea.setFocus(true);
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
						txtDescripcionArea.setValue(area.getDescripcion());
						txtDescripcionArea.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}


			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub
				
				String descripcion = txtDescripcionArea.getValue();
				String usuario = "JDE";
				Timestamp fechaAuditoria = new Timestamp(new Date().getTime());
				Area area = new Area(idArea, descripcion, usuario,
						fechaAuditoria,horaAuditoria);
				servicioArea.guardar(area);
				msj.mensajeInformacion(Mensaje.guardado);
				limpiar();
				catalogo.actualizarLista(servicioArea.buscarTodas());
				
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
				cerrarVentana(divVArea, "Area");
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
													servicioArea
															.eliminarVariasAreas(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioArea
															.buscarTodas());
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
													catalogo.actualizarLista(servicioArea.buscarTodas());
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
		botoneraArea.appendChild(botonera);

	}

	public void limpiarCampos() {
		idArea = 0;
		txtDescripcionArea.setValue("");
		catalogo.limpiarSeleccion();
		txtDescripcionArea.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionArea.getText().compareTo("") != 0) {
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

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(3).setVisible(!bol);

	}

	public void mostrarCatalogo() {

		final List<Area> listArea = servicioArea.buscarTodas();
		catalogo = new Catalogo<Area>(catalogoArea, "Catalogo de Areas",
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
				return null;
			}

		};
		catalogo.setParent(catalogoArea);

	}

}
