package cl.duoc.valparaiso.gastosapp.api

import cl.duoc.valparaiso.gastosapp.model.Gasto
import cl.duoc.valparaiso.gastosapp.model.GastoRequest
import retrofit2.http.*

interface ApiService {
    // GET - Obtener todos los gastos
    @GET("gastos")
    suspend fun obtenerGastos(): List<Gasto>

    // GET - Obtener gasto por ID
    @GET("gastos/{id}")
    suspend fun obtenerGastoPorId(@Path("id") id: Long): Gasto

    // POST - Crear nuevo gasto
    @POST("gastos")
    suspend fun crearGasto(@Body gasto: GastoRequest): Gasto

    // PUT - Actualizar gasto
    @PUT("gastos/{id}")
    suspend fun actualizarGasto(@Path("id") id: Long, @Body gasto: Gasto): Gasto

    // DELETE - Eliminar gasto
    @DELETE("gastos/{id}")
    suspend fun eliminarGasto(@Path("id") id: Long)

    // GET - Contar total de gastos
    @GET("gastos/count")
    suspend fun contarGastos(): Map<String, Long>
}