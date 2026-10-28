package co.edu.poli.sw2.Aplicacion.Puerto.Salida;

import java.util.Map;

import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;

/** Puerto de salida para consultar geocodificación y clima mediante un proveedor externo. */
public interface ProveedorClima {
	/**
	 * Busca ubicaciones y las devuelve asociadas con sus coordenadas.
	 *
	 * @param consulta texto ingresado para buscar
	 * @return ubicaciones en el orden recibido, con coordenadas {@code [latitud, longitud]}
	 */
	Map<String, double[]> buscarCiudades(String consulta);

	/**
	 * Obtiene el clima actual de las coordenadas indicadas.
	 *
	 * @param latitud latitud geográfica en grados
	 * @param longitud longitud geográfica en grados
	 * @return datos meteorológicos actuales
	 */
	Weather consultarClima(double latitud, double longitud);
}
