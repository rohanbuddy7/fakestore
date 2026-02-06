package com.fakestore.app.ui.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fakestore.app.ui.productDetail.ProductDetailScreen
import com.fakestore.app.ui.products.ProductsScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(navController, "products"){
        composable("products"){
            ProductsScreen(
                onNavigate = {
                    navController.navigate(it)
                },
                modifier = Modifier
            )
        }
        composable(
            "productDetail/{id}",
            arguments = listOf(
                navArgument("id"){ type = NavType.IntType })
        ){entry->
            val id = entry.arguments!!.getInt("id")
            ProductDetailScreen(
                id = id,
                onBack = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}