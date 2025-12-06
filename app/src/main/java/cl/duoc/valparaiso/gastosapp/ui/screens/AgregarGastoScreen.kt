package cl.duoc.valparaiso.gastosapp.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarGastoScreen(
    navController: NavController,
    gastosViewModel: GastosViewModel = viewModel()
) {
    // ---- ESTADOS ----
    val context = LocalContext.current // Contexto necesario para guardar la foto
    val formState by gastosViewModel.formUiState.collectAsState()
    var expandedCategoria by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var fotoCapturada by remember { mutableStateOf<Bitmap?>(null) }
    val categorias = listOf(
        "Alimentación", "Transporte", "Entretenimiento", "Compras",
        "Servicios", "Salud", "Educación", "Otros"
    )

    // ---- NAVEGACIÓN Y LÓGICA DE UI ----

    // Efecto que se dispara cuando el ViewModel indica que el guardado fue exitoso.
    LaunchedEffect(formState.exito) {
        if (formState.exito) {
            navController.popBackStack() // Vuelve a la pantalla anterior
            gastosViewModel.limpiarFormulario() // Resetea el formulario para la próxima vez
        }
    }

    // Si `showCamera` es true, muestra la pantalla de la cámara en lugar del formulario.
    if (showCamera) {
        CameraScreen(
            onPhotoTaken = { bitmap ->
                fotoCapturada = bitmap // Guardamos el Bitmap recibido
                showCamera = false // Cerramos la cámara para volver al formulario
            }
        )
        // Usamos 'return' para que el resto del Composable (el formulario) no se ejecute.
        return
    }

    // ---- INTERFAZ DE USUARIO (FORMULARIO) ----
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Gasto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- CAMPO MONTO ---
            OutlinedTextField(
                value = formState.monto,
                onValueChange = gastosViewModel::onMontoChange,
                label = { Text("Monto") },
                leadingIcon = { Icon(Icons.Default.MonetizationOn, contentDescription = null) },
                isError = formState.errores.containsKey("monto"),
                supportingText = {
                    formState.errores["monto"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // --- CAMPO DESCRIPCIÓN ---
            OutlinedTextField(
                value = formState.descripcion,
                onValueChange = gastosViewModel::onDescripcionChange,
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                isError = formState.errores.containsKey("descripcion"),
                supportingText = {
                    formState.errores["descripcion"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // --- SELECTOR DE CATEGORÍA ---
            ExposedDropdownMenuBox(
                expanded = expandedCategoria,
                onExpandedChange = { expandedCategoria = !expandedCategoria }
            ) {
                OutlinedTextField(
                    value = formState.categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoria) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedCategoria,
                    onDismissRequest = { expandedCategoria = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria) }, // Simplificado para claridad
                            onClick = {
                                gastosViewModel.onCategoriaChange(categoria)
                                expandedCategoria = false
                            }
                        )
                    }
                }
            }

            // --- MENSAJE DE ERROR GENERAL ---
            formState.errores["general"]?.let { error ->
                Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(vertical = 8.dp))
            }

            // --- BOTÓN CÁMARA ---
            Button(
                onClick = { showCamera = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Capturar Comprobante")
            }

            // --- VISTA PREVIA DE LA FOTO ---
            if (fotoCapturada != null) {
                Image(
                    bitmap = fotoCapturada!!.asImageBitmap(),
                    contentDescription = "Comprobante capturado",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.weight(1f)) // Empuja el botón de guardar hacia abajo

            // --- BOTÓN GUARDAR ---
            Button(
                onClick = {
                    // ¡LÓGICA CORREGIDA!
                    // 1. Pasamos el bitmap de la foto al ViewModel.
                    //gastosViewModel.onFotoComprobanteChange(fotoCapturada)
                    // 2. Llamamos a guardar, pasándole el contexto necesario para crear el archivo.
                    gastosViewModel.guardarGasto()
                },
                enabled = !formState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (formState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Guardar Gasto")
                }
            }
        }
    }
}
