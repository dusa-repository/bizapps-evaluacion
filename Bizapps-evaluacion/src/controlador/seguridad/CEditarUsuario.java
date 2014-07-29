package controlador.seguridad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import modelo.seguridad.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import componentes.Botonera;
import componentes.Validador;

import controlador.maestros.CGenerico;

public class CEditarUsuario extends CGenerico {

	@Wire
	private Textbox txtNombreUsuarioEditar;
	@Wire
	private Textbox txtClaveUsuarioNueva;
	@Wire
	private Textbox txtClaveUsuarioConfirmar;
	@Wire
	private Image imgUsuario;
	@Wire
	private Fileupload fudImagenUsuario;
	@Wire
	private Div botoneraEditarUsuario;
	@Wire
	private Div divEditarUsuario;
	private long id = 0;
	private Media media;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	URL url = getClass().getResource("/controlador/maestros/usuario.png");
	private static final long serialVersionUID = 2439502647179786175L;

	@Override
	public void inicializar() throws IOException {
		Usuario usuario = servicioUsuario
				.buscarUsuarioPorNombre(nombreUsuarioSesion());
		id = Long.valueOf(usuario.getCedula());
		txtNombreUsuarioEditar.setValue(usuario.getLogin());
	
		

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divEditarUsuario, "Editar Usuario");
			}

			@Override
			public void limpiar() {
				Usuario usuario = servicioUsuario
						.buscarUsuarioPorNombre(nombreUsuarioSesion());
				id = Long.valueOf(usuario.getCedula());
				txtNombreUsuarioEditar.setValue(usuario.getLogin());
				txtClaveUsuarioConfirmar.setValue("");
				txtClaveUsuarioNueva.setValue("");
			}

			@Override
			public void guardar() {
				if (validar()) {
					if (txtClaveUsuarioNueva.getValue().equals(
							txtClaveUsuarioConfirmar.getValue())) {
						
						Usuario usuario = servicioUsuario
								.buscarUsuarioPorNombre(nombreUsuarioSesion());
						
						String password = txtClaveUsuarioConfirmar.getValue();
						usuario.setPassword(password);
						servicioUsuario.guardar(usuario);
						Messagebox.show("Usuario Editado con Exito", "Informacion",
								Messagebox.OK, Messagebox.INFORMATION);
						limpiar();
					} else {
						Messagebox.show("Passwords No Coinciden", "Alerta",
								Messagebox.OK, Messagebox.ERROR);
					}
				}
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				
			}
		};
		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botoneraEditarUsuario.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtClaveUsuarioConfirmar.getValue().equals("")
				|| txtClaveUsuarioNueva.getValue().equals("")) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}

}
