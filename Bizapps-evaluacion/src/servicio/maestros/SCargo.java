package servicio.maestros;

import interfacedao.maestros.ICargoDAO;

import java.util.List;

import modelo.maestros.Cargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCargo")
public class SCargo {

	@Autowired
	private ICargoDAO cargoDAO;

	/* Servicio que permite guardar los datos de un cargo */
	public void guardar(Cargo cargo) {
		cargoDAO.save(cargo);
	}

	/* Servicio que permite buscar un cargo de acuerdo al id */
	public Cargo buscarCargo(int id) {
		return cargoDAO.findOne(id);
	}

	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public Cargo buscarPorNombre(String descripcion) {
		Cargo cargo;
		cargo = cargoDAO.findByDescripcion(descripcion);
		return cargo;
	}
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public List<Cargo> buscarPorNombres(String descripcion) {
		List<Cargo> cargos;
		cargos = cargoDAO.findByDescripcionAllIgnoreCase(descripcion);
		return cargos;
	}

	/* Servicio que permite buscar todos los cargos */
	public List<Cargo> buscarTodos() {
		return cargoDAO.findAll();
	}

	/* Servicio que permite eliminar un cargo */
	public void eliminarUnCargo(int id) {
		cargoDAO.delete(id);
	}

	/* Servicio que permite eliminar varios cargos */
	public void eliminarVariosCargos(List<Cargo> eliminar) {
		cargoDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar los cargos de una lista de acuerdo al id
	 */
	public List<Cargo> filtroId(String valor) {
		return cargoDAO.findByIdStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los cargos de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Cargo> filtroDescripcion(String valor) {
		return cargoDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los cargos de una lista de acuerdo a la
	 * nomina
	 */
	public List<Cargo> filtroNomina(String valor) {
		return cargoDAO.findByNominaStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los cargos de una lista de acuerdo al cargo
	 * auxiliar
	 */
	public List<Cargo> filtroCargoAuxiliar(String valor) {
		return cargoDAO.findByIdCargoAuxiliarStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los cargos de una lista de acuerdo a la
	 * empresa auxiliar
	 */
	public List<Cargo> filtroEmpresaAuxiliar(String valor) {
		return cargoDAO.findByIdEmpresaAuxiliarStartingWithAllIgnoreCase(valor);
	}

}
