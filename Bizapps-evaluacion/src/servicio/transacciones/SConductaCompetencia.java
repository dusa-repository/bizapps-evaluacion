package servicio.transacciones;


import interfacedao.maestros.ICompetenciaDAO;
import interfacedao.transacciones.IConductaCompetenciaDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Competencia;
import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConductaCompetencia")
public class SConductaCompetencia {

	@Autowired
	private IConductaCompetenciaDAO conductaCompetenciaDAO;

	
	/* Servicio que permite guardar los datos de una conducta*/
	public void guardar(ConductaCompetencia conducta) {
		conductaCompetenciaDAO.save(conducta);
	}

	/* Servicio que permite buscar una conducta de acuerdo al id */
	public ConductaCompetencia buscarConducta(int id) {
		return conductaCompetenciaDAO.findOne(id);
	}

	/* Servicio que permite buscar un conducta de acuerdo al nombre */
	public ConductaCompetencia buscarPorDescripcion(String descripcion) {
		ConductaCompetencia conducta;
		conducta = conductaCompetenciaDAO.findByDescripcion(descripcion);
		return conducta;
	}

	/* Servicio que permite buscar todos las conductas */
	public List<ConductaCompetencia> buscarTodas() {
		return conductaCompetenciaDAO.findAll();
	}

	/* Servicio que permite eliminar una conducta */
	public void eliminarUnaConducta(int id) {
		conductaCompetenciaDAO.delete(id);
	}

	/* Servicio que permite eliminar varias conductas */
	public void eliminarVariasConductas(List<ConductaCompetencia> eliminar) {
		conductaCompetenciaDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar las conductas de una lista de acuerdo al
	 * id
	 */
	public List<ConductaCompetencia> filtroId(String valor) {
		return conductaCompetenciaDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las conductas de una lista de acuerdo a la
	 * competencia
	 */
	public List<ConductaCompetencia> filtroCompetencia(String valor) {
		return conductaCompetenciaDAO.findByCompetenciaDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las conductas de una lista de acuerdo al
	 * dominio
	 */
	public List<ConductaCompetencia> filtroDominio(String valor) {
		return conductaCompetenciaDAO.findByDominioDescripcionDominioStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las conductas de una lista de acuerdo a la
	 * descripcion
	 */
	public List<ConductaCompetencia> filtroDescripcion(String valor) {
		return conductaCompetenciaDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las conductas de una lista de acuerdo al
	 * orden
	 */
	public List<ConductaCompetencia> filtroOrden(String valor) {
		return conductaCompetenciaDAO.findByOrdenStartingWithAllIgnoreCase(valor);
	}
	
	
	public List<ConductaCompetencia> buscarConductaCompetencias(Competencia id) {
		return conductaCompetenciaDAO.findByCompetencia(id);
	}
	
	public List<ConductaCompetencia> buscarConductaCompetenciasDominio(Competencia id,Dominio id2 ) {
		return conductaCompetenciaDAO.findByCompetenciaAndDominio(id, id2);
	}

}
