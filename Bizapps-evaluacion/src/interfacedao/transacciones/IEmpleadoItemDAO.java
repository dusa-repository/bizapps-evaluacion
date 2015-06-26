package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoItem;
import modelo.pk.EmpleadoItemPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoItemDAO extends JpaRepository<EmpleadoItem, EmpleadoItemPK> {

	public List<EmpleadoItem> findByIdEmpleadoAndIdCurso(Empleado empleado, Curso curso);

	

	
}
