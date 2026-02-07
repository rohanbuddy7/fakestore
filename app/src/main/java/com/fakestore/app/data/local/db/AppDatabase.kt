package com.fakestore.app.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fakestore.app.data.local.dao.ProductDao
import com.fakestore.app.data.local.entity.ProductEntity
import com.fakestore.app.data.local.entity.RatingConverter

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RatingConverter::class)
abstract class AppDatabase: RoomDatabase() {

    companion object{
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if(instance==null){
                instance = Room
                    .databaseBuilder(context, AppDatabase::class.java, "product.db")
                    .build()
            }
            return instance!!
        }
    }

    abstract fun productDao(): ProductDao

}