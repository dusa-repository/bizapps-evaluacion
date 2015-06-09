package interfacedao.maestros;


import java.util.List;

import modelo.maestros.UnidadMedida;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUnidadMedidaDAO extends JpaRepository<UnidadMedida, Integer> {

	UnidadMedida findByDescripcion(String descripcion);

	 public List<UnidadMedida> findAll();

	 UnidadMedida findById(Integer value);

	public List<UnidadMedida> findByIdStartingWithAllIgnoreCase(String valor);

	public List<UnidadMedida> findByDescripcionStartingWithAllIgnoreCase(String valor);
	
}
