package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Distribucion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDistribucionDAO extends JpaRepository<Distribucion, Integer> {

	Distribucion findByDescripcion(String descripcion);

	public List<Distribucion> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Distribucion> findByPorcentajeStartingWithAllIgnoreCase(String valor);


}
