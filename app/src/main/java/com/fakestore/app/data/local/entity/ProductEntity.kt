package com.fakestore.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.fakestore.app.domain.model.Product
import com.fakestore.app.domain.model.Rating
import com.google.gson.Gson

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

class RatingConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun toRating(json: String): Rating {
        return gson.fromJson(json, Rating::class.java)
    }
}


fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        image = image,
        category = category,
        rating = rating,
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        image = image,
        category = category,
        rating = rating,
    )
}
