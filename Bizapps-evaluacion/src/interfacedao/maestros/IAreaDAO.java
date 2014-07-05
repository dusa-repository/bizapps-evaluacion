package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAreaDAO extends JpaRepository<Area, Integer> {

	Area findByDescripcion(String descripcion);

	public List<Area> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Area> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Area> findByTipoFormacionStartingWithAllIgnoreCase(String valor);



	
}
