package com.example.moblab2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moblab2.model.Product

@Database(entities = [Product::class], version = 2)
@TypeConverters(ProductTypeConvertor::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao():ProductDao
}