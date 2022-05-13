package com.example.college.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.college.R
import com.example.college.databinding.FragmentLoginBinding
import com.example.college.util.ConnectionManager
import com.example.college.util.SessionManager
import com.example.college.util.Validations
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    /*Variables used in managing the login session*/
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root


        auth = FirebaseAuth.getInstance()



        sessionManager = SessionManager(activity as Context)


        binding.btnLogin.setOnClickListener {

            login()
        }
        binding.btnRegister.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.frgPw.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }


        return view
    }

    private fun login() {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass1.text.toString()

            // calling signInWithEmailAndPassword(email, pass)
            // function using Firebase auth object

            if (Validations.validateEmail(binding.etEmail.text.toString()) && Validations.validatePasswordLength(binding.etPass1.text.toString())) {
                if (ConnectionManager().isNetworkAvailable(activity as Context)) {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()) {
                        if (it.isSuccessful) {
                            val uid = auth.currentUser?.uid!!
                            sessionManager.putUid(uid)
                            Toast.makeText(
                                activity as Context,
                                "Successfully LoggedIn ",
                                Toast.LENGTH_SHORT
                            ).show()



                            sessionManager.setLogin(true)
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else
                            Toast.makeText(
                                activity as Context,
                                "Log In failed ",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }else {

                    Toast.makeText(activity as Context, "No internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }
            }else {

                Toast.makeText(activity as Context, "Invalid Email or Password", Toast.LENGTH_SHORT)
                    .show()
            }
    }

}