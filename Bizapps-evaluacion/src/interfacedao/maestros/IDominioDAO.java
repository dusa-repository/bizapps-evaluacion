package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDominioDAO extends JpaRepository<Dominio, Integer> {

	Dominio findByDescripcionDominio(String descripcion);

	public List<Dominio> findByTipo(String tipo);


	
}
