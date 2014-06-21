package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionIndicador;
import modelo.maestros.EvaluacionObjetivo;
import modelo.maestros.Medicion;
import modelo.maestros.Perspectiva;
import modelo.maestros.UnidadMedida;
import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
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
	private Spinner txtPeso;
	@Wire
	private Spinner txtValorMeta;
	@Wire
	private Spinner txtValorResultado;
	@Wire
	private Spinner txtResFy;
	@Wire
	private Spinner txtResultadoPorc;
	@Wire
	private Spinner txtPesoPorc;
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
	@Wire
	private Combobox cmbObjetivos;
	List<EvaluacionIndicador> indicadores = new ArrayList<EvaluacionIndicador>();
	

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
//		String periodo = "ACTIVO";
//		String revision = servicioPeriodo.buscarPorEstado(periodo);
//		System.out.println(revision);
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size();
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
		lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
//		lblRevision.setValue(revision);
		gpxAgregar.setOpen(false);
		gpxAgregados.setOpen(false);
		
		List<EvaluacionObjetivo> evaluacionObjetivo = servicioEvaluacionObjetivo.buscarObjetivos(ficha, numeroEvaluacion);
		cmbObjetivos.setModel(new ListModelList<EvaluacionObjetivo>(evaluacionObjetivo));
	}

	@Listen("onClick = #btnAgregar")
	public void AgregarObjetivo() {
		gpxAgregar.setOpen(true);
	}

	@Listen("onClick = #btnOk")
	public void AgregarIndicador() {
		
		
		if (cmbObjetivos.getText().compareTo("")==0 || txtIndicador.getText().compareTo("")==0 || cmbUnidad.getText().compareTo("")==0 
		|| cmbMedicion.getText().compareTo("")==0 || txtPeso.getText().compareTo("")==0 
		|| txtValorMeta.getText().compareTo("")==0 || txtResFy.getText().compareTo("")==0 || txtIndicador.getText().compareTo("")==0
		|| txtResultadoPorc.getText().compareTo("")==0 || txtPesoPorc.getText().compareTo("")==0 || txtValorMeta.getText().compareTo("")==0)
		{
		}	else {
			gpxAgregados.setOpen(true);
		String indicador = txtIndicador.getValue();
		String unidadCombo= cmbUnidad.getSelectedItem().getContext();
		UnidadMedida  unidad = servicioUnidadMedida.buscarUnidad(Integer.parseInt(unidadCombo));
		String medicionCombo= cmbMedicion.getSelectedItem().getContext();
		Medicion medicion = servicioMedicion.buscarMedicion(Integer.parseInt(medicionCombo));
		String idObjetivo = cmbObjetivos.getSelectedItem().getContext();
		Double peso = Double.valueOf(txtPeso.getValue());
		Double valorMeta = Double.valueOf(txtValorMeta.getValue());
		Double valorResultado = Double.valueOf(txtValorResultado.getValue());
		Double resFy = Double.valueOf(txtResFy.getValue());
		Double resultadoPorc = Double.valueOf(txtResultadoPorc.getValue());
		Double pesoPorc = Double.valueOf(txtPesoPorc.getValue());
		Integer linea = indicadores.size() + 1;		
		EvaluacionIndicador indicadorLista = new EvaluacionIndicador();
		indicadorLista.setIdObjetivo(Integer.parseInt(idObjetivo));
		indicadorLista.setDescripcionIndicador(indicador);
		indicadorLista.setMedicion(medicion);
		indicadorLista.setUnidadMedida(unidad);
		indicadorLista.setLinea(linea);
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
		servicioEvaluacionIndicador.guardar(indicadorLista);
		Messagebox.show("Indicador para el objetivo" + " " + cmbObjetivos.getValue() + " " + "ha sido guardado exitosamente",
				"Información", Messagebox.OK,
				Messagebox.INFORMATION);
		gpxAgregar.setOpen(false);
		
		limpiar ();
		}
	}

	public void limpiar() {
		txtIndicador.setValue("");
		txtPeso.setValue(null);
		txtPesoPorc.setValue(null);
		txtResFy.setValue(null);
		txtResultadoPorc.setValue(null);
		txtValorMeta.setValue(null);
		txtValorResultado.setValue(null);
		cmbMedicion.setValue(null);
		cmbUnidad.setValue(null);
	}
	
	

}