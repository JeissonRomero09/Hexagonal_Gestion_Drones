package co.edu.poli.sw2.Infraestuctura.Persistencia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.poli.sw2.Aplicacion.Puerto.Salida.ProveedorClima;
import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Adaptador HTTP de Open-Meteo para geocodificar ciudades y obtener el clima actual. */
public class GeoAPI implements ProveedorClima {
	private static final String DEFAULT_GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";
	private static final String DEFAULT_FORECAST_URL = "https://api.open-meteo.com/v1/forecast";
	private final String geocodingUrl;
	private final String forecastUrl;

	public GeoAPI() {
		this(DEFAULT_GEOCODING_URL, DEFAULT_FORECAST_URL);
	}

	GeoAPI(String geocodingUrl, String forecastUrl) {
		this.geocodingUrl = Objects.requireNonNull(geocodingUrl, "geocodingUrl");
		this.forecastUrl = Objects.requireNonNull(forecastUrl, "forecastUrl");
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, double[]> buscarCiudades(String consulta) {
		if (consulta == null || consulta.trim().length() < 2) {
			return new LinkedHashMap<>();
		}
		String url = geocodingUrl + "?name=" + codificar(consulta.trim())
				+ "&count=10&language=es&format=json";
		JsonObject respuesta = solicitarJson(url);
		JsonArray resultados = respuesta.has("results") && respuesta.get("results").isJsonArray()
				? respuesta.getAsJsonArray("results") : new JsonArray();
		Map<String, double[]> ciudades = new LinkedHashMap<>();
		for (JsonElement elemento : resultados) {
			JsonObject resultado = elemento.getAsJsonObject();
			double latitud = numero(resultado, "latitude");
			double longitud = numero(resultado, "longitude");
			String etiqueta = texto(resultado, "name");
			String region = texto(resultado, "admin1");
			String pais = texto(resultado, "country");
			if (!region.isEmpty()) {
				etiqueta += ", " + region;
			}
			if (!pais.isEmpty()) {
				etiqueta += ", " + pais;
			}
			if (ciudades.containsKey(etiqueta)) {
				etiqueta += String.format(Locale.ROOT, " (%.4f, %.4f)", latitud, longitud);
			}
			ciudades.put(etiqueta, new double[] { latitud, longitud });
		}
		return ciudades;
	}

	/** {@inheritDoc} */
	@Override
	public Weather consultarClima(double latitud, double longitud) {
		if (latitud < -90 || latitud > 90 || longitud < -180 || longitud > 180) {
			throw new IllegalArgumentException("Las coordenadas seleccionadas no son válidas.");
		}
		String url = forecastUrl + "?latitude=" + latitud + "&longitude=" + longitud
				+ "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,surface_pressure,visibility"
				+ "&wind_speed_unit=kmh&timezone=auto";
		JsonObject respuesta = solicitarJson(url);
		JsonObject actual = respuesta.getAsJsonObject("current");
		if (actual == null) {
			throw new IllegalStateException("Open-Meteo no devolvió datos meteorológicos actuales.");
		}
		return new Weather(numero(actual, "temperature_2m"), numero(actual, "relative_humidity_2m"),
				numero(actual, "wind_speed_10m"), numero(actual, "surface_pressure"),
				numero(actual, "visibility") / 1000.0);
	}

	/** Ejecuta una solicitud GET y convierte su respuesta JSON en un objeto. */
	private JsonObject solicitarJson(String direccion) {
		HttpURLConnection conexion = null;
		try {
			conexion = (HttpURLConnection) new URL(direccion).openConnection();
			conexion.setRequestMethod("GET");
			conexion.setConnectTimeout(10000);
			conexion.setReadTimeout(10000);
			int codigo = conexion.getResponseCode();
			InputStream flujo = codigo >= 200 && codigo < 300 ? conexion.getInputStream() : conexion.getErrorStream();
			if (flujo == null) {
				throw new IOException("Respuesta HTTP " + codigo + " sin contenido.");
			}
			StringBuilder contenido = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(new InputStreamReader(flujo, StandardCharsets.UTF_8))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					contenido.append(linea);
				}
			}
			if (codigo < 200 || codigo >= 300) {
				throw new IOException("Open-Meteo respondió HTTP " + codigo + ".");
			}
			return JsonParser.parseString(contenido.toString()).getAsJsonObject();
		} catch (IOException | IllegalStateException exception) {
			throw new IllegalStateException("No se pudo consultar Open-Meteo: " + exception.getMessage(), exception);
		} finally {
			if (conexion != null) {
				conexion.disconnect();
			}
		}
	}

	/** Codifica un parámetro de consulta con UTF-8. */
	private String codificar(String texto) {
		try {
			return URLEncoder.encode(texto, StandardCharsets.UTF_8.name());
		} catch (java.io.UnsupportedEncodingException exception) {
			throw new IllegalStateException("UTF-8 no está disponible.", exception);
		}
	}

	/** Obtiene una propiedad textual opcional de una respuesta JSON. */
	private String texto(JsonObject objeto, String propiedad) {
		JsonElement valor = objeto.get(propiedad);
		return valor == null || valor.isJsonNull() ? "" : valor.getAsString();
	}

	/** Obtiene una propiedad numérica obligatoria de una respuesta JSON. */
	private double numero(JsonObject objeto, String propiedad) {
		JsonElement valor = objeto.get(propiedad);
		if (valor == null || valor.isJsonNull()) {
			throw new IllegalStateException("Open-Meteo no devolvió el dato " + propiedad + ".");
		}
		return valor.getAsDouble();
	}

}
