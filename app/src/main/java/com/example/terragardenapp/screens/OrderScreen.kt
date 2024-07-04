package com.example.terragardenapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.terragardenapp.data.Order
import com.example.terragardenapp.data.fetchOrders

@Composable
fun OrderScreen(onOrderClick: (Order) -> Unit) {

    var orders by remember { mutableStateOf(listOf<Order>()) }

    LaunchedEffect(Unit) {
        fetchOrders { fetchedOrders ->
            orders = fetchedOrders
        }
    }
    LazyColumn {
        items(orders) {order ->
            OrderCard(order, onClick = {onOrderClick(order)})
        }
    }
}

@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: ${order.id}")
            Text(text = "Address: ${order.address}")
            Text(text = "Payment Method: ${order.paymentMethod}")
            Text(text = "Total Price: $${"%.2f".format(order.totalPrice)}")
        }
    }
}