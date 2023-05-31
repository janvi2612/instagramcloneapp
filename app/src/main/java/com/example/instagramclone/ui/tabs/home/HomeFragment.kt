package com.example.instagramclone.ui.tabs.home

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Status
import com.example.instagramclone.utils.getStatusBarHeight
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userArrayList: ArrayList<Status>
    private lateinit var myAdapter : StatusAdapter

    private lateinit var usersArrayList: ArrayList<Post>
    private lateinit var myAdapters : PostAdapter

    private lateinit var db :FirebaseFirestore
    private lateinit var loadingAlert: AlertDialog
    private lateinit var topicList: MutableMap<String, Any>
    lateinit var auth: FirebaseAuth
    var email : String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        db = FirebaseFirestore.getInstance()


        EventChangeListerner()
        EventChangeListernerPost()
        setUpui()
        auth= FirebaseAuth.getInstance()
        myAdapter = StatusAdapter()
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL ,false)
        userArrayList = arrayListOf()
        binding.guideline.setGuidelineBegin(getStatusBarHeight())
        myAdapters = PostAdapter()
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(requireContext())
        usersArrayList = arrayListOf()


        binding.recyclerView2.setHasFixedSize(true)
        binding.recyclerViewPost.setHasFixedSize(true)
        return binding.root

    }

    private fun setUpui(){
        topicList = HashMap()

        db.collection("UserProfile").get().addOnSuccessListener {documents ->

            for (document in documents )
            {
                email = document.get("email").toString()
                //Log.e("emails", "DocumentSnapshot data: ${emaildonor}")

                if (auth.currentUser?.email.toString() == email){

                    Timber.e(document.get("username").toString())
                    binding.textView5.text = document.get("username").toString()
                    binding.imgPost.load(document.get("profilepic").toString()){
                        crossfade(true)
                        transformations(CircleCropTransformation())
                        error(R.drawable.baseline_error_24)
                    }
                }
            }

        }

    }


    private fun EventChangeListerner(){

        //loadingAlert.show()
        db.collection("Userpost")
            .get().addOnSuccessListener {

                    for(data in it.documents)
                    {

                        //Timber.e(data.toString())
                        val requests : Status? = data.toObject(Status::class.java)
                        
                        if (requests != null) {
                            Timber.e(requests.toString())
                            userArrayList.add(requests)
                        }
                            //Timber.e(userArrayList.add(request).toString())

                    }
                    //myAdapter = StatusAdapter()
                    binding.recyclerView2.adapter = myAdapter
                     //binding.recyclerView2.adapter = StatusAdapter()
                  //  myAdapter.notifyDataSetChanged()
                    myAdapter.setData(userArrayList)
                   // loadingAlert.dismiss()
                }




    }


    private fun EventChangeListernerPost(){

        //loadingAlert.show()
        db.collection("UserAddPost").orderBy("timestamp",Query.Direction.DESCENDING)
            .get().addOnSuccessListener {

                for(data in it.documents)
                {
                    val request : Post? = data.toObject(Post::class.java)

                    if (request != null) {
                        usersArrayList.add(request)
                    }
                    //Timber.e(userArrayList.add(request).toString())

                }
                //myAdapter = StatusAdapter()
                binding.recyclerViewPost.adapter = myAdapters

                myAdapters.setData(usersArrayList)
                // loadingAlert.dismiss()
            }




    }

//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }



}