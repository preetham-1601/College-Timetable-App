package com.example.college.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.college.R
import com.example.college.databinding.FragmentSplashScreenBinding
import com.example.college.util.SessionManager


class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        val view = binding.root


        sessionManager = SessionManager(activity as Context)




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (sessionManager.isLoggedIn()) {
            binding.btnTch.visibility = View.GONE
            binding.btnStd.visibility = View.GONE
            binding.txt.visibility = View.GONE
            Handler().postDelayed({
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }, 2000)
            onDestroy()


        } else {

            binding.btnStd.setOnClickListener {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }

            binding.btnTch.setOnClickListener {

                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                //findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
                onDestroy()
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}