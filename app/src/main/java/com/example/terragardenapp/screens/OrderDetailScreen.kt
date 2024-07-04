package com.example.terragardenapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            .padding(16.dp)) {
            Text(text = "Order ID: ${it.id}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Address: ${it.address}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Payment Method: ${it.paymentMethod}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Total Price: $${"%.2f".format(it.totalPrice)}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Items:")
            it.items.forEach { item ->
                Text(text = "- ${item["name"]} x${item["quantity"]} @ $${item["price"]}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                deleteOrder(orderId,
                    onSuccess = {
                        navController.popBackStack()
                    },
                    onFailure = {
                        Log.e("OrderDetailScreen", "Error deleting order")
                    })
            }) {
                Text(text = "Selesaikan Pesanan")
            }
        }
    } ?: run {
        Text(text = "Loading order details...")
    }
}