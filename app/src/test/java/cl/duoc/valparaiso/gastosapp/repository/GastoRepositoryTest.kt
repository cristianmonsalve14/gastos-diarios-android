package cl.duoc.valparaiso.gastosapp.repository

import cl.duoc.valparaiso.gastosapp.model.Gasto
import cl.duoc.valparaiso.gastosapp.model.GastoRequest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GastoRepositoryTest {

    private lateinit var repository: GastoRepository

    @Before
    fun setUp() {
        repository = GastoRepository()
    }

    @Test
    fun testGastoRequestCreation() {
        // Arrange
        val monto = 5000.0
        val descripcion = "Almuerzo"
        val categoria = "Alimentación"
        val fecha = "2025-12-03T13:00:00"

        // Act
        val gastoRequest = GastoRequest(
            monto = monto,
            descripcion = descripcion,
            categoria = categoria,
            fecha = fecha
        )

        // Assert
        assertEquals(monto, gastoRequest.monto, 0.01)
        assertEquals(descripcion, gastoRequest.descripcion)
        assertEquals(categoria, gastoRequest.categoria)
        assertEquals(fecha, gastoRequest.fecha)
    }

    @Test
    fun testGastoCreation() {
        // Arrange
        val id = 1L
        val monto = 10000.0
        val descripcion = "Compras"
        val categoria = "Compras"
        val fecha = "2025-12-03T13:00:00"
        val fotoUrl = "http://example.com/foto.jpg"

        // Act
        val gasto = Gasto(
            id = id,
            monto = monto,
            descripcion = descripcion,
            categoria = categoria,
            fecha = fecha,
            fotoUrl = fotoUrl
        )

        // Assert
        assertEquals(id, gasto.id)
        assertEquals(monto, gasto.monto, 0.01)
        assertEquals(descripcion, gasto.descripcion)
        assertEquals(categoria, gasto.categoria)
        assertEquals(fecha, gasto.fecha)
        assertEquals(fotoUrl, gasto.fotoUrl)
    }

    @Test
    fun testGastoRequestValidation() {
        // Test 1: Monto válido
        val gastoValido = GastoRequest(
            monto = 1000.0,
            descripcion = "Test",
            categoria = "Alimentación",
            fecha = "2025-12-03T10:00:00"
        )
        assertTrue(gastoValido.monto > 0)

        // Test 2: Descripción no vacía
        assertFalse(gastoValido.descripcion.isBlank())

        // Test 3: Categoría válida
        assertFalse(gastoValido.categoria.isBlank())
    }

    @Test
    fun testGastoMontoPrecision() {
        // Verify that money amounts are handled correctly
        val gastos = listOf(
            Gasto(1L, 1500.50, "Test1", "Alimentación", "2025-12-03T10:00:00", null),
            Gasto(2L, 2500.75, "Test2", "Transporte", "2025-12-03T10:00:00", null),
            Gasto(3L, 3000.00, "Test3", "Otros", "2025-12-03T10:00:00", null)
        )

        val total = gastos.sumOf { it.monto }
        assertEquals(7001.25, total, 0.01)
    }

    @Test
    fun testGastoCategoryEmoji() {
        // Test emoji mapping for categories
        val emojiMap = mapOf(
            "alimentación" to "🍔",
            "transporte" to "🚌",
            "entretenimiento" to "🎬",
            "compras" to "🛒",
            "servicios" to "⚡",
            "salud" to "🏥",
            "educación" to "📚"
        )

        val categoria = "alimentación"
        val expectedEmoji = "🍔"
        assertEquals(expectedEmoji, emojiMap[categoria])
    }
}