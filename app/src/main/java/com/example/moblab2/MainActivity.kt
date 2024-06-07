package com.example.moblab2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moblab2.view.ProductFragment
import com.example.moblab2.view.ProductListFragment
import com.example.moblab2.databinding.ActivityMainBinding
import java.util.UUID

private const val TAG="MainActivity"
class MainActivity : AppCompatActivity(), ProductListFragment.Callbacks {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment=supportFragmentManager.
        findFragmentById(R.id.fragment_container)
        if(currentFragment==null){
            val fragment=ProductListFragment.newInstance()
            supportFragmentManager.beginTransaction().
            add(R.id.fragment_container,fragment).commit()
        }
    }

    override fun onProductSelected(productId: UUID) {
        val fragment= ProductFragment.newInstance(productId)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            fragment).addToBackStack(null).commit()
    }
}