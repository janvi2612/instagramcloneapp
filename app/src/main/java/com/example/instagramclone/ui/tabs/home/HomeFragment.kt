package com.example.instagramclone.ui.tabs.home

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)



        return binding.root

    }
//    override fun onCreateOptionsMenu(menu: Menu, ms: MenuInflater) : Boolean {
//        val inflater: MenuInflater = ms
//        inflater.inflate(R.menu.top_menu, menu)
//        return true
//    }



}