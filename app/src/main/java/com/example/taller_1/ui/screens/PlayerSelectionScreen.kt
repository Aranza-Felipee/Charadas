package com.example.taller_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerSelectionScreen(onPlayerSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selecciona el número de jugadores")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onPlayerSelected(1) }) {
            Text("1 Jugador")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onPlayerSelected(2) }) {
            Text("2 Jugadores")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onPlayerSelected(3) }) {
            Text("3 Jugadores")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onPlayerSelected(4) }) {
            Text("4 Jugadores")
        }
    }
}
