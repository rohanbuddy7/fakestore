package com.fakestore.app.ui.products

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.fakestore.app.data.network.NetworkResult
import com.fakestore.app.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    onNavigate: (String) -> Unit ,
    vm: ProductsViewModel = hiltViewModel(),
    modifier: Modifier
) {
    LaunchedEffect(Unit) {
        vm.getProducts()
    }

    val state by vm.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)){
            when(state){
                is NetworkResult.Success-> {
                    val items = (state as NetworkResult.Success<List<Product>>).data
                    Log.d("TAG", "ProductScreen: $items")
                    if(items.isEmpty()){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text("No products found", style = MaterialTheme.typography.bodyLarge)
                        }
                    } else {
                        ProductList(
                            modifier = modifier,
                            onNavigate = { id ->
                                onNavigate("detail/$id")
                            },
                            products = items
                        )
                    }
                }
                is NetworkResult.Error -> {
                    val message = (state as NetworkResult.Error).msg
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(message)
                    }
                }
                is NetworkResult.Loading -> {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    products: List<Product>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = products,
            key = { it.id }
        ) { anime ->
            ProductRow(anime) { id ->
                onNavigate(id)
            }
        }

    }
}

@Composable
fun ProductRow(
    item: Product,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .clickable { onClick(item.id) }
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            // Product Image
            Card(
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = item.title,
                    modifier = Modifier.size(96.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )

                Text("Category: ${item.category}")

                Text("₹ ${item.price}")

                Text("⭐ ${item.rating.rate} (${item.rating.count})")
            }
        }
    }
}



//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun ProductDetailScreenUI(
//    product: Product?,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//
//        // Image
//        item {
//            AsyncImage(
//                model = product?.image,
//                contentDescription = product?.title,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(280.dp),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        // Title
//        item {
//            Text(
//                text = product?.title ?: "No Title",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        // Price + Rating
//        item {
//            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                Text("₹ ${product?.price ?: "--"}")
//                Text("⭐ ${product?.rating?.rate ?: "--"} (${product?.rating?.count ?: 0})")
//            }
//        }
//
//        // Category (as a chip)
//        item {
//            if (!product?.category.isNullOrBlank()) {
//                FlowRow(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Card(onClick = {}) {
//                        Text(
//                            text = product!!.category,
//                            modifier = Modifier.padding(10.dp)
//                        )
//                    }
//                }
//            }
//        }
//
//        // Description
//        item {
//            Text(
//                text = "Description",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.SemiBold
//            )
//            Spacer(Modifier.height(6.dp))
//            Text(
//                text = product?.description ?: "No Description Available",
//                style = MaterialTheme.typography.bodyLarge
//            )
//        }
//
//        item { Spacer(Modifier.height(32.dp)) }
//    }
//}
//
