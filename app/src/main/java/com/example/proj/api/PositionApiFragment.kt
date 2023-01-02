package com.example.proj.api

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.proj.R
import com.example.proj.databinding.FragmentPositionApiBinding


class PositionApiFragment : Fragment() {

    private val viewModel: ApiViewModel by viewModels()

    private var _binding: FragmentPositionApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPositionApiBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.faq -> view?.findNavController()?.navigate(R.id.faqFragment)
            R.id.about -> view?.findNavController()?.navigate(R.id.aboutFragment)
        }
        return true
    }
}