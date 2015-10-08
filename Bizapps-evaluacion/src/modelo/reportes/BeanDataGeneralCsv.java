package modelo.reportes;


public class BeanDataGeneralCsv {
	
	private String periodo;
	private String ficha;
	private String nombre;
	private String cargo;
	private String unidad;
	private String gerencia;
	private Integer grado;
	private Double resultadoObjetivo;
	private Double resultadoCompetencia;
	private Double resultadoTotal;
	private String valoracion;
	
	
	
	public BeanDataGeneralCsv() {
		super();
	}
	
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getFicha() {
		return ficha;
	}
	public void setFicha(String ficha) {
		this.ficha = ficha;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getValoracion() {
		return valoracion;
	}
	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public Double getResultadoObjetivo() {
		return resultadoObjetivo;
	}

	public void setResultadoObjetivo(Double resultadoObjetivo) {
		this.resultadoObjetivo = resultadoObjetivo;
	}

	public Double getResultadoCompetencia() {
		return resultadoCompetencia;
	}

	public void setResultadoCompetencia(Double resultadoCompetencia) {
		this.resultadoCompetencia = resultadoCompetencia;
	}

	public Double getResultadoTotal() {
		return resultadoTotal;
	}

	public void setResultadoTotal(Double resultadoTotal) {
		this.resultadoTotal = resultadoTotal;
	}

	public String getGerencia() {
		return gerencia;
	}

	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	
	
	

}
