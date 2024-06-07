package com.example.moblab2.view

import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moblab2.R
import com.example.moblab2.ProductRepository
import com.example.moblab2.databinding.FragmentProductListBinding
import com.example.moblab2.model.Product
import com.example.moblab2.viewmodel.ProductListViewModel
import com.example.moblab2.viewmodel.ProductViewModel
import java.util.UUID
private const val TAG="ProductListFragment"
private const val DIALOG="ProductDialog"

class ProductListFragment:Fragment(R.layout.fragment_product_list) {
    interface Callbacks{
        fun onProductSelected(productId: UUID)
    }
    private var callbacks:Callbacks?=null
    private lateinit var binding: FragmentProductListBinding
    private var adapter: ProductAdapter?=ProductAdapter(emptyList())
    private val productListViewModel:ProductListViewModel by lazy {
        ViewModelProvider(this)[ProductListViewModel::class.java]
    }
    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProductListBinding.inflate(layoutInflater,
            container,false)
        binding.productRecyclerView.layoutManager=LinearLayoutManager(context)
        //binding.productRecyclerView.layoutManager=GridLayoutManager(context,2)
        productListViewModel.productListLiveData.observe(viewLifecycleOwner,
            Observer { products->
                adapter=ProductAdapter(products)
                binding.productRecyclerView.adapter=adapter
                ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
                {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val deleteProduct= products[viewHolder.adapterPosition]
                        Log.d("delete",deleteProduct.name)
                        productViewModel.deleteProduct(deleteProduct)
                        adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }).attachToRecyclerView(binding.productRecyclerView)
            })
        binding.idAdd.setOnClickListener{
            ProductDialogFragment().apply {
                show(this@ProductListFragment.parentFragmentManager, DIALOG)
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }

    private inner class ProductAdapter(var products:List<Product>):
        RecyclerView.Adapter<ProductAdapter.ProductHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
            val view=layoutInflater.inflate(R.layout.list_item_product,parent,false)
            return ProductHolder(view)
        }

        override fun getItemCount(): Int = products.size

        override fun onBindViewHolder(holder: ProductHolder, position: Int) {
            val product=products[position]
            holder.bind(product)
        }
        private inner class ProductHolder(view:View):RecyclerView.ViewHolder(view),
            View.OnClickListener{
            private lateinit var product: Product
            val productNameTextView:TextView=itemView.findViewById(R.id.product_name)
            val productManufacturerTextView:TextView=itemView.findViewById(R.id.product_manufacturer)
            val productQuantityTextView:TextView=itemView.findViewById(R.id.product_quantity)
            val productPriceTextView:TextView=itemView.findViewById(R.id.product_price)
            val productReleaseDate:TextView=itemView.findViewById(R.id.product_releasedate)
            init {
                itemView.setOnClickListener(this)
            }
            fun bind(product: Product){
                this.product=product
                productNameTextView.text=product.name
                productManufacturerTextView.text=product.manufacturer
                productQuantityTextView.text=product.quantity.toString()
                productPriceTextView.text=product.price.toString()
                productReleaseDate.text=product.releaseDate.toString()
            }

            override fun onClick(v: View?) {
                callbacks?.onProductSelected(product.id)
            }
        }

    }
    companion object{
        fun newInstance():ProductListFragment{
            return ProductListFragment()
        }
    }
}