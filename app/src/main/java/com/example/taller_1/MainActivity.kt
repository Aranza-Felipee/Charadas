package com.example.taller_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller_1.navigation.Screen
import com.example.taller_1.ui.screens.PlayerSelectionScreen
import com.example.taller_1.ui.screens.RoundSelectionScreen
import com.example.taller_1.ui.theme.Taller_1Theme

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
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.PlayerSelection.route) {
            PlayerSelectionScreen { playerCount ->
                navController.navigate(Screen.RoundSelection.route)
            }
        }
        composable(Screen.RoundSelection.route) {
            RoundSelectionScreen { roundCount ->
                // TODO: Navigate to the game screen
                navController.navigate(Screen.Home.route) { // For now, go back to home
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
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
        Button(onClick = { navController.navigate(Screen.PlayerSelection.route) }) {
            Text(text = "Jugar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Implement language selection */ }) {
            Text(text = "Lenguaje")
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
