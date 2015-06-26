package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Clase;
import modelo.maestros.EmpleadoClase;
import modelo.pk.EmpleadoClasePK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoClaseDAO extends JpaRepository<EmpleadoClase, EmpleadoClasePK> {

	public List<EmpleadoClase> findByIdClase(Clase clase);

	
}