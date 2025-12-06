package cl.duoc.valparaiso.gastosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cl.duoc.valparaiso.gastosapp.navigation.AppNavigation
import cl.duoc.valparaiso.gastosapp.ui.theme.GastosAppTheme
import cl.duoc.valparaiso.gastosapp.viewmodel.AuthViewModel
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

class MainActivity : ComponentActivity() {

    // Inicializamos ambos ViewModels a nivel de Actividad
    private val authViewModel: AuthViewModel by viewModels()
    private val gastosViewModel: GastosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GastosAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Pasamos ambos ViewModels al sistema de navegación
                    AppNavigation(
                        navController = navController,
                        authViewModel = authViewModel,
                        gastosViewModel = gastosViewModel
                    )
                }
            }
        }
    }
}

