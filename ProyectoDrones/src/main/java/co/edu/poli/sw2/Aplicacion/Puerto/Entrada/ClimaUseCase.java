package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import java.util.Map;

import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Define las operaciones meteorológicas disponibles para los adaptadores de entrada. */
public interface ClimaUseCase {
	/**
	 * Busca ubicaciones y devuelve sus coordenadas asociadas a etiquetas legibles.
	 *
	 * @param consulta nombre parcial de ciudad, región o país
	 * @return mapa ordenado por el proveedor entre etiqueta y {@code [latitud, longitud]}
	 */
	Map<String, double[]> buscarCiudades(String consulta);

	/**
	 * Consulta las condiciones actuales de una ubicación.
	 *
	 * @param latitud latitud geográfica en grados
	 * @param longitud longitud geográfica en grados
	 * @return datos meteorológicos actuales
	 */
	Weather consultarClima(double latitud, double longitud);
}
