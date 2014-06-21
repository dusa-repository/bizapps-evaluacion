package servicio.maestros;

import interfacedao.maestros.IDominioDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Cargo;
import modelo.maestros.Dominio;
import modelo.maestros.NivelCompetenciaCargo;
import modelo.maestros.Revision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDominio")
public class SDominio {

	@Autowired
	private IDominioDAO dominioDAO;

	/* Servicio que permite guardar los datos de un dominio*/
	public void guardar(Dominio dominio) {
		dominioDAO.save(dominio);
	}

	/* Servicio que permite buscar un dominio de acuerdo al id */
	public Dominio buscarDominio(int id) {
		return dominioDAO.findOne(id);
	}
	
	/* Servicio que permite buscar un dominio de acuerdo al nombre */
	public Dominio buscarPorNombre(String descripcion) {
		Dominio dominio;
		dominio = dominioDAO.findByDescripcionDominio(descripcion);
		return dominio;
	}
	
	/* Servicio que permite buscar todos los dominios */
	public List<Dominio> buscarTodos() {
		return dominioDAO.findAll();
	}
	
	/* Servicio que permite buscar los dominios de acuerdo a un tipo */

	public List<Dominio> buscarPorTipo(String tipo) {
		return dominioDAO.findByTipo(tipo);
	}
	
	/* Servicio que permite eliminar un dominio */
	public void eliminarUnDominio(int id) {
		dominioDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varios dominios */
	public void eliminarVariosDominios(List<Dominio> eliminar) {
		dominioDAO.delete(eliminar);
	}
	

}