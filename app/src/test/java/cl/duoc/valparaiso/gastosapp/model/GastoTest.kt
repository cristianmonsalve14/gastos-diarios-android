package cl.duoc.valparaiso.gastosapp.model

import org.junit.Test
import org.junit.Assert.*

class GastoTest {

    @Test
    fun testGastoCreation() {
        val gasto = Gasto(
            id = 1L,
            monto = 5000.0,
            descripcion = "Almuerzo",
            categoria = "Alimentación",
            fecha = "2025-12-03T10:00:00",
            fotoUrl = null
        )

        assertEquals(1L, gasto.id)
        assertEquals(5000.0, gasto.monto, 0.01)
        assertEquals("Almuerzo", gasto.descripcion)
        assertEquals("Alimentación", gasto.categoria)
    }

    @Test
    fun testGastoWithFoto() {
        val gasto = Gasto(
            id = 2L,
            monto = 3000.0,
            descripcion = "Cine",
            categoria = "Entretenimiento",
            fecha = "2025-12-03T15:30:00",
            fotoUrl = "https://example.com/foto.jpg"
        )

        assertNotNull(gasto.fotoUrl)
        assertEquals("https://example.com/foto.jpg", gasto.fotoUrl)
    }

    @Test
    fun testGastoSinFoto() {
        val gasto = Gasto(
            id = 3L,
            monto = 2000.0,
            descripcion = "Transporte",
            categoria = "Transporte",
            fecha = "2025-12-03T08:00:00",
            fotoUrl = null
        )

        assertNull(gasto.fotoUrl)
    }

    @Test
    fun testGastoIgualdad() {
        val gasto1 = Gasto(
            id = 1L,
            monto = 5000.0,
            descripcion = "Almuerzo",
            categoria = "Alimentación",
            fecha = "2025-12-03T10:00:00",
            fotoUrl = null
        )

        val gasto2 = Gasto(
            id = 1L,
            monto = 5000.0,
            descripcion = "Almuerzo",
            categoria = "Alimentación",
            fecha = "2025-12-03T10:00:00",
            fotoUrl = null
        )

        assertEquals(gasto1, gasto2)
    }

    @Test
    fun testGastoMontoDecimal() {
        val gasto = Gasto(
            id = 4L,
            monto = 7501.25,
            descripcion = "Compra",
            categoria = "Compras",
            fecha = "2025-12-03T12:00:00",
            fotoUrl = null
        )

        assertEquals(7501.25, gasto.monto, 0.01)
    }

    @Test
    fun testGastoValidoCategorias() {
        val categorias = listOf(
            "Alimentación", "Transporte", "Entretenimiento",
            "Compras", "Servicios", "Salud", "Educación", "Otros"
        )

        for (cat in categorias) {
            val gasto = Gasto(
                id = 1L,
                monto = 1000.0,
                descripcion = "Test",
                categoria = cat,
                fecha = "2025-12-03T10:00:00",
                fotoUrl = null
            )
            assertEquals(cat, gasto.categoria)
        }
    }

    @Test
    fun testGastoFechaString() {
        val fecha = "2025-12-03T14:30:45"
        val gasto = Gasto(
            id = 5L,
            monto = 4000.0,
            descripcion = "Cena",
            categoria = "Alimentación",
            fecha = fecha,
            fotoUrl = null
        )

        assertEquals(fecha, gasto.fecha)
    }
}