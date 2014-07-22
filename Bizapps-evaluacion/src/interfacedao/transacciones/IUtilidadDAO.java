package interfacedao.transacciones;

public interface IUtilidadDAO {
	
	public void eliminarConductaPorCompetencia(Integer eva, Integer com);
	public void eliminarConductaPorEvaluacion(Integer eva);
	public String obtenerValoracionFinal(Integer resultado);
	public String obtenerValoracionFinalSimple(Integer resultado);
	public void eliminarCompetenciaPorEvaluacion(Integer eva);

}
