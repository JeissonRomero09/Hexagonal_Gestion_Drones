package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import java.util.List;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Define la consulta de todos los drones registrados. */
public interface BuscarListaDronesUseCase {
    /**
     * Recupera la lista completa de drones.
     *
     * @return drones registrados, o una lista vacía si no hay resultados
     */
    List<Dron> buscarTodos();
}