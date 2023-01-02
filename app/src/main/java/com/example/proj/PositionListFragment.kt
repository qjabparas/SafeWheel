package com.example.proj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.adapter.PositionAdapter
import com.example.proj.database.models.Position
import com.example.proj.databinding.FragmentHomeBinding
import com.example.proj.databinding.FragmentPositionListBinding
import com.example.proj.viewmodel.PositionViewModel
import com.example.proj.viewmodel.PositionViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch


class PositionListFragment : Fragment() {
    private val viewModel: PositionViewModel by activityViewModels {
        PositionViewModelFactory(
            (activity?.application as MainApplication).database
                .positionDao()
        )
    }

    private var _binding: FragmentPositionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPositionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        val positionAdapter = PositionAdapter(
            {
                    val action = PositionListFragmentDirections.actionPositionListFragmentToUpdatePositionFragment(it.id)
                    findNavController().navigate(action)

            },
            {
                    position: Position -> showConfirmationDialog(position)
            }
        )

        recyclerView.adapter = positionAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getAllPosition().collect {
                positionAdapter.submitList(it)
            }
        }


        binding.createButton.setOnClickListener {
                view: View -> view.findNavController().navigate(R.id.action_positionListFragment_to_createPositionFragment)
        }
    }
    private fun deletePosition(position: Position) {
        viewModel.deletePosition(position)
    }
    private fun showConfirmationDialog(position: Position) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ -> deletePosition(position) }
            .show()
    }
}