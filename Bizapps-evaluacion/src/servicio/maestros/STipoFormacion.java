package servicio.maestros;

import interfacedao.maestros.ICargoDAO;
import interfacedao.maestros.ITipoFormacionDAO;

import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.TipoFormacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("STipoFormacion")
public class STipoFormacion {

	@Autowired
	private ITipoFormacionDAO tipoFormacionDAO;

	/* Servicio que permite guardar los datos de un tipo de formacion*/
	public void guardar(TipoFormacion tipo) {
		tipoFormacionDAO.save(tipo);
	}

	/* Servicio que permite buscar un tipo de formacion de acuerdo al id */
	public TipoFormacion buscarTipoFormacion(int id) {
		return tipoFormacionDAO.findOne(id);
	}
	
	/* Servicio que permite buscar un tipo de formacion de acuerdo al nombre */
	public TipoFormacion buscarPorNombre(String descripcion) {
		TipoFormacion tipo;
		tipo = tipoFormacionDAO.findByDescripcion(descripcion);
		return tipo;
	}
	
	/* Servicio que permite buscar todos los tipos de formacion */
	public List<TipoFormacion> buscarTodos() {
		return tipoFormacionDAO.findAll();
	}
	
	/* Servicio que permite eliminar un tipo de formacion */
	public void eliminarUnTipo(int id) {
		tipoFormacionDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varios tipos de formacion*/
	public void eliminarVariosTipos(List<TipoFormacion> eliminar) {
		tipoFormacionDAO.delete(eliminar);
	}
	

}