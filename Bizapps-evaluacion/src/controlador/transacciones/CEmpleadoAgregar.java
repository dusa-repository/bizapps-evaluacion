package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.Revision;
import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;

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
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import arbol.MArbol;
import arbol.Nodos;

import controlador.maestros.CGenerico;
import componentes.Mensaje;
import componentes.Validador;

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
	private static int numeroEvaluacion;
	private static Empleado empleado;
	Evaluacion evaluacion = new Evaluacion();
	public static Revision revision;
	private static int idEva;

	Mensaje msj = new Mensaje();

	@Override
	public void inicializar() throws IOException {

		arbolPersonalAgregar.setModel(getModel());

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
		Nodos oneLevelNode = new Nodos(null, 0, "", "");
		Nodos twoLevelNode = new Nodos(null, 0, "", "");
		Nodos threeLevelNode = new Nodos(null, 0, "", "");
		Nodos fourLevelNode = new Nodos(null, 0, "", "");

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());

		List<Empleado> empleado = servicioEmpleado.BuscarPorSupervisor(u
				.getCedula());
		List<Empleado> arboles = new ArrayList<Empleado>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int k = 0; k < empleado.size(); k++) {
			ids.add(empleado.get(k).getId());

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
			if (arboles.get(i).getFicha().equals(ficha)) {
				oneLevelNode = new Nodos(root, i, "("
						+ arboles.get(i).getGradoAuxiliar() + ")" + " "
						+ arboles.get(i).getNombre(), arboles.get(i).getFicha());
				// oneLevelNode = new Nodos(root, i, "("
				// + arboles.get(i).getGradoAuxiliar() + ")" + "  "
				// + arboles.get(i).getFicha());
				root.appendChild(oneLevelNode);
				temp1 = arboles.get(i).getFicha();
				arboles.remove(i);
				List<Empleado> hijos = servicioEmpleado
						.BuscarPorSupervisor(temp1);
				for (int j = i; j < hijos.size(); j++) {
					if (temp1.equals(hijos.get(j).getFichaSupervisor())) {

						twoLevelNode = new Nodos(root, i, "("
								+ hijos.get(i).getGradoAuxiliar() + ")" + " "
								+ hijos.get(i).getNombre(), hijos.get(i)
								.getFicha());
						// new Nodos(oneLevelNode, i, "("
						// + hijos.get(i).getGradoAuxiliar() + ")" + "  "
						// + hijos.get(j).getNombre());
						oneLevelNode.appendChild(twoLevelNode);
						temp2 = hijos.get(j).getFicha();
						hijos.remove(j);
						List<Empleado> hijos2 = servicioEmpleado
								.BuscarPorSupervisor(temp2);
						for (int k = j; k < hijos2.size(); k++) {

							if (temp2
									.equals(hijos2.get(k).getFichaSupervisor())) {

								threeLevelNode = new Nodos(root, i,
										"(" + hijos2.get(i).getGradoAuxiliar()
												+ ")" + " "
												+ hijos2.get(i).getNombre(),
										hijos2.get(i).getFicha());
								// new Nodos(twoLevelNode, i, "("
								// + hijos2.get(i).getGradoAuxiliar()
								// + ")" + "  "
								// + hijos2.get(k).getNombre());
								twoLevelNode.appendChild(threeLevelNode);
								temp3 = hijos2.get(k).getFicha();
								hijos2.remove(k);
								List<Empleado> hijos3 = servicioEmpleado
										.BuscarPorSupervisor(temp3);
								for (int z = k; z < hijos3.size(); z++) {

									if (temp3.equals(hijos3.get(z)
											.getFichaSupervisor())) {
										fourLevelNode = new Nodos(root, i, "("
												+ hijos3.get(i)
														.getGradoAuxiliar()
												+ ")" + " "
												+ hijos3.get(i).getNombre(),
												hijos3.get(i).getFicha());

										// new Nodos(
										// threeLevelNode, i, hijos3
										// .get(z).getNombre());
										threeLevelNode
												.appendChild(fourLevelNode);
										hijos3.remove(z);

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

	@Listen("onClick = #arbolPersonalAgregar")
	public void selectedNode() {
		if (arbolPersonalAgregar.getSelectedItem() != null) {
			String item = String.valueOf(arbolPersonalAgregar.getSelectedItem()
					.getContext());
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
			Integer idUsuario = u.getIdUsuario();
			Integer numeroEvaluacion = servicioEvaluacion.buscarIdSecundario(item) + 1;
			idEva = servicioEvaluacion.buscarId() + 1;
			Empleado empleado = servicioEmpleado.buscarPorFicha(item);
			String fichaEvaluador = empleado.getFichaSupervisor();
			Integer idCargo = empleado.getCargo().getId();
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
			servicioEvaluacion.guardar(evaluacion);
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ficha", item);
			map.put("id", idEva);
			map.put("numero", numeroEvaluacion);
			Sessions.getCurrent().setAttribute("itemsCatalogo", map);
			winEvaluacionEmpleadoAgregar = (Window) Executions
					.createComponents(
							"/vistas/transacciones/VPersonalAgregarEvaluacion.zul",
							null, map);
			winEvaluacionEmpleadoAgregar.doModal();
			winEvaluacionEmpleadoAgregar.setClosable(true);

		}
	}
}