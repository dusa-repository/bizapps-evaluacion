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
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.EvaluacionIndicador;
import modelos.EvaluacionObjetivo;
import modelos.Medicion;
import modelos.Perspectiva;
import modelos.UnidadMedida;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
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

public class CIndicadores extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Label lblEvaluacion;
	@Wire
	private Label lblFechaCreacion;
	@Wire
	private Label lblRevision;
	@Wire
	private Textbox txtIndicador;
	@Wire
	private Textbox txtUnidad;
	@Wire
	private Textbox txtPeso;
	@Wire
	private Textbox txtMedicion;
	@Wire
	private Textbox txtValorMeta;
	@Wire
	private Textbox txtValorResultado;
	@Wire
	private Textbox txtResFy;
	@Wire
	private Textbox txtResultadoPorc;
	@Wire
	private Textbox txtPesoPorc;
	@Wire
	private Button btnAgregar;
	@Wire
	private Button btnEliminar;
	@Wire
	private Button btnOk;
	@Wire
	private Listbox lbxAgregados;
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
	@Wire
	private Groupbox gpxAgregar;
	@Wire
	private Groupbox gpxAgregados;
	@Wire
	private Combobox cmbUnidad;
	@Wire
	private Combobox cmbMedicion;
	List<EvaluacionIndicador> indicadores = new ArrayList<EvaluacionIndicador>();
	public String horaCreacion = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ String.valueOf(calendario.get(Calendar.SECOND));

	@Override
	public void inicializar() throws IOException {

		List<Medicion> medicion = servicioMedicion.buscar();
		cmbMedicion.setModel(new ListModelList<Medicion>(medicion));

		List<UnidadMedida> unidad = servicioUnidadMedida.buscar();
		cmbUnidad.setModel(new ListModelList<UnidadMedida>(unidad));

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size() + 1;
		String nombreTrabajador = u.getNombre() + " " + u.getApellido();
		Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
		String cargo = empleado.getCargo().getDescripcion();
		String unidadOrganizativa = empleado.getUnidadOrganizativa()
				.getDescripcion();
		String gerenciaReporte = empleado.getUnidadOrganizativa().getGerencia()
				.getDescripcion();
		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(horaCreacion);
		gpxAgregar.setOpen(false);
		gpxAgregados.setOpen(false);
	}

	@Listen("onClick = #btnAgregar")
	public void AgregarObjetivo() {
		gpxAgregar.setOpen(true);
	}

	@Listen("onClick = #btnOk")
	public void AgregarObjetivo2() {
		gpxAgregados.setOpen(true);

		String indicador = txtIndicador.getValue();
//		String unidad = txtUnidad.getValue();
//		String medicion = txtMedicion.getValue();
		Double peso = Double.valueOf(txtPeso.getValue());
		Double valorMeta = Double.valueOf(txtValorMeta.getValue());
		Double valorResultado = Double.valueOf(txtValorResultado.getValue());
		Double resFy = Double.valueOf(txtResFy.getValue());
		Double resultadoPorc = Double.valueOf(txtResultadoPorc.getValue());
		Double pesoPorc = Double.valueOf(txtPesoPorc.getValue());

		EvaluacionIndicador indicadorLista = new EvaluacionIndicador();
		indicadorLista.setIdObjetivo(1);
		indicadorLista.setDescripcionIndicador(indicador);
		indicadorLista.setIdMedicion(0);
		indicadorLista.setIdIndicador(0);
		indicadorLista.setIdUnidad(0);
		indicadorLista.setLinea(1);
		indicadorLista.setPeso(peso);
		indicadorLista.setResultadoFyAnterior(resFy);
		indicadorLista.setResultadoPeso(pesoPorc);
		indicadorLista.setResultadoPorc(resultadoPorc);
		indicadorLista.setValorMeta(valorMeta);
		indicadorLista.setValorResultado(valorResultado);
		indicadorLista.setTotal(0);
		indicadores.add(indicadorLista);
		lbxAgregados.setModel(new ListModelList<EvaluacionIndicador>(
				indicadores));
		gpxAgregar.setOpen(false);

	}

	public void limpiar() {

	}

}