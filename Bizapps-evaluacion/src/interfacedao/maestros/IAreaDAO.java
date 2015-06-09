package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Area;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAreaDAO extends JpaRepository<Area, Integer> {

	Area findByDescripcion(String descripcion);
	
	Area findByDescripcionAndTipoFormacionDescripcion(String descripcion,String tipoFormacion);
	
	

	public List<Area> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Area> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Area> findByDescripcionAllIgnoreCase(String descripcion);

	public List<Area> findByTipoFormacionDescripcionStartingWithAllIgnoreCase(
			String valor);

	List<Area> findByTipoFormacionId(int tipo);



	
}
