package cl.duoc.valparaiso.gastosapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.valparaiso.gastosapp.model.CategoriaGasto
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarGastoScreen(
    navController: NavController,
    gastosViewModel: GastosViewModel = viewModel()
) {
    val formState by gastosViewModel.formUiState.collectAsState()
    var expandedCategoria by remember { mutableStateOf(false) }

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
            // Campo Monto
            OutlinedTextField(
                value = formState.monto,
                onValueChange = gastosViewModel::onMontoChange,
                label = { Text("Monto") },
                leadingIcon = {
                    Icon(Icons.Default.MonetizationOn, contentDescription = null)
                },
                isError = formState.errores.containsKey("monto"),
                supportingText = {
                    formState.errores["monto"]?.let { error ->
                        Text(error, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Descripción
            OutlinedTextField(
                value = formState.descripcion,
                onValueChange = gastosViewModel::onDescripcionChange,
                label = { Text("Descripción") },
                leadingIcon = {
                    Icon(Icons.Default.Description, contentDescription = null)
                },
                isError = formState.errores.containsKey("descripcion"),
                supportingText = {
                    formState.errores["descripcion"]?.let { error ->
                        Text(error, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de Categoría
            ExposedDropdownMenuBox(
                expanded = expandedCategoria,
                onExpandedChange = { expandedCategoria = !expandedCategoria }
            ) {
                OutlinedTextField(
                    value = formState.categoria.toString(),
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoria)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedCategoria,
                    onDismissRequest = { expandedCategoria = false }
                ) {
                    CategoriaGasto.values().forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.toString()) },
                            onClick = {
                                gastosViewModel.onCategoriaChange(categoria)
                                expandedCategoria = false
                            }
                        )
                    }
                }
            }

            // Error general
            formState.errores["general"]?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Guardar
            Button(
                onClick = {
                    gastosViewModel.guardarGasto()
                    if (formState.errores.isEmpty() && !formState.isLoading) {
                        navController.popBackStack()
                    }
                },
                enabled = !formState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (formState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Guardar Gasto")
                }
            }
        }
    }
}