package com.example.terragardenapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.terragardenapp.data.Product
import com.example.terragardenapp.viewmodel.CartViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailProductScreen(productId: String, cartViewModel: CartViewModel = viewModel()) {

    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(productId) {
        FirebaseFirestore.getInstance().collection("product").document(productId)
            .get()
            .addOnSuccessListener { document ->
                product = document.toObject(Product::class.java)
            }
    }

    product?.let {
        Column(Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = it.imageUrl),
                contentDescription = null,
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.name, style = MaterialTheme.typography.titleMedium)
            Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Rp ${it.price}", style = MaterialTheme.typography.labelLarge)
            Button(onClick = { cartViewModel.addToCart(it) }) {
                Text(text = "Add To Cart")
            }
        }
    } ?: run {
        CircularProgressIndicator()
    }

}