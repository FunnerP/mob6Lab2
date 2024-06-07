package com.example.moblab2

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.moblab2.database.ProductDatabase
import com.example.moblab2.model.Product
import java.util.concurrent.Executors
import java.util.UUID

private const val DATABASE_NAME="product-database"
class ProductRepository private constructor(context: Context)
{
    private val database:ProductDatabase=
        Room.databaseBuilder(
            context.applicationContext,
            ProductDatabase::class.java,
            DATABASE_NAME
        ).build()
    private val productDao=database.productDao()
    private val executor= Executors.newSingleThreadExecutor()
    fun getProducts(): LiveData<List<Product>> = productDao.getProducts()
    fun getProduct(id:UUID):LiveData<Product?> = productDao.getProduct(id)
    fun insertProduct(product: Product){
        executor.execute {
            productDao.insertProduct(product)
        }
    }
    fun updateProduct(product: Product){
        executor.execute{
            productDao.updateProduct(product)
        }
    }
    fun deleteProduct(product: Product){
        executor.execute{
            productDao.deleteProduct(product)
        }
    }
    companion object {
        private var INSTANCE: ProductRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ProductRepository(context)
            }
        }
        fun get(): ProductRepository {
            return INSTANCE ?: throw IllegalStateException("ProductRepository must be init")
        }
    }
}