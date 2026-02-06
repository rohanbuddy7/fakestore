package com.fakestore.app.domain.repo

import com.fakestore.app.data.api.ApiService
import com.fakestore.app.domain.model.Product

class ProductRepo(
    var apiService: ApiService
) {

    suspend fun getProducts(): List<Product>{
        return apiService.getProducts()
    }

}