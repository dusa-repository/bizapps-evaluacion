package interfacedao.transacciones;

import java.util.List;
import modelos.Evaluacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionDAO extends JpaRepository<Evaluacion, Long> {
	
	
	public List<Evaluacion> findByFicha(String ficha);
	
	@Query("select max(idEvaluacion) from Evaluacion")
	public Integer buscar();

	public Evaluacion findByIdEvaluacionSecundarioAndFicha (Integer id, String ficha); 
	
	public Evaluacion findByIdEvaluacion (Integer idEvaluacion);
}
