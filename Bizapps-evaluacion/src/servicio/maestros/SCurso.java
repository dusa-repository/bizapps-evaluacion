package servicio.maestros;

import interfacedao.maestros.ICursoDAO;
import interfacedao.maestros.IAreaDAO;

import java.util.List;

import modelo.maestros.Curso;
import modelo.seguridad.Grupo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCurso")
public class SCurso {

	@Autowired
	private ICursoDAO cursoDAO;

	/* Servicio que permite guardar los datos de un curso */
	public void guardar(Curso curso) {
		cursoDAO.save(curso);
	}

	/* Servicio que permite buscar un curso de acuerdo al id */
	public Curso buscarCurso(int id) {
		return cursoDAO.findOne(id);
	}

	/* Servicio que permite buscar un curso de acuerdo al nombre */
	public Curso buscarPorNombre(String nombre) {
		Curso curso;
		curso = cursoDAO.findByNombre(nombre);
		return curso;
	}

	/* Servicio que permite buscar todos las cursos */
	public List<Curso> buscarTodos() {
		return cursoDAO.findAll();
	}

	/* Servicio que permite eliminar una curso */
	public void eliminarUnCurso(int id) {
		cursoDAO.delete(id);
	}

	/* Servicio que permite eliminar varios cursos */
	public void eliminarVariosCursos(List<Curso> eliminar) {
		cursoDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar las cursoes de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Curso> filtroNombre(String valor) {
		return cursoDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar las cursoes de una lista de acuerdo a las
	 * horas
	 */
	public List<Curso> filtroDuracion(String valor) {
		return cursoDAO.findByDuracionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar las cursoes de una lista de acuerdo al
	 * estado
	 */
	public List<Curso> filtroEstado(String valor) {
		return cursoDAO.findByEstadoStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar las cursoes de una lista de acuerdo al area
	 */
	public List<Curso> filtroArea(String valor) {
		return cursoDAO.findByAreaDescripcionStartingWithAllIgnoreCase(valor);
	}

}
