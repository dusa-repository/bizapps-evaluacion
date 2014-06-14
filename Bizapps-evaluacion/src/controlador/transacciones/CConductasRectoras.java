package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.seguridad.Usuario;
import modelos.Competencia;
import modelos.ConductaCompetencia;
import modelos.Dominio;
import modelos.Evaluacion;
import modelos.NivelCompetenciaCargo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import controlador.maestros.CGenerico;
import componentes.Mensaje;

public class CConductasRectoras extends CGenerico {

	private static final long serialVersionUID = -5393608637902961029L;
	Mensaje msj = new Mensaje();

	@Wire
	private Listbox lbxConductasRectoras;
	@Wire
	private Panel panConductas;
	@Wire
	private Window wdwConductasRectoras;

	@Override
	public void inicializar() throws IOException {
		
		//Selectors.wireComponents(Component comp, this, false);
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				String dominio1 = (String) (map.get("idnivel"));
				int idDominio = Integer.parseInt(dominio1);			
				int idCompetencia = (Integer) map.get("id");
				Competencia competencia = servicioCompetencia.buscarCompetencia(idCompetencia);				
				String titulo=wdwConductasRectoras.getTitle();
				wdwConductasRectoras.setTitle(titulo+ "-"+ competencia.getDescripcion());
				Dominio dominio = servicioDominio.buscarDominio(idDominio);				
				Authentication auth = SecurityContextHolder.getContext()
						.getAuthentication();
				Usuario u = servicioUsuario.buscarUsuarioPorNombre(auth.getName());
				String ficha = u.getCedula();
				Integer numeroEvaluacion = servicioEvaluacion.buscar(ficha).size();
				Evaluacion evaluacion = servicioEvaluacion.buscarIdEvaluacion(numeroEvaluacion, ficha);	
				List<ConductaCompetencia> conductas = new ArrayList<ConductaCompetencia>();
				conductas = servicioConductaCompetencia.buscarConductaCompetencias(idCompetencia);
				lbxConductasRectoras.setMultiple(true);
				lbxConductasRectoras.setMultiple(true);
				lbxConductasRectoras.setMultiple(true);
				lbxConductasRectoras.setCheckmark(false);
				lbxConductasRectoras.setMultiple(true);				
				lbxConductasRectoras.setCheckmark(true);
				lbxConductasRectoras.setModel(new ListModelList<ConductaCompetencia>(
						conductas));
				
				
				
			}
		}

}
}