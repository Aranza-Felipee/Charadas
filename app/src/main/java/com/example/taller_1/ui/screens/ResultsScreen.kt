package com.example.taller_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resultados Finales", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))

        // Example Results
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Ronda 1", fontSize = 24.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Jugador 1: 5 aciertos")
                Text("Jugador 2: 3 aciertos")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Ronda 2", fontSize = 24.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Jugador 1: 4 aciertos")
                Text("Jugador 2: 6 aciertos")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text("Puntaje Final", fontSize = 28.sp)
        Text("Jugador 1: 9")
        Text("Jugador 2: 9")
        Text("¡Empate!", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { /* TODO: Restart Game */ }) {
            Text("Jugar de Nuevo")
        }
    }
}
