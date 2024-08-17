package com.hav.iot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hav.iot.ui.screen.HomeScreen
import com.hav.iot.ui.screen.ControllerScreen
import com.hav.iot.ui.screen.HistoryScreen
import com.hav.iot.ui.screen.ProfileScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Controller.route) {
            ControllerScreen()
        }
        composable(BottomNavItem.History.route) {
            HistoryScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
    }
}
