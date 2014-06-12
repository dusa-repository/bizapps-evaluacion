package interfacedao.maestros;

import java.util.ArrayList;
import java.util.List;

import modelos.Competencia;
import modelos.Empleado;
import modelos.Evaluacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICompetenciaDAO extends JpaRepository<Competencia, Long> {
	
	
	 @Query("select a from Competencia a where a.nivel = 'RECTORAS'")
		public List<Competencia> buscar();
}
