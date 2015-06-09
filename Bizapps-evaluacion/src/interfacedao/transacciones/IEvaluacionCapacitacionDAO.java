package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.EvaluacionCapacitacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEvaluacionCapacitacionDAO extends JpaRepository<EvaluacionCapacitacion, Integer> {


	List<EvaluacionCapacitacion> findByIdEvaluacion(Integer idEva);
	

}
