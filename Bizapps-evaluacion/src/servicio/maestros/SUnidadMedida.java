package servicio.maestros;

import interfacedao.maestros.IUnidadMedidaDAO;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Perspectiva;
import modelo.maestros.UnidadMedida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUnidadMedida")
public class SUnidadMedida {

	@Autowired
	private IUnidadMedidaDAO unidadMedidaDAO;

	/* Servicio que permite guardar los datos de una unidad de medida*/
	public void guardar(UnidadMedida unidad) {
		unidadMedidaDAO.save(unidad);
	}

	/* Servicio que permite buscar una unidadd de medida de acuerdo al id */
	public UnidadMedida buscarUnidad(int id) {
		return unidadMedidaDAO.findOne(id);
	}
	
	/* Servicio que permite buscar una unidad de medida de acuerdo al nombre */
	public UnidadMedida buscarPorNombre(String descripcion) {
		UnidadMedida unidad;
		unidad = unidadMedidaDAO.findByDescripcion(descripcion);
		return unidad;
	}
	
	/* Servicio que permite buscar todas las unidades de medida */
	public List<UnidadMedida> buscarTodas() {
		return unidadMedidaDAO.findAll();
	}
	
	/* Servicio que permite eliminar una unidad de medida */
	public void eliminarUnaUnidad(int id) {
		unidadMedidaDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias areas */
	public void eliminarVariasUnidades(List<UnidadMedida> eliminar) {
		unidadMedidaDAO.delete(eliminar);
	}
	

	public List<UnidadMedida> buscar() {
		return unidadMedidaDAO.findAll();
	}


}