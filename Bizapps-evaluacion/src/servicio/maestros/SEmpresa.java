package servicio.maestros;

import interfacedao.maestros.IEmpresaDAO;

import java.util.List;

import modelo.maestros.Empresa;

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
	
	
	/*
	 * Servicio que permite filtrar las empresas de una lista de acuerdo al
	 * id
	 */
	public List<Empresa> filtroId(String valor) {
		return empresaDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	
	/*
	 * Servicio que permite filtrar las empresas de una lista de acuerdo al
	 * nombre
	 */
	public List<Empresa> filtroNombre(String valor) {
		return empresaDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las empresas de una lista de acuerdo a la
	 * direccion
	 */
	public List<Empresa> filtroDireccion(String valor) {
		return empresaDAO.findByDireccionStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las empresas de una lista de acuerdo al
	 * telefono 1
	 */
	public List<Empresa> filtroTelefono1(String valor) {
		return empresaDAO.findByTelefono1StartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las empresas de una lista de acuerdo al
	 * telefono 2
	 */
	public List<Empresa> filtroTelefono2(String valor) {
		return empresaDAO.findByTelefono2StartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar las empresas de una lista de acuerdo a la
	 * empresa auxiliar
	 */
	public List<Empresa> filtroEmpresaAuxiliar(String valor) {
		return empresaDAO.findByIdEmpresaAuxiliarStartingWithAllIgnoreCase(valor);
	}
	
	/* Servicio que permite buscar un cargo de acuerdo al nombre */
	public List<Empresa> buscarPorNombres(String nombre) {
		List<Empresa> empresa;
		empresa = empresaDAO.findByNombreAllIgnoreCase(nombre);
		return empresa;
	}




	

}
