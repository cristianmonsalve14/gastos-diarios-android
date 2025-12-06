package cl.duoc.valparaiso.gastosapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.duoc.valparaiso.gastosapp.ui.screens.*
import cl.duoc.valparaiso.gastosapp.viewmodel.AuthViewModel
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    gastosViewModel: GastosViewModel
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val startDestination = if (isLoggedIn) Route.Dashboard.path else Route.Login.path

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.Login.path) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Route.Dashboard.path) {
            DashboardScreen(
                navController = navController,
                gastosViewModel = gastosViewModel
            )
        }

        composable(Route.AgregarGasto.path) {
            AgregarGastoScreen(
                navController = navController,
                gastosViewModel = gastosViewModel
            )
        }

        composable(Route.Historial.path) {
            // ---- LA MEJORA ESTÁ AQUÍ ----
            // Ahora la pantalla de Historial recibe el ViewModel compartido,
            // permitiendo que el borrado funcione correctamente.
            HistorialScreen(
                navController = navController,
                gastosViewModel = gastosViewModel
            )
        }

        composable(Route.Configuracion.path) {
            ConfiguracionScreen(
                navController = navController
            )
        }
    }
}

