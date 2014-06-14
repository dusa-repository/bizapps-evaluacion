package interfacedao.transacciones;


import java.util.List;

import modelos.EvaluacionConducta;
import modelos.EvaluacionObjetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionConductaDAO extends JpaRepository<EvaluacionConducta, Integer> {


}
