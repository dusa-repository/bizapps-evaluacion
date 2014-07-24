package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoParametro;
import modelo.pk.EmpleadoParametroPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoParametroDAO extends JpaRepository<EmpleadoParametro, EmpleadoParametroPK> {

	public List<EmpleadoParametro> findByEmpleadoAndCurso(Empleado empleado,
			Curso curso);

	
}
