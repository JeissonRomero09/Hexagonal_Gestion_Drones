package co.edu.poli.sw2.Dominio.Strategy;

import java.util.Objects;

/** Punto geográfico expresado mediante coordenadas WGS84. */
public class PuntoGeografico {
	public String nombre;
	public double latitud;
	public double longitud;

	public PuntoGeografico() {
	}

	public PuntoGeografico(String nombre, double latitud, double longitud) {
		this.nombre = Objects.requireNonNull(nombre, "nombre");
		this.latitud = latitud;
		this.longitud = longitud;
		validarCoordenadas();
	}

	/** Calcula la distancia de gran círculo al otro punto, en kilómetros. */
	public double distanciaA(PuntoGeografico destino) {
		Objects.requireNonNull(destino, "destino");
		validarCoordenadas();
		destino.validarCoordenadas();

		double latitudOrigen = Math.toRadians(latitud);
		double latitudDestino = Math.toRadians(destino.latitud);
		double diferenciaLatitud = latitudDestino - latitudOrigen;
		double diferenciaLongitud = Math.toRadians(destino.longitud - longitud);
		double a = Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2)
				+ Math.cos(latitudOrigen) * Math.cos(latitudDestino)
						* Math.sin(diferenciaLongitud / 2) * Math.sin(diferenciaLongitud / 2);
		double arco = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return 6371.0088 * arco;
	}

	private void validarCoordenadas() {
		if (!Double.isFinite(latitud) || latitud < -90 || latitud > 90
				|| !Double.isFinite(longitud) || longitud < -180 || longitud > 180) {
			throw new IllegalArgumentException("Las coordenadas geográficas no son válidas.");
		}
	}
}
