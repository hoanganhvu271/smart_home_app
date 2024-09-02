package com.hav.iot.navigation

import com.hav.iot.R


sealed class BottomNavItem(var title: Int, var icon: Int, var route: String) {
        object Home : BottomNavItem(R.string.home, R.drawable.ic_home, "home")
        object Controller : BottomNavItem(R.string.action, R.drawable.ic_controller, "controller")
        object History : BottomNavItem(R.string.sensor, R.drawable.ic_sensor, "history")
        object Profile : BottomNavItem(R.string.profile, R.drawable.ic_profile, "profile")
}
