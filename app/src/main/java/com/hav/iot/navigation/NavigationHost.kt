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
import com.hav.iot.viewmodel.DataSensorViewModel
import com.hav.iot.viewmodel.DeviceActionViewModel
import com.hav.iot.viewmodel.HomeViewmodel

@Composable
fun NavigationHost(homeViewModel: HomeViewmodel, navController: NavHostController, modifier: Modifier = Modifier,
                   dataSensorViewModel: DataSensorViewModel,
                     deviceActionViewModel: DeviceActionViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(homeViewModel)
        }
        composable(BottomNavItem.Controller.route) {
            ControllerScreen( deviceActionViewModel)
        }
        composable(BottomNavItem.History.route) {
            HistoryScreen(dataSensorViewModel)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
    }
}
