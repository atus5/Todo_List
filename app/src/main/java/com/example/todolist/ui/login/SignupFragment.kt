package com.example.todolist.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.data.model.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var txtInputUsername: TextInputLayout
    private lateinit var txtInputEmail: TextInputLayout
    private lateinit var txtInputPassword: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        btnRegister.setOnClickListener {
            if (validateInputs()) {
                regAccount()
            }
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)

        edtUsername = view.findViewById(R.id.edtUsername)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtPassword = view.findViewById(R.id.edtPassword)
        btnRegister = view.findViewById(R.id.btnLogin1)

        txtInputUsername = view.findViewById(R.id.txtInputUsername)
        txtInputEmail = view.findViewById(R.id.txtInputEmail)
        txtInputPassword = view.findViewById(R.id.txtInputPassword)
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (edtUsername.text.isNullOrEmpty()) {
            txtInputUsername.error = "Username cannot be empty!"
            isValid = false
        } else {
            txtInputUsername.error = null
        }

        if (edtEmail.text.isNullOrEmpty()) {
            txtInputEmail.error = "Email cannot be empty!"
            isValid = false
        } else {
            txtInputEmail.error = null
        }

        if (edtPassword.text.isNullOrEmpty()) {
            txtInputPassword.error = "Password cannot be empty!"
            isValid = false
        } else {
            txtInputPassword.error = null
        }

        return isValid
    }

    private fun regAccount() {
        val username = edtUsername.text.toString().trim().lowercase()
        var email = edtEmail.text.toString().trim().lowercase()
        val password = edtPassword.text.toString().trim()
        if (!email.contains("@")) email += "@gmail.com"

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseDatabase.getInstance(
            "https://todolist1-f780c-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).reference

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user!!.uid
                val userMap = mapOf(
                    "email" to email,
                    "username" to username
                    // tuyệt đối KHÔNG lưu password vào DB
                )
                db.child("users").child(uid).setValue(userMap)
                    .addOnSuccessListener {
                        showToast("Registration successful!")
                        navController.navigate(R.id.action_signinFragment_to_loginFragment)
                    }
                    .addOnFailureListener { e -> showToast("Error: ${e.message}") }
            }
            .addOnFailureListener { e -> showToast("Error: ${e.message}") }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
