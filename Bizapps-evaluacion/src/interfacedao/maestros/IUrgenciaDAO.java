package interfacedao.maestros;


import java.util.List;

import modelos.Urgencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUrgenciaDAO extends JpaRepository<Urgencia, Integer> {

	Urgencia findByDescripcionUrgencia(String descripcion);


	
}