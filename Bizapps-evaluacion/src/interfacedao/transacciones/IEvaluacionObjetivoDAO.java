package interfacedao.transacciones;


import java.util.List;

import modelos.EvaluacionObjetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionObjetivoDAO extends JpaRepository<EvaluacionObjetivo, Integer> {

	@Query ("select eo from EvaluacionObjetivo eo where idEvaluacion = " +
			"(select idEvaluacion from Evaluacion e where e.ficha = ?1 and" +
			" e.idEvaluacionSecundario = ?2) ")
	public List<EvaluacionObjetivo> buscarObjetivos (String ficha, Integer evaluacion);
	
}
