package com.example.sustentabilidade.fragment.certificationfragments.selectcomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.StringAdapter
import com.example.sustentabilidade.databinding.FragmentEditComponentBinding
import com.example.sustentabilidade.fragment.certificationfragments.managecertification.CreateCertificationItemClickListener

class EditComponentFragment : Fragment() {

    private lateinit var binding: FragmentEditComponentBinding
    private val adapter = StringAdapter()
    private lateinit var type: String
    private lateinit var viewModel: EditComponentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_component, container, false
        )
        viewModel = ViewModelProvider(this).get(EditComponentViewModel::class.java)
        initializeObservers()
        verifyArguments()
        return binding.root
    }

    private fun initializeObservers() {
        viewModel.mListUpdated.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(viewModel.getAdapterList())
            }
        })
    }

    private fun verifyArguments() {
        if (arguments == null) {
            binding.root.findNavController()
                .navigate(R.id.action_editComponentFragment_to_certificationsFragment)
        } else {
            initializeVariables()
        }
    }

    private fun initializeVariables() {
        type = arguments?.getString("type")!!
        binding.toolbar.title = type
        val listener = createRecyclerItemClickListener()
        adapter.attachListener(listener)
        binding.recyclerManageItemList.adapter = adapter
        viewModel.createList(type, resources, arguments?.getString("certificationID")!!)
    }

    private fun createRecyclerItemClickListener(): CreateCertificationItemClickListener {
        return object : CreateCertificationItemClickListener {
            override fun onClick(p: Int) {
                binding.root.findNavController().navigate(
                    R.id.updateComponentsFragment,
                    createBundle(p)
                )
            }
        }
    }

    private fun createBundle(p: Int): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationID", arguments?.getString("certificationID"))
        bundle.putString("type", type)
        bundle.putString("name", viewModel.getAdapterList()[p])
        return bundle

    }
}

