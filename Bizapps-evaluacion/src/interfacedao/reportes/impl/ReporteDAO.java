package interfacedao.reportes.impl;

import interfacedao.reportes.IReporteDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import modelo.reportes.BeanDataGeneralCsv;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	public List<BeanDataGeneralCsv> getDataGeneralCsv(
			Map<String, String> parametros) {
		// TODO Auto-generated method stub

		String sentencia = "";
		String restricciones = "";
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

		sentencia = " SELECT rev.descripcion as desc_rev,emp.ficha,emp.nombre,car.descripcion as desc_car,uni.descripcion as desc_uni,resultado_objetivos,resultado_competencias,resultado_final,valoracion,grado_auxiliar,ge.descripcion as desc_ger FROM  evaluacion as eva INNER JOIN revision as rev ON eva.id_revision = rev.id_revision INNER JOIN periodo as per ON per.id_periodo = rev.id_periodo INNER JOIN valoracion as valo ON valo.nombre = eva.valoracion INNER JOIN empleado as emp ON eva.ficha = emp.ficha INNER JOIN unidad_organizativa as uni ON uni.id_unidad_organizativa = emp.id_unidad_organizativa INNER JOIN gerencia as ge ON ge.id_gerencia = uni.id_gerencia INNER JOIN empresa as empr ON empr.id_empresa=emp.id_empresa INNER JOIN cargo as car ON car.id_cargo=emp.id_cargo ";
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
			beanData.setResultadoObjetivo((Double) obj[5]);
			beanData.setResultadoCompetencia((Double) obj[6]);
			beanData.setResultadoTotal((Double) obj[7]);
			beanData.setValoracion((String) obj[8]);
			beanData.setGrado((Integer) obj[9]);
			beanData.setGerencia((String) obj[10]);
			
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
