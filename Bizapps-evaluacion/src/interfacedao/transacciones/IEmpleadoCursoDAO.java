package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.EmpleadoCurso;
import modelo.pk.EmpleadoCursoPK;
import modelo.pk.NivelCompetenciaCargoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoCursoDAO extends JpaRepository<EmpleadoCurso, EmpleadoCursoPK> {

	public List<EmpleadoCurso> findByCurso(Curso curso);
	
}