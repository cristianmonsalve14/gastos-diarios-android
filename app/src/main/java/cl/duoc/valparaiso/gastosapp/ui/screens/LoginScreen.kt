package cl.duoc.valparaiso.gastosapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.valparaiso.gastosapp.navigation.Route
import cl.duoc.valparaiso.gastosapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    // --- ¡ESTA ES LA CORRECCIÓN CLAVE! ---
    // Añadimos el parámetro que AppNavigation nos está pasando.
    authViewModel: AuthViewModel
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Este efecto se activa cuando el estado de login en el ViewModel cambia a 'true'
    LaunchedEffect(authViewModel.isLoggedIn.collectAsState().value) {
        if (authViewModel.isLoggedIn.value) {
            // Si el login es exitoso, navega al Dashboard y limpia la pila de navegación
            // para que el usuario no pueda volver a la pantalla de login con el botón de "atrás".
            navController.navigate(Route.Dashboard.path) {
                popUpTo(Route.Login.path) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (error != null) {
            Text(
                text = error!!,
                color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Lógica de validación
                if (usuario.isNotBlank() && password.isNotBlank()) {
                    isLoading = true
                    error = null
                    // Aquí iría tu lógica real de API. Por ahora, simulamos un login exitoso.
                    // En un caso real, la llamada a la API decidiría si se llama a onLoginSuccess()
                    authViewModel.onLoginSuccess()
                } else {
                    error = "Por favor, ingrese usuario y contraseña."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            } else {
                Text("Ingresar")
            }
        }
    }
}
