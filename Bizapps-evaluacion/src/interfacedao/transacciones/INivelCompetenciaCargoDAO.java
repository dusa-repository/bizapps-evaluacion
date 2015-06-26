package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.pk.NivelCompetenciaCargoPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface INivelCompetenciaCargoDAO extends JpaRepository<NivelCompetenciaCargo, NivelCompetenciaCargoPK> {
	
	public List<NivelCompetenciaCargo> findByIdCargo(Cargo cargo);
	
	public NivelCompetenciaCargo findByIdDominio(Dominio dominio);
	
	
}
