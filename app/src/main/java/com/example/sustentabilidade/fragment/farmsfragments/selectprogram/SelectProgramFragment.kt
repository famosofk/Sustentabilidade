package com.example.sustentabilidade.fragment.farmsfragments.selectprogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.SelectProgramAdapter
import com.example.sustentabilidade.databinding.SelectProgramFragmentBinding
import com.example.sustentabilidade.helpers.ClickListener

class SelectProgramFragment : Fragment() {

    private val adapter = SelectProgramAdapter()
    private lateinit var binding: SelectProgramFragmentBinding
    private lateinit var viewModel: SelectProgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.select_program_fragment, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(SelectProgramViewModel::class.java)
        attachListeners()
        enableObservers()
        binding.recyclerSelectProgram.adapter = adapter
        viewModel.getPrograms()

        return binding.root

    }

    private fun attachListeners() {
        val listener = object : ClickListener {
            override fun onClick(id: Int) {
                val item: String = viewModel.list[id].name
                val bundle = Bundle()
                bundle.putString("Program", item)
                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                binding.root.findNavController()
                    .navigate(R.id.action_selectProgramFragment_to_joinFarmFragment, bundle)
            }
        }
        adapter.attachListener(listener)
    }

    private fun enableObservers() {
        viewModel.mItensRecovered.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(viewModel.list)
            }
        })
    }

}