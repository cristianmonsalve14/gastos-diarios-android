package cl.duoc.valparaiso.gastosapp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.valparaiso.gastosapp.model.Gasto
import cl.duoc.valparaiso.gastosapp.model.GastoRequest
import cl.duoc.valparaiso.gastosapp.model.ResumenMensual
import cl.duoc.valparaiso.gastosapp.model.ClimaBrevedad
import cl.duoc.valparaiso.gastosapp.repository.GastoRepository
import cl.duoc.valparaiso.gastosapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter
import android.util.Log
import java.time.LocalDateTime


data class GastoFormUiState(
    val monto: String = "",
    val descripcion: String = "",
    val categoria: String = "Otros",
    val fotoComprobante: Bitmap? = null,
    val errores: Map<String, String> = emptyMap(),
    val isLoading: Boolean = false,
    val exito: Boolean = false
)

class GastosViewModel : ViewModel() {

    private val repository = GastoRepository()
    private val weatherRepository = WeatherRepository()

    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos: StateFlow<List<Gasto>> = _gastos.asStateFlow()

    private val _formUiState = MutableStateFlow(GastoFormUiState())
    val formUiState: StateFlow<GastoFormUiState> = _formUiState.asStateFlow()

    private val _resumenMensual = MutableStateFlow(ResumenMensual())
    val resumenMensual: StateFlow<ResumenMensual> = _resumenMensual.asStateFlow()

    private val _isLoadingGastos = MutableStateFlow(false)
    val isLoadingGastos: StateFlow<Boolean> = _isLoadingGastos.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // StateFlow para clima
    private val _climaActual = MutableStateFlow<ClimaBrevedad?>(null)
    val climaActual = _climaActual.asStateFlow()

    private val _isLoadingClima = MutableStateFlow(false)
    val isLoadingClima = _isLoadingClima.asStateFlow()

    init {
        cargarGastosDelBackend()
        cargarClima()
        viewModelScope.launch {
            gastos.collect { listaGastos ->
                actualizarResumen(listaGastos)
            }
        }
    }

    fun cargarGastosDelBackend() {
        _isLoadingGastos.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            repository.obtenerGastos().onSuccess { gastosRecibidos ->
                _gastos.value = gastosRecibidos
            }.onFailure { error ->
                _errorMessage.value = "Error al cargar gastos: ${error.message}"
            }
            _isLoadingGastos.value = false
        }
    }

    fun cargarClima(ciudad: String = "Valparaiso") {
        viewModelScope.launch {
            _isLoadingClima.value = true
            val (lat, lon) = weatherRepository.getCoordinatesByCityName(ciudad)
            val clima = weatherRepository.getWeatherByCoordinates(lat, lon, ciudad)
            _climaActual.value = clima
            _isLoadingClima.value = false
        }
    }

    fun onMontoChange(monto: String) {
        _formUiState.update { it.copy(monto = monto, errores = it.errores.minus("monto")) }
    }

    fun onDescripcionChange(descripcion: String) {
        _formUiState.update { it.copy(descripcion = descripcion, errores = it.errores.minus("descripcion")) }
    }

    fun onCategoriaChange(categoria: String) {
        _formUiState.update { it.copy(categoria = categoria) }
    }

    fun onFotoComprobanteChange(bitmap: Bitmap?) {
        _formUiState.update { it.copy(fotoComprobante = bitmap) }
    }

    private fun validarFormulario(): Boolean {
        val errores = mutableMapOf<String, String>()
        val estado = _formUiState.value
        if (estado.monto.toDoubleOrNull() == null || estado.monto.toDouble() <= 0) {
            errores["monto"] = "Ingrese un monto válido mayor a 0"
        }
        if (estado.descripcion.isBlank() || estado.descripcion.length < 3) {
            errores["descripcion"] = "Descripción debe tener al menos 3 caracteres"
        }
        _formUiState.update { it.copy(errores = errores) }
        return errores.isEmpty()
    }

    fun limpiarFormulario() {
        _formUiState.value = GastoFormUiState()
    }

    fun guardarGasto() { // Ya no necesitamos el Context aquí
        if (!validarFormulario()) return

        _formUiState.update { it.copy(isLoading = true, errores = emptyMap()) }

        viewModelScope.launch {
            try {
                val fechaAhora = LocalDateTime.now()

                // ¡ESTA ES LA LÍNEA 112 CORREGIDA!
                // Usamos el formateador del paquete java.time.format
                val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

                // Formateamos la fecha a un String y le quitamos los decimales de los segundos.
                val fechaFormateada = fechaAhora.format(formatter).substringBefore(".")
                // CORRECCIÓN CLAVE: Creamos un GastoRequest, no un Gasto.
                val nuevoGastoRequest = GastoRequest(
                    monto = _formUiState.value.monto.toDouble(),
                    descripcion = _formUiState.value.descripcion.trim(),
                    categoria = _formUiState.value.categoria,
                    fecha = fechaFormateada.toString()
                )
                Log.d("GastoViewModel", "Nuevo GastoRequest creado: $nuevoGastoRequest")
                // Llamamos a la función del repositorio que ahora espera un GastoRequest
                repository.crearGasto(nuevoGastoRequest).onSuccess { gastoCreado ->
                    _gastos.value = _gastos.value + gastoCreado
                    limpiarYExito()
                }.onFailure { error ->
                    manejarError("Error al guardar el gasto: ${error.message}")
                }
            } catch (e: Exception) {
                manejarError("Error inesperado: ${e.message}")
            }
        }
    }

    private fun limpiarYExito() {
        _formUiState.update { it.copy(isLoading = false, exito = true) }
    }

    private fun manejarError(mensaje: String) {
        _formUiState.update {
            it.copy(
                errores = mapOf("general" to mensaje),
                isLoading = false
            )
        }
    }

    private fun actualizarResumen(gastos: List<Gasto>) {
        val total = gastos.sumOf { it.monto }
        val porCategoria = gastos.groupBy { it.categoria }
            .mapValues { (_, gastos) -> gastos.sumOf { it.monto } }

        _resumenMensual.value = ResumenMensual(
            totalGastado = total,
            gastosPorCategoria = porCategoria,
            promediosDiarios = if (gastos.isNotEmpty()) total / 30.0 else 0.0,
            cantidadTransacciones = gastos.size
        )
    }

    fun eliminarGasto(gastoId: Long) {
        viewModelScope.launch {
            repository.eliminarGasto(gastoId).onSuccess {
                _gastos.value = _gastos.value.filter { it.id != gastoId }
            }.onFailure { error ->
                _errorMessage.value = "Error al eliminar: ${error.message}"
            }
        }
    }
}





