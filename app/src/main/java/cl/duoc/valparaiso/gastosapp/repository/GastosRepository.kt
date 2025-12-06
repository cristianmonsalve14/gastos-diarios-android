package cl.duoc.valparaiso.gastosapp.repository

import cl.duoc.valparaiso.gastosapp.api.ApiService
import cl.duoc.valparaiso.gastosapp.api.RetrofitClient
import cl.duoc.valparaiso.gastosapp.model.Gasto
// ¡IMPORTANTE! Importamos el nuevo modelo
import cl.duoc.valparaiso.gastosapp.model.GastoRequest

class GastoRepository {
    private val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)

    suspend fun obtenerGastos(): Result<List<Gasto>> = try {
        val gastos = apiService.obtenerGastos()
        Result.success(gastos)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun obtenerGastoPorId(id: Long): Result<Gasto> = try {
        val gasto = apiService.obtenerGastoPorId(id)
        Result.success(gasto)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // CORRECCIÓN CLAVE: La función ahora acepta un GastoRequest, como pide la API.
    suspend fun crearGasto(gasto: GastoRequest): Result<Gasto> = try {
        val nuevoGasto = apiService.crearGasto(gasto)
        Result.success(nuevoGasto)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun actualizarGasto(id: Long, gasto: Gasto): Result<Gasto> = try {
        val gastoActualizado = apiService.actualizarGasto(id, gasto)
        Result.success(gastoActualizado)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun eliminarGasto(id: Long): Result<Unit> = try {
        apiService.eliminarGasto(id)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun contarGastos(): Result<Long> = try {
        val response = apiService.contarGastos()
        val count = response["count"] ?: 0L
        Result.success(count)
    } catch (e: Exception) {
        Result.failure(e)
    }
}


