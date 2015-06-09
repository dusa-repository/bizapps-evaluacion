package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Bitacora;
import modelo.maestros.Cargo;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.Revision;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import arbol.MArbol;
import arbol.Nodos;

import componentes.Mensaje;

import controlador.maestros.CGenerico;

public class CEmpleadoAgregar extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	@Wire
	private Tree arbolPersonalAgregar;
	@Wire
	private Include contenido;
	@Wire
	private Label etiqueta;

	TreeModel _model;
	List<String> listmenu1 = new ArrayList<String>();
	@Wire
	private Tab tab;
	@Wire
	private Tabbox tabBox;
	@Wire
	private West west;
	@Wire
	private Window winListaPersonal;
	@Wire
	private Window winEvaluacionEmpleadoAgregar;
	@Wire
	private Listbox lbxEvaluacion;
	@Wire
	private Listbox lbxPersonalCargo;
	@Wire
	private Listheader header;
	@Wire
	private Groupbox gpxListaPersonalCargo;
	private  int numeroEvaluacion;
	private  Empleado empleado;
	Evaluacion evaluacion = new Evaluacion();
	public  Revision revision;
	private  int idEva;
	private boolean singleton=false;

	Mensaje msj = new Mensaje();

	@Override
	public void inicializar() throws IOException {
		singleton=true;
		arbolPersonalAgregar.setModel(getModel());
		revision = servicioRevision.buscarPorEstado("ACTIVO");

	}

	/* Permite asignarle los nodos cargados con el metodo getFooRoot() al arbol */
	public TreeModel getModel() {
		if (_model == null) {
			_model = new MArbol(getFooRoot());
		}
		return _model;
	}

	/*
	 * Permite obtener las funcionalidades asociadas al usuario en session y asi
	 * crear un arbol estructurado, segun la distribucion de las mismas
	 */
	private Nodos getFooRoot() {
		Nodos root = new Nodos(null, 0, "", "");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());

		List<Empleado> empleado = servicioEmpleado.BuscarPorSupervisor(u
				.getCedula());
		List<Empleado> arboles = new ArrayList<Empleado>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		List<Empleado> hijos = new ArrayList<Empleado>();
		for (int k = 0; k < empleado.size(); k++) {
			ids.add(Integer.valueOf(empleado.get(k).getFicha()));
			hijos = servicioEmpleado.BuscarPorSupervisor(empleado.get(k)
					.getFicha());
			for (int i = 0; i < hijos.size(); i++) {
				empleado.add(hijos.get(i));
			}
		}
		Empleado jefe = servicioEmpleado.buscarPorFicha(u.getCedula());
		arboles.add(jefe);
		for (int t = 0; t < ids.size(); t++) {
			Empleado a;
			a = servicioEmpleado.buscarPorFicha(String.valueOf(ids.get(t)));
			arboles.add(a);
		}
		List<Nodos> nodos = new ArrayList<Nodos>();
		List<String> idsPadre = new ArrayList<String>();
		return crearArbol(root, nodos, arboles, 0, idsPadre, jefe.getFicha());
	}
	
	private Nodos crearArbol(Nodos roote, List<Nodos> nodos,
			List<Empleado> arboles, int i, List<String> idsPadre, String cedula) {
		for (int z = 0; z < arboles.size(); z++) {
			Nodos oneLevelNode = new Nodos(null, 0, "", "");
			Nodos two = new Nodos(null, 0, "", "");
			if (arboles.get(z).getFicha().equals(cedula)) {
				oneLevelNode = new Nodos(roote, z, "("
						+ arboles.get(z).getGradoAuxiliar() + ")" + " "
						+ arboles.get(z).getNombre(), arboles.get(z).getFicha());
				roote.appendChild(oneLevelNode);
				idsPadre.add(arboles.get(z).getFicha());
				nodos.add(oneLevelNode);
			} else {
				for (int j = 0; j < idsPadre.size(); j++) {
					if (idsPadre.get(j).equals(
							arboles.get(z).getFichaSupervisor())) {
						oneLevelNode = nodos.get(j);
						two = new Nodos(oneLevelNode, z, "("
								+ arboles.get(z).getGradoAuxiliar() + ")" + " "
								+ arboles.get(z).getNombre(), arboles.get(z)
								.getFicha());
						oneLevelNode.appendChild(two);
						idsPadre.add(arboles.get(z).getFicha());
						nodos.add(two);
					}
				}
			}
		}
		return roote;
	}
	
	
	@Listen("onDoubleClick = #arbolPersonalAgregar")
	public void selectedNodeDoubleClik()
	{
		//selectedNode();
	}


	@Listen("onClick = #arbolPersonalAgregar")
	public void selectedNodeClik() 
	{
		if (singleton) 
		{
			singleton=false;
			selectedNode();	
		}
		
		
	}
	
	
	public void selectedNode()
	{
		if (arbolPersonalAgregar.getSelectedItem() != null) {
			String item = String.valueOf(arbolPersonalAgregar.getSelectedItem()
					.getContext());
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
			Integer idUsuario = u.getIdUsuario();
			Integer numeroEvaluacion;
			if (servicioEvaluacion.buscarIdSecundario(item) != null){
			numeroEvaluacion = servicioEvaluacion.buscarIdSecundario(item) + 1;
			}
			else {
				numeroEvaluacion = 1;
			}
			idEva = servicioEvaluacion.buscarId() + 1;
			Empleado empleado = servicioEmpleado.buscarPorFicha(item);
			String fichaEvaluador = empleado.getFichaSupervisor();
			Cargo cargo = empleado.getCargo();
			Evaluacion evaluacion = new Evaluacion();
			evaluacion.setIdEvaluacion(idEva);
			evaluacion.setEstadoEvaluacion("EN EDICION");
			evaluacion.setFechaCreacion(fechaHora);
			evaluacion.setFechaAuditoria(fechaHora);
			evaluacion.setFicha(item);
			evaluacion.setRevision(revision);
			evaluacion.setIdEvaluacionSecundario(numeroEvaluacion);
			evaluacion.setIdUsuario(idUsuario);
			evaluacion.setFichaEvaluador(fichaEvaluador);
			evaluacion.setPeso(0);
			evaluacion.setResultado(0);
			evaluacion.setResultadoObjetivos(0);
			evaluacion.setResultadoGeneral(0);
			evaluacion.setCargo(cargo);
			evaluacion.setHoraAuditoria(horaAuditoria);
			
			Bitacora bitacora = new Bitacora();

			bitacora.setEvaluacion(evaluacion);
			bitacora.setIdUsuario(u);
			bitacora.setFechaAuditoria(fechaHora);
			bitacora.setHoraAuditoria(horaAuditoria);
			bitacora.setEstadoEvaluacion("EN EDICION");
			System.out.println(bitacora.getEvaluacion().getIdEvaluacion());
			System.out.println(evaluacion + " " + u + " " + fechaHora
					+ horaAuditoria);
			servicioEvaluacion.guardar(evaluacion);
			servicioBitacora.guardar(bitacora);
			
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("modo", "AGREGAR");
			map.put("ficha", item);
			map.put("id", idEva);
			map.put("numero", numeroEvaluacion);
			Sessions.getCurrent().setAttribute("itemsCatalogo", map);
			if (winEvaluacionEmpleadoAgregar != null){
				winEvaluacionEmpleadoAgregar.detach();
				winEvaluacionEmpleadoAgregar = null;
			}
			else{
			winEvaluacionEmpleadoAgregar = (Window) Executions
					.createComponents(
							"/vistas/transacciones/VAgregarEvaluacion.zul",
							null, map);
			winEvaluacionEmpleadoAgregar.doModal();
			winEvaluacionEmpleadoAgregar.setClosable(true);

		}
	}

	}
	
}