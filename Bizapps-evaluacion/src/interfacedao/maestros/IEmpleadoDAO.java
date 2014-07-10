package interfacedao.maestros;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmpleadoDAO extends JpaRepository<Empleado, Integer> {
	
	 public Empleado findByNombre(String nombre);


	 @Query("select a from Empleado a where a.fichaSupervisor = ?1")
		public List<Empleado> buscar(ArrayList<Long> ids);


	public Empleado findByFicha(String ficha);


	public List<Empleado> findByFichaSupervisor(String ficha);


	public List<Empleado> findByIdStartingWithAllIgnoreCase(String valor);


	public List<Empleado> findByEmpresaStartingWithAllIgnoreCase(String valor);


	public List<Empleado> findByCargoStartingWithAllIgnoreCase(String valor);


	public List<Empleado> findByUnidadOrganizativaStartingWithAllIgnoreCase(
			String valor);


	public List<Empleado> findByNombreStartingWithAllIgnoreCase(String valor);


	public List<Empleado> findByFichaStartingWithAllIgnoreCase(String valor);


	public List<Empleado> findByFichaSupervisorStartingWithAllIgnoreCase(
			String valor);


	public List<Empleado> findByGradoAuxiliarStartingWithAllIgnoreCase(
			String valor);


	public List<Empleado> findByNombreAllIgnoreCase(String descripcion);
}
