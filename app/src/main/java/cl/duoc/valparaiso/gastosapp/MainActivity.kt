package cl.duoc.valparaiso.gastosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cl.duoc.valparaiso.gastosapp.navigation.AppNavigation
import cl.duoc.valparaiso.gastosapp.ui.theme.GastosAppTheme
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GastosAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // --------- CREACIÓN CLAVE ---------
                    val navController = rememberNavController()
                    val gastosViewModel: GastosViewModel = viewModel()

                    AppNavigation(
                        navController = navController,
                        gastosViewModel = gastosViewModel
                    )
                }
            }
        }
    }
}

