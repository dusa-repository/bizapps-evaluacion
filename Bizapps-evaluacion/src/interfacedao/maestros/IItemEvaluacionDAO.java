package interfacedao.maestros;

import java.util.List;

import modelo.maestros.ItemEvaluacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IItemEvaluacionDAO extends
		JpaRepository<ItemEvaluacion, Integer> {

	ItemEvaluacion findByDescripcion(String descripcion);

	public List<ItemEvaluacion> findByDescripcionStartingWithAllIgnoreCase(
			String valor);

	public List<ItemEvaluacion> findByPonderacionStartingWithAllIgnoreCase(
			String valor);

	public List<ItemEvaluacion> findByPonderacion(String tipoPonderacion);

}