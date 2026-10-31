package co.edu.poli.sw2.Dominio.Command;

import java.util.Objects;

/**
 * Comando que solicita al receptor escanear una zona.
 *
 * @author Cristian Vera
 */
public class EscanearZonaCommand implements DroneCommand {
	private final DroneOperacion receptor;

	/**
	 * Crea el comando asociado al receptor indicado.
	 *
	 * @param receptor operación que realizará la tarea
	 */
	public EscanearZonaCommand(DroneOperacion receptor) {
		this.receptor = Objects.requireNonNull(receptor, "receptor");
	}

	/** {@inheritDoc} */
	@Override
	public String ejecutar() {
		return receptor.escanearZona();
	}
}