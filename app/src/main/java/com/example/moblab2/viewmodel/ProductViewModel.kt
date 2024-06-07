package com.example.moblab2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.moblab2.ProductRepository
import com.example.moblab2.model.Product
import java.util.UUID

class ProductViewModel:ViewModel() {
    private val productRepository=ProductRepository.get()
    private val productIdLiveData= MutableLiveData<UUID>()
    var productLiveData: LiveData<Product?> =
        productIdLiveData.switchMap { productId->productRepository.getProduct(productId) }
    fun loadProduct(productID: UUID){
        productIdLiveData.value=productID
    }
    fun updateProduct(product: Product){
        productRepository.updateProduct(product)
    }
    fun saveProduct(product: Product){
        productRepository.insertProduct(product)
    }
    fun  deleteProduct(product: Product){
        productRepository.deleteProduct(product)
    }
}