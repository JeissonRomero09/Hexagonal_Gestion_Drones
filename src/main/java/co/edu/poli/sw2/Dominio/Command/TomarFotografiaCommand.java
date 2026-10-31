package co.edu.poli.sw2.Dominio.Command;

import java.util.Objects;

/**
 * Comando que solicita al receptor tomar una fotografía.
 *
 * @author Cristian Vera
 */
public class TomarFotografiaCommand implements DroneCommand {
	private final DroneOperacion receptor;

	/**
	 * Crea el comando asociado al receptor indicado.
	 *
	 * @param receptor operación que realizará la tarea
	 */
	public TomarFotografiaCommand(DroneOperacion receptor) {
		this.receptor = Objects.requireNonNull(receptor, "receptor");
	}

	/** {@inheritDoc} */
	@Override
	public String ejecutar() {
		return receptor.tomarFotografia();
	}
}