package com.hav.iot.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEndAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberTopAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.ChartValues
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Shape

data class CustomValueFormatter(private val labels: List<String>) : CartesianValueFormatter {
    override fun format(value: Double, chartValues: ChartValues, verticalAxisPosition: Axis.Position.Vertical?): CharSequence {
        val index = value.toInt() % labels.size
        return labels.getOrElse(index) { value.toString() }
    }
}

@Composable
fun ComposeChart8(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
    val labels = listOf("16h", "17h", "18h", "19h", "20h")

    val bottomAxis = rememberBottomAxis(
//        valueFormatter = CustomValueFormatter(labels),
    )

    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider =
                ColumnCartesianLayer.ColumnProvider.series(
                    columnChartColors.map { color ->
                        rememberLineComponent(
                            color = color,
                            thickness = 12.dp,
                            shape = Shape.rounded(40)
                        )
                    }
                ),
                mergeMode = { ColumnCartesianLayer.MergeMode.Stacked },
                verticalAxisPosition = Axis.Position.Vertical.Start,
            ),
            rememberLineCartesianLayer(
                lineProvider =
                LineCartesianLayer.LineProvider.series(
                    rememberLine(remember { LineCartesianLayer.LineFill.single(fill(MainBG)) })
                ),
                verticalAxisPosition = Axis.Position.Vertical.End,
            ),
            startAxis = rememberStartAxis(title = "Temperature"),
            bottomAxis = bottomAxis,
            marker = rememberMarker(),
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}
@Composable
fun ComposeChart1(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
    val marker = rememberMarker()
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    rememberLine(remember { LineCartesianLayer.LineFill.single(fill(OnColor)) })
                )
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(guideline = null),
            marker = marker,
            persistentMarkers = rememberExtraLambda(marker) { marker at PERSISTENT_MARKER_X },
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}


private val color1 = OnColor
private val color2 = Color(0xFF1C6DE6)
private val color3 = Color(0xFFE0C5BB)
private val color4 = Color(0xffffc3a1)
private val columnChartColors = listOf(color1, color2, color3)


private const val PERSISTENT_MARKER_X = 7f