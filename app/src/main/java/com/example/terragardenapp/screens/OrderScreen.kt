package com.example.terragardenapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        modifier = Modifier
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
            Text(text = "Pesanan")
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn() {
            items(orders) { order ->
                OrderCard(order, onClick = { onOrderClick(order) })
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .shadow(3.dp, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
        ) {
            Text(
                text = "Order ID: ${order.id}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Alamat: ${order.address}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Metode Pembayaran: ${order.paymentMethod}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Total Harga: Rp${"%.2f".format(order.totalPrice)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}