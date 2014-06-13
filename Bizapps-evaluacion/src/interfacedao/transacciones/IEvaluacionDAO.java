package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelos.Empleado;
import modelos.Evaluacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEvaluacionDAO extends JpaRepository<Evaluacion, Long> {
	
	
	public List<Evaluacion> findByFicha(String ficha);
	
	 @Query("select max(idEvaluacion) from Evaluacion")
		public Integer buscar();

}
