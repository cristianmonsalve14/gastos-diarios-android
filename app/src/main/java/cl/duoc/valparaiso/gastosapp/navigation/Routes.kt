package cl.duoc.valparaiso.gastosapp.navigation
sealed class Route(val path: String) {
    data object Root : Route("root")
    data object Dashboard : Route("dashboard")
    data object AgregarGasto : Route("agregar_gasto")
    data object Historial : Route("historial")
    data object Configuracion : Route("configuracion")
}