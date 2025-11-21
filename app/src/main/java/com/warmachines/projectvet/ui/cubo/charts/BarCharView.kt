package com.warmachines.projectvet.ui.cubo.charts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import android.graphics.Color

@Composable
fun BarChartView(
    entries: List<BarEntry>,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BarChart(context).apply {

                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                axisRight.isEnabled = false

                isDragEnabled = true
                setScaleEnabled(false)
                setVisibleXRangeMaximum(5f)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = IndexAxisValueFormatter(labels)
                    granularity = 1f
                    textColor = Color.rgb(25, 35, 56)
                    textSize = 10f
                    labelRotationAngle = -45f
                    setDrawGridLines(false)
                }

                axisLeft.apply {
                    granularity = 1f
                    textColor = Color.rgb(25, 35, 56)
                    textSize = 10f
                    setDrawGridLines(false)
                }

                legend.textColor = Color.rgb(25, 35, 56)
                legend.isEnabled = true
            }
        },
        update = { chart ->
            val dataSet = BarDataSet(entries, "Postulaciones").apply {
                color = Color.rgb(30, 46, 79)
                valueTextColor = Color.rgb(25, 35, 56)
                valueTextSize = 11f
            }

            val barData = BarData(dataSet).apply {
                barWidth = 0.7f   // Aumentado (entre 0.5 y 0.9 se ve bien)
            }

            chart.setFitBars(true)

            chart.xAxis.axisMinimum = -0.5f
            chart.xAxis.axisMaximum = entries.size.toFloat() - 0.3f

            chart.data = barData
            chart.invalidate()
        }
    )
}