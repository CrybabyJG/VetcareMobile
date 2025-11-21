package com.warmachines.projectvet.ui.viewmodel.cubo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warmachines.projectvet.API.Cubo.ApiServices
import com.warmachines.projectvet.API.Cubo.RetrofitInstance
import com.warmachines.projectvet.models.cubo.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val api: ApiServices = RetrofitInstance.api
) : ViewModel() {

    var loading = androidx.compose.runtime.mutableStateOf(true)
        private set

    var errorMessage = androidx.compose.runtime.mutableStateOf<String?>(null)
        private set

    var datosCompletos =
        androidx.compose.runtime.mutableStateOf<List<UnionDimensionesHechos>>(emptyList())
        private set


    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            loading.value = true
            errorMessage.value = null

            try {
                val clientes = api.getClientes()
                val medicamentos = api.getMedicamentos()
                val tiempos = api.getTiempo()
                val ventas = api.getFactVentas()

                datosCompletos.value = unirTablas(ventas, clientes, medicamentos, tiempos)

            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = "Error al cargar datos: ${e.message}"
            } finally {
                loading.value = false
            }
        }
    }

    private fun unirTablas(
        ventas: List<FactVenta>,
        clientes: List<DimCliente>,
        medicamentos: List<DimMedicamento>,
        tiempos: List<DimTiempo>
    ): List<UnionDimensionesHechos> {

        return ventas.map { v ->
            UnionDimensionesHechos(
                cliente = clientes.firstOrNull { it.ID_Cliente == v.ID_Cliente },
                medicamento = medicamentos.firstOrNull { it.ID_Medicamento == v.ID_Medicamento },
                tiempo = tiempos.firstOrNull { it.IdTiempo == v.ID_Tiempo },
                venta = v
            )
        }
    }

    // ------------------------------------------------------------
    // FUNCIONES DE ESTADÍSTICAS PARA EL DASHBOARD
    // ------------------------------------------------------------

    /** Ventas totales por cliente */
    fun ventasPorCliente(): Map<String, Double> {
        return datosCompletos.value
            .groupBy { "${it.cliente?.Nombres} ${it.cliente?.Apellido1}" }
            .mapValues { (_, lista) ->
                lista.sumOf { it.venta?.Total ?: 0.0 }
            }
    }

    /** Ventas totales por medicamento */
    fun ventasPorMedicamento(): Map<String, Double> {
        return datosCompletos.value
            .groupBy { it.medicamento?.Nombre_medicamento ?: "Desconocido" }
            .mapValues { (_, lista) ->
                lista.sumOf { it.venta?.Total ?: 0.0 }
            }
    }

    /** Cantidades vendidas por medicamento */
    fun cantidadVendidaPorMedicamento(): Map<String, Int> {
        return datosCompletos.value
            .groupBy { it.medicamento?.Nombre_medicamento ?: "Desconocido" }
            .mapValues { (_, lista) ->
                lista.sumOf { it.venta?.Cantidad ?: 0 }
            }
    }

    /** Ventas por mes (Nombre del mes) */
    fun ventasPorMes(): Map<String, Double> {
        return datosCompletos.value
            .groupBy { it.tiempo?.NombreMes ?: "Sin mes" }
            .mapValues { (_, lista) ->
                lista.sumOf { it.venta?.Total ?: 0.0 }
            }
    }

    /** Ventas por año */
    fun ventasPorAño(): Map<Int, Double> {
        return datosCompletos.value
            .groupBy { it.tiempo?.Año ?: 0 }
            .mapValues { (_, lista) ->
                lista.sumOf { it.venta?.Total ?: 0.0 }
            }
    }

    /** Top clientes por ventas */
    fun topClientes(limit: Int = 5): List<Pair<String, Double>> {
        return ventasPorCliente()
            .toList()
            .sortedByDescending { it.second }
            .take(limit)
    }

    /** Top medicamentos por ventas totales */
    fun topMedicamentos(limit: Int = 5): List<Pair<String, Double>> {
        return ventasPorMedicamento()
            .toList()
            .sortedByDescending { it.second }
            .take(limit)
    }

    /** Medicamentos más vendidos por cantidad */
    fun topMedicamentosPorCantidad(limit: Int = 5): List<Pair<String, Int>> {
        return cantidadVendidaPorMedicamento()
            .toList()
            .sortedByDescending { it.second }
            .take(limit)
    }
}