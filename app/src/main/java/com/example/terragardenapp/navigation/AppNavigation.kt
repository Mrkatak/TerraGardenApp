package com.example.terragardenapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.terragardenapp.screens.CartScreen
import com.example.terragardenapp.screens.CheckoutScreen
import com.example.terragardenapp.screens.DetailProductScreen
import com.example.terragardenapp.screens.HomeScreen
import com.example.terragardenapp.screens.OrderDetailScreen
import com.example.terragardenapp.screens.OrderScreen
import com.example.terragardenapp.screens.ProductListScreen
import com.example.terragardenapp.screens.ProfileScreen
import com.example.terragardenapp.viewmodel.CartItem
import com.example.terragardenapp.viewmodel.CartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route



    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute in listOf("home", "cart", "order", "profile")) {
                BottomNavbar(navController)
            }
        },

        ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home"){ HomeScreen(navController) }
            composable("cart"){ CartScreen(cartViewModel, navController) }
            composable("order"){
                OrderScreen( onOrderClick = { order ->
                    navController.navigate("orderDetail/${order.id}")
                })
            }
            composable(
                "orderDetail/{orderId}",
                arguments = listOf(navArgument("orderId") {type = NavType.StringType})
            ){backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId")
                if (orderId != null){
                    OrderDetailScreen(orderId, navController)
                }

            }
            composable("profile"){ ProfileScreen() }
            composable("search"){ ProductListScreen(navController) }
            composable("detail/{productId}") {backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                DetailProductScreen(productId, cartViewModel)
            }
            composable("checkout"){
                val cartItems = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<ArrayList<CartItem>>("cartItems") ?: emptyList()
                CheckoutScreen(cartItems)
            }
        }
    }
}