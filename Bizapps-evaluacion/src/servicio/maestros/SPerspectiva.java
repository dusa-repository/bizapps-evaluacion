package servicio.maestros;

import interfacedao.maestros.IPerspectivaDAO;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Dominio;
import modelo.maestros.Perspectiva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPerspectiva")
public class SPerspectiva {

	@Autowired
	private IPerspectivaDAO perspectivaDAO;

	/* Servicio que permite guardar los datos de una perspectiva*/
	public void guardar(Perspectiva perspectiva) {
		perspectivaDAO.save(perspectiva);
	}
	
	public List<Perspectiva> buscar() {
		return perspectivaDAO.findAll();
	}
	
	public Perspectiva buscarId(Integer value) {
		return perspectivaDAO.findById(value);
	}
	
	public Perspectiva buscarNombre(String value) {
		return perspectivaDAO.findByDescripcion(value);
	}
	
	/* Servicio que permite eliminar una perspectiva */
	public void eliminarUnaPerspectiva(int id) {
		perspectivaDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias perspectivas */
	public void eliminarVariasPerspectivas(List<Perspectiva> eliminar) {
		perspectivaDAO.delete(eliminar);
	}
	
	
	/*
	 * Servicio que permite filtrar las perspectivas de una lista de acuerdo al
	 * id
	 */
	public List<Perspectiva> filtroId(String valor) {
		return perspectivaDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	
	/*
	 * Servicio que permite filtrar las perspectivas de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Perspectiva> filtroDescripcion(String valor) {
		return perspectivaDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	
	
}
