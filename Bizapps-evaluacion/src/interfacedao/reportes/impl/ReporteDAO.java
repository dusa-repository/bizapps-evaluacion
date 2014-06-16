package interfacedao.reportes.impl;

import interfacedao.reportes.IReporteDAO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;

@Repository
public class ReporteDAO implements IReporteDAO {

	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ReporteDAO() {
		super();
	}

	@Transactional
	public CategoryModel getDataResumenMacro(Map<String, String> parametros) {
		// TODO Auto-generated method stub

		String sentencia = "";
		String restricciones = "";
		String restricciones1 = "";
		String ordenamiento = "";
		String agrupamiento = "";

		for (Map.Entry<String, String> entrada : parametros.entrySet()) {
			// Por defecto los valores de TODOS o TODAS en los combo tienen el
			// valor cero (0) o blanco, se descartan estas entradas para las
			// restricciones
			if ((entrada.getValue().trim().compareTo("0") != 0)
					&& (entrada.getValue().trim().compareTo("") != 0)) {
				if (restricciones.compareTo("") == 0) {
					restricciones += " WHERE";
					restricciones1 += " WHERE";
				} else {
					restricciones += " AND";
					restricciones1 += " AND";
				}

				switch (entrada.getKey()) {
				case "programa":
					restricciones += " pr.programa_id=" + entrada.getValue()
							+ "";
					restricciones1 += " pr.programa_id=" + entrada.getValue()
							+ "";
					break;
				case "lapso":
					restricciones += " pa.pasantia_lapso_id="
							+ entrada.getValue() + "";
					restricciones1 += " 1=1 ";
					break;
				case "empresa":
					restricciones += " em.empresa_id='" + entrada.getValue()
							+ "'";
					restricciones1 += " em.empresa_id='" + entrada.getValue()
							+ "'";
					break;
				case "fecha_desde":
					restricciones += " 1=1 ";
					restricciones1 += " eo.fecha_validez_inicio>='"
							+ entrada.getValue() + "' ";
					break;
				case "fecha_hasta":
					restricciones += " 1=1 ";
					restricciones1 += " eo.fecha_validez_inicio<='"
							+ entrada.getValue() + "' ";
					break;
				default:
					break;
				}
			}
		}

		sentencia = "  SELECT     periodo.nombre, evaluacion.valoracion, COUNT(evaluacion.valoracion) AS Expr1,orden FROM         evaluacion INNER JOIN revision ON evaluacion.id_revision = revision.id_revision INNER JOIN periodo ON periodo.id_periodo = revision.id_periodo INNER JOIN valoracion ON valoracion.nombre=evaluacion.valoracion WHERE     (evaluacion.estado_evaluacion = 'FINALIZADA') GROUP BY periodo.nombre,evaluacion.valoracion,orden order by orden  ";

		ordenamiento = " ";
		agrupamiento = " ";

		Integer sum = (Integer) getEntityManager().createNativeQuery(
				"SELECT count(*)  FROM evaluacion WHERE (evaluacion.estado_evaluacion = 'FINALIZADA') ").getSingleResult();


		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			
			//float aux =  (float)  ((Integer) obj[2]) *100 / sum  ;

			model.setValue((String) obj[0], (String) obj[1], (Integer) obj[2] );

		}

		/*
		 * model.setValue("2013", "NC", 49.9); model.setValue("2014", "NC",
		 * 71.5); model.setValue("2013", "PDE", 59.9); model.setValue("2014",
		 * "PDE", 81.5); model.setValue("2013", "ME", 39.9);
		 * model.setValue("2014", "ME", 41.5); model.setValue("2013", "CE",
		 * 99.9); model.setValue("2014", "CE", 11.5); model.setValue("2013",
		 * "EE", 29.9); model.setValue("2014", "EE", 51.5);
		 */

		return model;
	}

}
