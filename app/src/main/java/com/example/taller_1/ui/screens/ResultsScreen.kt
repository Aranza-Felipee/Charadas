package com.example.taller_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taller_1.navigation.Screen
import com.example.taller_1.viewmodel.GameViewModel
import com.example.taller_1.viewmodel.Team

@Composable
fun ResultsScreen(navController: NavController, viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()
    val winner = findWinner(gameState.teams)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Fin del Juego!", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))

        if (winner != null) {
            Text("Ganador: Equipo ${winner.id}", fontSize = 28.sp)
        } else {
            Text("¡Es un empate!", fontSize = 28.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Resultados Finales", fontSize = 24.sp)
        gameState.teams.forEach {
            Text("Equipo ${it.id}: ${it.roundsWon} Rondas Ganadas, ${it.totalScore} Puntos Totales")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            viewModel.resetGame()
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        }) {
            Text("Jugar de Nuevo")
        }
    }
}

private fun findWinner(teams: List<Team>): Team? {
    if (teams.isEmpty()) return null

    val maxRoundsWon = teams.maxOfOrNull { it.roundsWon } ?: 0
    val potentialWinners = teams.filter { it.roundsWon == maxRoundsWon }

    return if (potentialWinners.size == 1) {
        potentialWinners.first()
    } else {
        // Tie in rounds won, use total score as a tiebreaker
        val maxScore = potentialWinners.maxOfOrNull { it.totalScore } ?: 0
        val finalWinners = potentialWinners.filter { it.totalScore == maxScore }
        if (finalWinners.size == 1) finalWinners.first() else null // It's a draw
    }
}
