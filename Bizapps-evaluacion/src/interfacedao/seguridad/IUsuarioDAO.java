package interfacedao.seguridad;

import java.util.List;

import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String nombre);

	Usuario findByCedula(String value);

	List<Usuario> findByCedulaStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByLoginStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByApellidoStartingWithAllIgnoreCase(String valor);
	
	Usuario findByIdUsuario(Integer idUsuario);

	Usuario findByCedulaAndEmail(String value, String value2);

	List<Usuario> findByFicha(String idUsuario);
}