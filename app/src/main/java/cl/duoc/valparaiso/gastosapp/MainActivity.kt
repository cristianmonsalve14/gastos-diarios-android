package cl.duoc.valparaiso.gastosapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cl.duoc.valparaiso.gastosapp.navigation.AppNavigation
import cl.duoc.valparaiso.gastosapp.ui.theme.GastosAppTheme
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Permiso de cámara otorgado o denegado
        // La app continuará funcionando de igual manera
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permiso de cámara si no lo tiene
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

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
