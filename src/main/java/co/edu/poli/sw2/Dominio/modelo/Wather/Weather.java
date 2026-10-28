package co.edu.poli.sw2.Dominio.modelo.Wather;

/** Inmutable: contiene los cinco datos meteorológicos utilizados por la evaluación de vuelo. */
public class Weather {
	private final double temperatura;
	private final double humedad;
	private final double velocidadViento;
	private final double presion;
	private final double visibilidad;

	/** Construye una observación meteorológica con las unidades normalizadas del sistema. */
	public Weather(double temperatura, double humedad, double velocidadViento, double presion, double visibilidad) {
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.velocidadViento = velocidadViento;
		this.presion = presion;
		this.visibilidad = visibilidad;
	}

	/** @return temperatura en grados Celsius */
	public double getTemperatura() {
		return temperatura;
	}

	/** @return humedad relativa en porcentaje */
	public double getHumedad() {
		return humedad;
	}

	/** @return velocidad del viento en kilómetros por hora */
	public double getVelocidadViento() {
		return velocidadViento;
	}

	/** @return presión atmosférica en hectopascales */
	public double getPresion() {
		return presion;
	}

	/** @return visibilidad en kilómetros */
	public double getVisibilidad() {
		return visibilidad;
	}
}