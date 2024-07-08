package com.example.terragardenapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.terragardenapp.data.Product
import com.example.terragardenapp.viewmodel.CartViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailProductScreen(productId: String, cartViewModel: CartViewModel = viewModel()) {

    var product by remember { mutableStateOf<Product?>(null) }
    val context = LocalContext.current

    LaunchedEffect(productId) {
        FirebaseFirestore.getInstance().collection("product").document(productId)
            .get()
            .addOnSuccessListener { document ->
                product = document.toObject(Product::class.java)
            }
    }

    product?.let {

        Box(Modifier
            .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = it.imageUrl),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(350.dp),

                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = it.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Rp ${it.price}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                          modifier = Modifier
                              .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))

                        ) {
                            Text(
                                text = "Terjual 200+",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Text(
                            text = "|",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null)

                        Text(
                            text = it.location,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Deskripsi produk :",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = it.description,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )


                }
            }
            Button(
                onClick = {
                    cartViewModel.addToCart(it)
                    Toast.makeText(context, "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Add To Cart")
            }
        }




    } ?: run {
        CircularProgressIndicator()
    }

}

