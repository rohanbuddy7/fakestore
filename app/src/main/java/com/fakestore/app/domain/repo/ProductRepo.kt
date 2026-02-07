package com.fakestore.app.domain.repo

import com.fakestore.app.data.api.ApiService
import com.fakestore.app.data.local.dao.ProductDao
import com.fakestore.app.data.local.entity.toEntity
import com.fakestore.app.data.local.entity.toProduct
import com.fakestore.app.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepo(
    var apiService: ApiService,
    var productDao: ProductDao
) {

    suspend fun getProducts(): List<Product>{
        val response = apiService.getProducts()

        val listProductEntity = response.map { it.toEntity() }
        productDao.insertProducts(listProductEntity)

        return response
    }

    suspend fun getProductDetail(id: Int): Product{
        return apiService.getProductDetail(id)
    }


    fun observeProducts(): Flow<List<Product>> {
        return productDao.getAllProduct().map { list ->
            list.map { it.toProduct() }
        }
    }

    fun observeProductsById(id: Int): Flow<Product?> {
        return productDao.observeProductById(id).map {
            it?.toProduct()
        }
    }

}