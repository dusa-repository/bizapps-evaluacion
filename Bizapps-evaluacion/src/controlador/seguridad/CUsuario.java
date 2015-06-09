package controlador.seguridad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import modelo.seguridad.Usuario;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import security.componente.SecurityCatalogo;
import security.componente.SecurityMensaje;
import security.controlador.CArbol;
import security.modelo.Grupo;
import security.modelo.UsuarioSeguridad;
import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;
import componentes.Validador;
import controlador.maestros.CGenerico;

public class CUsuario extends CGenerico {

	private static final long serialVersionUID = 7879830599305337459L;
	@Wire
	private Div divUsuario;
	@Wire
	private Div botoneraUsuario;
	@Wire
	private Div catalogoUsuario;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtApellido;
	@Wire
	private Textbox txtCorreo;
	@Wire
	private Textbox txtLoginUsuario;
	@Wire
	private Textbox txtPasswordUsuario;
	@Wire
	private Textbox txtPassword2Usuario;
	@Wire
	private Textbox txtCedula;
	@Wire
	private Textbox txtFicha;
	@Wire
	private Listbox ltbGruposDisponibles;
	@Wire
	private Listbox ltbGruposAgregados;
	@Wire
	private Groupbox gpxDatos;
	@Wire
	private Groupbox gpxRegistro;
	@Wire
	private Image imagen;
	@Wire
	private Button fudImagenUsuario;
	@Wire
	private Media media;
	Integer id = 0;
	Catalogo<Usuario> catalogo;
	protected List<Usuario> listaGeneral = new ArrayList<Usuario>();
	List<Grupo> gruposDisponibles = new ArrayList<Grupo>();
	List<Grupo> gruposOcupados = new ArrayList<Grupo>();
	URL url = getClass().getResource("/security/controlador/usuario.png");
	Botonera botonera;

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				map.clear();
				map = null;
			}
		}
		mostrarCatalogo();
		llenarListas(null);

		try {
			imagen.setContent(new AImage(url));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		gpxRegistro.setOpen(false);
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divUsuario, "Usuario", tabs);
			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validar()) {
					if (buscarPorLogin()) {
						Set<Grupo> gruposUsuario = new HashSet<Grupo>();
						for (int i = 0; i < ltbGruposAgregados.getItemCount(); i++) {
							Grupo grupo = ltbGruposAgregados.getItems().get(i)
									.getValue();
							gruposUsuario.add(grupo);
						}

						String correo = txtCorreo.getValue();
						String login = txtLoginUsuario.getValue();
						String password = txtPasswordUsuario.getValue();
						String nombre = txtNombre.getValue();
						String apellido = txtApellido.getValue();
						String cedula = txtCedula.getValue();
						String ficha = txtFicha.getValue();
						byte[] imagenUsuario = null;
						if (media instanceof org.zkoss.image.Image) {
							imagenUsuario = imagen.getContent().getByteData();

						} else {
							try {
								imagen.setContent(new AImage(url));
							} catch (IOException e) {
								e.printStackTrace();
							}
							imagenUsuario = imagen.getContent().getByteData();
						}
						Usuario usuario = new Usuario(id, apellido, cedula,
								correo, fechaHora, horaAuditoria, login,
								nombre, password, true, imagenUsuario, ficha);
						usuario.setIdRol(0);
						servicioUsuario.guardar(usuario);
						guardarDatosSeguridad(usuario, gruposUsuario);
						limpiar();
						listaGeneral = servicioUsuario.buscarTodos();
						catalogo.actualizarLista(listaGeneral);
						msj.mensajeInformacion(Mensaje.guardado);
					}
				}
			}

			@Override
			public void eliminar() {
				if (gpxDatos.isOpen()) {
					if (validarSeleccion()) {
						final List<Usuario> eliminarLista = catalogo
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
													inhabilitarSeguridad(eliminarLista);
													servicioUsuario
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(SecurityMensaje.eliminado);
													listaGeneral = servicioUsuario
															.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
												}
											}
										});
					}
				} else {
					if (!id.equals(0)) {
						Messagebox
								.show("¿Esta Seguro de Eliminar el Usuario?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													Usuario usuario = catalogo
															.objetoSeleccionadoDelCatalogo();
													List<Usuario> list = new ArrayList<Usuario>();
													list.add(usuario);
													inhabilitarSeguridad(list);
													servicioUsuario
															.eliminar(usuario);
													limpiar();
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioUsuario
															.buscarTodos();
													catalogo.actualizarLista(listaGeneral);

												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void seleccionar() {
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Usuario usuario = catalogo
								.objetoSeleccionadoDelCatalogo();
						txtCorreo.setValue(usuario.getEmail());
						txtLoginUsuario.setValue(usuario.getLogin());
						txtPasswordUsuario.setValue(usuario.getPassword());
						txtPassword2Usuario.setValue(usuario.getPassword());
						txtNombre.setValue(usuario.getNombre());
						txtApellido.setValue(usuario.getApellido());
						txtCedula.setValue(usuario.getCedula());
						txtFicha.setValue(usuario.getFicha());
						BufferedImage imag;
						if (usuario.getImagen() != null) {
							try {
								imag = ImageIO.read(new ByteArrayInputStream(
										usuario.getImagen()));
								imagen.setContent(imag);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						txtCedula.setDisabled(true);
						txtLoginUsuario.setDisabled(true);
						id = usuario.getIdUsuario();
						llenarListas(usuario);
					} else
						SecurityMensaje
								.mensajeAlerta(SecurityMensaje.editarSoloUno);
				}
			}

			@Override
			public void buscar() {
				abrirCatalogo();
			}

			@Override
			public void annadir() {
				abrirRegistro();
				mostrarBotones(false);
			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub

			}

		};
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botoneraUsuario.appendChild(botonera);
	}

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(2).setVisible(bol);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(3).setVisible(!bol);
		botonera.getChildren().get(5).setVisible(!bol);
	}

	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);
	}

	@Listen("onOpen = #gpxDatos")
	public void abrirCatalogo() {
		gpxDatos.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatos.setOpen(false);
								gpxRegistro.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatos.setOpen(true);
									gpxRegistro.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatos.setOpen(true);
			gpxRegistro.setOpen(false);
			mostrarBotones(true);
		}
	}

	private boolean camposEditando() {
		if (txtApellido.getText().compareTo("") != 0
				|| txtFicha.getText().compareTo("") != 0
				|| txtCedula.getText().compareTo("") != 0
				|| txtCorreo.getText().compareTo("") != 0
				|| txtLoginUsuario.getText().compareTo("") != 0
				|| txtPasswordUsuario.getText().compareTo("") != 0
				|| txtPassword2Usuario.getText().compareTo("") != 0
				|| txtNombre.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	public boolean validarSeleccion() {
		List<Usuario> seleccionados = catalogo.obtenerSeleccionados();
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

	public void recibirGrupo(List<Grupo> lista, Listbox l) {
		ltbGruposDisponibles = l;
		gruposDisponibles = lista;
		ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
				gruposDisponibles));
		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	public void limpiarCampos() {
		ltbGruposAgregados.getItems().clear();
		ltbGruposDisponibles.getItems().clear();
		txtApellido.setValue("");
		txtCedula.setValue("");
		txtCorreo.setValue("");
		txtFicha.setValue("");
		txtLoginUsuario.setValue("");
		txtPasswordUsuario.setValue("");
		txtPassword2Usuario.setValue("");
		txtNombre.setValue("");
		try {
			imagen.setContent(new AImage(url));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		id = 0;
		llenarListas(null);
		txtCedula.setDisabled(false);
		txtLoginUsuario.setDisabled(false);
	}

	/* Validaciones de pantalla para poder realizar el guardar */
	public boolean validar() {
		if (txtApellido.getText().compareTo("") == 0
				|| txtCedula.getText().compareTo("") == 0
				|| txtCorreo.getText().compareTo("") == 0
				|| txtLoginUsuario.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0
				|| txtPassword2Usuario.getText().compareTo("") == 0
				|| txtPasswordUsuario.getText().compareTo("") == 0) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarCorreo(txtCorreo.getValue())) {
				msj.mensajeAlerta(Mensaje.correoInvalido);
				return false;
			} else {
				if (!txtPasswordUsuario.getValue().equals(
						txtPassword2Usuario.getValue())) {
					msj.mensajeAlerta(Mensaje.contrasennasInvalidas);
					return false;
				} else
					return true;
			}
		}

	}

	/* Valida que los passwords sean iguales */
	@Listen("onChange = #txtPassword2Usuario")
	public void validarPassword() {
		if (!txtPasswordUsuario.getValue().equals(
				txtPassword2Usuario.getValue())) {
			msj.mensajeAlerta(Mensaje.contrasennasInvalidas);
		}
	}

	/* Valida el correo electronico */
	@Listen("onChange = #txtCorreoUsuario")
	public void validarCorreo() {
		if (!Validador.validarCorreo(txtCorreo.getValue())) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	/* LLena las listas dado un usario */
	public void llenarListas(Usuario usuario) {
		UsuarioSeguridad user = null;
		if (usuario != null)
			user = servicioUsuarioSeguridad.buscarPorLogin(usuario.getLogin());
		gruposDisponibles = servicioGrupo.buscarTodos();
		if (usuario == null) {
			ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
					gruposDisponibles));
		} else {
			gruposOcupados = servicioGrupo.buscarGruposDelUsuario(user);
			ltbGruposAgregados
					.setModel(new ListModelList<Grupo>(gruposOcupados));
			if (!gruposOcupados.isEmpty()) {
				List<Long> ids = new ArrayList<Long>();
				for (int i = 0; i < gruposOcupados.size(); i++) {
					long id = gruposOcupados.get(i).getIdGrupo();
					ids.add(id);
				}
				gruposDisponibles = servicioGrupo.buscarGruposDisponibles(ids);
				ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
						gruposDisponibles));
			}
		}
		ltbGruposAgregados.setMultiple(false);
		ltbGruposAgregados.setCheckmark(false);
		ltbGruposAgregados.setMultiple(true);
		ltbGruposAgregados.setCheckmark(true);

		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	/* Permite subir una imagen a la vista */
	@Listen("onUpload = #fudImagenUsuario")
	public void processMedia(UploadEvent event) {
		media = event.getMedia();
		imagen.setContent((org.zkoss.image.Image) media);

	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * izquierda a la lista de la derecha
	 */
	@Listen("onClick = #pasar1")
	public void moverDerecha() {
		// gruposDisponibles = servicioGrupo.buscarTodos();
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbGruposDisponibles.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Grupo grupo = listItem.get(i).getValue();
					gruposDisponibles.remove(grupo);
					gruposOcupados.add(grupo);
					ltbGruposAgregados.setModel(new ListModelList<Grupo>(
							gruposOcupados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbGruposDisponibles.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbGruposAgregados.setMultiple(false);
		ltbGruposAgregados.setCheckmark(false);
		ltbGruposAgregados.setMultiple(true);
		ltbGruposAgregados.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar2")
	public void moverIzquierda() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbGruposAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Grupo grupo = listItem2.get(i).getValue();
					gruposOcupados.remove(grupo);
					gruposDisponibles.add(grupo);
					ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
							gruposDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbGruposAgregados.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	public void mostrarCatalogo() {
		listaGeneral = servicioUsuario.buscarTodos();
		catalogo = new Catalogo<Usuario>(catalogoUsuario, "Usuario",
				listaGeneral, false, false, true, "Login", "Nombre",
				"Apellido", "Ficha", "Cedula", "Correo") {

			@Override
			protected List<Usuario> buscar(List<String> valores) {

				List<Usuario> user = new ArrayList<Usuario>();

				for (Usuario actividadord : listaGeneral) {
					if (actividadord.getLogin().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& actividadord.getEmail().toLowerCase()
									.contains(valores.get(5).toLowerCase())
							&& actividadord.getNombre().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& actividadord.getApellido().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& actividadord.getFicha().toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& actividadord.getCedula().toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						user.add(actividadord);
					}
				}
				return user;
			}

			@Override
			protected String[] crearRegistros(Usuario usuarios) {
				String[] registros = new String[6];
				registros[0] = usuarios.getLogin();
				registros[1] = usuarios.getNombre();
				registros[2] = usuarios.getApellido();
				registros[3] = usuarios.getFicha();
				registros[4] = usuarios.getCedula();
				registros[5] = usuarios.getEmail();
				return registros;
			}

		};
		catalogo.setParent(catalogoUsuario);
	}

	@Listen("onChange = #txtLoginUsuario; onOK =  #txtLoginUsuario")
	public boolean buscarPorLogin() {
		if (txtLoginUsuario.getText().compareTo("") != 0) {
			Usuario usuario = servicioUsuario
					.buscarUsuarioPorNombre(txtLoginUsuario.getValue());
			if (usuario == null)
				return true;
			else {
				if (usuario.getIdUsuario() == (id))
					return true;
				else {
					msj.mensajeAlerta("El login esta siendo utilizado por otro usuario");
					txtLoginUsuario.setValue("");
					txtLoginUsuario.setFocus(true);
					return false;
				}
			}
		}
		return false;
	}

	@Listen("onChange = #txtFicha; onOK =  #txtFicha")
	public void buscarCedula() {
		if (txtFicha.getText().compareTo("") != 0) {
			Usuario empleado = servicioUsuario.buscarFicha(txtFicha.getValue());
			if (empleado != null) {
				if (empleado.getIdUsuario() != id) {
					txtFicha.setFocus(true);
					txtFicha.setValue("");
					msj.mensajeAlerta("Esta Ficha ya ha sido asignada a otro usuario");
				}
			}
		}
	}

	@Listen("onChange = #txtCedula; onOK =  #txtCedula")
	public void buscarCedula2() {
		if (txtCedula.getText().compareTo("") != 0) {
			Usuario empleado = servicioUsuario.buscarPorCedula(txtCedula
					.getValue());
			if (empleado != null) {
				if (empleado.getIdUsuario() != id) {
					txtCedula.setFocus(true);
					txtCedula.setValue("");
					msj.mensajeAlerta("Esta Cedula ya ha sido asignada a otro usuario");
				}
			}
		}
	}

}
