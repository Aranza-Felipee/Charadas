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
fun RoundSelectionScreen(onRoundSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selecciona el número de rondas")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onRoundSelected(3) }) {
            Text("3 Rondas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onRoundSelected(5) }) {
            Text("5 Rondas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onRoundSelected(7) }) {
            Text("7 Rondas")
        }
    }
}
