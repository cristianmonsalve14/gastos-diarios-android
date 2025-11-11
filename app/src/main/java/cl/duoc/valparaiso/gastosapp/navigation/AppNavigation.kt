package cl.duoc.valparaiso.gastosapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.duoc.valparaiso.gastosapp.ui.screens.*
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    gastosViewModel: GastosViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Route.Dashboard.path
    ) {
        composable(Route.Dashboard.path) {
            DashboardScreen(navController, gastosViewModel)
        }

        composable(Route.AgregarGasto.path) {
            AgregarGastoScreen(navController, gastosViewModel)
        }

        // Pantallas temporales (las crearemos después)
        composable(Route.Historial.path) {
            DashboardScreen(navController, gastosViewModel)
        }

        composable(Route.Configuracion.path) {
            DashboardScreen(navController, gastosViewModel)
        }
    }
}

