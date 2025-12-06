package cl.duoc.valparaiso.gastosapp.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class GastoRequest(

    val monto: Double = 0.0,
    val descripcion: String = "",
    val categoria: String = "Otros",
    val fecha: String,

) {
  }
