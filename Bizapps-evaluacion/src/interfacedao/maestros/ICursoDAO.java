package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICursoDAO extends JpaRepository<Curso, Integer> {

	Curso findByNombre(String nombre);

	public List<Curso> findByNombreStartingWithAllIgnoreCase(String valor);

	public List<Curso> findByDuracionStartingWithAllIgnoreCase(String valor);

	public List<Curso> findByEstadoStartingWithAllIgnoreCase(String valor);

	public List<Curso> findByAreaDescripcionStartingWithAllIgnoreCase(
			String valor);


}