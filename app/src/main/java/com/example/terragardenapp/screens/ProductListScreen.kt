package com.example.terragardenapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        Column {
            OutlinedTextField(
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
                label = { Text(text = "Cari Disini", fontSize = 16.sp, color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(32.dp))
                    .padding(16.dp)
            )
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ){
            items(filteredList) {product ->
                ProductItem(product, onItemClick = {
                    navController.navigate("detail/${product.id}")
                })
            }
        }
    }
}