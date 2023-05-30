package com.example.instagramclone.ui.tabs.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Profile
import com.example.instagramclone.model.Status
import com.example.instagramclone.ui.tabs.home.PostAdapter
import com.example.instagramclone.ui.tabs.home.StatusAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersArrayList: ArrayList<Profile>


    private lateinit var myAdapterprofile : ProfileAdapter
    private lateinit var myAdapterpostgrid : UserPostAdapter

    private lateinit var db : FirebaseFirestore
    private lateinit var loadingAlert: AlertDialog

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()

        myAdapterprofile = ProfileAdapter()
        binding.recyclrview.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL ,false)
        usersArrayList = arrayListOf()

        myAdapterpostgrid = UserPostAdapter()
        binding.recycleruserpost.layoutManager = LinearLayoutManager(requireContext())
        usersArrayList = arrayListOf()

        EventChangeListerner()
        EventChangeListernerPost()
        binding.recyclrview.setHasFixedSize(true)
        binding.recycleruserpost.setHasFixedSize(true)
        return binding.root
    }
    private fun EventChangeListerner(){

        //loadingAlert.show()
        db.collection("ProfileDetails")
            .get().addOnSuccessListener {

                for(data in it.documents)
                {
                    //Timber.e(data.toString())
                    val requests : Profile? = data.toObject(Profile::class.java)

                    if (requests != null) {
                        Timber.e(requests.toString())
                        usersArrayList.add(requests)
                    }
                    //Timber.e(userArrayList.add(request).toString())

                }
                //myAdapter = StatusAdapter()
                binding.recycleruserpost.adapter = myAdapterpostgrid
                //binding.recyclerView2.adapter = StatusAdapter()
                //  myAdapter.notifyDataSetChanged()
                myAdapterpostgrid.setData(usersArrayList)
                // loadingAlert.dismiss()
            }




    }


    private fun EventChangeListernerPost(){

        //loadingAlert.show()
        db.collection("ProfileDetails")
            .get().addOnSuccessListener {

                for(data in it.documents)
                {
                    val request : Profile? = data.toObject(Profile::class.java)

                    if (request != null) {
                        usersArrayList.add(request)
                    }
                    //Timber.e(userArrayList.add(request).toString())

                }
                //myAdapter = StatusAdapter()
                binding.recyclrview.adapter = myAdapterprofile
                //binding.recyclerView2.adapter = StatusAdapter()
                //  myAdapter.notifyDataSetChanged()
                myAdapterprofile.setData(usersArrayList)
                // loadingAlert.dismiss()
            }




    }



}