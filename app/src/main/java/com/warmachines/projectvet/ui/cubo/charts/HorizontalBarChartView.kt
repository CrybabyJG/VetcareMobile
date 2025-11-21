package com.warmachines.projectvet.ui.cubo.charts

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun HorizontalBarChartView(
    entries: List<BarEntry>,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            HorizontalBarChart(context).apply {

                setBackgroundColor(Color.WHITE)
                setDrawGridBackground(true)
                setGridBackgroundColor(Color.WHITE)

                description.isEnabled = false
                axisRight.isEnabled = false

                legend.isEnabled = true
                legend.textColor = Color.rgb(25, 35, 56)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = IndexAxisValueFormatter(labels)
                    granularity = 1f
                    setDrawGridLines(false)
                    textColor = Color.rgb(25, 35, 56)
                }

                axisLeft.apply {
                    granularity = 1f
                    setDrawGridLines(false)
                    textColor = Color.rgb(25, 35, 56)
                }

                axisRight.isEnabled = false
            }
        },
        update = { chart ->

            val dataSet = BarDataSet(entries, "").apply {
                color = Color.rgb(30, 46, 79)  // azul principal
                valueTextColor = Color.rgb(25, 35, 56)
                valueTextSize = 12f
            }

            val barData = BarData(dataSet)
            barData.barWidth = 0.6f

            chart.data = barData
            chart.invalidate()
        }
    )
}