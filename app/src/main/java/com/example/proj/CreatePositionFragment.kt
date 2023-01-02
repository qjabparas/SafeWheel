package com.example.proj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.proj.databinding.FragmentCreatePositionBinding
import com.example.proj.viewmodel.PositionViewModel
import com.example.proj.viewmodel.PositionViewModelFactory


class CreatePositionFragment : Fragment() {

    private val viewModel: PositionViewModel by activityViewModels {
        PositionViewModelFactory(
            (activity?.application as MainApplication).database
                .positionDao()
        )
    }

    private var _binding: FragmentCreatePositionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePositionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener { addNewPosition() }
        binding.cancelButton.setOnClickListener { findNavController().navigateUp() }
    }

    private fun addNewPosition() {
        if (isEntryValid()){
            viewModel.addPosition(
                name = binding.addName.text.toString(),
                status = when (binding.positionOption.checkedRadioButtonId) {
                    binding.steadyOption.id -> "steady"
                    binding.tiltedOption.id -> "tilted"
                    binding.fallOption.id -> "fall"
                    else -> ""
                }
            )
            findNavController().navigateUp()
        }
        else {
            binding.nameLayout.error = getString(R.string.missing_info)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            name = binding.addName.text.toString()
        )
    }
}