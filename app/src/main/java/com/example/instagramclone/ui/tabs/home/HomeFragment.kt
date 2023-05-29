package com.example.instagramclone.ui.tabs.home

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.example.instagramclone.model.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber


class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userArrayList: ArrayList<Status>
    private lateinit var myAdapter : StatusAdapter
    private lateinit var db :FirebaseFirestore
    private lateinit var loadingAlert: AlertDialog

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        db = FirebaseFirestore.getInstance()
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        userArrayList = arrayListOf()

        EventChangeListerner()
        binding.recyclerView2.setHasFixedSize(true)

        return binding.root

    }
//    override fun onCreateOptionsMenu(menu: Menu, ms: MenuInflater) : Boolean {
//        val inflater: MenuInflater = ms
//        inflater.inflate(R.menu.top_menu, menu)
//        return true
//    }

    private fun EventChangeListerner(){

        //loadingAlert.show()
        db.collection("Userpost")
            .get().addOnSuccessListener {

                if (!it.isEmpty){
                    for(data in it.documents)
                    {
                        val request : Status? = data.toObject(Status::class.java)

                        if (request != null) {
                            userArrayList.add(request)
                        }
                            //Timber.e(userArrayList.add(request).toString())

                    }
                    myAdapter = StatusAdapter()
                    binding.recyclerView2.adapter = myAdapter
                     //binding.recyclerView2.adapter = StatusAdapter()
                    myAdapter.setData(userArrayList)
                   // loadingAlert.dismiss()
                }


            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}