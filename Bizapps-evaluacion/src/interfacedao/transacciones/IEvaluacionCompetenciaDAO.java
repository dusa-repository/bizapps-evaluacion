package interfacedao.transacciones;


import java.util.List;

import modelo.maestros.EvaluacionCompetencia;
import modelo.maestros.EvaluacionObjetivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionCompetenciaDAO extends JpaRepository<EvaluacionCompetencia, Integer> {

	
}
