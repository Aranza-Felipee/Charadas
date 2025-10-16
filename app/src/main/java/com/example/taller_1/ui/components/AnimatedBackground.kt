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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    when (shape.type) {
        ShapeType.CIRCLE -> {
            drawCircle(color = shape.color, radius = shape.sizePx / 2, center = center)
        }
        ShapeType.SQUARE -> {
            rotate(degrees = rotation, pivot = center) {
                drawRect(
                    color = shape.color,
                    topLeft = Offset(center.x - shape.sizePx / 2, center.y - shape.sizePx / 2),
                    size = Size(shape.sizePx, shape.sizePx)
                )
            }
        }
        ShapeType.RECTANGLE -> {
            drawRect(
                color = shape.color,
                topLeft = Offset(center.x - (shape.sizePx * 1.5f) / 2, center.y - shape.sizePx / 2),
                size = Size(shape.sizePx * 1.5f, shape.sizePx)
            )
        }
    }
}

private enum class ShapeType {
    CIRCLE, SQUARE, RECTANGLE
}

private data class Shape(
    val type: ShapeType,
    val x: Float, // position relative to width
    val y: Float, // position relative to height
    val size: Dp,
    val color: Color,
    val sizePx: Float = 0f
)
