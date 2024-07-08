package com.example.terragardenapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.terragardenapp.R
import com.example.terragardenapp.data.Product
import com.example.terragardenapp.data.PromoImages
import com.example.terragardenapp.data.get5Products
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    navController: NavController,

) {
    var productList by remember { mutableStateOf(listOf<Product>()) }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("product")
            .get()
            .addOnSuccessListener { result ->
                productList = result.map { document ->
                    document.toObject(Product::class.java).copy(id = document.id)
                }
            }
    }

    Column(modifier = Modifier
        .background(Color(0xFFEBF2F7))) {
        HomeHeader(userName = "Budiono", navController)
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            NewProductList()
            Spacer(modifier = Modifier.height(8.dp))
            ImagePromo()
            Spacer(modifier = Modifier.height(2.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
            ) {
                Text(
                    text = "Rekomendasi",
                    style = MaterialTheme.typography.bodyMedium,

                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .height(1050.dp),

                    ) {
                    items(productList) {product ->
                        ProductItem(product, onItemClick = {
                            navController.navigate("detail/${product.id}")
                        })
                    }
                }

            }
        }
    }




}

@Composable
fun HomeHeader(
    userName: String,
    navController: NavController,

) {
    val contex = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(16.dp)

    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.android_superhero1),
                    contentDescription = "profile_icon",
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                Column(

                ) {
                    Text(
                        text = "Selamat Datang",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "NotificationIcon",
                        tint = Color.LightGray
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ){
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "NotificationIcon",
                        tint = Color.LightGray
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .clickable { navController.navigate("search") }
                .padding(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cari...",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
    }
}

@Composable
fun NewProductList() {

    val products = remember { mutableStateOf<List<Product>>(emptyList()) }
    LaunchedEffect(Unit) {
        products.value = get5Products()
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(16.dp)
    ) {
        Text(text = "Terbaru", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(Modifier.padding()) {
            items(products.value) {
                NewProductItem(product = it)
            }
        }
    }
    
}
@Composable
fun NewProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .shadow(3.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))


    ) {
        Column(
            modifier = Modifier
                .background(Color.White)

        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(width = 125.dp, height = 170.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Rp ${product.price}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun ImagePromo() {
    var currentIndex by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit){
        while (true) {
            delay(5000)
            currentIndex = (currentIndex + 1) % PromoImages.drawableResIds.size
        }
    }

    Image(
        painter = painterResource(id = PromoImages.drawableResIds[currentIndex]),
        contentDescription = "Promo Image",
        modifier = Modifier.fillMaxWidth()
    )
}



@Composable
fun ProductItem(product: Product, onItemClick: ()-> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable ( onClick = onItemClick )
            .shadow(3.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)

        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(width = 170.dp, height = 135.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Rp ${product.price}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "LocationIcon",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = product.location,
                        style = MaterialTheme.typography.labelSmall
                    )

                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPrev() {
    
}
