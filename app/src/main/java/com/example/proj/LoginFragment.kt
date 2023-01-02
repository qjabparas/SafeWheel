package com.example.proj

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.proj.databinding.FragmentLoginBinding
import com.example.proj.viewmodel.AuthViewModel
import com.example.proj.viewmodel.AuthViewModelFactory

class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as MainApplication).database
                .userDao()
        )
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        binding.loginButton.setOnClickListener{
        login()
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            username = binding.username.text.toString(),
            password = binding.password.text.toString()
        )
    }

    private fun userExist(): Boolean {
        return viewModel.userExist(
            username = binding.username.text.toString(),
            password = binding.password.text.toString()
        )

    }

    private fun login() {
        if (isEntryValid())
            if (userExist()) {
                startActivity(Intent(activity,NavigationActivity::class.java))
            } else {
                binding.nameLayout.error = null
                binding.passwordLayout.error = getString(R.string.wrong_pass)
            }
        else {
            var username = binding.username.text.toString()
            if (username.isEmpty()) {
                binding.nameLayout.error = getString(R.string.missing_info)
                binding.passwordLayout.error = null
            } else {
                binding.passwordLayout.error = getString(R.string.missing_info)
                binding.nameLayout.error = null
            }
        }
    }
}
