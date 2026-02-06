package com.fakestore.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fakestore.app.ui.productDetail.ProductDetailScreen
import com.fakestore.app.ui.products.ProductsScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(navController, "products"){
        composable("products"){
            ProductsScreen(
                onNavigate = {},
                modifier = Modifier
            )
        }
        composable("productDetail"){
            ProductDetailScreen()
        }
    }

}