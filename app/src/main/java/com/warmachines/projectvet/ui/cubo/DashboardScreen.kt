package com.warmachines.projectvet.ui.cubo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.github.mikephil.charting.data.BarEntry
import com.warmachines.projectvet.ui.viewmodel.cubo.DashboardViewModel
import com.warmachines.projectvet.ui.cubo.charts.*

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {

    val loading by viewModel.loading
    val error by viewModel.errorMessage
    val datos = viewModel.datosCompletos.value

    when {
        loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        error != null -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = error ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
        }

        else -> DashboardContent(viewModel)
    }
}

@Composable
fun DashboardContent(viewModel: DashboardViewModel) {

    val ventasPorMes = viewModel.ventasPorMes()
    val topMedicamentosCantidad = viewModel.topMedicamentosPorCantidad()
    val ventasPorMedicamento = viewModel.ventasPorMedicamento()
    val ventasPorCliente = viewModel.ventasPorCliente()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {
            Text(
                "Dashboard de Ventas",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(0xFF192338)
                )
            )
            Spacer(Modifier.height(6.dp))
            Divider()
        }

        // ---------------------------------------------
        // PIE CHART - Medicamentos más vendidos por cantidad (Top 10)
        // ---------------------------------------------
        item {
            DashboardCard(title = "Medicamentos más vendidos (Cantidad)") {

                val top10Medicamentos = topMedicamentosCantidad.take(10)
                PieChartView(
                    data = top10Medicamentos,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }

        // ---------------------------------------------
        // HORIZONTAL BAR CHART - Ventas por medicamento (Top 10)
        // ---------------------------------------------
        item {
            val top10MedicamentosVentas = ventasPorMedicamento
                .toList()
                .sortedByDescending { it.second }
                .take(10)
                .toMap()

            val entries = top10MedicamentosVentas.values.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value.toFloat())
            }
            val labels = top10MedicamentosVentas.keys.toList()

            DashboardCard(title = "Ventas por medicamento") {

                HorizontalBarChartView(
                    entries = entries,
                    labels = labels,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }

        // ---------------------------------------------
        // BAR CHART VERTICAL - Ventas por cliente (Top 10 + Otros)
        // ---------------------------------------------
        item {
            val topClientes = ventasPorCliente
                .toList()
                .sortedByDescending { it.second }
                .take(10)
                .toMutableList()

            // Sumar el resto como "Otros"
            val otrosTotal = ventasPorCliente.toList().drop(10).sumOf { it.second }
            if (otrosTotal > 0) {
                topClientes.add("Otros" to otrosTotal)
            }

            val entries = topClientes.mapIndexed { index, pair ->
                BarEntry(index.toFloat(), pair.second.toFloat())
            }
            val labels = topClientes.map { it.first }

            DashboardCard(title = "Ventas por cliente") {

                BarChartView(
                    entries = entries,
                    labels = labels,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF192338)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            content()
        }
    }
}