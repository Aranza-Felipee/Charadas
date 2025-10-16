package com.example.taller_1.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private const val NUM_SHAPES = 20

@Composable
fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val shapes = remember {
        List(NUM_SHAPES) {
            val shapeType = ShapeType.values().random()
            Shape(
                type = shapeType,
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextInt(20, 60).dp,
                color = Color.White.copy(alpha = Random.nextFloat() * 0.1f + 0.05f)
            )
        }
    }

    val density = LocalDensity.current
    val updatedShapes = remember(density) {
        shapes.map { it.copy(sizePx = with(density) { it.size.toPx() }) }
    }


    Canvas(modifier = Modifier.fillMaxSize()) {
        updatedShapes.forEach { shape ->
            drawShape(shape, rotation)
        }
    }
}

private fun DrawScope.drawShape(shape: Shape, rotation: Float) {
    val center = Offset(x = size.width * shape.x, y = size.height * shape.y)
    rotate(degrees = rotation, pivot = center) {
        when (shape.type) {
            ShapeType.TRIANGLE -> {
                val path = Path().apply {
                    val radius = shape.sizePx / 2
                    moveTo(center.x, center.y - radius)
                    lineTo(center.x - radius, center.y + radius / 2)
                    lineTo(center.x + radius, center.y + radius / 2)
                    close()
                }
                drawPath(path = path, color = shape.color)
            }
            ShapeType.SQUARE -> {
                drawRect(
                    color = shape.color,
                    topLeft = Offset(center.x - shape.sizePx / 2, center.y - shape.sizePx / 2),
                    size = Size(shape.sizePx, shape.sizePx)
                )
            }
            ShapeType.HEXAGON -> {
                val path = Path().apply {
                    val radius = shape.sizePx / 2
                    for (i in 0..5) {
                        val angle = (60 * i - 90) * (Math.PI / 180).toFloat()
                        val x = center.x + radius * cos(angle)
                        val y = center.y + radius * sin(angle)
                        if (i == 0) {
                            moveTo(x, y)
                        } else {
                            lineTo(x, y)
                        }
                    }
                    close()
                }
                drawPath(path = path, color = shape.color)
            }
        }
    }
}

private enum class ShapeType {
    TRIANGLE, SQUARE, HEXAGON
}

private data class Shape(
    val type: ShapeType,
    val x: Float, // position relative to width
    val y: Float, // position relative to height
    val size: Dp,
    val color: Color,
    val sizePx: Float = 0f
)
