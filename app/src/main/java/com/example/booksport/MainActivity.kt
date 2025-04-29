package com.example.booksport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "ExploreScreen") {
        composable("ExploreScreen") {
            ExploreScreen(navController)
        }
        composable(route = "courtPage/{courtId}",
            arguments = listOf(navArgument("courtId") { type = NavType.IntType })
        ) { backStackEntry ->
            val courtId = backStackEntry.arguments?.getInt("courtId")

            if (courtId != null) {
                CourtPage(courtId, navController)
            } else {
                Text("Court ID not found")
            }
        }
    }
}