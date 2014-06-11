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
import modelos.EvaluacionObjetivo;
import modelos.Perspectiva;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
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

public class CObjetivos extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
		Mensaje msj = new Mensaje();

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
	private Listbox lbxEmpleado;
	@Wire
	private Listbox lbxObjetivos;
	@Wire
	private Listbox lbxObjetivosGuardados;
	@Wire
	private Label lblEvaluacion;
	@Wire
	private Label lblFechaCreacion;
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
	@Wire
	private Label lblRevision;
	@Wire
	private Combobox cmbPerspectiva;
	List<EvaluacionObjetivo> objetivosG = new ArrayList<EvaluacionObjetivo>();
	ListModelList<Perspectiva> perspectiva;
	public String horaCreacion = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ String.valueOf(calendario.get(Calendar.SECOND));
	
	public ListModelList<Perspectiva> perspectiva() {
		perspectiva = new ListModelList<Perspectiva>(
				servicioPerspectiva.buscar());
		return perspectiva;
	}

	@Override
	public void inicializar() throws IOException {
		
		List<Perspectiva> perspectiva = servicioPerspectiva.buscar();
		cmbPerspectiva.setModel(new ListModelList<Perspectiva>(perspectiva));
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size() + 1;
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(horaCreacion);
		String nombreTrabajador = u.getNombre() + " "  + u.getApellido();
		Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
		String cargo = empleado.getCargo().getDescripcion();
		String unidadOrganizativa = empleado.getUnidadOrganizativa().getDescripcion();
		String gerenciaReporte = empleado.getUnidadOrganizativa().getGerencia().getDescripcion();
		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);	
	}
	
	public void limpiar (){
		txtObjetivo.setValue("");
		cmbPerspectiva.setValue(null);
		txtCorresponsables.setValue("");
	}
	@Listen("onClick = #btnAgregar")
	public void AgregarObjetvo() {	
	
		String objetivo =txtObjetivo.getValue();
		String corresponsables = txtCorresponsables.getValue();
		String perspectiva = cmbPerspectiva.getValue();
		EvaluacionObjetivo objetivoLista = new EvaluacionObjetivo ();
		objetivoLista.setIdObjetivo(1);
		objetivoLista.setDescripcionObjetivo(objetivo);
		objetivoLista.setIdEvaluacion(1);
		objetivoLista.setIdPerspectiva(1);
		objetivoLista.setLinea(1);
		objetivoLista.setPeso(0);
		objetivoLista.setResultado(0);
		objetivoLista.setTotalInd(0);
		objetivoLista.setCorresponsables(corresponsables);
		objetivosG.add(objetivoLista);
		lbxObjetivosGuardados.setModel(new ListModelList<EvaluacionObjetivo>(objetivosG));
		limpiar ();
	
	}

}