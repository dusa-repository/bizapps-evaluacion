package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;
import modelos.Competencia;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.NivelCompetenciaCargo;
import modelos.Perspectiva;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.West;

import sun.util.calendar.BaseCalendar.Date;

import arbol.MArbol;
import arbol.Nodos;

import controlador.maestros.CGenerico;
import componentes.Mensaje;
import componentes.Validador;

public class CCompetenciasRectoras extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Label lblEvaluacion;
	@Wire
	private Label lblFechaCreacion;
	@Wire
	private Label lblRevision;
	@Wire
	private Textbox txtObjetivo;
	@Wire
	private Textbox txtCorresponsables;
	@Wire
	private Textbox txtPeso;
	@Wire
	private Textbox txtTotal;
	@Wire
	private Textbox txtResultados;
	@Wire
	private Button btnAgregar;
	@Wire
	private Button btnEliminar;
	@Wire
	private Button btnCalculo;
	@Wire
	private Listbox lbxCompetenciaRectora;
	@Wire
	private Listbox lbxObjetivos;
	@Wire
	private Listbox lbxEvaluacion;
	@Wire
	private Label lblFicha;
	@Wire
	private Label lblNombreTrabajador;
	@Wire
	private Label lblCargo;
	@Wire
	private Label lblUnidadOrganizativa;
	@Wire
	private Label lblGerencia;

	@Override
	public void inicializar() throws IOException {
		// List<Competencia> competenciaRectora = new ArrayList<Competencia>();
		// competenciaRectora = servicioCompetencia.buscarCompetenciasR();
		// lbxCompetenciaRectora.setModel(new
		// ListModelList<Competencia>(competenciaRectora));

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		String nombreTrabajador = u.getNombre() + " " + u.getApellido();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size();
		Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);

		String cargo = empleado.getCargo().getDescripcion();
		String unidadOrganizativa = empleado.getUnidadOrganizativa()
				.getDescripcion();
		String gerenciaReporte = empleado.getUnidadOrganizativa().getGerencia()
				.getDescripcion();

		List<NivelCompetenciaCargo> nivel = new ArrayList<NivelCompetenciaCargo>();

		nivel = servicioNivelCompetenciaCargo.buscar(empleado.getCargo());
		
		System.out.println(nivel);
		lbxCompetenciaRectora
				.setModel(new ListModelList<NivelCompetenciaCargo>(nivel));

		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(fechaHora.toString());
	}

}