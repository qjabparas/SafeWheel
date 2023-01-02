package com.example.proj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.proj.database.models.Position
import com.example.proj.databinding.FragmentUpdatePositionBinding
import com.example.proj.viewmodel.PositionViewModel
import com.example.proj.viewmodel.PositionViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UpdatePositionFragment : Fragment() {

    private val viewModel: PositionViewModel by activityViewModels {
        PositionViewModelFactory(
            (activity?.application as MainApplication).database
                .positionDao()
        )
    }

    private var _binding: FragmentUpdatePositionBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: UpdatePositionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePositionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id

        lifecycle.coroutineScope.launch {
            getPosition(id).collect {
                binding.apply {
                    updateName.setText(it.name.toString())
                    updatePositionOption.check(
                        when (it.status) {
                            "steady" -> binding.steadyOption.id
                            "tilted" -> binding.tiltedOption.id
                            else -> binding.fallOption.id
                        }
                    )
                }
            }
        }

        binding.cancelButton.setOnClickListener { findNavController().navigateUp() }
        binding.updateButton.setOnClickListener { updatePosition(id) }
    }
    private fun updatePosition(id: Int) {
        if (isEntryValid()) {
            viewModel.updatePosition(
                id = id,
                name = binding.updateName.text.toString(),
                status = when (binding.updatePositionOption.checkedRadioButtonId) {
                    binding.steadyOption.id -> "steady"
                    binding.tiltedOption.id -> "tilted"
                    binding.fallOption.id -> "fall"
                    else -> ""
                }
            )
            findNavController().navigateUp()
        } else {
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            name = binding.updateName.text.toString())
    }

    private fun getPosition(id: Int): Flow<Position> = viewModel.getPosition(id)

}
