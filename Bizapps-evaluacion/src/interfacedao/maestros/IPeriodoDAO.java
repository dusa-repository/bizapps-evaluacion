package interfacedao.maestros;


import java.util.List;

import modelos.Periodo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPeriodoDAO extends JpaRepository<Periodo, Integer> {

	Periodo findByNombre(String nombre);

	public List<Periodo> findByEstadoPeriodo(String estado);

	

	
}