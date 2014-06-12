package interfacedao.maestros;


import java.util.List;

import modelos.Revision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRevisionDAO extends JpaRepository<Revision, Integer> {

	Revision findByDescripcion(String descripcion);
	
	public List<Revision> findByEstadoRevision(String estado);

	

	
}
