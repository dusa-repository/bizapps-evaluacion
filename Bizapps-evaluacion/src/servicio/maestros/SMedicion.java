package servicio.maestros;

import interfacedao.maestros.IMedicionDAO;
import java.util.List;

import modelo.maestros.Area;
import modelo.maestros.Medicion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SMedicion")
public class SMedicion {

	@Autowired
	private IMedicionDAO medicionDAO;

	/* Servicio que permite guardar los datos de una medicion*/
	public void guardar(Medicion medicion) {
		medicionDAO.save(medicion);
	}

	/* Servicio que permite buscar una medicion de acuerdo al id */
	public Medicion buscarMedicion(int id) {
		return medicionDAO.findOne(id);
	}
	
	/* Servicio que permite buscar una medicion de acuerdo al nombre */
	public Medicion buscarPorNombre(String descripcion) {
		Medicion medicion;
		medicion = medicionDAO.findByDescripcionMedicion(descripcion);
		return medicion;
	}
	
	/* Servicio que permite buscar todas las mediciones*/
	public List<Medicion> buscarTodas() {
		return medicionDAO.findAll();
	}
	
	/* Servicio que permite eliminar una medicion */
	public void eliminarUnaMedicion(int id) {
		medicionDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias mediciones */
	public void eliminarVariasMediciones(List<Medicion> eliminar) {
		medicionDAO.delete(eliminar);
	}
	
	
	public List<Medicion> buscar() {
		return medicionDAO.findAll();
	}

}
