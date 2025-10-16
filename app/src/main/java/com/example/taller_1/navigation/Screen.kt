package com.example.taller_1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object PlayerSelection : Screen("player_selection")
    object RoundSelection : Screen("round_selection")
}