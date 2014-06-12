package interfacedao.transacciones;

import java.util.ArrayList;
import java.util.List;

import modelos.Empleado;
import modelos.Medicion;
import modelos.Perspectiva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMedicionDAO extends JpaRepository<Medicion, Integer> {
	
	 public List<Medicion> findAll();


	 }
