package co.edu.poli.sw2.Dominio.Command;

/**
 * Define las operaciones que pueden solicitar los comandos del dron.
 *
 * @author Cristian Vera
 */
public interface DroneOperacion {

	/** @return resultado de tomar una fotografía */
	String tomarFotografia();

	/** @return resultado de escanear una zona */
	String escanearZona();

	/** @return resultado de medir la temperatura */
	String medirTemperatura();
}