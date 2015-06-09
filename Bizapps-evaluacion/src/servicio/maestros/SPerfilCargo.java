package servicio.maestros;

import interfacedao.maestros.IPerfilCargoDAO;

import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.PerfilCargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPerfilCargo")
public class SPerfilCargo {

	@Autowired
	private IPerfilCargoDAO perfilCargoDAO;

	/* Servicio que permite guardar los datos de un perfil de cargo */
	public void guardar(PerfilCargo perfilCargo) {
		perfilCargoDAO.save(perfilCargo);
	}

	/* Servicio que permite buscar el perfil de un Cargo de acuerdo al id */
	public PerfilCargo buscarPerfilCargo(int id) {
		return perfilCargoDAO.findOne(id);
	}

	/* Servicio que permite buscar un perfilCargo de acuerdo al nombre */
	public PerfilCargo buscarPorNombre(String descripcion) {
		PerfilCargo perfilCargo;
		perfilCargo = perfilCargoDAO.findByDescripcion(descripcion);
		return perfilCargo;
	}

	/* Servicio que permite buscar todos los perfiles de Cargos */
	public List<PerfilCargo> buscarTodos() {
		return perfilCargoDAO.findAll();
	}

	/* Servicio que permite eliminar una perfil de un Cargo */
	public void eliminarUnPerfil(int id) {
		perfilCargoDAO.delete(id);
	}

	/* Servicio que permite eliminar varios perfiles de Cargos */
	public void eliminarVariosPerfiles(List<PerfilCargo> eliminar) {
		perfilCargoDAO.delete(eliminar);
	}
	
	
	/*
	 * Servicio que permite buscar un perfil de acuerdo a un cargo
	 */
	public PerfilCargo buscarPorCargo(Cargo cargo) {
		return perfilCargoDAO.findByCargo(cargo);
	}

	

	/*
	 * Servicio que permite filtrar las perfiles de Cargos de una lista de
	 * acuerdo a la descripcion
	 */
	public List<PerfilCargo> filtroNombre(String valor) {
		return perfilCargoDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los perfiles de cargos de una lista de acuerdo al
	 * nivel academico
	 */
	public List<PerfilCargo> filtroNivelAcademico(String valor) {
		return perfilCargoDAO
				.findByNivelAcademicoStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los perfiles de cargos de una lista de acuerdo a
	 * la especialidad
	 */
	public List<PerfilCargo> filtroEspecialidad(String valor) {
		return perfilCargoDAO
				.findByEspecialidadStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los perfiles de cargos de una lista de acuerdo a
	 * la especializacion
	 */
	public List<PerfilCargo> filtroEspecializacion(String valor) {
		return perfilCargoDAO
				.findByEspecializacionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los perfiles de cargos de una lista de acuerdo a
	 * la experiencia
	 */
	public List<PerfilCargo> filtroExperiencia(String valor) {
		return perfilCargoDAO
				.findByExperienciaPreviaStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar los perfiles de cargos de una lista de acuerdo al
	 * idioma
	 */
	public List<PerfilCargo> filtroIdioma(String valor) {
		return perfilCargoDAO
				.findByIdiomaStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las perfiles de un Cargo de una lista de acuerdo a las
	 * observaciones
	 */
	public List<PerfilCargo> filtroObservaciones(String valor) {
		return perfilCargoDAO
				.findByObservacionesStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las perfiles de un Cargo de una lista de acuerdo a un
	 * cargo
	 */
	public List<PerfilCargo> filtroCargo(String valor) {
		return perfilCargoDAO.findByCargoDescripcionStartingWithAllIgnoreCase(valor);
	}


}
