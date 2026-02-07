package com.fakestore.app.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakestore.app.data.network.NetworkResult
import com.fakestore.app.domain.model.Product
import com.fakestore.app.domain.repo.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(var repo: ProductRepo): ViewModel() {

    private var _state = MutableStateFlow<NetworkResult<List<Product>>>(NetworkResult.Loading())
    val state : StateFlow<NetworkResult<List<Product>>> = _state


    private var _detail = MutableStateFlow<NetworkResult<Product>>(NetworkResult.Loading())
    val detail : StateFlow<NetworkResult<Product>> = _detail

    init {
        observeCachedProducts()
    }

    fun getProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = repo.getProducts()
                _state.value = NetworkResult.Success(products)
            }catch (e: Exception){
                e.printStackTrace()
                if(_state.value !is NetworkResult.Success){
                    _state.value = NetworkResult.Error("Something went wrong")
                }
            }
        }
    }

    fun getProductDetail(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val detail = repo.getProductDetail(id)
                _detail.value = NetworkResult.Success(detail)
            }catch (e: Exception){
                e.printStackTrace()
                if(_detail.value !is NetworkResult.Success){
                    _detail.value = NetworkResult.Error("Something went wrong")
                }
            }
        }
    }


    private fun observeCachedProducts() {
        viewModelScope.launch {
            repo.observeProducts().collect { cachedProducts ->
                if (cachedProducts.isNotEmpty()) {
                    _state.value = NetworkResult.Success(cachedProducts)
                }
            }
        }
    }

    fun observeCachedProductsById(id: Int) {
        viewModelScope.launch {
            repo.observeProductsById(id).collect { cachedProduct ->
                if (cachedProduct != null) {
                    _detail.value = NetworkResult.Success(cachedProduct)
                }
            }
        }
    }

}