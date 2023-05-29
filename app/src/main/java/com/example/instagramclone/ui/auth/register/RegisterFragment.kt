package com.example.instagramclone.ui.auth.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentLoginBinding
import com.example.instagramclone.databinding.FragmentRegisterBinding
import com.example.instagramclone.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.ref.PhantomReference
import java.util.regex.Pattern

lateinit var auth : FirebaseAuth

@SuppressLint("StaticFieldLeak")
private lateinit var db : FirebaseFirestore

class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding ?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        setOnClick()
        return binding.root
    }
    private fun setOnClick(){
        binding.btnSignUp.setOnClickListener {
            //validation()
            singUpUser()

        }
        binding.textView4.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }
    private fun singUpUser(){
        val email = binding.etEmailRegistration.text.toString()
        val password = binding.etPasswordRegistration.text.toString()
        val name = binding.etName.text.toString()
        val number = binding.etPhoneRegistration.text.toString()
        val username = binding.etUserNameRegistration.text.toString()
        val myuser = db.collection("UserProfile").document()
        val id =  myuser.id.toString()
//        if (email.isBlank() || password.isBlank()){
//            Toast.makeText(requireContext(),"Please Enter Email and Password",Toast.LENGTH_LONG).show()
//            return
//        }
        if (!validation()){
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()){
                if (it.isSuccessful){

                    val user = User(name,email,number,username,"",id)
                    db.collection("UserProfile").add(user).addOnSuccessListener {
                        Toast.makeText(requireContext(),"Registration Success",Toast.LENGTH_LONG).show()
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                    }
                }

            }
    }
    private fun validation(): Boolean {
        if (binding.etEmailRegistration.text.toString().isEmpty()){
            binding.etEmailRegistration.error = "Email is Required"
            binding.etEmailRegistration.requestFocus()
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailRegistration.text.toString()).matches()){
            binding.etEmailRegistration.error = "Invalid Email"
            binding.etEmailRegistration.requestFocus()
            return false
        }
        else if (binding.etPhoneRegistration.text.toString().isEmpty()){
            binding.etPhoneRegistration.error = "Phone Number Is Required"
            binding.etPhoneRegistration.requestFocus()
            return false
        }
        else if (binding.etPhoneRegistration.text.toString().length != 10){
            binding.etPhoneRegistration.error = "Phone Number need 10 digits"
            binding.etPhoneRegistration.requestFocus()
            return false
        }
        else if (binding.etName.text.toString().isEmpty()){
            binding.etName.error = "Name Is Required"
            binding.etName.requestFocus()
            return false
        }
        else if (binding.etUserNameRegistration.text.toString().isEmpty()){
            binding.etUserNameRegistration.error = "User Name Is Required"
            binding.etUserNameRegistration.requestFocus()
            return false
        }
        else if (binding.etPasswordRegistration.text.toString().isEmpty()){
            binding.etPasswordRegistration.error = "Password IS Required"
            binding.etPasswordRegistration.requestFocus()
            return false
        }
        else if (binding.etPasswordRegistration.text.toString().length < 8){
            binding.etPasswordRegistration.error = "Password need at least 8 char"
            return false
        }
        else if(!binding.etPasswordRegistration.text.toString().matches(".*[A-Z].*".toRegex())) {
            binding.etPasswordRegistration.error = "Must Contain 1 Upper-case Character"
            binding.etPasswordRegistration.requestFocus()
            return false
        }
        else if(!binding.etPasswordRegistration.text.toString().matches(".*[a-z].*".toRegex())) {
            binding.etPasswordRegistration.error = "Must Contain 1 Lower-case Character"
            binding.etPasswordRegistration.requestFocus()
            return false
        }
        else if(!binding.etPasswordRegistration.text.toString().matches(".*[@#\$%^&+=].*".toRegex())) {
            binding.etPasswordRegistration.error = "Must Contain 1 Special Character (@#\$%^&+=)"
            binding.etPasswordRegistration.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}