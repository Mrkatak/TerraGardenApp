package com.example.terragardenapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import com.example.terragardenapp.data.saveOrderToFirestore
import com.example.terragardenapp.viewmodel.CartItem


@Composable
fun CheckoutScreen(cartItems: List<CartItem>) {

    var address by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    val paymentMethods = listOf("Credit Card", "Debit Card", "PayPal", "Cash on Delivery")
    var expanded by remember { mutableStateOf(false) }
    val totalPrice = cartItems.sumOf { it.price * it.quantity }
    var showDialog by remember { mutableStateOf(false) }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        LazyColumn(){
            items(cartItems) { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = cartItem.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${cartItem.quantity} X Rp${cartItem.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box {
            OutlinedTextField(
                value = paymentMethod,
                onValueChange = { },
                label = { Text("Payment Method") },
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
                        Text(text = method)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Total: $${"%.2f".format(totalPrice)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.End)
        )
        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Place Order")
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Order Confirmation") },
            text = { Text("Are you sure you want to place this order?") },
            confirmButton = {
                Button(
                    onClick = {
                        saveOrderToFirestore(cartItems, address, paymentMethod, totalPrice)
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