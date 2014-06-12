package interfacedao.maestros;


import java.util.List;

import modelos.Cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICargoDAO extends JpaRepository<Cargo, Integer> {

	Cargo findByDescripcion(String descripcion);

	
}
