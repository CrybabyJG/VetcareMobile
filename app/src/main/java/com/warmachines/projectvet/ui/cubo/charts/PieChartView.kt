package com.warmachines.projectvet.ui.cubo.charts

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

@Composable
fun PieChartView(
    data: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                setHoleColor(Color.TRANSPARENT)
                setTransparentCircleAlpha(110)

                legend.isEnabled = true
                legend.textColor = Color.rgb(25, 35, 56)
            }
        },
        update = { chart ->
            val entries = data.map { (label, value) ->
                PieEntry(value.toFloat(), label)
            }

            val dataSet = PieDataSet(entries, "").apply {

                colors = listOf(
                    Color.rgb(25, 35, 56),
                    Color.rgb(30, 46, 79),
                    Color.rgb(49, 72, 122),
                    Color.rgb(143, 179, 226),
                )

                valueTextColor = Color.rgb(25, 35, 56)
                valueTextSize = 12f
                sliceSpace = 3f
            }

            val pieData = PieData(dataSet)
            chart.data = pieData
            chart.invalidate()
        }
    )
}