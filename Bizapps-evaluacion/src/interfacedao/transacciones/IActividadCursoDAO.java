package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.ActividadCurso;
import modelo.maestros.Curso;
import modelo.pk.ActividadCursoPK;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IActividadCursoDAO extends JpaRepository<ActividadCurso, ActividadCursoPK> {

	public List<ActividadCurso> findByCurso(Curso curso);
	
}

