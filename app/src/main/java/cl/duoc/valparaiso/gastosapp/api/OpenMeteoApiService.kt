package cl.duoc.valparaiso.gastosapp.api

import cl.duoc.valparaiso.gastosapp.model.OpenMeteoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApiService {

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m",
        @Query("timezone") timezone: String = "auto"
    ): OpenMeteoResponse
}
