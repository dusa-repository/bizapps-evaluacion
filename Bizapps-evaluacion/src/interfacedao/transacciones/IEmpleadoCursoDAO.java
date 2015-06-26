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

	public List<EmpleadoCurso> findByIdCurso(Curso curso);

	public List<EmpleadoCurso> findByIdEmpleado(Empleado empleado);

	public EmpleadoCurso findByIdEmpleadoAndIdCurso(Empleado empleado, Curso curso);

	public List<EmpleadoCurso> findByIdEmpleadoAndIdCursoIn(Empleado empleado,
			List<Curso> procesadas);

	@Query("select coalesce(sum(em.id.curso.duracion), '0') from EmpleadoCurso em where em.id.empleado = ?1")
	public double sumHours(Empleado trabajador);

	public List<EmpleadoCurso> findByIdEmpleadoAndAsistioAndEstadoCursoLike(
			Empleado empleado, String string, String string2);

}