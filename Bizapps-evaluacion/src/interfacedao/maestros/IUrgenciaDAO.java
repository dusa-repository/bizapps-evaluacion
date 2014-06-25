package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Urgencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUrgenciaDAO extends JpaRepository<Urgencia, Integer> {

	Urgencia findByDescripcionUrgencia(String descripcion);

	public List<Urgencia> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Urgencia> findByDescripcionUrgenciaStartingWithAllIgnoreCase(
			String valor);


	
}