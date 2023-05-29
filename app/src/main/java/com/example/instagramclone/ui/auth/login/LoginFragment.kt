package com.example.instagramclone.ui.auth.login

import android.os.Bundle
import android.os.TokenWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentLoginBinding
import com.example.instagramclone.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding ?= null
    private val binding get() = _binding!!
    lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        onClick()
        return binding.root
    }
    private fun onClick(){
        binding.tvSignUpLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
    }
    private fun login(){
        val email = binding.etLoginUserName.text.toString()
        val password = binding.etLoginPassword.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(requireActivity()){
            if (it.isSuccessful){
                PrefManager.setBoolean(PrefManager.IS_LOGIN,true)
                PrefManager.setString(PrefManager.ACCESS_TOKEN,email)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment2())
            }
            else{
                Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}