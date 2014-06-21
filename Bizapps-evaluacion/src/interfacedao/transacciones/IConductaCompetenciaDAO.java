package interfacedao.transacciones;


import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IConductaCompetenciaDAO extends JpaRepository<ConductaCompetencia, Long> {
	
		 
	 public List<ConductaCompetencia> findByIdCompetencia(int id);
}
	
