package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.NombreCurso;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CNombreCurso extends CGenerico {

	@Wire
	private Window wdwVCurso;
	@Wire
	private Div botoneraCurso;
	@Wire
	private Groupbox gpxRegistroCurso;
	@Wire
	private Textbox txtNombreCurso;
	@Wire
	private Intbox txtAreaCurso;
	@Wire
	private Button btnBuscarArea;
	@Wire
	private Groupbox gpxDatosCurso;
	@Wire
	private Div catalogoCurso;
	@Wire
	private Div divCatalogoArea;
	@Wire
	private Label lblAreaCurso;

	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	private int idCurso = 0;
	private int idArea = 0;

	Mensaje msj = new Mensaje();
	Botonera botonera;
	Catalogo<NombreCurso> catalogo;
	Catalogo<Area> catalogoArea;
	protected List<NombreCurso> listaGeneral = new ArrayList<NombreCurso>();

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
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
		txtAreaCurso.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						NombreCurso curso = catalogo
								.objetoSeleccionadoDelCatalogo();
						idCurso = curso.getId();
						idArea = curso.getArea().getId();
						lblAreaCurso.setValue(curso.getArea().getDescripcion());
						txtNombreCurso.setValue(curso.getNombre());
						txtAreaCurso.setValue(curso.getArea().getId());
						txtAreaCurso.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

				if (validar()) {

					Area area = servicioArea.buscarArea(idArea);
					NombreCurso nombreCurso = servicioNombreCurso
							.buscarPorNombre(txtNombreCurso.getValue());

					if (area != null) {

						if (nombreCurso != null) {

							msj.mensajeAlerta(Mensaje.nombreCurso);
							txtNombreCurso.setFocus(true);

						} else {

							String nombre = txtNombreCurso.getValue();
							String usuario = nombreUsuarioSesion();
							Timestamp fechaAuditoria = new Timestamp(
									new Date().getTime());
							NombreCurso curso = new NombreCurso(idCurso, area,
									nombre, fechaAuditoria, horaAuditoria,
									usuario);

							servicioNombreCurso.guardar(curso);
							msj.mensajeInformacion(Mensaje.guardado);
							limpiar();
							listaGeneral = servicioNombreCurso
									.buscarTodos();
							catalogo.actualizarLista(listaGeneral);
							abrirCatalogo();
							

						}

					} else {

						msj.mensajeAlerta(Mensaje.codigoArea);
						txtAreaCurso.setFocus(true);

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
				cerrarVentana(wdwVCurso, titulo, tabs);
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatosCurso.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<NombreCurso> eliminarLista = catalogo
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
													servicioNombreCurso
															.eliminarVariosCursos(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioNombreCurso
															.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
													
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idCurso != 0) {
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
													servicioNombreCurso
															.eliminarUnCurso(idCurso);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioNombreCurso
															.buscarTodos();
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
		botoneraCurso.appendChild(botonera);

	}

	public void limpiarCampos() {
		idCurso = 0;
		idArea = 0;
		txtNombreCurso.setValue("");
		txtAreaCurso.setValue(null);
		catalogo.limpiarSeleccion();
		txtAreaCurso.setFocus(true);
		lblAreaCurso.setValue("");

	}

	public boolean camposEditando() {
		if (txtAreaCurso.getText().compareTo("") != 0
				|| txtNombreCurso.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistroCurso")
	public void abrirRegistro() {
		gpxDatosCurso.setOpen(false);
		gpxRegistroCurso.setOpen(true);
		mostrarBotones(false);

	}

	@Listen("onOpen = #gpxDatosCurso")
	public void abrirCatalogo() {
		gpxDatosCurso.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatosCurso.setOpen(false);
								gpxRegistroCurso.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatosCurso.setOpen(true);
									gpxRegistroCurso.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatosCurso.setOpen(true);
			gpxRegistroCurso.setOpen(false);
			mostrarBotones(true);
		}
	}

	public boolean validarSeleccion() {
		List<NombreCurso> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtAreaCurso.getText().compareTo("") == 0
				|| txtNombreCurso.getText().compareTo("") == 0) {
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

		listaGeneral = servicioNombreCurso.buscarTodos();
		catalogo = new Catalogo<NombreCurso>(catalogoCurso,
				"Catalogo de Cursos", listaGeneral, false, false, false, "�rea",
				"Nombre") {

			@Override
			protected List<NombreCurso> buscar(List<String> valores) {
				List<NombreCurso> lista = new ArrayList<NombreCurso>();

				for (NombreCurso curso : listaGeneral) {
					if (curso.getArea().getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& curso.getNombre().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(curso);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(NombreCurso curso) {
				String[] registros = new String[2];
				registros[0] = curso.getArea().getDescripcion();
				registros[1] = curso.getNombre();

				return registros;
			}

		};
		catalogo.setParent(catalogoCurso);

	}

	@Listen("onChange = #txtAreaCurso; onOk = #txtAreaCurso ")
	public boolean  buscarArea() {
		if(txtAreaCurso.getValue() != null){
			
			Area area = servicioArea.buscarArea(txtAreaCurso.getValue());
			if (area != null) {
				lblAreaCurso.setValue(area.getDescripcion());
				idArea = area.getId();
				return true;
			}else{
				msj.mensajeAlerta(Mensaje.codigoArea);
				txtAreaCurso.setFocus(true);
				return false;
			}
			
		}else
			return false;
		
	}
	

	@Listen("onClick = #btnBuscarArea")
	public void mostrarCatalogoArea() {
		final List<Area> listArea = servicioArea.buscarTodas();
		catalogoArea = new Catalogo<Area>(divCatalogoArea, "Catalogo de �reas",
				listArea, true, false, false, "Tipo de Formaci�n",
				"Descripci�n") {

			@Override
			protected List<Area> buscar(List<String> valores) {
				List<Area> lista = new ArrayList<Area>();

				for (Area area : listArea) {
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
				if (area.getTipoFormacion() != null)
					registros[0] = area.getTipoFormacion().getDescripcion();
				else
					registros[0] = "";
				registros[1] = area.getDescripcion();

				return registros;
			}

		};

		catalogoArea.setClosable(true);
		catalogoArea.setWidth("80%");
		catalogoArea.setParent(divCatalogoArea);
		catalogoArea.doModal();
	}

	@Listen("onSeleccion = #divCatalogoArea")
	public void seleccionArea() {
		Area area = catalogoArea.objetoSeleccionadoDelCatalogo();
		idArea = area.getId();
		txtAreaCurso.setValue(area.getId());
		lblAreaCurso.setValue(area.getDescripcion());
		catalogoArea.setParent(null);
	}

}
