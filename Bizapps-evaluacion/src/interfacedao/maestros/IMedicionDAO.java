package interfacedao.maestros;


import java.util.List;

import modelos.Medicion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMedicionDAO extends JpaRepository<Medicion, Integer> {

	Medicion findByDescripcionMedicion(String descripcion);


	
}
