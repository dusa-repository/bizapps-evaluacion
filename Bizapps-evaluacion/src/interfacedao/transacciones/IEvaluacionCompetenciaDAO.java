package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Competencia;
import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionCompetencia;
import modelo.pk.EvaluacionCompetenciaPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEvaluacionCompetenciaDAO extends
		JpaRepository<EvaluacionCompetencia, EvaluacionCompetenciaPK> {
	
	//@Query("select e from EvaluacionCompetencia e where e.estadoEvaluacion <> 'EN EDICION' and e.ficha = ?1")
	public EvaluacionCompetencia findByEvaluacionAndCompetencia (Evaluacion evaluacion, Competencia competencia);
	public List <EvaluacionCompetencia> findByEvaluacion (Evaluacion evaluacion);

}
