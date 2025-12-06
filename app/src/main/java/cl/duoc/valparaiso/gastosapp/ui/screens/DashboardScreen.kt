package cl.duoc.valparaiso.gastosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.duoc.valparaiso.gastosapp.model.Gasto
import cl.duoc.valparaiso.gastosapp.navigation.Route
import cl.duoc.valparaiso.gastosapp.viewmodel.GastosViewModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

// Función de utilidad para formatear moneda, ahora dentro del archivo para que sea accesible.
private fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    format.maximumFractionDigits = 0 // Sin decimales
    return format.format(amount)
}

// Función de utilidad para los emojis.
private fun obtenerEmoji(categoria: String): String {
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
fun DashboardScreen(
    navController: NavController,
    gastosViewModel: GastosViewModel
) {
    // CORRECCIÓN: Usamos .collectAsState() que es la función estándar y correcta.
    val gastos by gastosViewModel.gastos.collectAsState()
    val resumen by gastosViewModel.resumenMensual.collectAsState()
    val isLoading by gastosViewModel.isLoadingGastos.collectAsState()

    // ✅ NUEVAS LÍNEAS PARA CLIMA
    val clima by gastosViewModel.climaActual.collectAsState()
    val isLoadingClima by gastosViewModel.isLoadingClima.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel Principal", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.AgregarGasto.path) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Gasto")
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /* Ya estamos aquí */ }) {
                        Icon(Icons.Default.Home, contentDescription = "Dashboard", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = { navController.navigate(Route.Historial.path) }) {
                        Icon(Icons.Default.List, contentDescription = "Historial")
                    }
                    IconButton(onClick = { navController.navigate(Route.Configuracion.path) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                }
            }
        }
    ) { paddingValues ->
        // Si está cargando, muestra una animación
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Cuando termina de cargar, muestra el contenido
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp) // Espacio para el FAB
            ) {
                // Tarjeta de Resumen General
                item {
                    ResumenCard(totalGastado = resumen.totalGastado, cantidadTransacciones = resumen.cantidadTransacciones)
                }

                // ✅ NUEVA TARJETA DE CLIMA
                item {
                    if (clima != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF87CEEB) // Azul cielo
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Clima - ${clima!!.ciudad}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "${clima!!.icono} ${clima!!.temperatura.toInt()}°C",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = clima!!.descripcion,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("💧", fontSize = 24.sp)
                                        Text(
                                            "${clima!!.humedad}%",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("💨", fontSize = 24.sp)
                                        Text(
                                            "${clima!!.velocidadViento.toInt()} km/h",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    } else if (isLoadingClima) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF87CEEB)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        }
                    }
                }

                // Título de Gastos Recientes
                item {
                    Text(
                        "Gastos Recientes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Lista de Gastos o Mensaje de "Vacío"
                if (gastos.isEmpty()) {
                    item {
                        Text(
                            "Aún no tienes gastos. ¡Agrega el primero con el botón '+'!",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    items(gastos.take(5)) { gasto ->
                        GastoItem(gasto = gasto)
                    }
                }
            }
        }
    }
}

@Composable
private fun ResumenCard(totalGastado: Double, cantidadTransacciones: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Total Gastado este Mes", style = MaterialTheme.typography.titleMedium)
            Text(
                formatCurrency(totalGastado),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                "$cantidadTransacciones transacciones",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun GastoItem(gasto: Gasto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de categoría
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(obtenerEmoji(gasto.categoria), fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Descripción y fecha
            Column(modifier = Modifier.weight(1f)) {
                Text(gasto.descripcion, fontWeight = FontWeight.Bold, maxLines = 1)
                // CORRECCIÓN: Usamos el formateador estándar de Java Time
                Text(
                    gasto.fecha.format(DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Monto
            Text(
                // CORRECCIÓN: Usamos la función de formato de moneda
                formatCurrency(gasto.monto),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
