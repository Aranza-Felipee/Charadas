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
import androidx.navigation.NavController
import com.example.charadas.data.WordProvider
import com.example.taller_1.navigation.Screen
import com.example.taller_1.viewmodel.GameViewModel

@Composable
fun CategorySelectionScreen(navController: NavController, viewModel: GameViewModel) {
    val categories = WordProvider.getCategories()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona una categoría")
        Spacer(modifier = Modifier.height(16.dp))
        categories.forEach { category ->
            Button(onClick = {
                viewModel.selectCategory(category)
                navController.navigate(Screen.Game.route)
            }) {
                Text(category)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
