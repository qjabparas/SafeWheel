package com.example.proj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.proj.databinding.FragmentRegisterBinding
import com.example.proj.viewmodel.AuthViewModel
import com.example.proj.viewmodel.AuthViewModelFactory

class RegisterFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as MainApplication).database
                .userDao()
        )
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener{
            insertUser()


        }

        binding.cancelButton.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun isRegisterEntryValid(): Boolean {
        return viewModel.isRegisterEntryValid(
            username = binding.registerName.text.toString(),
            password = binding.registerPassword.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString()
        )
    }

    private fun isPasswordSimilar(): Boolean {
        return viewModel.isPasswordSimilar(
            password = binding.registerPassword.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString()
        )
    }

    private fun insertUser() {
        if (isRegisterEntryValid())
            if (isPasswordSimilar()) {
                viewModel.newUserEntry(
                    username = binding.registerName.text.toString(),
                    password = binding.registerPassword.text.toString()
                )
                binding.confirmpasswordLayout.error= null
                Toast.makeText(requireActivity(), "Added ${binding.registerName.text}", Toast.LENGTH_LONG).show()
                view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
            }
            else {
                binding.confirmpasswordLayout.error = getString(R.string.password_mismatch)
                binding.passwordLayout.error= null
                binding.registerName.error = null
            }
        else {
            var registerName = binding.registerName.text.toString()
            var registerPassword = binding.registerPassword.text.toString()

            if(registerName.isEmpty()){
                binding.registernameLayout.error = getString(R.string.missing_info)
                binding.passwordLayout.error= null
                binding.confirmpasswordLayout.error = null
            } else if (registerPassword.isEmpty()){
                binding.passwordLayout.error = getString(R.string.missing_info)
                binding.registernameLayout.error = null
                binding.confirmpasswordLayout.error = null
            } else {
                binding.confirmpasswordLayout.error = getString(R.string.missing_info)
                binding.passwordLayout.error= null
                binding.registernameLayout.error = null
            }
        }
    }
}