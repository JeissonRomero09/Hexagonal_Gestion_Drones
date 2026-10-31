package co.edu.poli.sw2.Dominio.Command;

/**
 * Define una tarea que puede programarse y ejecutarse sobre un dron.
 *
 * @author Cristian Vera
 */
public interface DroneCommand {

	/**
	 * Ejecuta la tarea encapsulada.
	 *
	 * @return resultado de la ejecución
	 */
	String ejecutar();
}