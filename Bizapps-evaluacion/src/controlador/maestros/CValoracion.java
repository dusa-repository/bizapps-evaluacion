package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Valoracion;

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
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CValoracion extends CGenerico {

	@Wire
	private Window wdwVValoracion;
	@Wire
	private Div botoneraValoracion;
	@Wire
	private Groupbox gpxRegistroValoracion;
	@Wire
	private Textbox txtNombreValoracion;
	@Wire
	private Textbox txtDescripcionValoracion;
	@Wire
	private Textbox txtOrdenValoracion;
	@Wire
	private Textbox txtRangoInferiorValoracion;
	@Wire
	private Textbox txtRangoSuperiorValoracion;
	@Wire
	private Textbox txtValorValoracion;
	@Wire
	private Groupbox gpxDatosValoracion;
	@Wire
	private Div catalogoValoracion;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idValoracion = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<Valoracion> catalogo;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtNombreValoracion.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Valoracion valoracion = catalogo
								.objetoSeleccionadoDelCatalogo();
						idValoracion = valoracion.getId();
						txtNombreValoracion.setValue(valoracion.getNombre());
						txtDescripcionValoracion.setValue(valoracion
								.getDescripcion());
						txtOrdenValoracion.setValue(String.valueOf(valoracion
								.getOrden()));
						txtRangoInferiorValoracion.setValue(String
								.valueOf(valoracion.getRangoInferior()));
						txtRangoSuperiorValoracion.setValue(String
								.valueOf(valoracion.getRangoSuperior()));
						txtValorValoracion.setValue(String.valueOf(valoracion
								.getValor()));
						txtNombreValoracion.setFocus(true);
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
					String nombre = txtNombreValoracion.getValue();
					String descripcion = txtDescripcionValoracion.getValue();			
					int orden = Integer.valueOf(txtOrdenValoracion.getValue());
					int rangoInferior = Integer
								.valueOf(txtRangoInferiorValoracion.getValue());
					int rangoSuperior = Integer
								.valueOf(txtRangoSuperiorValoracion.getValue());
					int valor = Integer.valueOf(txtValorValoracion.getValue());
					String usuario = nombreUsuarioSesion();
					Timestamp fechaAuditoria = new Timestamp(
							new Date().getTime());
					Valoracion valoracion = new Valoracion(idValoracion,
							descripcion, fechaAuditoria, horaAuditoria,
							usuario, nombre, orden, rangoInferior,
							rangoSuperior, valor);
					servicioValoracion.guardar(valoracion);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					catalogo.actualizarLista(servicioValoracion.buscarTodas());
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
				cerrarVentana1(wdwVValoracion, "Valoracion");
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosValoracion.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Valoracion> eliminarLista = catalogo
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
													servicioValoracion
															.eliminarVariasValoraciones(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioValoracion
															.buscarTodas());
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idValoracion != 0) {
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
													servicioValoracion
															.eliminarUnaValoracion(idValoracion);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioValoracion
															.buscarTodas());
													abrirCatalogo();
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
		botoneraValoracion.appendChild(botonera);

	}

	public void limpiarCampos() {
		idValoracion = 0;
		txtNombreValoracion.setValue("");
		txtDescripcionValoracion.setValue("");
		txtOrdenValoracion.setConstraint("");
		txtOrdenValoracion.setValue("");
		txtOrdenValoracion
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El orden de la valoración debe ser numérico");
		txtRangoInferiorValoracion.setConstraint("");
		txtRangoInferiorValoracion.setValue("");
		txtRangoInferiorValoracion
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El rango inferior de la valoración debe ser numérico");
		txtRangoSuperiorValoracion.setConstraint("");
		txtRangoSuperiorValoracion.setValue("");
		txtRangoSuperiorValoracion
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El rango superior de la valoración debe ser numérico");
		txtValorValoracion.setConstraint("");
		txtValorValoracion.setValue("");
		txtValorValoracion
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El valor de la valoración debe ser numérico");
		catalogo.limpiarSeleccion();
		txtNombreValoracion.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtNombreValoracion.getText().compareTo("") != 0
				|| txtDescripcionValoracion.getText().compareTo("") != 0
				|| txtOrdenValoracion.getText().compareTo("") != 0
				|| txtRangoInferiorValoracion.getText().compareTo("") != 0
				|| txtRangoSuperiorValoracion.getText().compareTo("") != 0
				|| txtValorValoracion.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroValoracion")
	public void abrirRegistro() {
		gpxDatosValoracion.setOpen(false);
		gpxRegistroValoracion.setOpen(true);
		txtOrdenValoracion
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El orden de la valoración debe ser numérico");
		txtRangoInferiorValoracion
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El rango inferior de la valoración debe ser numérico");
		txtRangoSuperiorValoracion
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El rango superior de la valoración debe ser numérico");
		txtValorValoracion
		.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El valor de la valoración debe ser numérico");
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosValoracion")
	public void abrirCatalogo() {
		txtOrdenValoracion.setConstraint("");
		txtOrdenValoracion.setValue("");
		txtRangoInferiorValoracion.setConstraint("");
		txtRangoInferiorValoracion.setValue("");
		txtRangoSuperiorValoracion.setConstraint("");
		txtRangoSuperiorValoracion.setValue("");
		txtValorValoracion.setConstraint("");
		txtValorValoracion.setValue("");
		gpxDatosValoracion.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosValoracion.setOpen(false);
								gpxRegistroValoracion.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosValoracion.setOpen(true);
									gpxRegistroValoracion.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosValoracion.setOpen(true);
			gpxRegistroValoracion.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<Valoracion> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtNombreValoracion.getText().compareTo("") == 0) {
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

		final List<Valoracion> listValoracion = servicioValoracion
				.buscarTodas();
		catalogo = new Catalogo<Valoracion>(catalogoValoracion,
				"Catalogo de Valoraciones", listValoracion,
				"Código valoración", "Nombre", "Descripción", "Orden",
				"Rango inferior", "Rango superior", "Valor") {

			@Override
			protected List<Valoracion> buscarCampos(List<String> valores) {
				List<Valoracion> lista = new ArrayList<Valoracion>();

				for (Valoracion valoracion : listValoracion) {
					if (String.valueOf(valoracion.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& valoracion.getNombre().toLowerCase()
									.startsWith(valores.get(1))
							&& valoracion.getDescripcion().toLowerCase()
									.startsWith(valores.get(2))
							&& String.valueOf(valoracion.getOrden())
									.toLowerCase().startsWith(valores.get(3))
							&& String.valueOf(valoracion.getRangoInferior())
									.toLowerCase().startsWith(valores.get(4))
							&& String.valueOf(valoracion.getRangoSuperior())
									.toLowerCase().startsWith(valores.get(5))
							&& String.valueOf(valoracion.getValor())
									.toLowerCase().startsWith(valores.get(6))) {
						lista.add(valoracion);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Valoracion valoracion) {
				String[] registros = new String[7];
				registros[0] = String.valueOf(valoracion.getId());
				registros[1] = valoracion.getNombre();
				registros[2] = valoracion.getDescripcion();
				registros[3] = String.valueOf(valoracion.getOrden());
				registros[4] = String.valueOf(valoracion.getRangoInferior());
				registros[5] = String.valueOf(valoracion.getRangoSuperior());
				registros[6] = String.valueOf(valoracion.getValor());

				return registros;
			}

			@Override
			protected List<Valoracion> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código valoración"))
					return servicioValoracion.filtroId(valor);
				if (combo.equals("Nombre"))
					return servicioValoracion.filtroNombre(valor);
				else if (combo.equals("Descripción"))
					return servicioValoracion.filtroDescripcion(valor);
				else if (combo.equals("Orden"))
					return servicioValoracion.filtroOrden(valor);
				else if (combo.equals("Rango inferior"))
					return servicioValoracion.filtroRangoInferior(valor);
				else if (combo.equals("Rango superior"))
					return servicioValoracion.filtroRangoSuperior(valor);
				else if (combo.equals("Valor"))
					return servicioValoracion.filtroValor(valor);
				else
					return servicioValoracion.buscarTodas();
			}

		};
		catalogo.setParent(catalogoValoracion);

	}

}