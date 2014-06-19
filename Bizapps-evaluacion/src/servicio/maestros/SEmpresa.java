package servicio.maestros;

import interfacedao.maestros.IEmpresaDAO;

import java.util.List;

import modelos.Area;
import modelos.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEmpresa")
public class SEmpresa {

	@Autowired
	private IEmpresaDAO empresaDAO;

	/* Servicio que permite guardar los datos de una empresa*/
	public void guardar(Empresa empresa) {
		empresaDAO.save(empresa);
	}

	/* Servicio que permite buscar una empresa de acuerdo al id */
	public Empresa buscarEmpresa(int id) {
		return empresaDAO.findOne(id);
	}
	
	/* Servicio que permite buscar una empresa de acuerdo al nombre */
	public Empresa buscarPorNombre(String nombre) {
		Empresa empresa;
		empresa = empresaDAO.findByNombre(nombre);
		return empresa;
	}
	
	/* Servicio que permite buscar todas las empresas */
	public List<Empresa> buscarTodas() {
		return empresaDAO.findAll();
	}
	
	/* Servicio que permite eliminar una empresa */
	public void eliminarUnaEmpresa(int id) {
		empresaDAO.delete(id);
	}
	
	/* Servicio que permite eliminar varias empresas */
	public void eliminarVariasEmpresas(List<Empresa> eliminar) {
		empresaDAO.delete(eliminar);
	}
	

}
