package com.example.moblab2.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moblab2.R
import com.example.moblab2.databinding.FragmentProductBinding
import com.example.moblab2.model.Product
import com.example.moblab2.viewmodel.ProductViewModel
import java.util.UUID

private const val ARG_PRODUCT_ID="product_id"
private const val TAG="ProductFragment"
private const val ARG_PRODUCT = "product"
private const val REQUEST_PRODUCT = 0

class ProductFragment : Fragment(R.layout.fragment_product) {

    companion object {
        fun newInstance(productId:UUID) :ProductFragment {
            val args=Bundle().apply {
                putSerializable(ARG_PRODUCT_ID,productId)
            }
            return ProductFragment().apply {
                arguments=args
            }
        }
    }
    private lateinit var binding: FragmentProductBinding
    private lateinit var product:Product
    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product= Product()
        val productId:UUID=arguments?.getSerializable(ARG_PRODUCT_ID) as UUID
        productViewModel.loadProduct(productId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProductBinding.inflate(layoutInflater,container,false)
        productViewModel.productLiveData.observe(viewLifecycleOwner,
            Observer {
                    product->
                product?.let {
                    this.product=product
                    binding.tvName.text=product.name
                    binding.tvQua.text=product.quantity.toString()
                    binding.tvManu.text=product.manufacturer
                    binding.tvPrice.text=product.price.toString()
                    binding.tvRelDate.text=product.releaseDate.toString()
                }
            })
        binding.btEdit.setOnClickListener {
            ProductDialogFragment.newInstance(product).apply {
                setTargetFragment(this@ProductFragment, REQUEST_PRODUCT)
                show(this@ProductFragment.requireFragmentManager(), ARG_PRODUCT)
            }
        }
        return binding.root
    }
}