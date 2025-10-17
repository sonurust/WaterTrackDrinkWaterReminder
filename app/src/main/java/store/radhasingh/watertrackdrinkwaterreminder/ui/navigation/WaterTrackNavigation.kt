package store.radhasingh.watertrackdrinkwaterreminder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import store.radhasingh.watertrackdrinkwaterreminder.ui.screen.AddGlassScreen
import store.radhasingh.watertrackdrinkwaterreminder.ui.screen.HomeScreen
import store.radhasingh.watertrackdrinkwaterreminder.ui.screen.SettingsScreen

@Composable
fun WaterTrackNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onAddGlassClick = {
                    navController.navigate(Screen.AddGlass.route)
                }
            )
        }
        
        composable(Screen.AddGlass.route) {
            AddGlassScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddGlass : Screen("add_glass")
    object Settings : Screen("settings")
}
