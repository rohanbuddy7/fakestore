package com.fakestore.app.data.network

sealed class NetworkResult<T>(data: T?, msg: String?) {
    data class Success<T>(var data: T): NetworkResult<T>(data, null)
    data class Error<T>(var msg: String): NetworkResult<T>(null, msg)
    class Loading<T>(): NetworkResult<T>(null, null)
}