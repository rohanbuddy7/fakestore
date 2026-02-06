package com.fakestore.app.data.api

import com.fakestore.app.domain.model.Product
import retrofit2.http.GET

interface ApiService {

    @GET("/products")
    suspend fun getProducts(): List<Product>

}