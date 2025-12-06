package cl.duoc.valparaiso.gastosapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    // Estado privado para saber si el usuario está logueado.
    // Inicia en 'false' por defecto.
    private val _isLoggedIn = MutableStateFlow(false)

    // Estado público e inmutable que la UI observará.
    val isLoggedIn = _isLoggedIn.asStateFlow()

    /**
     * Función que se llamará cuando el login sea exitoso.
     */
    fun onLoginSuccess() {
        _isLoggedIn.value = true
    }

    /**
     * Función que se llamará para cerrar la sesión.
     */
    fun onLogout() {
        _isLoggedIn.value = false
    }
}
