package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelos.Empleado;
import modelos.Perspectiva;
import modelos.UnidadMedida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUnidadMedidaDAO extends JpaRepository<UnidadMedida, Integer> {
	
	 public List<UnidadMedida> findAll();


	 }
