package com.example.terragardenapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.terragardenapp.viewmodel.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel, navController: NavController) {

    val cartItems = cartViewModel.cartItems
    val totalPrice by remember {
        derivedStateOf { cartViewModel.getTotalPrice() }
    }

    Log.d("CartScreen", "Total Price: $totalPrice") // Tambahkan log untuk debugging

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEBF2F7))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Keranjang")
        }

        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(8.dp)
        ) {
            items(cartItems) {cartItem ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp)),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = cartItem.imgUrl),
                            contentDescription = null,
                            modifier = Modifier.size(width = 80.dp, height = 80.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = cartItem.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${cartItem.quantity} X Rp ${cartItem.price}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        IconButton(onClick = { cartViewModel.decreaseQuantity(cartItem) }) {
                            Icon( Icons.Default.Delete, contentDescription = null)
                        }
                        IconButton(onClick = { cartViewModel.increaseQuantity(cartItem) }) {
                            Icon( Icons.Default.Add, contentDescription = null)
                        }
                    }
                }


            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
        ) {
            Text(
                text = "Total: Rp ${"%.2f".format(totalPrice)}",
                style = MaterialTheme.typography.labelLarge,

                )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                Log.d("CartScreen", "Navigating to checkout with items: $cartItems")
                navController.currentBackStackEntry?.savedStateHandle?.set("cartItems", ArrayList(cartItems))
                navController.navigate("checkout")
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
            ) {
                Text(text = "Checkout")
            }
        }

        Spacer(modifier = Modifier.height(56.dp))
    }


}