package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.PerfilCargo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilCargoDAO extends JpaRepository<PerfilCargo, Integer> {

	PerfilCargo findByDescripcion(String descripcion);

	public List<PerfilCargo> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<PerfilCargo> findByNivelAcademicoStartingWithAllIgnoreCase(String valor);

	public List<PerfilCargo> findByEspecialidadStartingWithAllIgnoreCase(String valor);

	public List<PerfilCargo> findByEspecializacionStartingWithAllIgnoreCase(
			String valor);

	public List<PerfilCargo> findByExperienciaPreviaStartingWithAllIgnoreCase(
			String valor);

	public List<PerfilCargo> findByIdiomaStartingWithAllIgnoreCase(String valor);

	public List<PerfilCargo> findByObservacionesStartingWithAllIgnoreCase(String valor);

	public List<PerfilCargo> findByCargoDescripcionStartingWithAllIgnoreCase(
			String valor);

	PerfilCargo findByCargo(Cargo cargo);

}