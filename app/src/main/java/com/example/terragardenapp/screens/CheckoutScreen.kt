package com.example.terragardenapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.terragardenapp.data.saveOrderToFirestore
import com.example.terragardenapp.viewmodel.CartItem


@Composable
fun CheckoutScreen(cartItems: List<CartItem>) {

    var address by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    val paymentMethods = listOf("Credit Card", "Debit Card", "PayPal", "Cash on Delivery")
    var expanded by remember { mutableStateOf(false) }

    var shippingMethod by remember {
        mutableStateOf("")
    }
    var shippingMethods = listOf("JNE Reguler", "JNT", "PAXEL")
    var shippingExpanded by remember { mutableStateOf(false) }

    val totalPrice = cartItems.sumOf { it.price * it.quantity }
    var showDialog by remember { mutableStateOf(false) }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEBF2F7))
        .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Checkout")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
        ) {

            LazyColumn(modifier = Modifier.heightIn(max = 300.dp)){
                items(cartItems) { cartItem ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier
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
                            Column(modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                            ) {
                                Text(
                                    text = cartItem.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp${cartItem.price}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            Text(
                                text = "${cartItem.quantity}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )

                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
        ) {

            Row {
                Icon(imageVector = Icons.Default.Place, contentDescription = "location")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Alamat", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = address,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { address = it },
                label = { Text("Masukkan alamat yang sesuai", fontSize = 16.sp, color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "location")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Pengiriman", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
            ){
                OutlinedTextField(
                    value = shippingMethod,
                    onValueChange = {},
                    textStyle = MaterialTheme.typography.bodyMedium,
                    label = { Text("Jasa Pengiriman", fontSize = 16.sp) },
                    trailingIcon = {
                        IconButton(onClick = { shippingExpanded = !shippingExpanded }) {
                            Icon(imageVector = if (shippingExpanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown, contentDescription = null)
                        }
                    },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = shippingExpanded,
                    onDismissRequest = { shippingExpanded = false }
                ) {
                    shippingMethods.forEach { method ->
                        DropdownMenuItem(
                            onClick = {
                                shippingMethod = method
                                shippingExpanded = false
                            }
                        ) {
                            Text(text = method, fontSize = 16.sp)
                        }

                    }
                }
            }


        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = paymentMethod,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { },
                label = { Text("Payment Method", fontSize = 16.sp) },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(imageVector = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                paymentMethods.forEach { method ->
                    DropdownMenuItem(
                        onClick = {
                            paymentMethod = method
                            expanded = false
                        }
                    ) {
                        Text(text = method, fontSize = 16.sp)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text(
                text = "Total: Rp${"%.2f".format(totalPrice)}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
            ) {
                Text(text = "Place Order")
            }
        }

    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Order Confirmation", style = MaterialTheme.typography.titleMedium) },
            text = { Text("Apakah anda yakin untuk memesan?", style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                Button(
                    onClick = {
                        saveOrderToFirestore(cartItems, address, shippingMethod, paymentMethod, totalPrice)
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}