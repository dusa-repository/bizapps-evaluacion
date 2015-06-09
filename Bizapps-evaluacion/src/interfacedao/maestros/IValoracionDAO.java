package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Valoracion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IValoracionDAO extends JpaRepository<Valoracion, Integer> {

	public Valoracion findByNombre(String nombre);

	public List<Valoracion> findByNombreStartingWithAllIgnoreCase(String valor);

	public List<Valoracion> findByDescripcionStartingWithAllIgnoreCase(
			String valor);

	public List<Valoracion> findByOrdenStartingWithAllIgnoreCase(String valor);
	
	public List<Valoracion> findByRangoInferiorStartingWithAllIgnoreCase(String valor);

	public List<Valoracion> findByRangoSuperiorStartingWithAllIgnoreCase(
			String valor);

	public List<Valoracion> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Valoracion> findByValorStartingWithAllIgnoreCase(String valor);


	
}