package com.example.terragardenapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.terragardenapp.data.Order
import com.example.terragardenapp.data.deleteOrder
import com.example.terragardenapp.data.fetchOrderById

@Composable
fun OrderDetailScreen(orderId: String, navController: NavController) {
    var order by remember { mutableStateOf<Order?>(null) }

    LaunchedEffect(orderId) {
        fetchOrderById(orderId) { fetchedOrder ->
            order = fetchedOrder
        }
    }

    order?.let {
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
                Text(text = "Detail Pesanan")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)

                ) {
                    Text(
                        text = "Order ID: ${it.id}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Items:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    it.items.forEach { item ->
                        Text(
                            text = "- ${item["name"]} x${item["quantity"]} \n \t Jml : Rp${item["price"]}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                ) {
                    Text(
                        text = "Address: ${it.address}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Shipping Method: ${it.shippingMethod}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Payment Method: ${it.paymentMethod}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Total Price: $${"%.2f".format(it.totalPrice)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
            Button(onClick = {
                deleteOrder(orderId,
                    onSuccess = {
                        navController.popBackStack()
                    },
                    onFailure = {
                        Log.e("OrderDetailScreen", "Error deleting order")
                    })
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Selesaikan Pesanan")
            }

        }
    } ?: run {
        Text(text = "Loading order details...")
    }
}