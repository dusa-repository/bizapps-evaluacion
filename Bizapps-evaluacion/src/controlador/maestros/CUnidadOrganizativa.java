package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Gerencia;
import modelo.maestros.UnidadOrganizativa;

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

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CUnidadOrganizativa extends CGenerico {

	@Wire
	private Div divVUnidadOrganizativa;
	@Wire
	private Div botoneraUnidadOrganizativa;
	@Wire
	private Groupbox gpxRegistroUnidadOrganizativa;
	@Wire
	private Textbox txtGerenciaUnidadOrganizativa;
	@Wire
	private Button btnBuscarGerencia;
	@Wire
	private Label lblGerenciaUnidadOrganizativa;
	@Wire
	private Textbox txtDescripcionUnidadOrganizativa;
	@Wire
	private Textbox txtNivelUnidadOrganizativa;
	@Wire
	private Textbox txtSubNivelUnidadOrganizativa;
	@Wire
	private Textbox txtEmpresaAuxiliarUnidadOrganizativa;
	@Wire
	private Textbox txtUnidadOrganizativaAuxiliar;
	@Wire
	private Groupbox gpxDatosUnidadOrganizativa;
	@Wire
	private Div catalogoUnidadOrganizativa;
	@Wire
	private Div divCatalogoGerencia;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idUnidadOrganizativa = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<UnidadOrganizativa> catalogo;
	Catalogo<Gerencia> catalogoGerencia;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtGerenciaUnidadOrganizativa.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						UnidadOrganizativa unidad = catalogo
								.objetoSeleccionadoDelCatalogo();
						idUnidadOrganizativa = unidad.getId();
						txtGerenciaUnidadOrganizativa.setValue(String
								.valueOf(unidad.getGerencia().getId()));
						lblGerenciaUnidadOrganizativa.setValue(servicioGerencia
								.buscarGerencia(unidad.getGerencia().getId())
								.getDescripcion());
						txtDescripcionUnidadOrganizativa.setValue(unidad
								.getDescripcion());
						txtNivelUnidadOrganizativa.setValue(String
								.valueOf(unidad.getNivel()));
						txtSubNivelUnidadOrganizativa.setValue(String
								.valueOf(unidad.getSubNivel()));
						txtEmpresaAuxiliarUnidadOrganizativa.setValue(unidad
								.getIdEmpresaAuxiliar());
						txtUnidadOrganizativaAuxiliar.setValue(unidad
								.getIdUnidadOrganizativaAuxiliar());
						txtDescripcionUnidadOrganizativa.setFocus(true);
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
					String descripcion = txtDescripcionUnidadOrganizativa
							.getValue();
					Gerencia gerencia = servicioGerencia.buscarGerencia(Integer
							.valueOf(txtGerenciaUnidadOrganizativa.getValue()));

					if (gerencia != null) {

						int nivel = 0;
						int subNivel = 0;
						if (txtNivelUnidadOrganizativa.getValue() != null)
							nivel = Integer.valueOf(txtNivelUnidadOrganizativa
									.getValue());
						if (txtSubNivelUnidadOrganizativa.getValue() != null)
							subNivel = Integer
									.valueOf(txtSubNivelUnidadOrganizativa
											.getValue());
						String idEmpresaAuxiliar = txtEmpresaAuxiliarUnidadOrganizativa
								.getValue();
						String idUnidadOrganizativaAuxiliar = txtUnidadOrganizativaAuxiliar
								.getValue();
						String usuario = nombreUsuarioSesion();
						Timestamp fechaAuditoria = new Timestamp(
								new Date().getTime());
						UnidadOrganizativa unidad = new UnidadOrganizativa(
								idUnidadOrganizativa, descripcion,
								fechaAuditoria, horaAuditoria,
								idEmpresaAuxiliar,
								idUnidadOrganizativaAuxiliar, nivel, subNivel,
								usuario, gerencia);
						servicioUnidadOrganizativa.guardar(unidad);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						catalogo.actualizarLista(servicioUnidadOrganizativa
								.buscarTodas());
					} else {

						msj.mensajeAlerta(Mensaje.codigoGerencia);
						txtGerenciaUnidadOrganizativa.setFocus(true);
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
				cerrarVentana(divVUnidadOrganizativa, "Unidad Organizativa");
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosUnidadOrganizativa.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<UnidadOrganizativa> eliminarLista = catalogo
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
													servicioUnidadOrganizativa
															.eliminarVariasUnidades(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioUnidadOrganizativa
															.buscarTodas());
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idUnidadOrganizativa != 0) {
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
													servicioUnidadOrganizativa
															.eliminarUnaUnidad(idUnidadOrganizativa);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioUnidadOrganizativa
															.buscarTodas());
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
		botoneraUnidadOrganizativa.appendChild(botonera);

	}

	public void limpiarCampos() {
		idUnidadOrganizativa = 0;
		txtGerenciaUnidadOrganizativa.setConstraint("");
		txtGerenciaUnidadOrganizativa.setValue("");
		txtGerenciaUnidadOrganizativa
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la gerencia debe ser numérico");
		txtDescripcionUnidadOrganizativa.setValue("");
		txtNivelUnidadOrganizativa.setConstraint("");
		txtNivelUnidadOrganizativa.setValue("");
		txtNivelUnidadOrganizativa
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El nivel debe ser numérico");
		txtSubNivelUnidadOrganizativa.setConstraint("");
		txtSubNivelUnidadOrganizativa.setValue("");
		txtSubNivelUnidadOrganizativa
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El sub-nivel debe ser numérico");
		txtEmpresaAuxiliarUnidadOrganizativa.setValue("");
		txtUnidadOrganizativaAuxiliar.setValue("");
		lblGerenciaUnidadOrganizativa.setValue("");
		catalogo.limpiarSeleccion();
		txtDescripcionUnidadOrganizativa.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtGerenciaUnidadOrganizativa.getText().compareTo("") != 0
				|| txtDescripcionUnidadOrganizativa.getText().compareTo("") != 0
				|| txtNivelUnidadOrganizativa.getText().compareTo("") != 0
				|| txtSubNivelUnidadOrganizativa.getText().compareTo("") != 0
				|| txtEmpresaAuxiliarUnidadOrganizativa.getText().compareTo("") != 0
				|| txtUnidadOrganizativaAuxiliar.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroUnidadOrganizativa")
	public void abrirRegistro() {
		gpxDatosUnidadOrganizativa.setOpen(false);
		gpxRegistroUnidadOrganizativa.setOpen(true);
		txtGerenciaUnidadOrganizativa
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la gerencia debe ser numérico");
		txtNivelUnidadOrganizativa
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El nivel debe ser numérico");
		txtSubNivelUnidadOrganizativa
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El sub-nivel debe ser numérico");
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosUnidadOrganizativa")
	public void abrirCatalogo() {
		txtGerenciaUnidadOrganizativa.setConstraint("");
		txtGerenciaUnidadOrganizativa.setValue("");
		txtNivelUnidadOrganizativa.setConstraint("");
		txtNivelUnidadOrganizativa.setValue("");
		txtSubNivelUnidadOrganizativa.setConstraint("");
		txtSubNivelUnidadOrganizativa.setValue("");
		gpxDatosUnidadOrganizativa.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosUnidadOrganizativa.setOpen(false);
								gpxRegistroUnidadOrganizativa.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosUnidadOrganizativa.setOpen(true);
									gpxRegistroUnidadOrganizativa
											.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosUnidadOrganizativa.setOpen(true);
			gpxRegistroUnidadOrganizativa.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<UnidadOrganizativa> seleccionados = catalogo
				.obtenerSeleccionados();
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
		if (txtGerenciaUnidadOrganizativa.getText().compareTo("") == 0) {
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

		final List<UnidadOrganizativa> listUnidadOrganizativa = servicioUnidadOrganizativa
				.buscarTodas();
		catalogo = new Catalogo<UnidadOrganizativa>(catalogoUnidadOrganizativa,
				"Catalogo de UnidadOrganizativas", listUnidadOrganizativa,
				"Código Unidad", "Código Gerencia", "Descripción", "Nivel",
				"Sub-Nivel", "Empresa Auxiliar", "Unidad Auxiliar") {

			@Override
			protected List<UnidadOrganizativa> buscarCampos(List<String> valores) {
				List<UnidadOrganizativa> lista = new ArrayList<UnidadOrganizativa>();

				for (UnidadOrganizativa unidad : listUnidadOrganizativa) {
					if (String.valueOf(unidad.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& String.valueOf(unidad.getGerencia().getId())
									.toLowerCase().startsWith(valores.get(1))
							&& unidad.getDescripcion().toLowerCase()
									.startsWith(valores.get(2))
							&& String.valueOf(unidad.getNivel()).toLowerCase()
									.startsWith(valores.get(3))
							&& String.valueOf(unidad.getSubNivel())
									.toLowerCase().startsWith(valores.get(4))
							&& unidad.getIdEmpresaAuxiliar().toLowerCase()
									.startsWith(valores.get(5))
							&& unidad.getIdUnidadOrganizativaAuxiliar()
									.toLowerCase().startsWith(valores.get(6))) {
						lista.add(unidad);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(UnidadOrganizativa unidad) {
				String[] registros = new String[7];
				registros[0] = String.valueOf(unidad.getId());
				registros[1] = String.valueOf(unidad.getGerencia().getId());
				registros[2] = unidad.getDescripcion();
				registros[3] = String.valueOf(unidad.getNivel());
				registros[4] = String.valueOf(unidad.getSubNivel());
				registros[5] = String.valueOf(unidad.getIdEmpresaAuxiliar());
				registros[6] = String.valueOf(unidad
						.getIdUnidadOrganizativaAuxiliar());
				return registros;
			}

			@Override
			protected List<UnidadOrganizativa> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código Unidad"))
					return servicioUnidadOrganizativa.filtroId(valor);
				else if (combo.equals("Código Gerencia"))
					return servicioUnidadOrganizativa.filtroGerencia(valor);
				else if (combo.equals("Descripción"))
					return servicioUnidadOrganizativa.filtroDescripcion(valor);
				else if (combo.equals("Nivel"))
					return servicioUnidadOrganizativa.filtroNivel(valor);
				else if (combo.equals("Sub-Nivel"))
					return servicioUnidadOrganizativa.filtroSubNivel(valor);
				else if (combo.equals("Empresa Auxiliar"))
					return servicioUnidadOrganizativa
							.filtroEmpresaAuxiliar(valor);
				else if (combo.equals("Unidad Auxiliar"))
					return servicioUnidadOrganizativa
							.filtroUnidadAuxiliar(valor);
				else
					return servicioUnidadOrganizativa.buscarTodas();
			}

		};
		catalogo.setParent(catalogoUnidadOrganizativa);

	}

	@Listen("onChange = #txtGerenciaUnidadOrganizativa")
	public void buscarGerencia() {
		Gerencia gerencia = servicioGerencia.buscarGerencia(Integer
				.valueOf(txtGerenciaUnidadOrganizativa.getValue()));
		if (gerencia == null) {
			msj.mensajeAlerta(Mensaje.codigoGerencia);
			txtGerenciaUnidadOrganizativa.setFocus(true);
		}

	}

	@Listen("onClick = #btnBuscarGerencia")
	public void mostrarCatalogoGerencia() {
		final List<Gerencia> listGerencia = servicioGerencia.buscarTodas();
		catalogoGerencia = new Catalogo<Gerencia>(divCatalogoGerencia,
				"Catalogo de Gerencias", listGerencia, "Código gerencia",
				"Descripción") {

			@Override
			protected List<Gerencia> buscarCampos(List<String> valores) {
				List<Gerencia> lista = new ArrayList<Gerencia>();

				for (Gerencia gerencia : listGerencia) {
					if (String.valueOf(gerencia.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& gerencia.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))) {
						lista.add(gerencia);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Gerencia gerencia) {
				String[] registros = new String[2];
				registros[0] = String.valueOf(gerencia.getId());
				registros[1] = gerencia.getDescripcion();

				return registros;
			}

			@Override
			protected List<Gerencia> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código gerencia"))
					return servicioGerencia.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioGerencia.filtroDescripcion(valor);
				else
					return servicioGerencia.buscarTodas();
			}

		};

		catalogoGerencia.setParent(divCatalogoGerencia);
		catalogoGerencia.doModal();
	}

	@Listen("onSeleccion = #divCatalogoGerencia")
	public void seleccionGerencia() {
		Gerencia gerencia = catalogoGerencia.objetoSeleccionadoDelCatalogo();
		txtGerenciaUnidadOrganizativa
				.setValue(String.valueOf(gerencia.getId()));
		lblGerenciaUnidadOrganizativa.setValue(gerencia.getDescripcion());
		catalogoGerencia.setParent(null);
	}

}
