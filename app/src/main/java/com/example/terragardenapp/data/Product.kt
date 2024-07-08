package com.example.terragardenapp.data

import android.util.Log
import com.example.terragardenapp.viewmodel.CartItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val description: String = "",
    val location: String = ""
)

data class Order(
    val id: String,
    val items: List<Map<String, Any>>,
    val address: String,
    val shippingMethod: String,
    val paymentMethod: String,
    val totalPrice: Double
)

suspend fun get5Products(): List<Product> {
    val db = FirebaseFirestore.getInstance()
    return try {
        val snapshot = db.collection("product").limit(5).get().await()
        snapshot.toObjects(Product::class.java)
    } catch (e: Exception) {
        emptyList()
    }
}

suspend fun get10Products(): List<Product> {
    val db = FirebaseFirestore.getInstance()
    return try {
        val snapshot = db.collection("product").limit(10).get().await()
        snapshot.toObjects(Product::class.java)
    } catch (e: Exception) {
        emptyList()
    }
}


fun saveOrderToFirestore(cartItems: List<CartItem>, address: String, shippingMethod: String, paymentMethod: String, totalPrice: Double){
    val db = FirebaseFirestore.getInstance()
    val orderData = hashMapOf(
        "items" to cartItems.map {
            hashMapOf("id" to it.id, "name" to it.name, "price" to it.price, "quantity" to it.quantity) },
        "address" to address,
        "shippingMethod" to shippingMethod,
        "paymentMethod" to paymentMethod,
        "totalPrice" to totalPrice
    )
    db.collection("orders")
        .add(orderData)
        .addOnSuccessListener { documentReference ->
            // Tindakan setelah berhasil menyimpan data, misalnya menampilkan toast atau navigasi
            Log.d("Firestore", "Order added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            // Tindakan ketika terjadi kesalahan
            Log.w("Firestore", "Error adding order", e)
        }
}

fun fetchOrders(onOrdersFetched: (List<Order>) -> Unit)  {
    val db = FirebaseFirestore.getInstance()
    db.collection("orders")
        .get()
        .addOnSuccessListener { result ->
            val orders = result.map { document ->
                Order(
                    id = document.id,
                    items = document.get("items") as List<Map<String, Any>>,
                    address = document.getString("address") ?: "",
                    shippingMethod = document.getString("shippingMethod") ?: "",
                    paymentMethod = document.getString("paymentMethod") ?: "",
                    totalPrice = document.getDouble("totalPrice") ?: 0.0
                )
            }
            onOrdersFetched(orders)
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Error getting documents.", exception)
        }
}

fun fetchOrderById(orderId: String, onOrderFetched: (Order?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("orders").document(orderId)
        .get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val order = Order(
                    id = document.id,
                    items = document.get("items") as List<Map<String, Any>>,
                    address = document.getString("address") ?: "",
                    shippingMethod = document.getString("shippingMethod") ?: "",
                    paymentMethod = document.getString("paymentMethod") ?: "",
                    totalPrice = document.getDouble("totalPrice") ?: 0.0
                )
                onOrderFetched(order)
            } else {
                onOrderFetched(null)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Error getting document.", exception)
            onOrderFetched(null)
        }
}

fun deleteOrder(orderId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("orders").document(orderId)
        .delete()
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}