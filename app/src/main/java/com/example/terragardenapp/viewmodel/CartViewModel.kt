package com.example.terragardenapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.terragardenapp.data.Product

class CartViewModel: ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: SnapshotStateList<CartItem> get() = _cartItems

    fun addToCart(product: Product) {
        val existingItem = _cartItems.find { it.id == product.id }
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            val index = _cartItems.indexOf(existingItem)
            _cartItems[index] = updatedItem
        } else {
            _cartItems.add(CartItem(id = product.id, name = product.name, price = product.price))
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        val index = _cartItems.indexOf(cartItem)
        if (index != -1) {
            _cartItems[index] = cartItem.copy(quantity = cartItem.quantity + 1)
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        val index = _cartItems.indexOf(cartItem)
        if (index != -1 && cartItem.quantity > 1) {
            _cartItems[index] = cartItem.copy(quantity = cartItem.quantity - 1)
        } else if (index != -1 && cartItem.quantity == 1) {
            _cartItems.removeAt(index)
        }
    }

    fun getTotalPrice(): Double{
        //return _cartItems.sumOf { it.price * it.quantity }
        val total = _cartItems.sumOf { it.price * it.quantity }
        println("Total Price: $total") // Tambahkan log untuk debugging
        return total
    }
}