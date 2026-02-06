package com.fakestore.app.ui.productDetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fakestore.app.data.network.NetworkResult
import com.fakestore.app.domain.model.Product
import com.fakestore.app.ui.products.ProductList
import com.fakestore.app.ui.products.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    id: Int,
    onBack: () -> Unit ,
    vm: ProductsViewModel = hiltViewModel(),
    modifier: Modifier
) {

    LaunchedEffect(id) {
        vm.getProductDetail(id)
    }

    val state by vm.detail.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues = paddingValues)){
            when(state){
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
                        modifier = Modifier.fillMaxWidth().padding(paddingValues).wrapContentSize(Alignment.Center)
                    )
                }
                is NetworkResult.Success -> {
                    val items = (state as NetworkResult.Success<Product>).data
                    Log.d("TAG", "ProductScreen: $items")
                    ProductDetailScreenUI(
                        modifier = modifier,
                        product = items
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductDetailScreenUI(
    product: Product?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Image
        item {
            AsyncImage(
                model = product?.image,
                contentDescription = product?.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Title
        item {
            Text(
                text = product?.title ?: "No Title",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Price + Rating
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("₹ ${product?.price ?: "--"}")
                Text("⭐ ${product?.rating?.rate ?: "--"} (${product?.rating?.count ?: 0})")
            }
        }

        // Category (as a chip)
        item {
            if (!product?.category.isNullOrBlank()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(onClick = {}) {
                        Text(
                            text = product!!.category,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

        // Description
        item {
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = product?.description ?: "No Description Available",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}
