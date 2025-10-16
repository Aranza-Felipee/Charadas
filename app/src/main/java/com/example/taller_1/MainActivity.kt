package com.example.taller_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Public
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller_1.navigation.Screen
import com.example.taller_1.ui.components.AnimatedBackground
import com.example.taller_1.ui.screens.PlayerSelectionScreen
import com.example.taller_1.ui.screens.RoundSelectionScreen
import com.example.taller_1.ui.screens.CategorySelectionScreen
import com.example.taller_1.ui.screens.GameScreen
import com.example.taller_1.ui.screens.ResultsScreen
import com.example.taller_1.ui.theme.Orange
import com.example.taller_1.ui.theme.Taller_1Theme
import com.example.taller_1.ui.theme.Yellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Taller_1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBackground()
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.PlayerSelection.route) {
                PlayerSelectionScreen(navController = navController) { playerCount ->
                    navController.navigate(Screen.RoundSelection.route)
                }
            }
            composable(Screen.RoundSelection.route) {
                RoundSelectionScreen(navController = navController) { roundCount ->
                    navController.navigate(Screen.CategorySelection.route)
                }
            }
            composable(Screen.CategorySelection.route) {
                CategorySelectionScreen(navController = navController)
            }
            composable(Screen.Game.route) {
                GameScreen(navController = navController)
            }
            composable(Screen.Results.route) {
                ResultsScreen(navController = navController)
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
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Game Icon",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Screen.PlayerSelection.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Yellow)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Jugar", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Implement language selection */ },
            colors = ButtonDefaults.buttonColors(containerColor = Orange)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Public, contentDescription = "Language Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Lenguaje", color = Color.Black)
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
