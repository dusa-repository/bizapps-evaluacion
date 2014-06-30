package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Dominio;
import modelo.maestros.Empresa;
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

public class CConductaCompetencia extends CGenerico {

	@Wire
	private Window wdwVConductaCompetencia;
	@Wire
	private Div botoneraConductaCompetencia;
	@Wire
	private Groupbox gpxRegistroConductaCompetencia;
	@Wire
	private Textbox txtCompetenciaConductaCompetencia;
	@Wire
	private Button btnBuscarCompetencia;
	@Wire
	private Label lblCompetencia;
	@Wire
	private Textbox txtDominioConductaCompetencia;
	@Wire
	private Button btnBuscarDominio;
	@Wire
	private Label lblDominio;
	@Wire
	private Textbox txtDescripcionConductaCompetencia;
	@Wire
	private Textbox txtOrdenConductaCompetencia;
	@Wire
	private Groupbox gpxDatosConductaCompetencia;
	@Wire
	private Div catalogoConductaCompetencia;
	@Wire
	private Div divCatalogoCompetencia;
	@Wire
	private Div divCatalogoDominio;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idConductaCompetencia = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<ConductaCompetencia> catalogo;
	Catalogo<Competencia> catalogoCompetencia;
	Catalogo<Dominio> catalogoDominio;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtCompetenciaConductaCompetencia.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						ConductaCompetencia conducta = catalogo
								.objetoSeleccionadoDelCatalogo();
						idConductaCompetencia = conducta.getId();
						txtCompetenciaConductaCompetencia.setValue(String
								.valueOf(conducta.getCompetencia().getId()));
						lblCompetencia.setValue(servicioCompetencia
								.buscarCompetencia(
										conducta.getCompetencia().getId())
								.getDescripcion());
						txtDominioConductaCompetencia.setValue(String
								.valueOf(conducta.getDominio().getId()));
						lblDominio.setValue(servicioDominio.buscarDominio(
								conducta.getDominio().getId())
								.getDescripcionDominio());
						txtDescripcionConductaCompetencia.setValue(conducta
								.getDescripcion());
						txtDescripcionConductaCompetencia.setValue(conducta
								.getDescripcion());
						txtOrdenConductaCompetencia.setValue(String
								.valueOf(conducta.getOrden()));
						txtCompetenciaConductaCompetencia.setFocus(true);
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

					Competencia competencia = servicioCompetencia
							.buscarCompetencia(Integer
									.valueOf(txtCompetenciaConductaCompetencia
											.getValue()));
					Dominio dominio = servicioDominio.buscarDominio(Integer
							.valueOf(txtDominioConductaCompetencia.getValue()));

