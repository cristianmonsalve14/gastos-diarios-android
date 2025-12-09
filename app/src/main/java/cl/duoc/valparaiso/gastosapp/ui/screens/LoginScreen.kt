package cl.duoc.valparaiso.gastosapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.valparaiso.gastosapp.navigation.Route
import cl.duoc.valparaiso.gastosapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var isRegistering by remember { mutableStateOf(false) } // Toggle entre Login y Registro

    // Este efecto se activa cuando el estado de login en el ViewModel cambia a 'true'
    LaunchedEffect(authViewModel.isLoggedIn.collectAsState().value) {
        if (authViewModel.isLoggedIn.value) {
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
        // Título dinámico
        Text(
            text = if (isRegistering) "Crear Cuenta" else "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isRegistering) "Regístrate para comenzar" else "Bienvenido de nuevo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Campo Email (solo en registro)
        if (isRegistering) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Campo Usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo Confirmar Contraseña (solo en registro)
        if (isRegistering) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Mensaje de error
        if (error != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón principal (Login o Registro)
        Button(
            onClick = {
                error = null

                if (isRegistering) {
                    // Validación para registro
                    when {
                        usuario.isBlank() -> error = "El usuario no puede estar vacío"
                        usuario.length < 3 -> error = "El usuario debe tener al menos 3 caracteres"
                        email.isBlank() -> error = "El correo no puede estar vacío"
                        !email.contains("@") -> error = "Ingrese un correo válido"
                        password.isBlank() -> error = "La contraseña no puede estar vacía"
                        password.length < 6 -> error = "La contraseña debe tener al menos 6 caracteres"
                        password != confirmPassword -> error = "Las contraseñas no coinciden"
                        else -> {
                            isLoading = true
                            // Usuario creado exitosamente, hacer login automático
                            authViewModel.onLoginSuccess()
                        }
                    }
                } else {
                    // Validación para login
                    when {
                        usuario.isBlank() -> error = "El usuario no puede estar vacío"
                        password.isBlank() -> error = "La contraseña no puede estar vacía"
                        else -> {
                            isLoading = true
                            authViewModel.onLoginSuccess()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(if (isRegistering) "Crear Cuenta" else "Ingresar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para cambiar entre Login y Registro
        TextButton(
            onClick = {
                isRegistering = !isRegistering
                error = null
                confirmPassword = ""
                email = ""
            }
        ) {
            Text(
                text = if (isRegistering)
                    "¿Ya tienes cuenta? Inicia sesión"
                else
                    "¿No tienes cuenta? Regístrate",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}