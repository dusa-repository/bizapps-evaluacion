package interfacedao.reportes.impl;

import interfacedao.reportes.IReporteDAO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import modelo.reportes.BeanDataGeneralCsv;

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

	/* ----------------- PERIODO ---------------------- */

	@Transactional
	public CategoryModel getDataResumenMacroPeriodo(
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
					restricciones += " WHERE ";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision in ( "
							+ entrada.getValue() + ","
							+ parametros.get("periodo_comparar") + " )";
					break;
				case "periodo_comparar":
					restricciones += " 1=1 ";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					break;

				default:
					break;
				}
			}
		}

		sentencia = " SELECT rev.descripcion, eva.valoracion, COUNT(eva.valoracion) AS Expr1, valo.orden FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa ";
		ordenamiento = " ORDER BY valo.orden ";
		agrupamiento = " GROUP BY rev.descripcion, eva.valoracion, valo.orden ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			model.setValue((String) obj[0], (String) obj[1], (Integer) obj[2]);
		}

		return model;
	}

	@Override
	public CategoryModel getDataCumplimientoObjetivoPeriodo(
			Map<String, String> parametros) {
		// TODO Auto-generated method stub

		String sentencia = "";
		String sentencia1 = "";
		String restricciones = "";
		String restricciones1 = "";
		String ordenamiento = "";
		String agrupamiento = "";
		boolean vsGerencia = false;

		for (Map.Entry<String, String> entrada : parametros.entrySet()) {
			// Por defecto los valores de TODOS o TODAS en los combo tienen el
			// valor cero (0) o blanco, se descartan estas entradas para las
			// restricciones
			if ((entrada.getValue().trim().compareTo("0") != 0)
					&& (entrada.getValue().trim().compareTo("") != 0)) {

				if (restricciones.compareTo("") == 0) {
					restricciones += " WHERE";
					restricciones1 += " AND";
				} else {
					restricciones += " AND";
					restricciones1 += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " 1=1 ";
					restricciones1 += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					vsGerencia = true;
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					restricciones1 += " 1=1 ";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					restricciones1 += " 1=1 ";
					break;
				case "periodo":
					restricciones += " eva.id_revision in ( "
							+ entrada.getValue() + ","
							+ parametros.get("periodo_comparar") + " )";
					restricciones1 += " 1=1 ";
					break;
				case "periodo_comparar":
					restricciones += " 1=1 ";
					restricciones1 += " 1=1 ";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					restricciones1 += " 1=1 ";
					break;

				default:
					break;
				}
			}
		}

		sentencia = "  SELECT     rev.descripcion, AVG(eva.resultado) AS Expr1 FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa   ";
		ordenamiento = " ";
		agrupamiento = " GROUP BY rev.descripcion ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			model.setValue((String) obj[0], "GENERAL", (double) obj[1]);
		}

		if (vsGerencia) {

			sentencia1 = "SELECT  rev.descripcion as desc1,ge.descripcion as desc2, AVG(eva.resultado) AS Expr1 FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa ";
			ordenamiento = " ";
			agrupamiento = " GROUP BY rev.descripcion,ge.descripcion ";

			qSentencia = getEntityManager().createNativeQuery(
					sentencia1 + restricciones + restricciones1 + agrupamiento
							+ ordenamiento);

			results = qSentencia.getResultList();

			for (Object[] obj : results) {
				model.setValue((String) obj[0], (String) obj[1],
						(double) obj[2]);
			}
		}

		return model;

	}

	@Override
	public CategoryModel getDataResumenGeneralBrechaPeriodo(
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
					restricciones += " AND";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision in ( "
							+ entrada.getValue() + ","
							+ parametros.get("periodo_comparar") + " )";
					break;
				case "periodo_comparar":
					restricciones += " 1=1 ";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					break;
				case "competencia":
					restricciones += " comp.id_competencia = '"
							+ entrada.getValue() + "' ";
					break;
				default:
					break;
				}
			}
		}

		sentencia = "  select  rev.descripcion as desc_rev,nivc.id_competencia,comp.descripcion as desc_comp,Count(*) as cantidad  from evaluacion as eva INNER JOIN empleado as emp ON eva.ficha=emp.ficha INNER JOIN nivel_competencia_cargo as nivc ON nivc.id_cargo=emp.id_cargo INNER JOIN evaluacion_competencia as evac ON evac.id_evaluacion=eva.id_evaluacion INNER JOIN competencia as comp ON comp.id_competencia=nivc.id_competencia INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo ON periodo.id_periodo = rev.id_periodo     INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion  WHERE nivc.id_competencia=evac.id_competencia  and evac.id_dominio<>0 AND nivc.id_dominio > (evac.id_dominio+5)     ";
		ordenamiento = "  order by id_competencia ";
		agrupamiento = " group by rev.descripcion,nivc.id_competencia,comp.descripcion ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {

			model.setValue((String) obj[0], (String) obj[2], (Integer) obj[3]);
		}

		return model;

	}

	/* ----------------- GERENCIA ---------------------- */

	@Transactional
	public CategoryModel getDataResumenMacroGerencia(
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
					restricciones += " WHERE ";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia in ( "
							+ entrada.getValue() + ","
							+ parametros.get("gerencia_comparar") + " )";
					break;
				case "gerencia_comparar":
					restricciones += " 1=1 ";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision=" + entrada.getValue()
							+ "";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					break;

				default:
					break;
				}
			}
		}

		sentencia = " SELECT ge.descripcion, eva.valoracion, COUNT(eva.valoracion) AS Expr1, valo.orden FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa ";
		ordenamiento = " ORDER BY valo.orden ";
		agrupamiento = " GROUP BY ge.descripcion, eva.valoracion, valo.orden ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			model.setValue((String) obj[0], (String) obj[1], (Integer) obj[2]);
		}

		return model;
	}

	@Override
	public CategoryModel getDataCumplimientoObjetivoGerencia(
			Map<String, String> parametros) {
		// TODO Auto-generated method stub

		String sentencia = "";
		String sentencia1 = "";
		String restricciones = "";
		String restricciones1 = "";
		String ordenamiento = "";
		String agrupamiento = "";
		boolean vsGerencia = false;

		for (Map.Entry<String, String> entrada : parametros.entrySet()) {
			// Por defecto los valores de TODOS o TODAS en los combo tienen el
			// valor cero (0) o blanco, se descartan estas entradas para las
			// restricciones
			if ((entrada.getValue().trim().compareTo("0") != 0)
					&& (entrada.getValue().trim().compareTo("") != 0)) {

				if (restricciones.compareTo("") == 0) {
					restricciones += " WHERE ";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia in ( "
							+ entrada.getValue() + ","
							+ parametros.get("gerencia_comparar") + " )";
					break;
				case "gerencia_comparar":
					restricciones += " 1=1 ";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision=" + entrada.getValue()
							+ "";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					break;

				default:
					break;
				}

			}
		}

		// sentencia =
		// "  SELECT     rev.descripcion, AVG(eva.resultado) AS Expr1 FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa   ";
		sentencia = "SELECT  ge.descripcion as desc1,ge.descripcion as desc2, AVG(eva.resultado) AS Expr1 FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa ";
		ordenamiento = " ";
		agrupamiento = " GROUP BY ge.descripcion ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			model.setValue((String) obj[0], (String) obj[1], (double) obj[1]);
		}

		// if (vsGerencia) {
		//
		// sentencia1 =
		// "SELECT  rev.descripcion as desc1,ge.descripcion as desc2, AVG(eva.resultado) AS Expr1 FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa ";
		// ordenamiento = " ";
		// agrupamiento = " GROUP BY rev.descripcion,ge.descripcion ";
		//
		// qSentencia = getEntityManager().createNativeQuery(
		// sentencia1 + restricciones + restricciones1 + agrupamiento
		// + ordenamiento);
		//
		// results = qSentencia.getResultList();
		//
		// for (Object[] obj : results) {
		// model.setValue((String) obj[0], (String) obj[1],
		// (double) obj[2]);
		// }
		// }

		return model;

	}

	@Override
	public CategoryModel getDataResumenGeneralBrechaGerencia(
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
					restricciones += " AND";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision in ( "
							+ entrada.getValue() + ","
							+ parametros.get("periodo_comparar") + " )";
					break;
				case "periodo_comparar":
					restricciones += " 1=1 ";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					break;
				case "competencia":
					restricciones += " comp.id_competencia = '"
							+ entrada.getValue() + "' ";
					break;
				default:
					break;
				}
			}
		}

		sentencia = "  select  rev.descripcion as desc_rev,nivc.id_competencia,comp.descripcion as desc_comp,Count(*) as cantidad  from evaluacion as eva INNER JOIN empleado as emp ON eva.ficha=emp.ficha INNER JOIN nivel_competencia_cargo as nivc ON nivc.id_cargo=emp.id_cargo INNER JOIN evaluacion_competencia as evac ON evac.id_evaluacion=eva.id_evaluacion INNER JOIN competencia as comp ON comp.id_competencia=nivc.id_competencia INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo ON periodo.id_periodo = rev.id_periodo     INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion  WHERE nivc.id_competencia=evac.id_competencia  and evac.id_dominio<>0 AND nivc.id_dominio > (evac.id_dominio+5)     ";
		ordenamiento = "  order by id_competencia ";
		agrupamiento = " group by rev.descripcion,nivc.id_competencia,comp.descripcion ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {

			model.setValue((String) obj[0], (String) obj[2], (Integer) obj[3]);
		}

		return model;

	}

	@Override
	public CategoryModel getDataEvaluadosBrecha(Map<String, String> parametros) {
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
					restricciones1 += " WHERE";
				} else {
					restricciones += " AND";
					restricciones1 += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					restricciones1 += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					restricciones1 += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					restricciones1 += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision=" + entrada.getValue()
							+ "";
					restricciones1 += " eva.id_revision=" + entrada.getValue()
							+ "";
					break;
				case "periodo_comparar":
					restricciones += " 1=1 ";
					restricciones1 += " 1=1 ";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					restricciones1 += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";

					break;
				case "competencia":
					restricciones += " comp.id_competencia = '"
							+ entrada.getValue() + "' ";
					restricciones1 += " 1=1 ";
					break;
				default:
					break;
				}
			}
		}

		sentencia = "  select  emp.ficha,Count(*) as cantidad  from evaluacion as eva INNER JOIN empleado as emp ON eva.ficha=emp.ficha INNER JOIN nivel_competencia_cargo as nivc ON nivc.id_cargo=emp.id_cargo INNER JOIN evaluacion_competencia as evac ON evac.id_evaluacion=eva.id_evaluacion INNER JOIN competencia as comp ON comp.id_competencia=nivc.id_competencia INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo ON periodo.id_periodo = rev.id_periodo     INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion  WHERE nivc.id_competencia=evac.id_competencia  and evac.id_dominio<>0 AND nivc.id_dominio > (evac.id_dominio+5)     ";
		ordenamiento = "  order by emp.ficha ";
		agrupamiento = " group by emp.ficha ";

		CategoryModel model;
		model = new DefaultCategoryModel();

		Integer evaluadosConBrecha;

		Integer sum = (Integer) getEntityManager()
				.createNativeQuery(
						" SELECT  COUNT(eva.valoracion) AS Expr1 FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa "
								+ restricciones1).getSingleResult();

		model.setValue("Total Evaluados", "CANTIDAD", sum);

		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		if (results != null) {
			evaluadosConBrecha = results.size();
			model.setValue("Evaluados Con Brechas", "CANTIDAD",
					evaluadosConBrecha);
		}

		return model;

	}
	
	
	@Transactional
	public List<BeanDataGeneralCsv> getDataGeneralCsv(
			Map<String, String> parametros) {
		// TODO Auto-generated method stub

		String sentencia = "";
		String restricciones = "";
		String restricciones1 = "";
		String ordenamiento = "";
		String agrupamiento = "";
		List<BeanDataGeneralCsv> listaDataGenericaCsv = new ArrayList<BeanDataGeneralCsv>();

		for (Map.Entry<String, String> entrada : parametros.entrySet()) {
			// Por defecto los valores de TODOS o TODAS en los combo tienen el
			// valor cero (0) o blanco, se descartan estas entradas para las
			// restricciones
			if ((entrada.getValue().trim().compareTo("0") != 0)
					&& (entrada.getValue().trim().compareTo("") != 0)) {

				if (restricciones.compareTo("") == 0) {
					restricciones += " WHERE ";
				} else {
					restricciones += " AND";
				}

				switch (entrada.getKey()) {
				case "gerencia":
					restricciones += " ge.id_gerencia=" + entrada.getValue()
							+ "";
					break;
				case "empresa":
					restricciones += " emp.id_empresa=" + entrada.getValue()
							+ "";
					break;
				case "unidad":
					restricciones += " uni.id_unidad_organizativa="
							+ entrada.getValue() + "";
					break;
				case "periodo":
					restricciones += " eva.id_revision="
							+ entrada.getValue() + "";
					break;
				case "periodo_comparar":
					restricciones += " 1=1 ";
					break;
				case "estado_evaluacion":
					restricciones += " eva.estado_evaluacion = '"
							+ entrada.getValue() + "' ";
					break;

				default:
					break;
				}
			}
		}

		sentencia = " SELECT rev.descripcion as desc_rev,emp.ficha,emp.nombre,car.descripcion as desc_car,uni.descripcion as desc_uni,resultado_objetivos,resultado_competencias,resultado_final,valoracion FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa INNER JOIN cargo as car ON car.id_cargo=emp.id_cargo ";
		ordenamiento = "   ";
		agrupamiento = "  ";

	
		Query qSentencia = getEntityManager().createNativeQuery(
				sentencia + restricciones + agrupamiento + ordenamiento);

		@SuppressWarnings("unchecked")
		List<Object[]> results = qSentencia.getResultList();

		for (Object[] obj : results) {
			
			BeanDataGeneralCsv beanData = new BeanDataGeneralCsv();
			
			beanData.setPeriodo((String) obj[0]);
			beanData.setFicha((String) obj[1]);
			beanData.setNombre((String) obj[2]);
			beanData.setCargo((String) obj[3]);
			beanData.setUnidad((String) obj[4]);
			beanData.setResultadoObjetivo((Integer) obj[5]);
			beanData.setResultadoCompetencia((Integer) obj[6]);
			beanData.setResultadoTotal((Integer) obj[7]);
			beanData.setValoracion((String) obj[8]);
			listaDataGenericaCsv.add(beanData);
			
		}

		return listaDataGenericaCsv;
	}

	/*
	 * Integer sum = (Integer) getEntityManager() .createNativeQuery(
	 * "SELECT count(*)  FROM evaluacion WHERE (evaluacion.estado_evaluacion = 'FINALIZADA') "
	 * ) .getSingleResult();
	 */

	// float aux = (float) ((Integer) obj[2]) *100 / sum ;

}
