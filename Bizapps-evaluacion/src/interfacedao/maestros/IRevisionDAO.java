package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Periodo;
import modelo.maestros.Revision;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRevisionDAO extends JpaRepository<Revision, Integer> {

	Revision findByDescripcion(String descripcion);
	
	//public List<Revision> findByEstadoRevision(String estado);
	public Revision findByEstadoRevision(String estado);
	public List<Revision> findByIdStartingWithAllIgnoreCase(String valor);

	public List<Revision> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Revision> findByEstadoRevisionStartingWithAllIgnoreCase(String valor);

	Revision findByPeriodoAndEstadoRevision(Periodo periodo, String estadoRevision);

	public List<Revision> findByPeriodoDescripcionStartingWithAllIgnoreCase(
			String valor);

	

	
}
