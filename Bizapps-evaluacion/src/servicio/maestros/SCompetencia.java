package servicio.maestros;

import interfacedao.maestros.ICompetenciaDAO;

import java.util.List;

import modelo.maestros.Competencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCompetencia")
public class SCompetencia {

	@Autowired
	private ICompetenciaDAO competenciaDAO;

	/* Servicio que permite guardar los datos de una competencia */
	public void guardar(Competencia competencia) {
		competenciaDAO.save(competencia);
	}

	/* Servicio que permite buscar todos las competencias */
	public List<Competencia> buscarTodas() {
		return competenciaDAO.findAll();
	}

	/* Servicio que permite eliminar una competencia */
	public void eliminarUnaCompetencia(int id) {
		competenciaDAO.delete(id);
	}
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public Competencia buscarPorNombre(String descripcion) {
		Competencia competencia;
		competencia = competenciaDAO.findByDescripcion(descripcion);
		return competencia;
	}

	/* Servicio que permite eliminar varias competencias */
	public void eliminarVariasCompetencias(List<Competencia> eliminar) {
		competenciaDAO.delete(eliminar);
	}

	public List<Competencia> buscarCompetenciasR() {
		return competenciaDAO.buscar();
	}

	public Competencia buscarCompetencia(int id) {
		// TODO Auto-generated method stub
		Competencia competencia;
		competencia = competenciaDAO.findById(id);
		return competencia;
	}

	/*
	 * Servicio que permite filtrar las competencias de una lista de acuerdo al
	 * id
	 */
	public List<Competencia> filtroId(String valor) {
		return competenciaDAO.findByIdStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar las competencias de una lista de acuerdo a
	 * la descripcion
	 */
	public List<Competencia> filtroDescripcion(String valor) {
		return competenciaDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar las competencias de una lista de acuerdo al
	 * nivel
	 */
	public List<Competencia> filtroNivel(String valor) {
		return competenciaDAO.findByNivelStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las competencias de una lista de acuerdo al
	 * comentario
	 */
	public List<Competencia> filtroComentario(String valor) {
		return competenciaDAO.findByComentarioStartingWithAllIgnoreCase(valor);
	}
	
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public List<Competencia> buscarPorNombres(String descripcion) {
		List<Competencia> competencia;
		competencia = competenciaDAO.findByDescripcionAllIgnoreCase(descripcion);
		return competencia;
	}

}
