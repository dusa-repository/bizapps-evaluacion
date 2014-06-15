package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.seguridad.Usuario;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.NivelCompetenciaCargo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CListaPersonalCargo extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Window winListaPersonal;
	@Wire
	private Window winEvaluacionEmpleado;
	@Wire
	private Listbox lbxEvaluacion;
	@Wire
	private Listbox lbxPersonalCargo;
	@Wire
	private Listheader header;

	private static int numeroEvaluacion;
	private static Empleado empleado;
	Evaluacion evaluacion = new Evaluacion();

	@Override
	public void inicializar() throws IOException {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario usuario = servicioUsuario
				.buscarUsuarioPorNombre(auth.getName());
		Empleado emple = servicioEmpleado.buscarPorFicha(usuario.getCedula());
		header.setLabel(emple.getNombre());
		List<Empleado> empleados = servicioEmpleado.BuscarPorSupervisor(usuario
				.getCedula());
		if (!empleados.isEmpty()) {
			lbxPersonalCargo.setModel(new ListModelList<Empleado>(empleados));
			}
	}
	
	
	@Listen("onClick = #lbxPersonalCargo")
	public void mostrarEvaluaciones() {
		if(lbxPersonalCargo.getSelectedItem() != null){
		lbxEvaluacion.getItems().clear();
		empleado = lbxPersonalCargo.getSelectedItem().getValue();	
		String ficha = empleado.getFicha();
		List<Evaluacion> evaluacion = new ArrayList<Evaluacion>();
		evaluacion = servicioEvaluacion.buscar(ficha);		
		if(evaluacion.isEmpty()){
		Messagebox.show(
				"El empleado no tiene evaluaciones registradas",
				"Error", Messagebox.OK, Messagebox.ERROR);
		}else{		
			
		lbxEvaluacion.setModel(new ListModelList<Evaluacion>(
				evaluacion));		
		}
	}
	}
	
	public Empleado buscarNombre(){
		return empleado;
	}
	
	@Listen("onDoubleClick = #lbxEvaluacion")
	public void mostrarEvaluacion() {
			
			
			if (lbxEvaluacion.getItemCount() != 0) {
				
				Listitem listItem = lbxEvaluacion.getSelectedItem();	
				if (listItem != null) {
						
					Evaluacion evaluacion = (Evaluacion) listItem.getValue();
					final HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", evaluacion.getIdEvaluacion());
					Sessions.getCurrent().setAttribute("itemsCatalogo", map);
					winEvaluacionEmpleado = (Window) Executions.createComponents("/vistas/transacciones/VEvaluacionEmpleados.zul", null, map);				
					winEvaluacionEmpleado.doModal();
					winListaPersonal.onClose();
				}
				
			}
	
}
}