package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.poli.sw2.Dominio.Command.DroneCommand;

/**
 * Invocador que programa y ejecuta comandos en el orden en que se registraron.
 *
 * @author Cristian Vera
 */
public class ProgramadorTareas {
	private final List<DroneCommand> tareas = new ArrayList<>();

	/**
	 * Agrega una tarea al final de la programación.
	 *
	 * @param tarea comando que se ejecutará
	 */
	public void programar(DroneCommand tarea) {
		tareas.add(Objects.requireNonNull(tarea, "tarea"));
	}

	/** Elimina todas las tareas que aún están programadas. */
	public void limpiarTareas() {
		tareas.clear();
	}

	/**
	 * Ejecuta e imprime los resultados respetando el orden de programación.
	 * Las tareas permanecen programadas, por lo que una llamada posterior vuelve
	 * a ejecutarlas.
	 *
	 * @return resultados en el mismo orden en que se ejecutaron las tareas
	 */
	public List<String> ejecutarTareas() {
		List<String> resultados = new ArrayList<>(tareas.size());
		for (DroneCommand tarea : tareas) {
			String resultado = tarea.ejecutar();
			resultados.add(resultado);
			System.out.println(resultado);
		}
		return List.copyOf(resultados);
	}
}