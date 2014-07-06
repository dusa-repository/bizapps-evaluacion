package interfacedao.transacciones;
import modelo.maestros.Bitacora;
import modelo.maestros.Evaluacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBitacoraDAO extends JpaRepository<Bitacora, Long> {
	
}
