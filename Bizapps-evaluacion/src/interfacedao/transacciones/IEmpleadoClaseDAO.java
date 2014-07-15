package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Clase;
import modelo.maestros.EmpleadoClase;
import modelo.pk.EmpleadoClasePK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoClaseDAO extends JpaRepository<EmpleadoClase, EmpleadoClasePK> {

	public List<EmpleadoClase> findByClase(Clase clase);

	
}