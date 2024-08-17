package com.hav.iot.navigation

import com.hav.iot.R


sealed class BottomNavItem(var title: Int, var icon: Int, var route: String) {
        object Home : BottomNavItem(R.string.home, R.drawable.ic_home, "home")
        object Controller : BottomNavItem(R.string.controller, R.drawable.ic_controller, "controller")
        object History : BottomNavItem(R.string.history, R.drawable.ic_history, "history")
        object Profile : BottomNavItem(R.string.profile, R.drawable.ic_profile, "profile")
}
