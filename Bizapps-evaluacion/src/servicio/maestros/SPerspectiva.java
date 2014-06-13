package servicio.maestros;

import interfacedao.maestros.IPerspectivaDAO;
import java.util.List;
import modelos.Perspectiva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPerspectiva")
public class SPerspectiva {

	@Autowired
	private IPerspectivaDAO perspectivaDAO;

	public List<Perspectiva> buscar() {
		return perspectivaDAO.findAll();
	}
	
	public Perspectiva buscarId(Integer value) {
		return perspectivaDAO.findByIdPerspectiva(value);
	}
}
