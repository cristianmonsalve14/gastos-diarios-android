package cl.duoc.valparaiso.gastosapp.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Gasto(
    val id: String = "",
    val monto: Double = 0.0,
    val descripcion: String = "",
    val categoria: CategoriaGasto = CategoriaGasto.OTROS,
    val fecha: LocalDateTime = LocalDateTime.now(),
    val ubicacion: String? = null,
    val fotoComprobante: String? = null
) {
    fun formatearMonto(): String = "$${String.format("%.0f", monto)}"
    fun formatearFecha(): String = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    fun formatearFechaCompleta(): String = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
}

enum class CategoriaGasto(val displayName: String, val emoji: String) {
    COMIDA("Comida", "🍔"),
    TRANSPORTE("Transporte", "🚌"),
    ENTRETENIMIENTO("Entretenimiento", "🎬"),
    COMPRAS("Compras", "🛒"),
    SERVICIOS("Servicios", "⚡"),
    SALUD("Salud", "🏥"),
    EDUCACION("Educación", "📚"),
    OTROS("Otros", "💼");

    override fun toString(): String = "$emoji $displayName"
}

data class ResumenMensual(
    val totalGastado: Double = 0.0,
    val gastosPorCategoria: Map<CategoriaGasto, Double> = emptyMap(),
    val promediosDiarios: Double = 0.0,
    val cantidadTransacciones: Int = 0
)