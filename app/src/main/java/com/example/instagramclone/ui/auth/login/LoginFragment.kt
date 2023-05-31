package com.example.instagramclone.ui.auth.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.TokenWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentLoginBinding
import com.example.instagramclone.utils.PrefManager
import com.example.instagramclone.utils.getStatusBarHeight
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
        binding.glLogin.setGuidelineBegin(getStatusBarHeight())
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
        if (!validation()){
            return
        }
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
    private fun validation(): Boolean {
        if (binding.etLoginUserName.text.toString().isEmpty()){
            binding.etLoginUserName.error = "Username is Required"
            binding.etLoginUserName.requestFocus()
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etLoginUserName.text.toString()).matches()){
            binding.etLoginUserName.error = "Invalid Email"
            binding.etLoginUserName.requestFocus()
            return false
        }

        else if (binding.etLoginPassword.text.toString().isEmpty()){
            binding.etLoginPassword.error = "Password IS Required"
            binding.etLoginPassword.requestFocus()
            return false
        }
        else if (binding.etLoginPassword.text.toString().length < 8){
            binding.etLoginPassword.error = "Password need at least 8 char"
            binding.etLoginPassword.requestFocus()
            return false
        }
        else if(!binding.etLoginPassword.text.toString().matches(".*[A-Z].*".toRegex())) {
            binding.etLoginPassword.error = "Must Contain 1 Upper-case Character"
            binding.etLoginPassword.requestFocus()
            return false
        }
        else if(!binding.etLoginPassword.text.toString().matches(".*[a-z].*".toRegex())) {
            binding.etLoginPassword.error = "Must Contain 1 Lower-case Character"
            binding.etLoginPassword.requestFocus()
            return false
        }
        else if(!binding.etLoginPassword.text.toString().matches(".*[@#\$%^&+=].*".toRegex())) {
            binding.etLoginPassword.error = "Must Contain 1 Special Character (@#\$%^&+=)"
            binding.etLoginPassword.requestFocus()
            return false
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}