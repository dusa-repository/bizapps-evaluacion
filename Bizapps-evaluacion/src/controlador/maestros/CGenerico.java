package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import arbol.CArbol;


import servicio.maestros.SArea;
import servicio.maestros.SCargo;
import servicio.maestros.SCompetencia;
import servicio.maestros.SDominio;
import servicio.maestros.SEmpleado;
import servicio.maestros.SEmpresa;
import servicio.maestros.SGerencia;
import servicio.maestros.SMedicion;
import servicio.maestros.SPeriodo;
import servicio.maestros.SPerspectiva;
import servicio.maestros.SRevision;
import servicio.maestros.STipoFormacion;
import servicio.maestros.SUnidadMedida;
import servicio.maestros.SUnidadOrganizativa;
import servicio.maestros.SUrgencia;
import servicio.maestros.SValoracion;
import servicio.reportes.SReporte;
import servicio.seguridad.SArbol;
import servicio.seguridad.SGrupo;
import servicio.seguridad.SUsuario;

import servicio.transacciones.SConductaCompetencia;
import servicio.transacciones.SEvaluacion;
import servicio.transacciones.SEvaluacionCompetencia;
import servicio.transacciones.SEvaluacionConducta;
import servicio.transacciones.SEvaluacionIndicador;
import servicio.transacciones.SEvaluacionObjetivo;
import servicio.transacciones.SNivelCompetenciaCargo;



@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class CGenerico extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2264423023637489596L;


	@WireVariable("SArbol")
	protected SArbol servicioArbol;
	@WireVariable("SGrupo")
	protected SGrupo servicioGrupo;
	@WireVariable("SUsuario")
	protected SUsuario servicioUsuario;
	@WireVariable("SEmpleado")
	protected SEmpleado servicioEmpleado;
	@WireVariable("SPerspectiva")
	protected SPerspectiva servicioPerspectiva;
	@WireVariable("SEvaluacion")
	protected SEvaluacion servicioEvaluacion;
	@WireVariable("SCompetencia")
	protected SCompetencia servicioCompetencia;
	@WireVariable("SNivelCompetenciaCargo")
	protected SNivelCompetenciaCargo servicioNivelCompetenciaCargo;
	@WireVariable("SCargo")
	protected SCargo servicioCargo;
	@WireVariable("SEmpresa")
	protected SEmpresa servicioEmpresa;
	@WireVariable("SGerencia")
	protected SGerencia servicioGerencia;
	@WireVariable("SUnidadOrganizativa")
	protected SUnidadOrganizativa servicioUnidadOrganizativa;
	@WireVariable("SRevision")
	protected SRevision servicioRevision;
	@WireVariable("SPeriodo")
	protected SPeriodo servicioPeriodo;
	@WireVariable("STipoFormacion")
	protected STipoFormacion servicioTipoFormacion;
	@WireVariable("SArea")
	protected SArea servicioArea;
	@WireVariable("SDominio")
	protected SDominio servicioDominio;
	@WireVariable("SMedicion")
	protected SMedicion servicioMedicion;
	@WireVariable("SUnidadMedida")
	protected SUnidadMedida servicioUnidadMedida;
	@WireVariable("SUrgencia")
	protected SUrgencia servicioUrgencia;
	@WireVariable("SValoracion")
	protected SValoracion servicioValoracion;
	@WireVariable("SEvaluacionObjetivo")
	protected SEvaluacionObjetivo servicioEvaluacionObjetivo;
	@WireVariable("SEvaluacionIndicador")
	protected SEvaluacionIndicador servicioEvaluacionIndicador;
	@WireVariable("SConductaCompetencia")
	protected SConductaCompetencia servicioConductaCompetencia;
	@WireVariable("SEvaluacionConducta")
	protected SEvaluacionConducta servicioEvaluacionConducta;
	@WireVariable("SEvaluacionCompetencia")
	protected SEvaluacionCompetencia servicioEvaluacionCompetencia;
	@WireVariable("SReporte")
	protected SReporte servicioReporte;
	protected static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd-MM-yyyy");
	public static  List<Tab> tabs = new ArrayList<Tab>();
	protected DateFormat df = new SimpleDateFormat("HH:mm:ss");
	public final Calendar calendario = Calendar.getInstance();
	public String horaAuditoria = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.SECOND));
	public java.util.Date fecha = new Date();
	public Timestamp fechaHora = new Timestamp(fecha.getTime());
	
	/* Titulos de Mensaje */
	public String informacion = "INFORMACION";
	public String alerta = "ALERTA";


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		inicializar();
	}

	public abstract void inicializar() throws IOException;

	public void cerrarVentana(Div div, String id) {
		div.setVisible(false);
		for(int i =0; i<tabs.size();i++){
			if(tabs.get(i).getLabel().equals(id)){
				if(i==(tabs.size()-1)&& tabs.size()>1){
					tabs.get(i-1).setSelected(true);
				}
				tabs.get(i).onClose();
				tabs.remove(i);
			}
		}
	}
	public void cerrarVentana1(Window window, String id) {
		window.setVisible(false);
		for(int i =0; i<tabs.size();i++){
			if(tabs.get(i).getLabel().equals(id)){
				if(i==(tabs.size()-1)&& tabs.size()>1){
					tabs.get(i-1).setSelected(true);
				}
				tabs.get(i).onClose();
				tabs.remove(i);
			}
		}
	}
	
	public void cerrarWindow(Window win, String id) {
		win.setVisible(false);
		for(int i =0; i<tabs.size();i++){
			if(tabs.get(i).getLabel().equals(id)){
				if(i==(tabs.size()-1)&& tabs.size()>1){
					tabs.get(i-1).setSelected(true);
				}
				tabs.get(i).onClose();
				tabs.remove(i);
			}
		}
	}

	public String nombreUsuarioSesion() {
		Authentication sesion = SecurityContextHolder.getContext()
				.getAuthentication();
		return sesion.getName();
	}
	
	/* Metodo que permite enviar un correo electronico a cualquier destinatario */
	public boolean enviarEmailNotificacion(String correo, String mensajes) {
		try {

			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			String asunto = "Notificacion de SITEG";
			String remitente = "siteg.ucla@gmail.com";
			String contrasena = "Equipo.2";
			String destino = correo;
			String mensaje = mensajes;

			String destinos[] = destino.split(",");

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(asunto);
			message.setText(mensaje);

			Transport t = session.getTransport("smtp");
			t.connect(remitente, contrasena);
			t.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));

			t.close();

			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}