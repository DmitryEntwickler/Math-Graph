package com.example.graphic.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.roundToLong


@Composable
fun MainScreenComposable(mMainScreenViewModel: MainScreenViewModel) {

    val mNumberOfSquares = 24

    var sliderStateFaktor by remember { mutableStateOf(1f) }
    var sliderStatePlus by remember { mutableStateOf(0f) }

    Column {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
        )
        {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Netz vertikal
            for (x in 0..size.width.toInt() step (size.width / mNumberOfSquares).toInt()) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = x.toFloat(), 0F),
                    end = Offset(x = x.toFloat(), y = size.height)
                )
            }
            for (y in 0..size.height.toInt() step (size.width / mNumberOfSquares).toInt()) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = 0F, y = y.toFloat()),
                    end = Offset(x = size.width, y = y.toFloat())
                )
            }
            // horizontal axis
            drawLine(
                color = Color.Black,
                start = Offset(x = 0F, y = size.height / 2f),
                end = Offset(x = size.width, y = size.height / 2f),
                strokeWidth = 2f
            )
            // vertical axis
            drawLine(
                color = Color.Black,
                start = Offset(x = size.width / 2f, y = 0f),
                end = Offset(x = size.width / 2f, y = size.height),
                strokeWidth = 2f
            )

            val xNull = size.width / 2f
            val yNull = size.height / 2f

            val masstab = size.width / 24f
            val faktorX = sliderStateFaktor                     // das ändern
            val plusMinus = (sliderStatePlus) * masstab       // das ändern
            val xStart = (xNull - 300).toFloat()
            val yStart = (yNull + 300 * faktorX).toFloat() - plusMinus
            val myPath = Path()
            myPath.moveTo(xStart, yStart)

            for (x in (-size.width / 2f).toInt()..(size.width / 2f).toInt()) {
                val y = x * faktorX + plusMinus
                myPath.lineTo(xNull + x.toFloat(), yNull - y.toFloat())
            }
            myPath.close()

            drawPath(
                path = myPath,
                color = Color.Blue,
                style = Stroke(width = 2f)
            )

        } // Canvas Ende


        Text(
            text = "Faktor: $sliderStateFaktor",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            color = Color.Black,
            fontSize = 14.sp
        )
        Slider(value = sliderStateFaktor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            valueRange = -10f..10f,
            onValueChange = { newValue ->
                sliderStateFaktor = newValue
            }
        )
        Text(
            text = "+: $sliderStatePlus",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            color = Color.Black,
            fontSize = 14.sp
        )
        Slider(value = sliderStatePlus,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            valueRange = -10f..10f,
            onValueChange = { newValue ->
                sliderStatePlus = newValue
            }
        )
        Text(
            text = if (sliderStatePlus < 0) {
                "Y = ${(sliderStateFaktor*100.0).roundToInt()/100.0} X - ${((sliderStatePlus*100.0).roundToInt()/100.0).absoluteValue}"
            } else if (sliderStatePlus > 0) "Y = ${(sliderStateFaktor*100.0).roundToInt()/100.0} X + ${(sliderStatePlus*100.0).roundToInt()/100.0}"
            else "Y = ${(sliderStateFaktor*100.0).roundToInt()/100.0} X",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            color = Color.Black,
            fontSize = 24.sp
        )
        OutlinedButton(
            onClick = { sliderStateFaktor = 1f; sliderStatePlus = 0f},
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {

            Text(
                text = "neu",
                color = Color.Black,
                fontSize = 14.sp
            )
        }


    }   // End of Column
}