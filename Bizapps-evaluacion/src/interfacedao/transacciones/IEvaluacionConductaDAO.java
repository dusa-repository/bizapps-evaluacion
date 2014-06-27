package interfacedao.transacciones;


import java.util.List;

import modelo.maestros.Evaluacion;
import modelo.maestros.EvaluacionConducta;
import modelo.maestros.EvaluacionObjetivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionConductaDAO extends JpaRepository<EvaluacionConducta, Integer> {
	
	public List <EvaluacionConducta> findByEvaluacionIdEvaluacion (Integer id);



}
