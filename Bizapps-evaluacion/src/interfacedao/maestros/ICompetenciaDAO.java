package interfacedao.maestros;

import java.util.List;
import modelos.Competencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICompetenciaDAO extends JpaRepository<Competencia, Integer> {
	
	
	 @Query("select a from Competencia a where a.nivel = 'RECTORAS'")
		public List<Competencia> buscar();
	 
	 public Competencia findByIdCompetencia(int id);
}
	
