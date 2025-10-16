package com.example.taller_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameScreen(navController: NavController) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val buttonSize = maxWidth / 4

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Jugador: 1", fontSize = 20.sp)
                Text("Tiempo: 60", fontSize = 20.sp)
            }

            Text("Palabra a Adivinar", fontSize = 32.sp)

            // Empty row to push buttons to the bottom via Arrangement.SpaceBetween
            Row {}
        }

        Button(
            onClick = { /* TODO: Handle pass */ },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .size(buttonSize)
        ) {
            Text("Pasar", fontSize = 20.sp)
        }

        Button(
            onClick = { /* TODO: Handle correct guess */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(buttonSize)
        ) {
            Text("Acertaste", fontSize = 20.sp)
        }
    }
}
