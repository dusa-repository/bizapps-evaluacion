package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Dominio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConductaCompetenciaDAO extends
		JpaRepository<ConductaCompetencia, Integer> {

	public List<ConductaCompetencia> findByCompetencia(Competencia id);

	public ConductaCompetencia findByDescripcion(String descripcion);

	public List<ConductaCompetencia> findByIdStartingWithAllIgnoreCase(
			String valor);

	public List<ConductaCompetencia> findByDescripcionStartingWithAllIgnoreCase(
			String valor);

	public List<ConductaCompetencia> findByOrdenStartingWithAllIgnoreCase(
			String valor);

	public List<ConductaCompetencia> findByCompetenciaAndDominio(Competencia id,
			Dominio id2);

	public List<ConductaCompetencia> findByCompetenciaDescripcionStartingWithAllIgnoreCase(
			String valor);

	public List<ConductaCompetencia> findByDominioDescripcionDominioStartingWithAllIgnoreCase(
			String valor);
}
