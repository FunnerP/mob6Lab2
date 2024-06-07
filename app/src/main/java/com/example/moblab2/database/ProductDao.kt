package com.example.moblab2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.moblab2.model.Product
import java.util.UUID

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getProducts():LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE id=(:id)")
    fun getProduct(id: UUID): LiveData<Product?>

    @Insert
    fun insertProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)
}