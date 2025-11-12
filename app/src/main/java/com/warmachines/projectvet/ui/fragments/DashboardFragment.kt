package com.warmachines.projectvet.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.warmachines.projectvet.R
import com.warmachines.projectvet.models.ClienteMedicamentoStat
import com.warmachines.projectvet.models.MedicamentoDia
import com.warmachines.projectvet.models.MedicamentoStat

class DashboardFragment : Fragment() {

    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Referencias de los gráficos
        pieChart = view.findViewById(R.id.pieChartMedicamentos)
        barChart = view.findViewById(R.id.barChartClientesMedicamentos)
        lineChart = view.findViewById(R.id.lineChartMedicamentosDia)

        // Mostrar los gráficos
        mostrarGraficoMedicamentos()
        mostrarGraficoClientesMedicamentos()
        mostrarGraficoMedicamentoDia()

        return view
    }

    // Función para leer JSON desde assets
    private fun obtenerDatosMedicamentos(context: Context): List<MedicamentoStat>? {
        return try {
            val json = context.assets.open("AnalisisMedicamento.json")
                .bufferedReader()
                .use { it.readText() }
            Gson().fromJson(json, Array<MedicamentoStat>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun obtenerDatosClienteMedicamento(context: Context): List<ClienteMedicamentoStat>? {
        return try {
            val json = context.assets.open("AnalisisClienteMedicamento.json")
                .bufferedReader()
                .use { it.readText() }
            Gson().fromJson(json, Array<ClienteMedicamentoStat>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun obtenerDatosMedicamentoDia(context: Context): List<MedicamentoDia>? {
        return try {
            val json = context.assets.open("AnalisisMedicamentoDia.json")
                .bufferedReader()
                .use { it.readText() }
            Gson().fromJson(json, Array<MedicamentoDia>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }



    // Función para mostrar gráfico de pastel
    private fun mostrarGraficoMedicamentos() {
        val datos = obtenerDatosMedicamentos(requireContext()) ?: return

        val entries = ArrayList<PieEntry>()
        datos.forEach {
            entries.add(PieEntry(it.total.toFloat(), it.medicamento))
        }

        val dataSet = PieDataSet(entries, "Medicamentos")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f
        dataSet.sliceSpace = 2f

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Distribución de Medicamentos"
        pieChart.setUsePercentValues(true)
        pieChart.animateY(1000)
        pieChart.invalidate() // Refresca el gráfico
    }

    // Función para mostrar gráfico de barras de clientes y medicamentos
    private fun mostrarGraficoClientesMedicamentos() {
        val datos = obtenerDatosClienteMedicamento(requireContext()) ?: return

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        datos.forEachIndexed { index, item ->
            entries.add(BarEntry(index.toFloat(), item.cantidad.toFloat()))
            labels.add(item.medicamento)
        }

        val dataSet = BarDataSet(entries, "Clientes Medicamentos")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.description.isEnabled = false
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.isGranularityEnabled = true
        barChart.animateY(1000)
        barChart.invalidate() // refresca el gráfico
    }

    private fun mostrarGraficoMedicamentoDia() {
        val datos = obtenerDatosMedicamentoDia(requireContext()) ?: return

        val entries = ArrayList<Entry>()
        val labels = ArrayList<String>()

        // Rellenar las entradas para el gráfico
        datos.forEachIndexed { index, item ->
            entries.add(Entry(item.dia.toFloat(), item.total.toFloat()))  // Asignar el día al eje X y el total al eje Y
            labels.add(item.dia)  // Los días serán las etiquetas del eje X
        }

        // Crear un DataSet para las entradas
        val lineDataSet = LineDataSet(entries, "Total Medicamento por Día")
        lineDataSet.color = ColorTemplate.MATERIAL_COLORS[0]  // Color de la línea
        lineDataSet.valueTextSize = 12f  // Tamaño de la etiqueta de los valores
        lineDataSet.setCircleColor(ColorTemplate.MATERIAL_COLORS[1])  // Color de los círculos de los puntos
        lineDataSet.circleRadius = 6f  // Radio de los círculos

        // Crear los datos para el gráfico de líneas
        val lineData = LineData(lineDataSet)

        // Asignar los datos al gráfico
        lineChart.data = lineData

        // Configurar el eje X (días)
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        lineChart.xAxis.granularity = 1f  // Asegura que las etiquetas de los días estén espaciadas de forma uniforme
        lineChart.xAxis.isGranularityEnabled = true
        lineChart.xAxis.setDrawGridLines(false)  // Desactiva las líneas de la cuadrícula en el eje X

        // Configurar el eje Y (totales)
        lineChart.axisLeft.setDrawGridLines(true)  // Activa las líneas de la cuadrícula en el eje Y
        lineChart.axisLeft.setDrawLabels(true)  // Muestra los valores en el eje Y
        lineChart.axisRight.isEnabled = false  // Desactiva el eje Y derecho (no necesario)

        // Mejoras visuales
        lineChart.description.isEnabled = false  // Desactiva la descripción del gráfico
        lineChart.legend.isEnabled = false  // Desactiva la leyenda
        lineChart.setDrawGridBackground(false)  // Desactiva el fondo de la cuadrícula
        lineChart.animateX(1000)  // Animación en el eje X
        lineChart.invalidate()  // Refresca el gráfico
    }

}