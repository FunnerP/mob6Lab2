package com.example.moblab2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.moblab2.R
import com.example.moblab2.databinding.ProductDialogBinding
import com.example.moblab2.model.Product
import com.example.moblab2.viewmodel.ProductViewModel

private const val ARG_PRODUCT = "product"
class ProductDialogFragment: DialogFragment() {
    companion object {
        fun newInstance(product: Product): ProductDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_PRODUCT, product)
            }

            return ProductDialogFragment().apply {
                arguments = args
            }
        }
    }
    private lateinit var binding: ProductDialogBinding
    private lateinit var product:Product
    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ProductDialogBinding.inflate(inflater,container,false)
        var productArgs:Product
        if(arguments!=null) {
            productArgs = arguments?.getSerializable(ARG_PRODUCT) as Product
            productViewModel.loadProduct(productArgs.id)
        }
        productViewModel.productLiveData.observe(viewLifecycleOwner
        ) { product ->
            product?.let {
                if (arguments != null) {
                    this.product = product
                    binding.etName.setText(product.name)
                    binding.etQua.setText(product.quantity.toString())
                    binding.etPrice.setText(product.price.toString())
                    binding.etManu.setText(product.manufacturer)
                    binding.etRelDate.setText(product.releaseDate.toString())
                }
            }
        }
        binding.btOk.setOnClickListener {

            if (arguments != null) {
                product.name = binding.etName.text.toString()
                product.quantity = binding.etQua.text.toString().toInt()
                product.price = binding.etPrice.text.toString().toInt()
                product.manufacturer = binding.etManu.text.toString()
                product.releaseDate = binding.etRelDate.text.toString().toInt()
                productViewModel.updateProduct(product)
            }
            else
            {
                this.product= Product()
                product.name = binding.etName.text.toString()
                product.quantity = binding.etQua.text.toString().toInt()
                product.price = binding.etPrice.text.toString().toInt()
                product.manufacturer = binding.etManu.text.toString()
                product.releaseDate = binding.etRelDate.text.toString().toInt()
                productViewModel.saveProduct(product)
            }
            dismiss()
        }
        binding.btCancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}