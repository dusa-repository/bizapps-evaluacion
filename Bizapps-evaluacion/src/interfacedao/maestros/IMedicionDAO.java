package interfacedao.maestros;


import java.util.List;

import modelos.Medicion;
import modelos.Perspectiva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMedicionDAO extends JpaRepository<Medicion, Integer> {

	Medicion findByDescripcionMedicion(String descripcion);

	 public List<Medicion> findAll();

	 Medicion findByIdMedicion (Integer value);
}
