package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Define la consulta de un dron individual desde la aplicación. */
public interface BuscarDronUseCase {
    /**
     * Busca un dron por su identificador.
     *
     * @param id identificador del dron
     * @return el dron encontrado o {@code null} si no existe
     */
    Dron buscar(int id);
}