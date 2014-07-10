package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICargoDAO extends JpaRepository<Cargo, Integer> {

	public List<Cargo> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Cargo> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Cargo> findByNominaStartingWithAllIgnoreCase(String valor);

	public List<Cargo> findByIdCargoAuxiliarStartingWithAllIgnoreCase(String valor);

	public List<Cargo> findByIdEmpresaAuxiliarStartingWithAllIgnoreCase(String valor);

	public List<Cargo> findByDescripcionAllIgnoreCase(String descripcion);

	public Cargo findByDescripcion(String descripcion);


	
}
