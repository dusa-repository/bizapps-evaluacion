package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoParametro;
import modelo.pk.EmpleadoParametroPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoParametroDAO extends JpaRepository<EmpleadoParametro, EmpleadoParametroPK> {

	public List<EmpleadoParametro> findByIdEmpleadoAndIdCurso(Empleado empleado,
			Curso curso);

	
}
