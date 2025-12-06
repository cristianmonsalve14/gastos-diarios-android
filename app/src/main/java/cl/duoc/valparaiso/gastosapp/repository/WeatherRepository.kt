package cl.duoc.valparaiso.gastosapp.repository

import cl.duoc.valparaiso.gastosapp.api.OpenMeteoRetrofitClient
import cl.duoc.valparaiso.gastosapp.model.ClimaBrevedad

class WeatherRepository {

    // Función para convertir código de clima a descripción
    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Cielo despejado"
            1, 2 -> "Mayormente despejado"
            3 -> "Nublado"
            45, 48 -> "Neblina"
            51, 53, 55 -> "Llovizna"
            61, 63, 65 -> "Lluvia"
            71, 73, 75 -> "Nieve"
            80, 81, 82 -> "Lluvia fuerte"
            95, 96, 99 -> "Tormenta"
            else -> "Desconocido"
        }
    }

    // Función para convertir código a emoji
    private fun getWeatherEmoji(code: Int): String {
        return when (code) {
            0 -> "☀️"
            1, 2 -> "🌤️"
            3 -> "☁️"
            45, 48 -> "🌫️"
            51, 53, 55 -> "🌦️"
            61, 63, 65 -> "🌧️"
            71, 73, 75 -> "❄️"
            80, 81, 82 -> "⛈️"
            95, 96, 99 -> "⚡"
            else -> "🌍"
        }
    }

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
        cityName: String = "Santiago"
    ): ClimaBrevedad? {
        return try {
            val response = OpenMeteoRetrofitClient.openMeteoApiService.getWeather(
                latitude = latitude,
                longitude = longitude
            )

            val current = response.current
            val description = getWeatherDescription(current.weather_code)
            val emoji = getWeatherEmoji(current.weather_code)

            ClimaBrevedad(
                temperatura = current.temperature_2m,
                descripcion = description,
                humedad = current.relative_humidity_2m,
                velocidadViento = current.wind_speed_10m,
                ciudad = cityName,
                icono = emoji
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Coordenadas de principales ciudades de Chile
    fun getCoordinatesByCityName(city: String): Pair<Double, Double> {
        return when (city.lowercase()) {
            "santiago" -> Pair(-33.8688, -151.2093)
            "valparaiso" -> Pair(-33.0395, -71.5497)
            "concepcion" -> Pair(-36.8201, -73.0544)
            "la serena" -> Pair(-29.9027, -71.2519)
            "punta arenas" -> Pair(-53.1638, -70.9123)
            else -> Pair(-33.8688, -151.2093) // Por defecto Santiago
        }
    }
}

