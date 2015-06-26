package controlador.reportes.evaluacion;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.Empleado;
import modelo.maestros.EmpleadoCurso;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import controlador.maestros.CGenerico;

public class ReporteBase extends CGenerico {

	protected Connection conexion;

	public ReporteBase() {
	}

	public JasperReport cargarReporte(URL url) {
		JasperReport reporte = null;
		try {
			reporte = (JasperReport) JRLoader.loadObject(url);

		} catch (JRException e) {
			System.out.println("Error cargando el reporte : " + e.getMessage());
			System.exit(3);
		}

		return reporte;
	}

	public byte[] getReporte(String par1, String par2, String par3,
			String par4, String par5, String par6, String par7, String par8,
			String par9, String par10) {
		byte[] fichero = null;

		conexion = null;
		try {

			ClassLoader cl = this.getClass().getClassLoader();
			InputStream fis = null;

			String path_banner = "";
			try {
				// path_banner=getClass().getResource("/imagenes/banner.jpg").getPath();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}

			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("evaluacion", par2);
			parameters.put("evaluador", par3);
			parameters.put("path_banner", path_banner);

			if (par1.compareTo("sencillo") == 0) {
				fis = (cl
						.getResourceAsStream("controlador/reportes/evaluacion/jasper/reporteEvaluacion.jasper"));
			} else {
				if (par1.equals("gna")) {
					Empleado trabajador = CGenerico.getServicioEmpleado()
							.buscarEmpleadoPorFicha(par2);
					parameters.put("ficha", par2);
					parameters.put("cedula", trabajador.getCedula());
					parameters.put("nombre_empleado", trabajador.getNombre());
					Double horas = CGenerico.getServicioEmpleadoCurso()
							.sumarHoras(trabajador);
					List<EmpleadoCurso> lista = CGenerico
							.getServicioEmpleadoCurso()
							.buscarCursos(trabajador);
					parameters.put("horas", horas.floatValue());
					if (!lista.isEmpty())
						parameters.put("periodo", lista.get(0).getId().getCurso()
								.getPeriodo().getNombre());
//					Revisar este periodo, a cualse refiere
					parameters.put("cargo", trabajador.getCargo()
							.getDescripcion());
					parameters.put("unidad", trabajador.getUnidadOrganizativa()
							.getDescripcion());
					parameters.put("gerencia", trabajador
							.getUnidadOrganizativa().getGerencia()
							.getDescripcion());
					parameters.put("nivel1", trabajador.getNivelAcademico());
					parameters.put("carrera1", trabajador.getEspecialidad());
					parameters.put("especializacion1",
							trabajador.getEspecializacion());
					if (trabajador.getCargo().getNivelAcademico() != null)
						parameters.put("nivel2", trabajador.getCargo()
								.getNivelAcademico());
					else
						parameters.put("nivel2", "");
					if (trabajador.getCargo().getEspecialidad() != null)
						parameters.put("carrera2", trabajador.getCargo()
								.getEspecialidad());
					else
						parameters.put("carrera2", "");
					if (trabajador.getCargo().getEspecializacion() != null)
						parameters.put("especializacion2", trabajador
								.getCargo().getEspecializacion());
					else
						parameters.put("especializacion2", "");
					if (trabajador.getCargo().getExperiencia() != null)
						parameters.put("experiencia", trabajador.getCargo()
								.getExperiencia());
					else
						parameters.put("experiencia", "");
					if (trabajador.getCargo().getIdioma() != null)
						parameters.put("idioma", trabajador.getCargo()
								.getIdioma());
					else
						parameters.put("idioma", "");
					if (trabajador.getCargo().getObservaciones() != null)
						parameters.put("observaciones", par2);
					else
						parameters.put("observaciones", "");
					parameters.put("observaciones2", "");
					parameters.put("firma", "");
					trabajador = CGenerico.getServicioEmpleado()
							.buscarEmpleadoPorFicha(par3);
					parameters.put("nombre_supervisor", trabajador.getNombre());
					fis = (cl
							.getResourceAsStream("controlador/reportes/evaluacion/jasper/reporteGNA.jasper"));

				} else
					fis = (cl
							.getResourceAsStream("controlador/reportes/evaluacion/jasper/reporteEvaluacionFull.jasper"));
			}

			List<String> lista = obtenerPropiedades();
			String user = lista.get(0);
			String password = lista.get(1);
			String url = lista.get(2);

			try {

				try {
					conexion = java.sql.DriverManager.getConnection(url, user,
							password);
					// conexion=Conexion.getConexion();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			JasperPrint jasperPrint = null;

			try {

				if (fichero == null) {
					fichero = JasperRunManager.runReportToPdf(fis, parameters,
							conexion);
				}

			} catch (JRException ex) {
				ex.printStackTrace();
				System.out.println(ex.toString());
			}

			if (conexion != null) {
				conexion.close();
			}

		} catch (SQLException e) {
			System.out.println("Error de conexión: " + e.getMessage());
			System.exit(4);
		} catch (Exception e) {
			System.out.println("Error de Reporte: " + e.toString());
			System.exit(4);
		}

		return fichero;

	}

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

	}

}
