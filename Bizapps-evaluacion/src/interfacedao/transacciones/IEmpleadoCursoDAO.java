package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;
import modelo.pk.EmpleadoCursoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEmpleadoCursoDAO extends
		JpaRepository<EmpleadoCurso, EmpleadoCursoPK> {

	public List<EmpleadoCurso> findByCurso(Curso curso);

	public List<EmpleadoCurso> findByEmpleado(Empleado empleado);

	public EmpleadoCurso findByEmpleadoAndCurso(Empleado empleado, Curso curso);

	public List<EmpleadoCurso> findByEmpleadoAndCursoIn(Empleado empleado,
			List<Curso> procesadas);

	@Query("select coalesce(sum(em.curso.duracion), '0') from EmpleadoCurso em where em.empleado = ?1")
	public double sumHours(Empleado trabajador);

	public List<EmpleadoCurso> findByEmpleadoAndAsistioAndEstadoCursoLike(
			Empleado empleado, String string, String string2);

}