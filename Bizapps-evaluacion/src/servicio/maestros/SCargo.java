package servicio.maestros;

import interfacedao.maestros.ICargoDAO;

import java.util.List;

import modelos.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCargo")
public class SCargo {

	@Autowired
	private ICargoDAO cargoDAO;

	/* Servicio que permite guardar los datos de un cargo*/
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
	
	/* Servicio que permite buscar todos los cargos */
	public List<Cargo> buscarTodos() {
		return cargoDAO.findAll();
	}
	
	/* Servicio que permite eliminar un cargo */
	public void eliminarUnCargo(int id) {
		cargoDAO.delete(id);
	}
	

}
