package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.Dominio;
import modelo.maestros.Empleado;
import modelo.maestros.Evaluacion;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.pk.NivelCompetenciaCargoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface INivelCompetenciaCargoDAO extends JpaRepository<NivelCompetenciaCargo, NivelCompetenciaCargoPK> {
	
	public List<NivelCompetenciaCargo> findByCargo(Cargo cargo);
	
	public NivelCompetenciaCargo findByDominio(Dominio dominio);
	
	
}
