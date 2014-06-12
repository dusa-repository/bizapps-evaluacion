package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelo.pk.NivelCompetenciaCargoPK;
import modelos.Cargo;
import modelos.Competencia;
import modelos.Empleado;
import modelos.Evaluacion;
import modelos.NivelCompetenciaCargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface INivelCompetenciaCargoDAO extends JpaRepository<NivelCompetenciaCargo, NivelCompetenciaCargoPK> {
	
	public List<NivelCompetenciaCargo> findByCargo(Cargo cargo);
}
