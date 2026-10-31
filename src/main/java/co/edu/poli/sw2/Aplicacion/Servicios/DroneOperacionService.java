package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.Objects;

import co.edu.poli.sw2.Dominio.Command.DroneOperacion;
import co.edu.poli.sw2.Dominio.modelo.Dron;

/**
 * Receptor de las operaciones solicitadas por los comandos del dron.
 *
 * <p>Las operaciones son simuladas porque el proyecto no cuenta con integraciones
 * de cámara, escaneo ni sensor térmico.</p>
 *
 * @author Cristian Vera
 */
public class DroneOperacionService implements DroneOperacion {
	private final Dron dron;

	/**
	 * Crea el receptor para el dron que ejecutará las operaciones.
	 *
	 * @param dron dron sobre el que se reportarán las operaciones
	 */
	public DroneOperacionService(Dron dron) {
		this.dron = Objects.requireNonNull(dron, "dron");
	}

	/**
	 * Simula la toma de una fotografía.
	 *
	 * @return resultado descriptivo de la operación
	 */
	public String tomarFotografia() {
		return resultado("Fotografía tomada");
	}

	/**
	 * Simula el escaneo de una zona.
	 *
	 * @return resultado descriptivo de la operación
	 */
	public String escanearZona() {
		return resultado("Zona escaneada");
	}

	/**
	 * Simula una medición de temperatura sin generar una lectura de sensor real.
	 *
	 * @return resultado descriptivo de la operación
	 */
	public String medirTemperatura() {
		return resultado("Medición de temperatura ejecutada");
	}

	private String resultado(String operacion) {
		String identificador = dron.getSerial() == null || dron.getSerial().isBlank()
				? Integer.toString(dron.getId())
				: dron.getSerial();
		return "[Simulación] " + operacion + " para el dron " + identificador
				+ "; no hay integración de hardware.";
	}
}