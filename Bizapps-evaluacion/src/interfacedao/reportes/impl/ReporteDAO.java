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
					restricciones += " AND";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					break;
				default:
					break;
				}
			}
		}

		sentencia = "SELECT periodo.nombre, evaluacion.valoracion, COUNT(evaluacion.valoracion) AS Expr1, valoracion.orden FROM  evaluacion INNER JOIN revision ON evaluacion.id_revision = revision.id_revision INNER JOIN periodo ON periodo.id_periodo = revision.id_periodo INNER JOIN valoracion ON valoracion.nombre = evaluacion.valoracion INNER JOIN empleado ON evaluacion.ficha = empleado.ficha INNER JOIN unidad_organizativa ON unidad_organizativa.id_unidad_organizativa = empleado.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = unidad_organizativa.id_gerencia WHERE (evaluacion.estado_evaluacion = 'FINALIZADA') "
				+ restricciones
				+ " GROUP BY periodo.nombre, evaluacion.valoracion, valoracion.orden ORDER BY valoracion.orden ";
		ordenamiento = " ";
		agrupamiento = " ";

		Integer sum = (Integer) getEntityManager()
				.createNativeQuery(
						"SELECT count(*)  FROM evaluacion WHERE (evaluacion.estado_evaluacion = 'FINALIZADA') ")
				.getSingleResult();

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			// float aux = (float) ((Integer) obj[2]) *100 / sum ;
			model.setValue((String) obj[0], (String) obj[1], (Integer) obj[2]);
		}

		return model;
	}

	@Override
	public CategoryModel getDataCumplimientoObjetivo(
			Map<String, String> parametros) {
		// TODO Auto-generated method stub

		String sentencia = "";
		String sentencia1 = "";
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
					restricciones += " AND";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					sentencia1 = "SELECT     periodo.nombre,ge.descripcion, AVG(evaluacion.resultado) AS Expr1 FROM         evaluacion INNER JOIN revision ON evaluacion.id_revision = revision.id_revision INNER JOIN periodo ON periodo.id_periodo = revision.id_periodo INNER JOIN valoracion ON valoracion.nombre=evaluacion.valoracion INNER JOIN empleado ON evaluacion.ficha = empleado.ficha INNER JOIN unidad_organizativa ON unidad_organizativa.id_unidad_organizativa = empleado.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = unidad_organizativa.id_gerencia WHERE     (evaluacion.estado_evaluacion = 'FINALIZADA') "
							+ restricciones
							+ " GROUP BY periodo.nombre,ge.descripcion";
					break;
				default:
					break;
				}
			}
		}

		sentencia = "  SELECT     periodo.nombre, AVG(evaluacion.resultado) AS Expr1 FROM         evaluacion INNER JOIN revision ON evaluacion.id_revision = revision.id_revision INNER JOIN periodo ON periodo.id_periodo = revision.id_periodo INNER JOIN valoracion ON valoracion.nombre=evaluacion.valoracion WHERE     (evaluacion.estado_evaluacion = 'FINALIZADA')  GROUP BY periodo.nombre ";
		ordenamiento = " ";
		agrupamiento = " ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			// float aux = (float) ((Integer) obj[2]) *100 / sum ;
			model.setValue((String) obj[0], "GENERAL", (double) obj[1]);
		}

		if (sentencia1.compareTo("") != 0) {
			qSentencia = getEntityManager().createNativeQuery(
					sentencia1 + agrupamiento + ordenamiento);

			results = qSentencia.getResultList();

			for (Object[] obj : results) {
				// float aux = (float) ((Integer) obj[2]) *100 / sum ;
				model.setValue((String) obj[0], (String) obj[1],
						(double) obj[2]);
			}
		}

		return model;

	}

	@Override
	public CategoryModel getDataResumenGeneralBrecha(
			Map<String, String> parametros) {
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

		sentencia = "  select  periodo.nombre,nivel_competencia_cargo.id_competencia,competencia.descripcion,Count(*) as cantidad from evaluacion INNER JOIN empleado ON evaluacion.ficha=empleado.ficha INNER JOIN nivel_competencia_cargo ON nivel_competencia_cargo.id_cargo=empleado.id_cargo INNER JOIN evaluacion_competencia ON evaluacion_competencia.id_evaluacion=evaluacion.id_evaluacion INNER JOIN competencia ON competencia.id_competencia=nivel_competencia_cargo.id_competencia INNER JOIN revision ON evaluacion.id_revision = revision.id_revision INNER JOIN periodo ON periodo.id_periodo = revision.id_periodo  WHERE nivel_competencia_cargo.id_competencia=evaluacion_competencia.id_competencia and estado_evaluacion='FINALIZADA' and evaluacion_competencia.id_dominio<>0 AND nivel_competencia_cargo.id_dominio > (evaluacion_competencia.id_dominio+5) group by periodo.nombre,nivel_competencia_cargo.id_competencia,competencia.descripcion order by id_competencia";
		ordenamiento = " ";
		agrupamiento = " ";

		Integer sum = (Integer) getEntityManager()
				.createNativeQuery(
						"SELECT count(*)  FROM evaluacion WHERE (evaluacion.estado_evaluacion = 'FINALIZADA') ")
				.getSingleResult();

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			// float aux = (float) ((Integer) obj[2]) *100 / sum ;
			model.setValue((String) obj[0], (String) obj[2], (Integer) obj[3]);
		}

		return model;

	}

}
