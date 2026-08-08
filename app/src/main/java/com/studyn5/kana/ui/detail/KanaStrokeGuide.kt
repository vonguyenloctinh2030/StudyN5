package com.studyn5.kana.ui.detail

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.PathParser
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

private const val KANJIVG_VIEWPORT = 109f

private data class StrokeGuide(
    val number: Int,
    val path: Path,
    val numberPosition: Offset,
    val arrowStart: Offset,
    val arrowEnd: Offset,
)

private object KanaStrokeRepository {
    private val cache = mutableMapOf<String, List<StrokeGuide>>()
    private val pathTagPattern = Regex("""<path\b[^>]*/>""")
    private val strokeIdPattern = Regex("""id="[^"]+-s(\d+)"""")
    private val pathDataPattern = Regex("""d="([^"]+)"""")
    private val numberPattern = Regex(
        """<text[^>]*transform="matrix\(\s*1\s+0\s+0\s+1\s+([-\d.]+)\s+([-\d.]+)\s*\)"[^>]*>\s*(\d+)\s*</text>""",
    )

    @Synchronized
    fun load(context: Context, character: String): List<StrokeGuide> {
        return cache.getOrPut(character) {
            runCatching { parse(context, character) }.getOrDefault(emptyList())
        }
    }

    private fun parse(context: Context, character: String): List<StrokeGuide> {
        val codePoint = character.codePointAt(0)
        val fileName = String.format(Locale.US, "%05x.svg", codePoint)
        val source = context.assets.open("strokes/$fileName").bufferedReader().use { it.readText() }

        val numberPositions = numberPattern.findAll(source).associate { match ->
            val x = match.groupValues[1].toFloat()
            val y = match.groupValues[2].toFloat()
            match.groupValues[3].toInt() to Offset(x, y)
        }

        return pathTagPattern.findAll(source).mapNotNull { tagMatch ->
            val tag = tagMatch.value
            val number = strokeIdPattern.find(tag)?.groupValues?.get(1)?.toIntOrNull()
                ?: return@mapNotNull null
            val pathData = pathDataPattern.find(tag)?.groupValues?.get(1)
                ?: return@mapNotNull null
            val path = PathParser.createPathFromPathData(pathData)
                ?: return@mapNotNull null
            val arrow = arrowFor(path)
            StrokeGuide(
                number = number,
                path = path,
                numberPosition = numberPositions[number] ?: arrow.first,
                arrowStart = arrow.first,
                arrowEnd = arrow.second,
            )
        }.sortedBy(StrokeGuide::number).toList()
    }

    private fun arrowFor(path: Path): Pair<Offset, Offset> {
        val measure = PathMeasure(path, false)
        val start = FloatArray(2)
        val end = FloatArray(2)
        val tangent = FloatArray(2)
        measure.getPosTan(0f, start, tangent)
        measure.getPosTan(min(12f, measure.length * 0.22f), end, null)

        val length = sqrt(tangent[0] * tangent[0] + tangent[1] * tangent[1]).coerceAtLeast(1f)
        val normalX = -tangent[1] / length * 5f
        val normalY = tangent[0] / length * 5f
        return Offset(start[0] + normalX, start[1] + normalY) to
            Offset(end[0] + normalX, end[1] + normalY)
    }
}

@Composable
fun KanaStrokeGuide(
    character: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val strokes = remember(character) { KanaStrokeRepository.load(context, character) }
    val guideColor = Color(0x409B8E84)
    val directionColor = Color(0xFFC48D68)

    Canvas(modifier = modifier) {
        val scale = size.minDimension / KANJIVG_VIEWPORT
        val numberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = directionColor.toArgb()
            textSize = 8f
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        withTransform({ scale(scale, scale, pivot = Offset.Zero) }) {
            strokes.forEach { stroke ->
                drawPath(
                    path = stroke.path.asComposePath(),
                    color = guideColor,
                    style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round),
                )
                drawDirectionArrow(
                    start = stroke.arrowStart,
                    end = stroke.arrowEnd,
                    color = directionColor,
                )
                drawContext.canvas.nativeCanvas.drawText(
                    stroke.number.toString(),
                    stroke.numberPosition.x,
                    stroke.numberPosition.y,
                    numberPaint,
                )
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawDirectionArrow(
    start: Offset,
    end: Offset,
    color: Color,
) {
    drawLine(color, start, end, strokeWidth = 1.4f, cap = StrokeCap.Round)

    val angle = atan2(end.y - start.y, end.x - start.x)
    val headLength = 4f
    val spread = 0.55f
    val left = Offset(
        x = end.x - headLength * cos(angle - spread),
        y = end.y - headLength * sin(angle - spread),
    )
    val right = Offset(
        x = end.x - headLength * cos(angle + spread),
        y = end.y - headLength * sin(angle + spread),
    )
    drawLine(color, end, left, strokeWidth = 1.4f, cap = StrokeCap.Round)
    drawLine(color, end, right, strokeWidth = 1.4f, cap = StrokeCap.Round)
}
