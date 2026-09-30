package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Define la operación de aplicación para registrar un dron y sus asociaciones. */
public interface CrearDronUseCase {
    /**
     * Registra un dron y devuelve el identificador generado por la base de datos.
     *
     * @param dron datos del dron que se va a registrar
     * @param pilotoId identificador del piloto asociado
     * @param sensorId identificador del sensor asociado
     * @return identificador asignado al dron creado
     */
    int crear(Dron dron, int pilotoId, int sensorId);
}