package cl.duoc.valparaiso.gastosapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.valparaiso.gastosapp.model.Gasto
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.*

// --- FUNCIONES DE UTILIDAD (Requeridas por esta pantalla) ---

private fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    format.maximumFractionDigits = 0
    return format.format(amount)
}

private fun getEmojiForCategoria(categoria: String): String {
    return when (categoria.lowercase()) {
        "alimentación" -> "🍔"
        "transporte" -> "🚌"
        "entretenimiento" -> "🎬"
        "compras" -> "🛒"
        "servicios" -> "⚡"
        "salud" -> "🏥"
        "educación" -> "📚"
        else -> "💼"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    navController: NavController,
    // ¡ESTA ES LA CORRECCIÓN CLAVE!
    // Ya no creamos un viewModel nuevo, lo recibimos como parámetro.
    gastosViewModel: GastosViewModel
) {
    // Recolectamos la lista de gastos del ViewModel COMPARTIDO.
    val gastos by gastosViewModel.gastos.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var gastoAEliminar by remember { mutableStateOf<Gasto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Gastos") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ResumenHistorialCard(gastos)

            if (gastos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aún no tienes un historial de gastos.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Usamos el ID del gasto como clave, que nunca es nulo.
                    items(gastos.sortedByDescending { it.fechaAsLocalDateTime }, key = { it.id }) { gasto ->
                        GastoHistorialItem(
                            gasto = gasto,
                            onDeleteClick = {
                                gastoAEliminar = it
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog && gastoAEliminar != null) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                gastoAEliminar = null // Limpiamos al cerrar
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este gasto de forma permanente?") },
            confirmButton = {
                Button(
                    onClick = {
                        // ---- ¡ESTA ES LA MEJORA CLAVE! ----
                        // 1. Capturamos el ID en una variable local segura (val).
                        //    Esto asegura que no cambiará aunque el diálogo se cierre.
                        val idParaBorrar = gastoAEliminar?.id

                        // 2. Cerramos el diálogo inmediatamente para una mejor experiencia de usuario.
                        showDialog = false
                        gastoAEliminar = null

                        // 3. Si el ID es válido, llamamos al ViewModel con el ID seguro.
                        if (idParaBorrar != null) {
                            gastosViewModel.eliminarGasto(idParaBorrar)

                            // ✅ ¡ESTA ES LA CORRECCIÓN PRINCIPAL!
                            // Después de eliminar, recargamos la lista del backend
                            gastosViewModel.cargarGastosDelBackend()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    gastoAEliminar = null // Limpiamos también al cancelar
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun ResumenHistorialCard(gastos: List<Gasto>) {
    val total = gastos.sumOf { it.monto }
    val promedio = if (gastos.isNotEmpty()) gastos.map { it.monto }.average() else 0.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ResumenItem(valor = "${gastos.size}", etiqueta = "Transacciones")
            ResumenItem(valor = formatCurrency(total), etiqueta = "Total Gastado")
            ResumenItem(valor = formatCurrency(promedio), etiqueta = "Promedio")
        }
    }
}

@Composable
private fun ResumenItem(valor: String, etiqueta: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = valor, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(etiqueta, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun GastoHistorialItem(gasto: Gasto, onDeleteClick: (Gasto) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = gasto.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${getEmojiForCategoria(gasto.categoria)} ${gasto.categoria}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // Usamos la nueva propiedad 'fechaAsLocalDateTime' para formatear.
                Text(
                    text = gasto.fechaAsLocalDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formatCurrency(gasto.monto),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { onDeleteClick(gasto) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}