package com.fakestore.app.data.api

import com.fakestore.app.domain.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/products")
    suspend fun getProducts(): List<Product>


    @GET("/products/{id}")
    suspend fun getProductDetail(
        @Path("id") id: Int
    ): Product

}