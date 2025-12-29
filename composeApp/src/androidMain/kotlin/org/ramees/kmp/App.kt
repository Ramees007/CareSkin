package org.ramees.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() = MaterialTheme {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToDetails = {
                    navController.navigate(
                        "details/{recommendation_data}".replace(
                            "{recommendation_data}",
                            it
                        )
                    )
                }
            )
        }

        composable("details/{recommendation_data}") {
            DetailsScreen(it.arguments?.getString("recommendation_data", "").orEmpty())
        }
    }
}

