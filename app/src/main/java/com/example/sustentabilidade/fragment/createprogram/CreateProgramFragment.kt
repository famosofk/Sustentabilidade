package com.example.sustentabilidade.fragment.createprogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.CreateProgramFragmentBinding

class CreateProgramFragment : Fragment() {

    companion object {
        fun newInstance() = CreateProgramFragment()
    }

    private lateinit var viewModel: CreateProgramViewModel
    private lateinit var binding: CreateProgramFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CreateProgramViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.create_program_fragment, container, false)
        setObservers()
        setListeners()
        return binding.root

    }

    private fun setObservers() {
        viewModel.mNavigation.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController()
                    .navigate(R.id.action_createProgramFragment_to_farmsFragment)
            }
        })
    }

    private fun setListeners() {
        binding.createProgramButton.setOnClickListener {
            viewModel.createProgram(binding.createProgramNameEditText.text.toString())
        }
    }
}