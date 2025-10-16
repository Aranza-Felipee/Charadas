package com.example.taller_1.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        ActiveGameScreen(navController, viewModel)
    }
}

@Composable
fun ActiveGameScreen(navController: NavController, viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val buttonSize = maxWidth / 4
        val currentTeam = gameState.teams.getOrNull(gameState.currentTeamIndex)
        
        // Pause Button
        IconButton(
            onClick = { viewModel.pauseGame() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Pause, contentDescription = "Pausar Juego", modifier = Modifier.size(36.dp))
        }

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

        if (gameState.isPaused) {
            PauseDialog(viewModel = viewModel)
        }
    }
}

@Composable
fun PauseDialog(viewModel: GameViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.resumeGame() }, // Resume if user clicks outside
        title = { Text("Juego Pausado") },
        text = { Text("¿Qué deseas hacer?") },
        confirmButton = {
            Button(
                onClick = { viewModel.resumeGame() })
            {
                Text("Reanudar")
            }
        },
        dismissButton = {
            Button(
                onClick = { viewModel.endGame() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Terminar Juego")
            }
        }
    )
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
