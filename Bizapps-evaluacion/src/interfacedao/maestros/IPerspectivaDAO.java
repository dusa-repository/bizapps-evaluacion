package interfacedao.maestros;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Empleado;
import modelo.maestros.Perspectiva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPerspectivaDAO extends JpaRepository<Perspectiva, Integer> {
	
	 public List<Perspectiva> findAll();
	 
	 Perspectiva findById (Integer value);

	public Perspectiva findByDescripcion(String value);

	public List<Perspectiva> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Perspectiva> findByDescripcionStartingWithAllIgnoreCase(
			String valor);

	 }