					if (competencia != null && dominio != null) {

						String descripcion = txtDescripcionConductaCompetencia
								.getValue();
						int orden = Integer.valueOf(txtOrdenConductaCompetencia
								.getValue());
						String usuario = nombreUsuarioSesion();
						Timestamp fechaAuditoria = new Timestamp(
								new Date().getTime());
						ConductaCompetencia conducta = new ConductaCompetencia(
								idConductaCompetencia, descripcion,
								competencia, dominio, orden, fechaAuditoria,
								horaAuditoria, usuario);
						servicioConductaCompetencia.guardar(conducta);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						catalogo.actualizarLista(servicioConductaCompetencia
								.buscarTodas());
						abrirCatalogo();
					} else {

						if (competencia == null) {
							msj.mensajeAlerta(Mensaje.codigoCompetencia);
							txtCompetenciaConductaCompetencia.setFocus(true);
						} else if (dominio == null) {
							msj.mensajeAlerta(Mensaje.codigoDominio);
							txtDominioConductaCompetencia.setFocus(true);
						}
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
				cerrarVentana1(wdwVConductaCompetencia, "ConductaCompetencia");
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosConductaCompetencia.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<ConductaCompetencia> eliminarLista = catalogo
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
													servicioConductaCompetencia
															.eliminarVariasConductas(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(servicioConductaCompetencia
															.buscarTodas());
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idConductaCompetencia != 0) {
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
													servicioConductaCompetencia
															.eliminarUnaConducta(idConductaCompetencia);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioConductaCompetencia
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
		botoneraConductaCompetencia.appendChild(botonera);

	}

	public void limpiarCampos() {
		idConductaCompetencia = 0;
		txtCompetenciaConductaCompetencia.setConstraint("");
		txtCompetenciaConductaCompetencia.setValue("");
		txtCompetenciaConductaCompetencia
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la competencia debe ser numérico");
		lblCompetencia.setValue("");
		txtDominioConductaCompetencia.setConstraint("");
		txtDominioConductaCompetencia.setValue("");
		txtDominioConductaCompetencia
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la competencia debe ser numérico");
		lblDominio.setValue("");
		txtDescripcionConductaCompetencia.setValue("");
		txtOrdenConductaCompetencia.setConstraint("");
		txtOrdenConductaCompetencia.setValue("");
		txtOrdenConductaCompetencia
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la competencia debe ser numérico");
		catalogo.limpiarSeleccion();
		txtCompetenciaConductaCompetencia.setFocus(true);

	}

	public boolean camposEditando() {
		if (txtCompetenciaConductaCompetencia.getText().compareTo("") != 0
				|| txtDominioConductaCompetencia.getText().compareTo("") != 0
				|| txtDescripcionConductaCompetencia.getText().compareTo("") != 0
				|| txtOrdenConductaCompetencia.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroConductaCompetencia")
	public void abrirRegistro() {
		txtCompetenciaConductaCompetencia
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la competencia debe ser numérico");
		txtDominioConductaCompetencia
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la competencia debe ser numérico");
		txtOrdenConductaCompetencia
				.setConstraint("/[0,1,2,3,4,5,6,7,8,9,-]+/: El código de la competencia debe ser numérico");
		gpxDatosConductaCompetencia.setOpen(false);
		gpxRegistroConductaCompetencia.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosConductaCompetencia")
	public void abrirCatalogo() {
		txtCompetenciaConductaCompetencia.setConstraint("");
		txtCompetenciaConductaCompetencia.setValue("");
		txtDominioConductaCompetencia.setConstraint("");
		txtDominioConductaCompetencia.setValue("");
		txtOrdenConductaCompetencia.setConstraint("");
		txtOrdenConductaCompetencia.setValue("");
		gpxDatosConductaCompetencia.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosConductaCompetencia.setOpen(false);
								gpxRegistroConductaCompetencia.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosConductaCompetencia.setOpen(true);
									gpxRegistroConductaCompetencia
											.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosConductaCompetencia.setOpen(true);
			gpxRegistroConductaCompetencia.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<ConductaCompetencia> seleccionados = catalogo
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
		if (txtCompetenciaConductaCompetencia.getText().compareTo("") == 0
				|| txtDominioConductaCompetencia.getText().compareTo("") == 0) {
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

		final List<ConductaCompetencia> listConductaCompetencia = servicioConductaCompetencia
				.buscarTodas();
		catalogo = new Catalogo<ConductaCompetencia>(
				catalogoConductaCompetencia,
				"Catalogo de ConductaCompetencias", listConductaCompetencia,
				"Código conducta", "Código competencia", "Competencia",
				"Código dominio", "Dominio", "Descripción", "Orden") {

			@Override
			protected List<ConductaCompetencia> buscarCampos(
					List<String> valores) {
				List<ConductaCompetencia> lista = new ArrayList<ConductaCompetencia>();

				for (ConductaCompetencia conducta : listConductaCompetencia) {
					if (String.valueOf(conducta.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& String
									.valueOf(conducta.getCompetencia().getId())
									.toLowerCase().startsWith(valores.get(1))
							&& conducta.getCompetencia().getDescripcion()
									.toLowerCase().startsWith(valores.get(2))
							&& String.valueOf(conducta.getDominio().getId())
									.toLowerCase().startsWith(valores.get(3))
							&& conducta.getDominio().getDescripcionDominio()
									.toLowerCase().startsWith(valores.get(4))
							&& conducta.getDescripcion().toLowerCase()
									.startsWith(valores.get(5))
							&& String.valueOf(conducta.getOrden())
									.toLowerCase().startsWith(valores.get(6))) {
						lista.add(conducta);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(ConductaCompetencia conducta) {
				String[] registros = new String[7];
				registros[0] = String.valueOf(conducta.getId());
				registros[1] = String
						.valueOf(conducta.getCompetencia().getId());
				registros[2] = conducta.getCompetencia().getDescripcion();
				registros[3] = String.valueOf(conducta.getDominio().getId());
				registros[4] = conducta.getDominio().getDescripcionDominio();
				registros[5] = conducta.getDescripcion();
				registros[6] = String.valueOf(conducta.getOrden());

				return registros;
			}

			@Override
			protected List<ConductaCompetencia> buscar(String valor,
					String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código conducta"))
					return servicioConductaCompetencia.filtroId(valor);
				else if (combo.equals("Código competencia"))
					return servicioConductaCompetencia.filtroCompetencia(valor);
				else if (combo.equals("Competencia"))
					return servicioConductaCompetencia.filtroCompetencia(valor);
				else if (combo.equals("Código dominio"))
					return servicioConductaCompetencia.filtroDominio(valor);
				else if (combo.equals("Dominio"))
					return servicioConductaCompetencia.filtroDominio(valor);
				else if (combo.equals("Descripción"))
					return servicioConductaCompetencia.filtroDescripcion(valor);
				else if (combo.equals("Orden"))
					return servicioConductaCompetencia.filtroOrden(valor);
				else
					return servicioConductaCompetencia.buscarTodas();
			}

		};
		catalogo.setParent(catalogoConductaCompetencia);

	}

	@Listen("onChange = #txtCompetenciaConductaCompetencia")
	public void buscarCompetencia() {
		Competencia competencia = servicioCompetencia.buscarCompetencia(Integer
				.valueOf(txtCompetenciaConductaCompetencia.getValue()));
		if (competencia == null) {
			msj.mensajeAlerta(Mensaje.codigoCompetencia);
			txtCompetenciaConductaCompetencia.setFocus(true);
		}

	}

	@Listen("onChange = #txtDominioConductaCompetencia")
	public void buscarDominio() {
		Dominio dominio = servicioDominio.buscarDominio(Integer
				.valueOf(txtDominioConductaCompetencia.getValue()));
		if (dominio == null) {
			msj.mensajeAlerta(Mensaje.codigoDominio);
			txtDominioConductaCompetencia.setFocus(true);
		}

	}

	@Listen("onClick = #btnBuscarCompetencia")
	public void mostrarCatalogoCompetencia() {
		final List<Competencia> listCompetencia = servicioCompetencia
				.buscarTodas();
		catalogoCompetencia = new Catalogo<Competencia>(divCatalogoCompetencia,
				"Catalogo de Competencias", listCompetencia,
				"Código competencia", "Descripción", "Nivel", "Comentario") {

			@Override
			protected List<Competencia> buscarCampos(List<String> valores) {
				List<Competencia> lista = new ArrayList<Competencia>();

				for (Competencia competencia : listCompetencia) {
					if (String.valueOf(competencia.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& competencia.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))
							&& competencia.getNivel().toLowerCase()
									.startsWith(valores.get(2))
							&& competencia.getComentario().toLowerCase()
									.startsWith(valores.get(3))) {
						lista.add(competencia);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Competencia competencia) {
				String[] registros = new String[4];
				registros[0] = String.valueOf(competencia.getId());
				registros[1] = competencia.getDescripcion();
				registros[2] = competencia.getNivel();
				registros[3] = competencia.getComentario();

				return registros;
			}

			@Override
			protected List<Competencia> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código competencia"))
					return servicioCompetencia.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioCompetencia.filtroDescripcion(valor);
				else if (combo.equals("Nivel"))
					return servicioCompetencia.filtroNivel(valor);
				else if (combo.equals("Comentario"))
					return servicioCompetencia.filtroComentario(valor);
				else
					return servicioCompetencia.buscarTodas();
			}

		};

		catalogoCompetencia.setParent(divCatalogoCompetencia);
		catalogoCompetencia.doModal();
	}

	@Listen("onSeleccion = #divCatalogoCompetencia")
	public void seleccionCompetencia() {
		Competencia competencia = catalogoCompetencia
				.objetoSeleccionadoDelCatalogo();
		txtCompetenciaConductaCompetencia.setValue(String.valueOf(competencia
				.getId()));
		lblCompetencia.setValue(competencia.getDescripcion());
		catalogoCompetencia.setParent(null);
	}

	@Listen("onClick = #btnBuscarDominio")
	public void mostrarCatalogoDominio() {
		final List<Dominio> listDominio = servicioDominio.buscarTodos();
		catalogoDominio = new Catalogo<Dominio>(divCatalogoDominio,
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
				if (combo.equals("Código dominio"))
					return servicioDominio.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioDominio.filtroDescripcion(valor);
				else if (combo.equals("Tipo"))
					return servicioDominio.filtroTipo(valor);
				else if (combo.equals("Comentario"))
					return servicioDominio.filtroComentario(valor);
				else
					return servicioDominio.buscarTodos();
			}

		};

		catalogoDominio.setParent(divCatalogoDominio);
		catalogoDominio.doModal();
	}

	@Listen("onSeleccion = #divCatalogoDominio")
	public void seleccionDominio() {
		Dominio dominio = catalogoDominio.objetoSeleccionadoDelCatalogo();
		txtDominioConductaCompetencia.setValue(String.valueOf(dominio.getId()));
		lblDominio.setValue(dominio.getDescripcionDominio());
		catalogoDominio.setParent(null);
	}

}
