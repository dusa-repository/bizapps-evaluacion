package servicio.maestros;


import interfacedao.maestros.ICompetenciaDAO;
import java.util.List;
import modelos.Competencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCompetencia")
public class SCompetencia {

	@Autowired
	private ICompetenciaDAO competenciaDAO;

	public List<Competencia> buscarCompetenciasR() {
		return competenciaDAO.buscar();
	}
	
	public Competencia buscarCompetencia(int id) {
		// TODO Auto-generated method stub
		Competencia competencia;
		competencia = competenciaDAO.findByIdCompetencia(id);
		return competencia;
	}

}
