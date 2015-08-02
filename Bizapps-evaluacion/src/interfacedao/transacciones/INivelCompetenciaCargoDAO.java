package interfacedao.transacciones;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.pk.NivelCompetenciaCargoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface INivelCompetenciaCargoDAO extends JpaRepository<NivelCompetenciaCargo, NivelCompetenciaCargoPK> {
	
	public List<NivelCompetenciaCargo> findByIdCargo(Cargo cargo);
	
	public NivelCompetenciaCargo findByIdDominio(Dominio dominio);
		
	
	
}
