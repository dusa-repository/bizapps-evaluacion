package servicio.maestros;

import interfacedao.maestros.IItemEvaluacionDAO;

import java.util.List;

import modelo.maestros.ItemEvaluacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemEvaluacion")
public class SItemEvaluacion {

	@Autowired
	private IItemEvaluacionDAO itemEvaluacionDAO;

	/* Servicio que permite guardar los datos de un item */
	public void guardar(ItemEvaluacion item) {
		itemEvaluacionDAO.save(item);
	}

	/* Servicio que permite buscar un item de acuerdo al id */
	public ItemEvaluacion buscarItem(int id) {
		return itemEvaluacionDAO.findOne(id);
	}

	/* Servicio que permite buscar un item de acuerdo al nombre */
	public ItemEvaluacion buscarPorNombre(String descripcion) {
		ItemEvaluacion item;
		item = itemEvaluacionDAO.findByDescripcion(descripcion);
		return item;
	}
	
	/* Servicio que permite buscar un item de acuerdo al tipo de ponderacion */
	public List<ItemEvaluacion> buscarPorTipoPonderacion(String tipoPonderacion) {
		List<ItemEvaluacion> items;
		items = itemEvaluacionDAO.findByPonderacion(tipoPonderacion);
		return items;
	}

	/* Servicio que permite buscar todos los items */
	public List<ItemEvaluacion> buscarTodos() {
		return itemEvaluacionDAO.findAll();
	}

	/* Servicio que permite eliminar un item */
	public void eliminarUnItem(int id) {
		itemEvaluacionDAO.delete(id);
	}

	/* Servicio que permite eliminar varios items */
	public void eliminarVariosItems(List<ItemEvaluacion> eliminar) {
		itemEvaluacionDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar los items de una lista de acuerdo a la
	 * descripcion
	 */
	public List<ItemEvaluacion> filtroNombre(String valor) {
		return itemEvaluacionDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	/*
	 * Servicio que permite filtrar los items de una lista de acuerdo al
	 * tipo de ponderacion
	 */ 
	public List<ItemEvaluacion> filtroTipoPonderacion(String valor) {
		return itemEvaluacionDAO.findByPonderacionStartingWithAllIgnoreCase(valor);
	}

}

