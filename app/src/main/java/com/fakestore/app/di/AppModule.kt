package com.fakestore.app.di

import android.content.Context
import com.fakestore.app.data.api.ApiService
import com.fakestore.app.data.local.dao.ProductDao
import com.fakestore.app.data.local.db.AppDatabase
import com.fakestore.app.data.network.NetworkModule
import com.fakestore.app.domain.repo.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideProductDao(appDatabase: AppDatabase) = appDatabase.productDao()

    @Singleton
    @Provides
    fun provideProductRepo(apiService: ApiService, productDao: ProductDao): ProductRepo =
        ProductRepo(apiService = apiService, productDao = productDao)

}