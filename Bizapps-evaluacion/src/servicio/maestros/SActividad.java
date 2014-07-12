package servicio.maestros;

import interfacedao.maestros.IActividadDAO;
import interfacedao.maestros.IAreaDAO;

import java.util.List;

import modelo.maestros.Actividad;
import modelo.seguridad.Grupo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SActividad")
public class SActividad {

	@Autowired
	private IActividadDAO actividadDAO;

	/* Servicio que permite guardar los datos de una actividad */
	public void guardar(Actividad actividad) {
		actividadDAO.save(actividad);
	}

	/* Servicio que permite buscar una actividad de acuerdo al id */
	public Actividad buscarActividad(int id) {
		return actividadDAO.findOne(id);
	}

	/* Servicio que permite buscar una actividad de acuerdo al nombre */
	public Actividad buscarPorNombre(String descripcion) {
		Actividad actividad;
		actividad = actividadDAO.findByDescripcion(descripcion);
		return actividad;
	}

	/* Servicio que permite buscar todos las actividades */
	public List<Actividad> buscarTodas() {
		return actividadDAO.findAll();
	}

	/* Servicio que permite eliminar una actividad */
	public void eliminarUnaActividad(int id) {
		actividadDAO.delete(id);
	}

	/* Servicio que permite eliminar varias actividades */
	public void eliminarVariasActividades(List<Actividad> eliminar) {
		actividadDAO.delete(eliminar);
	}

	/*
	 * Servicio que permite filtrar las actividades de una lista de acuerdo a la
	 * descripcion
	 */
	public List<Actividad> filtroNombre(String valor) {
		return actividadDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
	

}
