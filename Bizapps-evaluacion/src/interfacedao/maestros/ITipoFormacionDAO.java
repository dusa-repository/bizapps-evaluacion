package interfacedao.maestros;


import java.util.List;

import modelo.maestros.TipoFormacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITipoFormacionDAO extends JpaRepository<TipoFormacion, Integer> {

	TipoFormacion findByDescripcion(String descripcion);


	
}