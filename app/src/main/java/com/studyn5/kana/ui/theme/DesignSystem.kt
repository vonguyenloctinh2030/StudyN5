package com.studyn5.kana.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

val KanaCardShape = RoundedCornerShape(22.dp)
val KanaSmallShape = RoundedCornerShape(15.dp)

/** Warm paper background with a restrained seigaiha-inspired detail. */
@Composable
fun KanaBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier.background(KanaIvory)) {
        Canvas(Modifier.fillMaxSize()) {
            val color = KanaNavy.copy(alpha = 0.035f)
            val radius = 54.dp.toPx()
            val stroke = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
            val origin = Offset(size.width - radius * 0.42f, radius * 0.12f)
            repeat(4) { index ->
                val r = radius * (index + 1)
                drawArc(
                    color = color,
                    startAngle = 15f,
                    sweepAngle = 150f,
                    useCenter = false,
                    topLeft = Offset(origin.x - r, origin.y - r),
                    size = Size(r * 2, r * 2),
                    style = stroke,
                )
            }
        }
        content()
    }
}
