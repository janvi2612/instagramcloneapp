package com.example.instagramclone.ui.tabs.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Profile
import com.example.instagramclone.utils.getStatusBarHeight
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var usersArrayLists: ArrayList<Profile>
    private lateinit var usersArrayList: ArrayList<Post>
    private lateinit var myAdapterprofile : ProfileAdapter
    private lateinit var myAdapterpostgrid : UserPostAdapter
    private lateinit var topicList: MutableMap<String, Any>


    private lateinit var db : FirebaseFirestore
    private lateinit var loadingAlert: AlertDialog

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()
        binding.guideline.setGuidelineBegin(getStatusBarHeight())

        auth= FirebaseAuth.getInstance()
        myAdapterpostgrid = UserPostAdapter()
        binding.recycleruserpost.layoutManager = GridLayoutManager(requireContext(),3)
        setUpUi()
        usersArrayList = arrayListOf()

        myAdapterprofile = ProfileAdapter()
        binding.recyclrview.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL ,false)
        usersArrayLists = arrayListOf()


        EventChangeListerner()
        EventChangeListernerPost()
        binding.recyclrview.setHasFixedSize(true)
        binding.recycleruserpost.setHasFixedSize(true)
        return binding.root
    }
    private fun setUpUi(){
        topicList = HashMap()
//        val user_name = binding.textView5.text.toString()
//        val user_profle = binding.btnEditProfileImg.load(""){
//            crossfade(true)
//            transformations()
//            error(R.drawable.baseline_error_24)
//        }

        db.collection("UserProfile").get().addOnSuccessListener {documents ->

            for (document in documents )
            {
                val email = document.get("email").toString()
                //Log.e("emails", "DocumentSnapshot data: ${emaildonor}")

                if (auth.currentUser?.email.toString() == email){

                    Timber.e(document.get("username").toString())
                    binding.txtusername.text = document.get("name").toString()
                    binding.textusername.text = document.get("username").toString()
                    binding.txtpostno.text = document.get("postno").toString()
                    binding.txtfollowersno.text = document.get("followersno").toString()
                    binding.txtfollowingno.text = document.get("followingno").toString()
                    binding.txt.text = document.get("bio").toString()
                    binding.imgprofile.load(document.get("profilepic").toString()){
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
        db.collection("UserAddPost").orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {

                for(data in it.documents)
                {

                    //Timber.e(data.toString())
                    val requests : Post? = data.toObject(Post::class.java)

                    if (requests != null) {
                        if (requests.email == auth.currentUser?.email.toString()){
                            Timber.e(requests.toString())
                            usersArrayList.add(requests)
                        }

                    }
                }
                binding.recycleruserpost.adapter = myAdapterpostgrid
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
                        if (request.email == auth.currentUser?.email.toString()){
                            usersArrayLists.add(request)
                        }

                    }
                }
                //myAdapter = StatusAdapter()
                binding.recyclrview.adapter = myAdapterprofile
                myAdapterprofile.setData(usersArrayLists)
                // loadingAlert.dismiss()
            }
    }



}