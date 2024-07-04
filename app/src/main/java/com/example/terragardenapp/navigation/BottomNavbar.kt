package com.example.terragardenapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.terragardenapp.NavItem

@Composable
fun BottomNavbar(navController: NavController) {
    val navItems = listOf(
        NavItem("Home", "home" , Icons.Default.Home),
        NavItem("Cart", "cart", Icons.Default.ShoppingCart),
        NavItem("Order", "order", Icons.Default.Menu),
        NavItem("Profile", "profile", Icons.Default.Person)
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.DarkGray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.name)},
                icon = { Icon(imageVector = item.icon, contentDescription = null) }
            )

        }
    }
}

