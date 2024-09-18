package com.hav.iot.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hav.iot.navigation.BottomNavigationBar
import com.hav.iot.navigation.NavigationHost
import com.hav.iot.viewmodel.DataSensorViewModel
import com.hav.iot.viewmodel.HomeViewmodel

@Composable
fun MainScreen(homeViewModel: HomeViewmodel) {
    val navController = rememberNavController()
    val dataSensorViewModel: DataSensorViewModel = DataSensorViewModel()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavigationHost(homeViewModel, navController, Modifier.padding(innerPadding), dataSensorViewModel)
    }
}
