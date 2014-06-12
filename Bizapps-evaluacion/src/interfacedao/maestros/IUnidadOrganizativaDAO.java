package interfacedao.maestros;


import java.util.List;

import modelos.UnidadOrganizativa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUnidadOrganizativaDAO extends JpaRepository<UnidadOrganizativa, Integer> {

	UnidadOrganizativa findByDescripcion(String descripcion);

	

	
}
