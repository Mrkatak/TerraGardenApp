package com.example.terragardenapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.terragardenapp.data.Product
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProductListScreen(navController: NavController) {

    var searchQuery by remember {
        mutableStateOf("")
    }
    var filteredList by remember { mutableStateOf(listOf<Product>()) }
    var productList by remember { mutableStateOf(listOf<Product>()) }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("product")
            .get()
            .addOnSuccessListener { result ->
                productList = result.map { document ->
                    document.toObject(Product::class.java).copy(id = document.id)
                }
                filteredList = productList
            }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { text ->
                searchQuery = text
                filteredList = if (searchQuery.isEmpty()){
                    productList
                } else{
                    productList.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }
                }

            },
            label = { Text(text = "Cari Disini")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ){
            items(filteredList) {product ->
                ProductItem(product, onItemClick = {
                    navController.navigate("detail/${product.id}")
                })
            }
        }
    }
}