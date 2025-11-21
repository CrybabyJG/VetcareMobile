package com.warmachines.projectvet.ui.cubo.charts

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun LineChartView(
    data: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {

                description.isEnabled = false
                axisRight.isEnabled = false

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)
                    textColor = Color.rgb(25, 35, 56)
                }

                axisLeft.apply {
                    granularity = 1f
                    setDrawGridLines(false)
                    textColor = Color.rgb(25, 35, 56)
                }

                legend.textColor = Color.rgb(25, 35, 56)
            }
        },
        update = { chart ->
            val labels = data.keys.toList()
            val entries = data.values.mapIndexed { index, value ->
                Entry(index.toFloat(), value.toFloat())
            }

            val dataSet = LineDataSet(entries, "Postulaciones").apply {
                color = Color.rgb(49, 72, 122)
                setCircleColor(Color.rgb(30, 46, 79))
                setCircleHoleColor(Color.rgb(30, 46, 79))
                lineWidth = 3f
                circleRadius = 5f

                valueTextColor = Color.rgb(25, 35, 56)
                valueTextSize = 12f

                mode = LineDataSet.Mode.CUBIC_BEZIER
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}