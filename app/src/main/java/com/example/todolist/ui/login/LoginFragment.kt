package com.example.todolist.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuthException
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.data.model.User
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.values


class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)


      binding.btnLogin.setOnClickListener {
          if (binding.edtUsername.text.isNullOrEmpty()) {
              binding.txtInputUsername.error = "Can not empty !"
          } else {
              binding.txtInputUsername.error = null
          }


          if (binding.edtPassword.text.isNullOrEmpty()) {
              binding.txtInputPassword.error = "Can not empty !"
          } else {
              binding.txtInputPassword.error = null
          }
          checkLogin()
      }
        binding.txtRegister.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_signinFragment)
        }

    }



    private fun checkLogin() {
        val username = binding.edtUsername.text.toString().trim().lowercase()
        val password = binding.edtPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val email = if (username.contains("@")) username else "$username@gmail.com"

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }




    private fun init(view: View) {
        navController = Navigation.findNavController(view)

    }

}