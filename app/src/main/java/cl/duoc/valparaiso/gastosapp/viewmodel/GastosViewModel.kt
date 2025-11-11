package cl.duoc.valparaiso.gastosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.valparaiso.gastosapp.model.Gasto
import cl.duoc.valparaiso.gastosapp.model.CategoriaGasto
import cl.duoc.valparaiso.gastosapp.model.ResumenMensual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

data class GastoFormUiState(
    val monto: String = "",
    val descripcion: String = "",
    val categoria: CategoriaGasto = CategoriaGasto.OTROS,
    val errores: Map<String, String> = emptyMap(),
    val isLoading: Boolean = false
)

class GastosViewModel : ViewModel() {

    // Lista de gastos
    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos = _gastos.asStateFlow()

    // Estado del formulario
    private val _formUiState = MutableStateFlow(GastoFormUiState())
    val formUiState = _formUiState.asStateFlow()

    // Resumen calculado
    private val _resumenMensual = MutableStateFlow(ResumenMensual())
    val resumenMensual = _resumenMensual.asStateFlow()

    init {
        cargarGastosEjemplo()

        viewModelScope.launch {
            gastos.collect { listaGastos ->
                actualizarResumen(listaGastos)
            }
        }
    }

    // Funciones del formulario
    fun onMontoChange(monto: String) {
        _formUiState.value = _formUiState.value.copy(
            monto = monto,
            errores = _formUiState.value.errores.toMutableMap().apply { remove("monto") }
        )
    }

    fun onDescripcionChange(descripcion: String) {
        _formUiState.value = _formUiState.value.copy(
            descripcion = descripcion,
            errores = _formUiState.value.errores.toMutableMap().apply { remove("descripcion") }
        )
    }

    fun onCategoriaChange(categoria: CategoriaGasto) {
        _formUiState.value = _formUiState.value.copy(categoria = categoria)
    }

    fun validarFormulario(): Boolean {
        val errores = mutableMapOf<String, String>()
        val estado = _formUiState.value

        val monto = estado.monto.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            errores["monto"] = "Ingrese un monto válido mayor a 0"
        }

        if (estado.descripcion.isBlank()) {
            errores["descripcion"] = "La descripción es obligatoria"
        } else if (estado.descripcion.length < 3) {
            errores["descripcion"] = "Descripción debe tener al menos 3 caracteres"
        }

        _formUiState.value = estado.copy(errores = errores)
        return errores.isEmpty()
    }

    fun guardarGasto() {
        if (!validarFormulario()) return

        _formUiState.value = _formUiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val nuevoGasto = Gasto(
                    id = UUID.randomUUID().toString(),
                    monto = _formUiState.value.monto.toDouble(),
                    descripcion = _formUiState.value.descripcion.trim(),
                    categoria = _formUiState.value.categoria,
                    fecha = LocalDateTime.now()
                )

                kotlinx.coroutines.delay(500) // Simular guardado

                _gastos.value = _gastos.value + nuevoGasto
                limpiarFormulario()

            } catch (e: Exception) {
                _formUiState.value = _formUiState.value.copy(
                    errores = mapOf("general" to "Error al guardar: ${e.message}"),
                    isLoading = false
                )
            }
        }
    }

    fun limpiarFormulario() {
        _formUiState.value = GastoFormUiState()
    }

    fun eliminarGasto(gastoId: String) {
        _gastos.value = _gastos.value.filter { it.id != gastoId }
    }

    private fun actualizarResumen(gastos: List<Gasto>) {
        val total = gastos.sumOf { it.monto }
        val porCategoria = gastos.groupBy { it.categoria }
            .mapValues { (_, gastos) -> gastos.sumOf { it.monto } }

        _resumenMensual.value = ResumenMensual(
            totalGastado = total,
            gastosPorCategoria = porCategoria,
            promediosDiarios = if (gastos.isNotEmpty()) total / 30 else 0.0,
            cantidadTransacciones = gastos.size
        )
    }

    private fun cargarGastosEjemplo() {
        val gastosEjemplo = listOf(
            Gasto(
                id = "1",
                monto = 15000.0,
                descripcion = "Almuerzo en restaurante",
                categoria = CategoriaGasto.COMIDA,
                fecha = LocalDateTime.now().minusDays(1)
            ),
            Gasto(
                id = "2",
                monto = 2500.0,
                descripcion = "Transporte público",
                categoria = CategoriaGasto.TRANSPORTE,
                fecha = LocalDateTime.now().minusHours(3)
            ),
            Gasto(
                id = "3",
                monto = 35000.0,
                descripcion = "Compras supermercado",
                categoria = CategoriaGasto.COMPRAS,
                fecha = LocalDateTime.now().minusDays(2)
            )
        )
        _gastos.value = gastosEjemplo
    }
}