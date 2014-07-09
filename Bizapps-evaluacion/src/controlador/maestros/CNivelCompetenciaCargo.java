package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.Dominio;
import modelo.maestros.NivelCompetenciaCargo;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CNivelCompetenciaCargo extends CGenerico {

	@Wire
	private Window wdwVNivelCompetenciaCargo;
	@Wire
	private Button btnGuardar;
	@Wire
	private Button btnLimpiar;
	@Wire
	private Button btnSalir;
	@Wire
	private Textbox txtCargoNivelCompetenciaCargo;
	@Wire
	private Button btnBuscarCargo;
	@Wire
	private Label lblCargoNivelCompetencia;
	@Wire
	private Listbox lsbCompetencia;
	@Wire
	private Div divCatalogoCargo;
	private Listbox lsbCompetenciasSeleccionadas;
	List<Competencia> competenciasDisponibles = new ArrayList<Competencia>();
	ListModelList<Dominio> tipos;
	private int idCargo = 0;

	Mensaje msj = new Mensaje();
	Catalogo<Cargo> catalogoCargo;

	public ListModelList<Dominio> getTipos() {
		tipos = new ListModelList<Dominio>(
				servicioDominio.buscarPorTipo("REQUERIDO"));
		return tipos;
	}

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

		txtCargoNivelCompetenciaCargo.setFocus(true);

	}

	@Listen("onClick = #btnBuscarCargo")
	public void mostrarCatalogoCargo() {
		final List<Cargo> listCargo = servicioCargo.buscarTodos();
		catalogoCargo = new Catalogo<Cargo>(divCatalogoCargo,
				"Catalogo de Cargos", listCargo, "Código cargo", "Descripción",
				"Nómina", "Cargo Auxiliar", "Empresa Auxiliar") {

			@Override
			protected List<Cargo> buscarCampos(List<String> valores) {
				List<Cargo> lista = new ArrayList<Cargo>();

				for (Cargo cargo : listCargo) {
					if (String.valueOf(cargo.getId()).toLowerCase()
							.startsWith(valores.get(0))
							&& cargo.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))
							&& cargo.getNomina().toLowerCase()
									.startsWith(valores.get(2))
							&& cargo.getIdCargoAuxiliar().toLowerCase()
									.startsWith(valores.get(3))
							&& cargo.getIdEmpresaAuxiliar().toLowerCase()
									.startsWith(valores.get(4))) {
						lista.add(cargo);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Cargo cargo) {
				String[] registros = new String[5];
				registros[0] = String.valueOf(cargo.getId());
				registros[1] = cargo.getDescripcion();
				registros[2] = cargo.getNomina();
				registros[3] = cargo.getIdCargoAuxiliar();
				registros[4] = cargo.getIdEmpresaAuxiliar();

				return registros;
			}

			@Override
			protected List<Cargo> buscar(String valor, String combo) {
				// TODO Auto-generated method stub
				if (combo.equals("Código cargo"))
					return servicioCargo.filtroId(valor);
				else if (combo.equals("Descripción"))
					return servicioCargo.filtroDescripcion(valor);
				else if (combo.equals("Nómina"))
					return servicioCargo.filtroNomina(valor);
				else if (combo.equals("Cargo Auxiliar"))
					return servicioCargo.filtroCargoAuxiliar(valor);
				else if (combo.equals("Empresa Auxiliar"))
					return servicioCargo.filtroEmpresaAuxiliar(valor);
				else
					return servicioCargo.buscarTodos();
			}

		};

		catalogoCargo.setClosable(true);
		catalogoCargo.setWidth("80%");
		catalogoCargo.setParent(divCatalogoCargo);
		catalogoCargo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoCargo")
	public void seleccionCargo() {
		Cargo cargo = catalogoCargo.objetoSeleccionadoDelCatalogo();
		txtCargoNivelCompetenciaCargo.setValue(String.valueOf(cargo.getId()));
		lblCargoNivelCompetencia.setValue(cargo.getDescripcion());
		idCargo = cargo.getId();
		catalogoCargo.setParent(null);
		llenarLista();
	}

	@Listen("onChange = #txtCargoNivelCompetenciaCargo")
	public void buscarCargo() {
		Cargo cargo = servicioCargo.buscarCargo(Integer
				.valueOf(txtCargoNivelCompetenciaCargo.getValue()));

		if (cargo == null) {
			msj.mensajeAlerta(Mensaje.codigoCargo);
			txtCargoNivelCompetenciaCargo.setFocus(true);
		} else {
			idCargo = cargo.getId();
			lblCargoNivelCompetencia.setValue(cargo.getDescripcion());
			llenarLista();
		}

	}

	@Listen("onClick = #btnLimpiar")
	public void limpiarCampos() {

		idCargo = 0;
		txtCargoNivelCompetenciaCargo.setValue("");
		lblCargoNivelCompetencia.setValue("");
		lsbCompetencia.setModel(new ListModelList<Competencia>());
		txtCargoNivelCompetenciaCargo.setFocus(true);
	}

	@Listen("onClick = #btnSalir")
	public void salir() {

		cerrarVentana1(wdwVNivelCompetenciaCargo, "Nivel Competencia Cargo");
	}

	public void llenarLista() {

		competenciasDisponibles = servicioCompetencia.buscarTodas();
		lsbCompetencia.setModel(new ListModelList<Competencia>(
				competenciasDisponibles));
		lsbCompetencia.setMultiple(false);
		lsbCompetencia.setCheckmark(false);
		lsbCompetencia.setMultiple(true);
		lsbCompetencia.setCheckmark(true);
	}

	@Listen("onClick = #btnGuardar")
	public void guardar() {

		if (lsbCompetencia.getItemCount() != 0) {
			for (int i = 0; i < lsbCompetencia.getItemCount(); i++) {
				Listitem listItem = lsbCompetencia.getItemAtIndex(i);
				int codigoCompetencia = ((Intbox) ((listItem.getChildren()
						.get(0))).getFirstChild()).getValue();
				String tipoDominio = ((Combobox) ((listItem.getChildren()
						.get(4))).getFirstChild()).getValue();

				Cargo cargo = servicioCargo.buscarCargo(idCargo);
				Competencia competencia = servicioCompetencia
						.buscarCompetencia(codigoCompetencia);
				Dominio dominio = servicioDominio.buscarPorNombre(tipoDominio);
				NivelCompetenciaCargo nivel = new NivelCompetenciaCargo(
						competencia, cargo, dominio);
				servicioNivelCompetenciaCargo.guardar(nivel);
				msj.mensajeInformacion(Mensaje.guardado);
				limpiarCampos();

			}
		}

	}

}
