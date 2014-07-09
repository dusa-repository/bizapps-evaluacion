package servicio.transacciones;


import interfacedao.transacciones.IBitacoraDAO;
import modelo.maestros.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacora")
public class SBitacora {

	@Autowired
	private IBitacoraDAO bitacoraDAO;

	public void guardar(Bitacora bitacora) {
		System.out.println("save"+bitacora.getEvaluacion().getIdEvaluacion());
		bitacoraDAO.save(bitacora);
	}
}
 
