package cl.duoc.valparaiso.gastosapp.model

import com.google.gson.annotations.SerializedName

// Respuesta de Open-Meteo
data class OpenMeteoResponse(
    val current: CurrentWeather,
    val timezone: String
)

data class CurrentWeather(
    val temperature_2m: Double,
    val weather_code: Int,
    val relative_humidity_2m: Int,
    val wind_speed_10m: Double,
    val time: String
)

// Modelo simplificado para UI
data class ClimaBrevedad(
    val temperatura: Double,
    val descripcion: String,
    val humedad: Int,
    val velocidadViento: Double,
    val ciudad: String,
    val icono: String
)

