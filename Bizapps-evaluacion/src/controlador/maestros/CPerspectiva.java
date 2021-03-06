package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Perspectiva;

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

public class CPerspectiva extends CGenerico {

	@Wire
	private Window wdwVPerspectiva;
	@Wire
	private Div botoneraPerspectiva;
	@Wire
	private Groupbox gpxRegistroPerspectiva;
	@Wire
	private Textbox txtDescripcionPerspectiva;
	@Wire
	private Spinner spnOrdenPerspectiva;
	@Wire
	private Groupbox gpxDatosPerspectiva;
	@Wire
	private Div catalogoPerspectiva;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idPerspectiva = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Perspectiva> catalogo;
	protected List<Perspectiva> listaGeneral = new ArrayList<Perspectiva>();

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
		txtDescripcionPerspectiva.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Perspectiva perspectiva = catalogo
								.objetoSeleccionadoDelCatalogo();
						idPerspectiva = perspectiva.getId();
						txtDescripcionPerspectiva.setValue(perspectiva
								.getDescripcion());
						spnOrdenPerspectiva.setValue(perspectiva.getOrden());
						txtDescripcionPerspectiva.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub
				if (validar()) {
					String descripcion = txtDescripcionPerspectiva.getValue();
					String usuario = nombreUsuarioSesion();
					Timestamp fechaAuditoria = new Timestamp(
							new Date().getTime());
					int orden = spnOrdenPerspectiva.getValue();
					Perspectiva perspectiva = new Perspectiva(idPerspectiva,
							descripcion, usuario, fechaAuditoria,
							horaAuditoria, orden);
					servicioPerspectiva.guardar(perspectiva);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					listaGeneral = servicioPerspectiva.buscar();
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
				cerrarVentana2(wdwVPerspectiva, titulo,tabs);
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosPerspectiva.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Perspectiva> eliminarLista = catalogo
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
													servicioPerspectiva
															.eliminarVariasPerspectivas(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioPerspectiva.buscar();
													catalogo.actualizarLista(listaGeneral);
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idPerspectiva != 0) {
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
													servicioPerspectiva
															.eliminarUnaPerspectiva(idPerspectiva);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioPerspectiva.buscar();
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
		botoneraPerspectiva.appendChild(botonera);

	}

	public void limpiarCampos() {
		idPerspectiva = 0;
		txtDescripcionPerspectiva.setValue("");
		spnOrdenPerspectiva.setValue(0);
		catalogo.limpiarSeleccion();
		txtDescripcionPerspectiva.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionPerspectiva.getText().compareTo("") != 0
				|| spnOrdenPerspectiva.getText().compareTo("0") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroPerspectiva")
	public void abrirRegistro() {
		gpxDatosPerspectiva.setOpen(false);
		gpxRegistroPerspectiva.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosPerspectiva")
	public void abrirCatalogo() {
		gpxDatosPerspectiva.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosPerspectiva.setOpen(false);
								gpxRegistroPerspectiva.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosPerspectiva.setOpen(true);
									gpxRegistroPerspectiva.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosPerspectiva.setOpen(true);
			gpxRegistroPerspectiva.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Perspectiva> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtDescripcionPerspectiva.getText().compareTo("") == 0) {
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

		listaGeneral = servicioPerspectiva.buscar();
		catalogo = new Catalogo<Perspectiva>(catalogoPerspectiva,
				"Catalogo de Perspectivas", listaGeneral, false,false,false,"Descripci�n",
				"Orden") {

			@Override
			protected List<Perspectiva> buscar(List<String> valores) {
				List<Perspectiva> lista = new ArrayList<Perspectiva>();

				for (Perspectiva perspectiva : listaGeneral) {
					if (perspectiva.getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& String.valueOf(perspectiva.getOrden())
									.toLowerCase().contains(valores.get(1).toLowerCase())) {
						lista.add(perspectiva);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Perspectiva perspectiva) {
				String[] registros = new String[2];
				registros[0] = String.valueOf(perspectiva.getDescripcion());
				registros[1] = String.valueOf(perspectiva.getOrden());

				return registros;
			}


		};
		catalogo.setParent(catalogoPerspectiva);

	}

}
