package servicio.seguridad;

import interfacedao.seguridad.IUsuarioDAO;

import java.util.List;

import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SUsuario")
public class SUsuario {

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Transactional
	public Usuario buscarUsuarioPorId(long codigo) {
		return usuarioDAO.findOne(codigo);
	}

	// @Transactional
	// public Usuario buscarUsuarioPorId(String id) {
	// return usuarioDAO.findByCedula(id);
	// }

	public void guardar(Usuario usuario) {
		usuarioDAO.save(usuario);
	}

	@Transactional
	public Usuario buscarUsuarioPorNombre(String nombre) {
		return usuarioDAO.findByLogin(nombre);
	}

	@Transactional
	public Usuario buscarPorCedula(String value) {
		return usuarioDAO.findByCedula(value);
	}

	public List<Usuario> buscarTodos() {
		return usuarioDAO.findAll();
	}

	public void eliminar(Usuario usuario) {
		usuarioDAO.delete(usuario);
	}

	public List<Usuario> filtroCedula(String valor) {
		return usuarioDAO.findByCedulaStartingWithAllIgnoreCase(valor);
	}

	// public List<Usuario> filtroLogin(String valor) {
	// return usuarioDAO.findByLoginStartingWithAllIgnoreCase(valor);
	// }

	public Usuario buscarId(Integer idUsuario) {
		return usuarioDAO.findByIdUsuario(idUsuario);
	}

	public Usuario buscarPorCedulayCorreo(String value, String value2) {
		return usuarioDAO.findByCedulaAndEmail(value, value2);
	}

	public void eliminarVarios(List<Usuario> eliminarLista) {
		usuarioDAO.delete(eliminarLista);
	}

	public Usuario buscarFicha(String idUsuario) {
		List<Usuario> lista = usuarioDAO.findByFicha(idUsuario);
		if (!lista.isEmpty())
			return lista.get(0);
		else
			return null;
	}

}