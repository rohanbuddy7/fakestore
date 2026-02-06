package com.fakestore.app.di

import com.fakestore.app.data.api.ApiService
import com.fakestore.app.data.network.NetworkModule
import com.fakestore.app.domain.repo.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = NetworkModule.api

    @Singleton
    @Provides
    fun provideProductRepo(apiService: ApiService): ProductRepo =
        ProductRepo(apiService = apiService)

}