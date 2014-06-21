package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Dominio;

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

public class CDominio extends CGenerico {

	@Wire
	private Div divVDominio;
	@Wire
	private Div botoneraDominio;
	@Wire
	private Groupbox gpxRegistroDominio;
	@Wire
	private Textbox txtDescripcionDominio;
	@Wire
	private Textbox txtTipoDominio;
	@Wire
	private Textbox txtComentarioDominio;
	@Wire
	private Groupbox gpxDatosDominio;
	@Wire
	private Div catalogoDominio;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idDominio = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Dominio> catalogo;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtDescripcionDominio.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Dominio dominio = catalogo
								.objetoSeleccionadoDelCatalogo();
						idDominio = dominio.getId();
						txtDescripcionDominio.setValue(dominio
								.getDescripcionDominio());
						txtTipoDominio.setValue(dominio.getTipo());
						txtComentarioDominio.setValue(dominio.getComentario());
						txtDescripcionDominio.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

				String descripcionDominio = txtDescripcionDominio.getValue();
				String tipo = txtTipoDominio.getValue();
				String comentario = txtComentarioDominio.getValue();
				String usuario = "JDE";
				Timestamp fechaAuditoria = new Timestamp(new Date().getTime());
				Dominio dominio = new Dominio(idDominio, comentario,
						descripcionDominio, tipo, usuario, fechaAuditoria,
						horaAuditoria);
				servicioDominio.guardar(dominio);
				msj.mensajeInformacion(Mensaje.guardado);
				limpiar();
				catalogo.actualizarLista(servicioDominio.buscarTodos());

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
				cerrarVentana(divVDominio, "Dominio");
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosDominio.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Dominio> eliminarLista = catalogo
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
													servicioDominio
															.eliminarVariosDominios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioDominio
															.buscarTodos());
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idDominio != 0) {
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
													servicioDominio
															.eliminarUnDominio(idDominio);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioDominio
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
		botoneraDominio.appendChild(botonera);

	}

	public void limpiarCampos() {
		idDominio = 0;
		txtDescripcionDominio.setValue("");
		txtTipoDominio.setValue("");
		txtComentarioDominio.setValue("");
		catalogo.limpiarSeleccion();
		txtDescripcionDominio.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionDominio.getText().compareTo("") != 0
				|| txtTipoDominio.getText().compareTo("") != 0
				|| txtComentarioDominio.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroDominio")
	public void abrirRegistro() {
		gpxDatosDominio.setOpen(false);
		gpxRegistroDominio.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosDominio")
	public void abrirCatalogo() {
		gpxDatosDominio.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosDominio.setOpen(false);
								gpxRegistroDominio.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosDominio.setOpen(true);
									gpxRegistroDominio.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosDominio.setOpen(true);
			gpxRegistroDominio.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Dominio> seleccionados = catalogo.obtenerSeleccionados();
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

		final List<Dominio> listDominio = servicioDominio.buscarTodos();
		catalogo = new Catalogo<Dominio>(catalogoDominio,
				"Catalogo de Dominios", listDominio, "Código dominio",
				"Descripción", "Tipo", "Comentario") {

			@Override
			protected List<Dominio> buscarCampos(List<String> valores) {
				List<Dominio> lista = new ArrayList<Dominio>();

				for (Dominio dominio : listDominio) {
					if (String.valueOf(dominio.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& dominio.getDescripcionDominio().toLowerCase()
									.startsWith(valores.get(1))
							&& dominio.getTipo().toLowerCase()
									.startsWith(valores.get(2))
							&& dominio.getComentario().toLowerCase()
									.startsWith(valores.get(3))) {
						lista.add(dominio);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Dominio dominio) {
				String[] registros = new String[4];
				registros[0] = String.valueOf(dominio.getId());
				registros[1] = dominio.getDescripcionDominio();
				registros[2] = dominio.getTipo();
				registros[3] = dominio.getComentario();

				return registros;
			}

			@Override
			protected List<Dominio> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		catalogo.setParent(catalogoDominio);

	}

}
