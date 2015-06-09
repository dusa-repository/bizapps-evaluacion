package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Parametro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IParametroDAO extends JpaRepository<Parametro, Integer> {

	Parametro findByDescripcion(String descripcion);

	public List<Parametro> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Parametro> findByTipoStartingWithAllIgnoreCase(String valor);

	public List<Parametro> findByTipo(String tipo);


}