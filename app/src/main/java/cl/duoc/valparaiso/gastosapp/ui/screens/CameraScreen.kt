package cl.duoc.valparaiso.gastosapp.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.valparaiso.gastosapp.viewmodel.CameraViewModel

@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = viewModel(),
    onPhotoTaken: (Bitmap) -> Unit // Callback para devolver la foto a la pantalla anterior
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    // Estado para gestionar si tenemos el permiso de cámara
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher para solicitar el permiso si no lo tenemos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> hasPermission = isGranted }
    )

    // Efecto que se ejecuta una vez para solicitar el permiso
    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Observamos el estado del bitmap capturado desde el ViewModel
    val capturedBitmap by cameraViewModel.capturedBitmap

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasPermission) {
            if (capturedBitmap == null) {
                // --- VISTA DE CÁMARA ACTIVA ---
                // Muestra la vista previa de la cámara
                AndroidView(
                    factory = { ctx ->
                        PreviewView(ctx).apply {
                            controller = cameraController
                            cameraController.bindToLifecycle(lifecycleOwner)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Botón para tomar la foto
                FloatingActionButton(
                    onClick = {
                        // La UI solo notifica al ViewModel. No hace ningún trabajo pesado.
                        cameraViewModel.takePhoto(context, cameraController)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Camera, contentDescription = "Tomar Foto")
                }
            } else {
                // --- VISTA DE FOTO CAPTURADA ---
                // Muestra la imagen que se capturó
                Image(
                    bitmap = capturedBitmap!!.asImageBitmap(),
                    contentDescription = "Foto Capturada",
                    modifier = Modifier.fillMaxSize()
                )

                // Botón para aceptar la foto
                Button(
                    onClick = {
                        onPhotoTaken(capturedBitmap!!)
                        // Opcional: navegar hacia atrás
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text("Aceptar")
                }

                // Botón para reintentar
                Button(
                    onClick = {
                        // Limpiamos el estado en el ViewModel para volver a la vista de cámara
                        cameraViewModel.capturedBitmap.value = null
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text("Reintentar")
                }
            }
        } else {
            // --- VISTA SIN PERMISOS ---
            Text(
                text = "Se necesita permiso de cámara para usar esta función.",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
