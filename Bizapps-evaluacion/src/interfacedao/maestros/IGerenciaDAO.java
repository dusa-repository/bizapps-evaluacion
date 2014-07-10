package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Gerencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGerenciaDAO extends JpaRepository<Gerencia, Integer> {

	public List<Gerencia> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Gerencia> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public Gerencia findByDescripcion(String descripcion);

	public List<Gerencia> findByDescripcionAllIgnoreCase(String descripcion);

	
	
}