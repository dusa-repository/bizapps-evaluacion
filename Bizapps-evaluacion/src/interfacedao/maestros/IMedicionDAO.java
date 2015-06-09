package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Medicion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IMedicionDAO extends JpaRepository<Medicion, Integer> {

	Medicion findByDescripcionMedicion(String descripcion);

	 public List<Medicion> findAll();

	 Medicion findById (Integer value);

	public List<Medicion> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Medicion> findByDescripcionMedicionStartingWithAllIgnoreCase(
			String valor);
}
