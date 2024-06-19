package com.example.terragardenapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.terragardenapp.screens.CartScreen
import com.example.terragardenapp.screens.HomeScreen
import com.example.terragardenapp.screens.OrderScreen
import com.example.terragardenapp.screens.ProfileScreen


@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Cart", Icons.Default.ShoppingCart),
        NavItem("Order", Icons.Default.Menu),
        NavItem("Profile", Icons.Default.Person)
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                //containerColor = Color.Blue
            ) {
                navItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icon" )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {
        ContentScreen(modifier = Modifier.padding(paddingValues = it), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when(selectedIndex){
        0-> HomeScreen()
        1-> CartScreen()
        2-> OrderScreen()
        3-> ProfileScreen()
    }

}