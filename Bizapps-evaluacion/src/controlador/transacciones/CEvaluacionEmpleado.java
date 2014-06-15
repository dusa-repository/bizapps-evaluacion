package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import modelo.seguridad.Usuario;
import modelos.Competencia;
import modelos.Dominio;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.EvaluacionObjetivo;
import modelos.NivelCompetenciaCargo;
import modelos.Perspectiva;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import controlador.maestros.CGenerico;
import componentes.Mensaje;


public class CEvaluacionEmpleado extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

		@Wire
	private Textbox txtFortalezas;
	@Wire
	private Textbox txtOportunidades;
	@Wire
	private Textbox txtResumen;
	@Wire
	private Textbox txtCompromisos;
	@Wire
	private Button btnGuardar;
	@Wire
	private Button btnImprimir;
	@Wire
	private Button btnOk;
	@Wire
	private Listbox lbxIndicadoresAgregados;
	@Wire
	private Listbox lbxObjetivosGuardados;
	@Wire
	private Listbox lbxCompetenciaEspecifica;
	@Wire
	private Combobox cmbObjetivos;
	@Wire
	private Window window;
	@Wire
	private Groupbox gpxAgregar;
	@Wire
	private Groupbox gpxAgregados;
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
	private Combobox cmbNivelRequerido;	
	@Wire
	private Window wdwConductasRectoras;
	String tipo = "REQUERIDO";
	ListModelList<Dominio> dominio;

	@Override
	public void inicializar() throws IOException {
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
		List<NivelCompetenciaCargo> nivel2 = new ArrayList<NivelCompetenciaCargo>();
		NivelCompetenciaCargo nivelRectoras = new NivelCompetenciaCargo ();
		
		nivel = servicioNivelCompetenciaCargo.buscar(empleado.getCargo());
		for (int j = 0; j < nivel.size(); j++) {
			if (nivel.get(j).getCompetencia().getNivel().equals("RECTORAS")) {
				nivelRectoras = nivel.get(j);
				nivel2.add(nivelRectoras);
			}
				else {
				nivel.remove(j);
			}
				lbxCompetenciaRectora
						.setModel(new ListModelList<NivelCompetenciaCargo>(
								nivel2));
		}
		
		
		lblFicha.setValue(ficha);
		lblNombreTrabajador.setValue(nombreTrabajador);
		lblCargo.setValue(cargo);
		lblUnidadOrganizativa.setValue(unidadOrganizativa);
		lblGerencia.setValue(gerenciaReporte);
		lblEvaluacion.setValue(numeroEvaluacion.toString());
		lblFechaCreacion.setValue(fechaHora.toString());
	}
	
	
	public ListModelList<Dominio> getDominio(){
		dominio = new ListModelList<Dominio> (servicioDominio.buscarPorTipo(tipo));
		return dominio;
	}
	
	@Listen("onDoubleClick = #lbxCompetenciaRectora")
	public void mostrarDatosCatalogo() {
			
			
			if (lbxCompetenciaRectora.getItemCount() != 0) {
				
				Listitem listItem = lbxCompetenciaRectora.getSelectedItem();	
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
					map.put("id", competencia.getCompetencia().getIdCompetencia());
					map.put("idnivel", nivel);
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					wdwConductasRectoras = (Window) Executions.createComponents("/vistas/transacciones/vConductasRectoras.zul", null, map);
					wdwConductasRectoras.doModal();				
					}
					}							
				
			}
	
}
}