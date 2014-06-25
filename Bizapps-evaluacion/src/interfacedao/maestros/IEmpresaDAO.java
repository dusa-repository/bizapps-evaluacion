package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEmpresaDAO extends JpaRepository<Empresa, Integer> {

	Empresa findByNombre(String nombre);

	public List<Empresa> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Empresa> findByNombreStartingWithAllIgnoreCase(String valor);

	public List<Empresa> findByDireccionStartingWithAllIgnoreCase(String valor);

	public List<Empresa> findByTelefono1StartingWithAllIgnoreCase(String valor);

	public List<Empresa> findByTelefono2StartingWithAllIgnoreCase(String valor);

	public List<Empresa> findByIdEmpresaAuxiliarStartingWithAllIgnoreCase(String valor);


	
}