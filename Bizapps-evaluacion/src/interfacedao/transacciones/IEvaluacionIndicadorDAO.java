package interfacedao.transacciones;


import java.util.List;

import modelo.maestros.EvaluacionIndicador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionIndicadorDAO extends JpaRepository<EvaluacionIndicador, Integer> {

	List <EvaluacionIndicador> findByIdObjetivo (Integer idObjetivo);
	
	EvaluacionIndicador findByIdIndicador(Integer idIndicador);
	
	@Query("select max(idIndicador) from EvaluacionIndicador")
	public Integer buscar();

	
}