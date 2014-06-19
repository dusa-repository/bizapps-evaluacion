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
import modelos.Dominio;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.NivelCompetenciaCargo;
import modelos.Perspectiva;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Messagebox;
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

public class CCompetenciasEspecificas extends CGenerico {

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
	private Listbox lbxCompetenciaEspecifica;
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
	private Label lblEvaluacion;
	@Wire
	private Label lblFechaCreacion;
	@Wire
	private Label lblRevision;
	@Wire
	private Window wdwConductasEspecificas;
	String tipo = "REQUERIDO";
	ListModelList<Dominio> dominio;
	
	@Override
	public void inicializar() throws IOException {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
		String ficha = u.getCedula();
		Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size() + 1;
		String nombreTrabajador = u.getNombre() + " " + u.getApellido();
		Empleado empleado = servicioEmpleado.buscarPorFicha(ficha);
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(formatoFecha.format(fechaHora));
		String cargo = empleado.getCargo().getDescripcion();
		String unidadOrganizativa = empleado.getUnidadOrganizativa()
				.getDescripcion();
		String gerenciaReporte = empleado.getUnidadOrganizativa().getGerencia()
				.getDescripcion();
		
		List<NivelCompetenciaCargo> nivel = new ArrayList<NivelCompetenciaCargo>();
		List<NivelCompetenciaCargo> nivel2 = new ArrayList<NivelCompetenciaCargo>();
		NivelCompetenciaCargo nivelRectoras = new NivelCompetenciaCargo ();
		
		nivel = servicioNivelCompetenciaCargo.buscar(empleado.getCargo());
		for (int j = 0; j < nivel.size(); j++) {
			if (nivel.get(j).getCompetencia().getNivel().equals("ESPECIFICAS")) {
				nivelRectoras = nivel.get(j);
				nivel2.add(nivelRectoras);
			}
				else {
				nivel.remove(j);
			}
			lbxCompetenciaEspecifica
						.setModel(new ListModelList<NivelCompetenciaCargo>(
								nivel2));
		}
		
		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);

	}

	
	public ListModelList<Dominio> getDominio(){
		dominio = new ListModelList<Dominio> (servicioDominio.buscarPorTipo(tipo));
		return dominio;
	}
	
	@Listen("onDoubleClick = #lbxCompetenciaEspecifica")
	public void mostrarDatosCatalogo() {
			
			
			if (lbxCompetenciaEspecifica.getItemCount() != 0) {
				
				Listitem listItem = lbxCompetenciaEspecifica.getSelectedItem();	
				if (listItem != null) {
					if ( ((Combobox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getValue() == ""){
						Messagebox.show("Debe Seleccionar un nivel de dominio",
								"Error", Messagebox.OK,
								Messagebox.ERROR);
					}else{
						String nivel = ((Combobox) ((listItem.getChildren().get(2)))
								.getFirstChild()).getSelectedItem().getDescription();
								
					NivelCompetenciaCargo competencia = (NivelCompetenciaCargo) listItem.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					String titulo = "Competencias Especificas";
					map.put("id", competencia.getCompetencia().getIdCompetencia());
					map.put("idnivel", nivel);
					map.put("titulo", titulo);
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					wdwConductasEspecificas = (Window) Executions.createComponents("/vistas/transacciones/VEvaluacionConductas.zul", null, map);
					wdwConductasEspecificas.doModal();				
					}
					}							
				
			}
		
	}
	
	
	
	
	
	
	
	


}