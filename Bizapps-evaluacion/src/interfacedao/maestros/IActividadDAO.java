package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Actividad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IActividadDAO extends JpaRepository<Actividad, Integer> {

	Actividad findByDescripcion(String descripcion);

	public List<Actividad> findByDescripcionStartingWithAllIgnoreCase(String valor);

}