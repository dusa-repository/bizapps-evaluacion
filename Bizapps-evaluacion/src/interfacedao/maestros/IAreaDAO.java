package interfacedao.maestros;


import java.util.List;

import modelos.Area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAreaDAO extends JpaRepository<Area, Integer> {

	Area findByDescripcion(String descripcion);



	
}
