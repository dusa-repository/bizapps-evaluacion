package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Distribucion;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CDistribucion extends CGenerico {

	@Wire
	private Window wdwVDistribucion;
	@Wire
	private Div botoneraDistribucion;
	@Wire
	private Groupbox gpxRegistroDistribucion;
	@Wire
	private Textbox txtDescripcionDistribucion;
	@Wire
	private Spinner spnPorcentajeDistribucion;
	@Wire
	private Groupbox gpxDatosDistribucion;
	@Wire
	private Div catalogoDistribucion;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idDistribucion = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Distribucion> catalogo;
	protected List<Distribucion> listaGeneral = new ArrayList<Distribucion>();

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
		txtDescripcionDistribucion.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Distribucion distribucion = catalogo
								.objetoSeleccionadoDelCatalogo();
						idDistribucion = distribucion.getId();
						txtDescripcionDistribucion.setValue(distribucion
								.getDescripcion());
						spnPorcentajeDistribucion.setValue(distribucion
								.getPorcentaje());
						txtDescripcionDistribucion.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub
				if (validar()) {
					String descripcion = txtDescripcionDistribucion.getValue();
					int porcentaje = spnPorcentajeDistribucion.getValue();
					String usuario = nombreUsuarioSesion();
					Timestamp fechaAuditoria = new Timestamp(
							new Date().getTime());
					Distribucion distribucion = new Distribucion(
							idDistribucion, descripcion, usuario,
							fechaAuditoria, horaAuditoria, porcentaje);
					servicioDistribucion.guardar(distribucion);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					listaGeneral = servicioDistribucion.buscarTodas();
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
				cerrarVentana2(wdwVDistribucion, titulo,tabs);
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosDistribucion.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Distribucion> eliminarLista = catalogo
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
													servicioDistribucion
															.eliminarVariasDistribuciones(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioDistribucion.buscarTodas();
													catalogo.actualizarLista(listaGeneral);
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idDistribucion != 0) {
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
													servicioDistribucion
															.eliminarUnaDistribucion(idDistribucion);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioDistribucion.buscarTodas();
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
		botoneraDistribucion.appendChild(botonera);

	}

	public void limpiarCampos() {
		idDistribucion = 0;
		txtDescripcionDistribucion.setValue("");
		spnPorcentajeDistribucion.setValue(null);
		catalogo.limpiarSeleccion();
		txtDescripcionDistribucion.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionDistribucion.getText().compareTo("") != 0
				|| spnPorcentajeDistribucion.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroDistribucion")
	public void abrirRegistro() {
		gpxDatosDistribucion.setOpen(false);
		gpxRegistroDistribucion.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosDistribucion")
	public void abrirCatalogo() {
		gpxDatosDistribucion.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosDistribucion.setOpen(false);
								gpxRegistroDistribucion.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosDistribucion.setOpen(true);
									gpxRegistroDistribucion.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosDistribucion.setOpen(true);
			gpxRegistroDistribucion.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Distribucion> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtDescripcionDistribucion.getText().compareTo("") == 0
				|| spnPorcentajeDistribucion.getText().compareTo("") == 0) {
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

		listaGeneral = servicioDistribucion
				.buscarTodas();
		catalogo = new Catalogo<Distribucion>(catalogoDistribucion,
				"Catalogo de Distribuciones", listaGeneral, false,false,false,"Descripci�n",
				"Porcentaje") {

			@Override
			protected List<Distribucion> buscar(List<String> valores) {
				List<Distribucion> lista = new ArrayList<Distribucion>();

				for (Distribucion distribucion : listaGeneral) {
					if (distribucion.getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& String.valueOf(distribucion.getPorcentaje())
									.toLowerCase().contains(valores.get(1).toLowerCase())) {
						lista.add(distribucion);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Distribucion distribucion) {
				String[] registros = new String[2];
				registros[0] = distribucion.getDescripcion();
				registros[1] = String.valueOf(distribucion.getPorcentaje());

				return registros;
			}


		};
		catalogo.setParent(catalogoDistribucion);

	}

}
