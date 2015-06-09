package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.ActividadCurso;
import modelo.maestros.Curso;
import modelo.pk.ActividadCursoPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IActividadCursoDAO extends JpaRepository<ActividadCurso, ActividadCursoPK> {

	public List<ActividadCurso> findByCurso(Curso curso);
	
}

