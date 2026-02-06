package com.fakestore.app.ui.productDetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Detail") }
            )
        }
    ) { paddingValues ->



    }

}