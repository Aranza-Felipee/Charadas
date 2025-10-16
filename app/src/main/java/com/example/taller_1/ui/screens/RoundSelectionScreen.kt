package com.example.taller_1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taller_1.ui.theme.Blue
import com.example.taller_1.ui.theme.Pink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundSelectionScreen(navController: NavController, onRoundSelected: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Tournament",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                RoundCard(roundCount = 3, color = Pink, onRoundSelected = onRoundSelected)
                RoundCard(roundCount = 5, color = Blue, onRoundSelected = onRoundSelected)
                RoundCard(roundCount = 7, color = Pink, onRoundSelected = onRoundSelected)
            }
        }
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoundCard(roundCount: Int, color: Color, onRoundSelected: (Int) -> Unit) {
    Card(
        onClick = { onRoundSelected(roundCount) },
        modifier = Modifier.size(120.dp, 150.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star, // Using a star icon as a placeholder for the trophy
                contentDescription = "Trophy Icon",
                modifier = Modifier.size(60.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$roundCount Rounds",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
