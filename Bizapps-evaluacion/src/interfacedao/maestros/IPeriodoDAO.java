package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Periodo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPeriodoDAO extends JpaRepository<Periodo, Integer> {

	Periodo findByNombre(String nombre);

	public List<Periodo> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Periodo> findByNombreStartingWithAllIgnoreCase(String valor);

	public List<Periodo> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Periodo> findByFechaInicioStartingWithAllIgnoreCase(String valor);

	public List<Periodo> findByFechaFinStartingWithAllIgnoreCase(String valor);

	public List<Periodo> findByEstadoPeriodoStartingWithAllIgnoreCase(String valor);

	Periodo findByEstadoPeriodo(String estadoPeriodo);

	public List<Periodo> findByNombreAllIgnoreCase(String nombre);

	
}