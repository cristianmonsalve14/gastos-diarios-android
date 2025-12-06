package cl.duoc.valparaiso.gastosapp.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Gasto(
    val id: Long, // El ID de un gasto existente nunca debería ser nulo.
    val monto: Double,
    val descripcion: String,
    val categoria: String,
    // El campo 'fecha' es un String, tal como viene del servidor.
    val fecha: String,
    val fotoUrl: String?
) {
    /**
     * Propiedad segura que convierte el String de la fecha a un objeto LocalDateTime.
     * La usaremos para cualquier manipulación de fechas en la UI.
     */
    val fechaAsLocalDateTime: LocalDateTime
        get() {
            return try {
                // Intenta convertir el String a un objeto LocalDateTime
                LocalDateTime.parse(fecha, DateTimeFormatter.ISO_DATE_TIME)
            } catch (e: Exception) {
                // Si falla, devuelve la fecha actual para evitar que la app se caiga.
                LocalDateTime.now()
            }
        }
}

// El resto de los data class no necesitan estar dentro del mismo archivo,
// pero los mantenemos por ahora para seguir tu estructura.

enum class CategoriaGasto(val displayName: String, val emoji: String) {
    ALIMENTACION("Alimentación", "🍔"),
    TRANSPORTE("Transporte", "🚌"),
    ENTRETENIMIENTO("Entretenimiento", "🎬"),
    COMPRAS("Compras", "🛒"),
    SERVICIOS("Servicios", "⚡"),
    SALUD("Salud", "🏥"),
    EDUCACION("Educación", "📚"),
    OTROS("Otros", "💼");
}

data class ResumenMensual(
    val totalGastado: Double = 0.0,
    val gastosPorCategoria: Map<String, Double> = emptyMap(),
    val promediosDiarios: Double = 0.0,
    val cantidadTransacciones: Int = 0
)

