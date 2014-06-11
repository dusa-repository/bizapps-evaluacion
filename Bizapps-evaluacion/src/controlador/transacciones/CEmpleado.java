package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;
import modelos.Empleado;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.West;

import arbol.MArbol;
import arbol.Nodos;

import controlador.maestros.CGenerico;
import componentes.Mensaje;
import componentes.Validador;

public class CEmpleado extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	@Wire
	private Tree arbolPersonal ;
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

	Mensaje msj = new Mensaje();

	@Override
	public void inicializar() throws IOException {
		
		arbolPersonal.setModel(getModel());

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

		Nodos root = new Nodos(null, 0, "");
		Nodos oneLevelNode = new Nodos(null, 0, "");
		Nodos twoLevelNode = new Nodos(null, 0, "");
		Nodos threeLevelNode = new Nodos(null, 0, "");
		Nodos fourLevelNode = new Nodos(null, 0, "");
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());

		List<Empleado> empleado = servicioEmpleado.BuscarPorSupervisor(u.getCedula());
		List<Empleado> arboles = new ArrayList<Empleado>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int k = 0; k < empleado.size(); k++) {	
				ids.add(empleado.get(k).getIdEmpleado());
			
		}

		Collections.sort(ids); 
		Empleado jefe = servicioEmpleado.buscarPorFicha(u.getCedula());
		arboles.add(jefe); 
		for (int t = 0; t < ids.size(); t++) {
			Empleado a;
			a = servicioEmpleado.buscarPorId(ids.get(t));
			arboles.add(a);
		}
		String temp1, temp2, temp3 = "";
		for (int i = 0; i < arboles.size(); i++) {
			String ficha = jefe.getFicha(); 	
			if (arboles.get(i).getFicha().equals(ficha))  {
				oneLevelNode = new Nodos(root, i, arboles.get(i).getNombre());
				root.appendChild(oneLevelNode);
//				temp1 = arboles.get(i).getIdEmpleado();
				temp1 = arboles.get(i).getFicha();
				arboles.remove(i);

				for (int j = i; j < arboles.size(); j++) {
					if (temp1.equals(arboles.get(j).getFichaSupervisor())) {
						twoLevelNode = new Nodos(oneLevelNode, i, arboles
								.get(j).getNombre());
						oneLevelNode.appendChild(twoLevelNode);
//						temp2 = arboles.get(j).getIdEmpleado();
						temp2 = arboles.get(j).getFicha();
						arboles.remove(j);

						for (int k = j; k < arboles.size(); k++) {

							if (temp2.equals(arboles.get(k).getFichaSupervisor())) {
							
								threeLevelNode = new Nodos(twoLevelNode, i,
										arboles.get(k).getNombre());
								twoLevelNode.appendChild(threeLevelNode);
//								temp3 = arboles.get(k).getIdEmpleado();
								temp3 = arboles.get(k).getFicha();
								arboles.remove(k);

								for (int z = k; z < arboles.size(); z++) {

									if (temp3.equals(arboles.get(z).getFichaSupervisor())) {
										fourLevelNode = new Nodos(
												threeLevelNode, i, arboles.get(
														z).getNombre());
										threeLevelNode
												.appendChild(fourLevelNode);
										arboles.remove(z);

										z = z - 1;
									}
								}
								k = k - 1;
							}
						}
						j = j - 1;
					}
				}
				i = i - 1;
			}
		}
		return root;
	}

	
}