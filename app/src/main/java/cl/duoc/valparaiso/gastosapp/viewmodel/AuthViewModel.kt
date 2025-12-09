package cl.duoc.valparaiso.gastosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Estado privado para saber si el usuario está logueado.
    // Inicia en 'false' por defecto.
    private val _isLoggedIn = MutableStateFlow(false)

    // Estado público e inmutable que la UI observará.
    val isLoggedIn = _isLoggedIn.asStateFlow()

    // Estado para almacenar información del usuario actual (opcional)
    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser = _currentUser.asStateFlow()

    /**
     * Función que se llamará cuando el login sea exitoso.
     */
    fun onLoginSuccess() {
        _isLoggedIn.value = true
    }

    /**
     * Función para cerrar la sesión.
     */
    fun onLogout() {
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    /**
     * Función para iniciar sesión (simulada, conectar con tu API aquí)
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            // Simular llamada a API
            delay(1000)

            // Aquí iría tu lógica real de API
            // val response = authRepository.login(username, password)

            // Por ahora, simulamos un login exitoso
            _currentUser.value = UserData(
                id = 1,
                username = username,
                email = "$username@example.com"
            )
            onLoginSuccess()
        }
    }

    /**
     * Función para registrar un nuevo usuario (simulada, conectar con tu API aquí)
     */
    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            // Simular llamada a API
            delay(1000)

            // Aquí iría tu lógica real de API de registro
            // val response = authRepository.register(username, email, password)

            // Por ahora, simulamos un registro exitoso
            _currentUser.value = UserData(
                id = System.currentTimeMillis(),
                username = username,
                email = email
            )
        }
    }
}

/**
 * Modelo de datos para el usuario (opcional, puedes usarlo o no)
 */
data class UserData(
    val id: Long,
    val username: String,
    val email: String
)