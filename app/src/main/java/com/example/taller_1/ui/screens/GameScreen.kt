package com.example.taller_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.example.taller_1.ui.theme.Yellow
import com.example.taller_1.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()

    if (gameState.isGameOver) {
        navController.navigate(Screen.Results.route) {
            popUpTo(Screen.Home.route)
        }
        return
    }

    if (gameState.isTurnOver) {
        TurnResultScreen(viewModel)
    } else {
        ActiveGameScreen(viewModel)
    }
}

@Composable
fun ActiveGameScreen(viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val buttonSize = maxWidth / 4
        val currentTeam = gameState.teams.getOrNull(gameState.currentTeamIndex)

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
                Text("Equipo: ${currentTeam?.id ?: ""}", fontSize = 20.sp)
                Text("Puntaje de Ronda: ${currentTeam?.currentRoundScore ?: ""}", fontSize = 20.sp)
                Text("Tiempo: ${gameState.timeLeft}", fontSize = 20.sp)
            }

            Text(gameState.currentWord, fontSize = 32.sp)

            Row { }
        }

        Button(
            onClick = { viewModel.onSkip() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .size(buttonSize)
        ) {
            Text("Pasar", fontSize = 20.sp)
        }

        Button(
            onClick = { viewModel.onCorrect() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(buttonSize)
        ) {
            Text("Acertaste", fontSize = 20.sp)
        }
    }
}

@Composable
fun TurnResultScreen(viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()
    val currentTeam = gameState.teams.getOrNull(gameState.currentTeamIndex)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Fin del Turno", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Equipo ${currentTeam?.id}", fontSize = 24.sp)
            Text("Puntos en este turno: ${currentTeam?.currentRoundScore}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Palabras Acertadas:", fontSize = 20.sp)
            LazyColumn(modifier = Modifier.weight(1f).padding(top = 8.dp)) {
                items(currentTeam?.guessedWordsInTurn ?: emptyList()) { word ->
                    Text(word, fontSize = 18.sp)
                }
            }
        }

        Button(
            onClick = { viewModel.onNextTurn() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Yellow)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Siguiente Jugador", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente")
            }
        }
    }
}
