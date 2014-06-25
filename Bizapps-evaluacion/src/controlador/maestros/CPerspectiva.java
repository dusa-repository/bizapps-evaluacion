package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Perspectiva;

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

public class CPerspectiva extends CGenerico {

	@Wire
	private Div divVPerspectiva;
	@Wire
	private Div botoneraPerspectiva;
	@Wire
	private Groupbox gpxRegistroPerspectiva;
	@Wire
	private Textbox txtDescripcionPerspectiva;
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

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

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
						Perspectiva perspectiva = catalogo.objetoSeleccionadoDelCatalogo();
						idPerspectiva = perspectiva.getId();
						txtDescripcionPerspectiva.setValue(perspectiva.getDescripcion());
						txtDescripcionPerspectiva.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}


			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub
				
				String descripcion = txtDescripcionPerspectiva.getValue();
				String usuario = nombreUsuarioSesion();
				Timestamp fechaAuditoria = new Timestamp(new Date().getTime());
				Perspectiva perspectiva = new Perspectiva(idPerspectiva, descripcion, usuario,
						 fechaAuditoria, horaAuditoria);
				servicioPerspectiva.guardar(perspectiva);
				msj.mensajeInformacion(Mensaje.guardado);
				limpiar();
				catalogo.actualizarLista(servicioPerspectiva.buscar());
				
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
				cerrarVentana(divVPerspectiva, "Perspectiva");
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
													servicioPerspectiva
															.eliminarVariasPerspectivas(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioPerspectiva
															.buscar());
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
													catalogo.actualizarLista(servicioPerspectiva.buscar());
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
		botoneraPerspectiva.appendChild(botonera);

	}

	public void limpiarCampos() {
		idPerspectiva = 0;
		txtDescripcionPerspectiva.setValue("");
		catalogo.limpiarSeleccion();
		txtDescripcionPerspectiva.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtDescripcionPerspectiva.getText().compareTo("") != 0) {
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

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(3).setVisible(!bol);

	}

	public void mostrarCatalogo() {

		final List<Perspectiva> listPerspectiva = servicioPerspectiva.buscar();
		catalogo = new Catalogo<Perspectiva>(catalogoPerspectiva, "Catalogo de Perspectivas",
				listPerspectiva, "Código perspectiva", "Descripción") {

			@Override
			protected List<Perspectiva> buscarCampos(List<String> valores) {
				List<Perspectiva> lista = new ArrayList<Perspectiva>();

				for (Perspectiva perspectiva : listPerspectiva) {
					if (String.valueOf(perspectiva.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& perspectiva.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))) {
						lista.add(perspectiva);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Perspectiva perspectiva) {
				String[] registros = new String[2];
				registros[0] = String.valueOf(perspectiva.getId());
				registros[1] = perspectiva.getDescripcion();

				return registros;
			}

			@Override
			protected List<Perspectiva> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código perspectiva"))
					return servicioPerspectiva.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioPerspectiva.filtroDescripcion(valor);
				else
					return servicioPerspectiva.buscar();
			}

		};
		catalogo.setParent(catalogoPerspectiva);

	}

}

