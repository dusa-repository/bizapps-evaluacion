package interfacedao.maestros;


import java.util.List;

import modelos.Perspectiva;
import modelos.UnidadMedida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUnidadMedidaDAO extends JpaRepository<UnidadMedida, Integer> {

	UnidadMedida findByDescripcion(String descripcion);

	 public List<UnidadMedida> findAll();

	 UnidadMedida findByIdUnidad (Integer value);
	
}
