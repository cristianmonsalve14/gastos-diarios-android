package cl.duoc.valparaiso.gastosapp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    // Estado para guardar el bitmap de la foto capturada. La UI observará este estado.
    val capturedBitmap = mutableStateOf<Bitmap?>(null)

    /**
     * Inicia el proceso de captura de la foto.
     * Esta función se llama desde la UI y delega el trabajo pesado a otros métodos.
     */
    fun takePhoto(
        context: Context,
        cameraController: LifecycleCameraController
    ) {
        val mainExecutor = ContextCompat.getMainExecutor(context)

        // CameraX necesita que la llamada inicial se haga en el hilo principal.
        cameraController.takePicture(
            mainExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    // ¡Éxito! La imagen está en memoria.
                    // Ahora procesamos la imagen en un hilo de fondo para no causar un ANR.
                    processCapturedImage(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraViewModel", "Error al capturar la imagen: ${exception.message}", exception)
                }
            }
        )
    }

    /**
     * Procesa la imagen capturada en un hilo de fondo (IO) usando corrutinas.
     * Esto evita que la interfaz de usuario se congele (ANR).
     */
    private fun processCapturedImage(image: ImageProxy) {
        viewModelScope.launch(Dispatchers.IO) { // <-- ¡LA CLAVE! Se ejecuta en un hilo secundario.
            try {
                // 1. Convertir el objeto ImageProxy (formato de cámara) a un Bitmap (formato de imagen estándar).
                val sourceBitmap = imageProxyToBitmap(image)

                // 2. Corregir la rotación. A menudo, la imagen capturada está de lado.
                val rotationDegrees = image.imageInfo.rotationDegrees
                val rotatedBitmap = rotateBitmapIfNeeded(sourceBitmap, rotationDegrees)

                // 3. Una vez que la imagen está lista, volvemos al hilo principal para actualizar la UI.
                launch(Dispatchers.Main) {
                    capturedBitmap.value = rotatedBitmap
                }
            } catch (e: Exception) {
                Log.e("CameraViewModel", "Error procesando la imagen: ${e.message}", e)
            } finally {
                // 4. ¡MUY IMPORTANTE! Cierra el ImageProxy. Si no lo haces, la cámara no podrá tomar más fotos.
                image.close()
            }
        }
    }

    /**
     * Función de utilidad para convertir un ImageProxy a un Bitmap.
     */
    private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planeProxy = image.planes[0]
        val buffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * Función de utilidad para rotar el bitmap si es necesario.
     */
    private fun rotateBitmapIfNeeded(source: Bitmap, degrees: Int): Bitmap {
        if (degrees == 0) return source
        val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }
}
