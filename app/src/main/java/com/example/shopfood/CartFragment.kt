package com.example.shopfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfood.Adapters.CartAdapter
import com.example.shopfood.Models.PopularModel
import com.example.shopfood.Models.SharedModel
import com.example.shopfood.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding


    private lateinit var adapter: CartAdapter

    private  lateinit var sharedModel: SharedModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCartBinding.inflate(inflater, container, false)

        sharedModel = ViewModelProvider(requireActivity()).get(SharedModel :: class.java)


        adapter = CartAdapter(requireContext(), sharedModel.cartItem.value ?: ArrayList())
        binding.cartRv.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRv.adapter = adapter
        return binding.root

    }

}