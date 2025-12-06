package cl.duoc.valparaiso.gastosapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenMeteoRetrofitClient {
    private const val BASE_URL = "https://api.open-meteo.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openMeteoApiService: OpenMeteoApiService = retrofit.create(OpenMeteoApiService::class.java)
}

