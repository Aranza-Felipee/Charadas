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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taller_1.navigation.Screen
import com.example.taller_1.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()

    if (gameState.isGameOver) {
        navController.navigate(Screen.Results.route) { 
            popUpTo(Screen.Home.route)
        }
    }

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
                Text("Puntaje: ${currentTeam?.score ?: ""}", fontSize = 20.sp)
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
