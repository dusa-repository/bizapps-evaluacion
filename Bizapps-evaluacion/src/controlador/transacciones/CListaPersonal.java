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
import modelos.NivelCompetenciaCargo;
import modelos.Perspectiva;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import sun.util.calendar.BaseCalendar.Date;

import arbol.MArbol;
import arbol.Nodos;

import controlador.maestros.CGenerico;
import componentes.Mensaje;
import componentes.Validador;

public class CListaPersonal extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
		Mensaje msj = new Mensaje();

	private Button btnAgregar;
	@Wire
	private Button btnEliminar;
	@Wire
	private Window winListaPersonal;
	@Wire
	private Listbox lbxEvaluacion;
	@Wire
	private Window winEvaluacionEmpleado;
	@Wire
	private Groupbox gpxListaPersonal;

	@Override
	public void inicializar() throws IOException {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		gpxListaPersonal.setTitle("(" + "  " + ficha + ")" + "   " + u.getNombre() + "   " + u.getApellido());
		
	}

	@Listen("onClick = #btnAgregar")
	public void AgregarEvaluacion() {	
		winListaPersonal.onClose();
	}
	
	@Listen("onClick = #btnBuscar")
	public void BuscarEvaluacion() {	
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		List<Evaluacion> evaluacion = new ArrayList<Evaluacion>();
		evaluacion = servicioEvaluacion.buscarPorEstado(ficha, "EN EDICION"); 
		lbxEvaluacion
		.setModel(new ListModelList<Evaluacion>(
				evaluacion));
		
	
	}
	
	@Listen("onDoubleClick = #lbxEvaluacion")
	public void mostrarEvaluacion() {
			
			
			if (lbxEvaluacion.getItemCount() != 0) {
				
				Listitem listItem = lbxEvaluacion.getSelectedItem();	
				if (listItem != null) {
						
					Evaluacion evaluacion = (Evaluacion) listItem.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", evaluacion.getIdEvaluacion());
					map.put("titulo", evaluacion.getFicha());
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					winEvaluacionEmpleado = (Window) Executions.createComponents("/vistas/transacciones/VEvaluacionEnEdicion.zul", null, map);				
					winEvaluacionEmpleado.doModal();
					winEvaluacionEmpleado.setClosable(true);
					winListaPersonal.onClose();
				}
				
			}
	
}
}