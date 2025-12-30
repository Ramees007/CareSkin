package org.ramees.kmp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() = AppTheme {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToDetails = {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("recommendation_data", it)
                    navController.navigate(
                        "details"
                    )
                }
            )
        }

        composable(
            route = "details"
        ) {
            val data =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<String>("recommendation_data")
            DetailsScreen(
                data = data.orEmpty(),
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

