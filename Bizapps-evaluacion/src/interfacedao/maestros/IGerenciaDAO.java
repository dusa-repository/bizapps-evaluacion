package interfacedao.maestros;


import java.util.List;

import modelos.Gerencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGerenciaDAO extends JpaRepository<Gerencia, Integer> {

	Gerencia findByDescripcion(String descripcion);

	
	
}