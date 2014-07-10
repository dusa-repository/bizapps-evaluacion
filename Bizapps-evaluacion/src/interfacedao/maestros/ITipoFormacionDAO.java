package interfacedao.maestros;


import java.util.List;

import modelo.maestros.TipoFormacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITipoFormacionDAO extends JpaRepository<TipoFormacion, Integer> {

	public List<TipoFormacion> findByIdStartingWithAllIgnoreCase(String valor);

	public List<TipoFormacion> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public TipoFormacion findByDescripcion(String descripcion);

	public List<TipoFormacion> findByDescripcionAllIgnoreCase(String descripcion);


	
}