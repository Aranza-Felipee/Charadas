package com.example.taller_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller_1.navigation.Screen
import com.example.taller_1.ui.components.AnimatedBackground
import com.example.taller_1.ui.screens.CategorySelectionScreen
import com.example.taller_1.ui.screens.GameScreen
import com.example.taller_1.ui.screens.PlayerSelectionScreen
import com.example.taller_1.ui.screens.ResultsScreen
import com.example.taller_1.ui.screens.RoundSelectionScreen
import com.example.taller_1.ui.theme.Taller_1Theme
import com.example.taller_1.ui.theme.Yellow
import com.example.taller_1.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    private val gameViewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Taller_1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(gameViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: GameViewModel) {
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBackground()
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.PlayerSelection.route) {
                PlayerSelectionScreen(navController = navController) { playerCount ->
                    viewModel.setGameData(playerCount, 0) // round count will be set in the next screen
                    navController.navigate(Screen.RoundSelection.route)
                }
            }
            composable(Screen.RoundSelection.route) {
                RoundSelectionScreen(navController = navController) { roundCount ->
                    val currentState = viewModel.gameState.value
                    viewModel.setGameData(currentState.teams.size, roundCount)
                    navController.navigate(Screen.CategorySelection.route)
                }
            }
            composable(Screen.CategorySelection.route) {
                CategorySelectionScreen(navController = navController, viewModel = viewModel)
            }
            composable(Screen.Game.route) {
                GameScreen(navController = navController, viewModel = viewModel)
            }
            composable(Screen.Results.route) {
                ResultsScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Charadas Game", fontSize = 48.sp, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Screen.PlayerSelection.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
            modifier = Modifier.size(width = 220.dp, height = 70.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Jugar", color = Color.Black, fontSize = 28.sp)
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
@Composable
fun HomeScreenPreview() {
    Taller_1Theme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}
