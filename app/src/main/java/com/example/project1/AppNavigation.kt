package com.example.project1

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Artist : Screen("artist")


}