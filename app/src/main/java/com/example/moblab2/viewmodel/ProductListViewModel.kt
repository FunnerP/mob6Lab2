package com.example.moblab2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moblab2.ProductRepository

class ProductListViewModel: ViewModel() {
    private val productRepository=ProductRepository.get();
    val productListLiveData=productRepository.getProducts()
}