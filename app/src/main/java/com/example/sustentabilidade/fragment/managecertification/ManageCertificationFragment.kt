package com.example.sustentabilidade.fragment.managecertification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentManageCertificationBinding


class ManageCertificationFragment : Fragment() {

    private lateinit var binding: FragmentManageCertificationBinding
    private lateinit var viewModel: ManageCertificationViewModel
    private val adapter = CreateCertificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_manage_certification,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(ManageCertificationViewModel::class.java)
        initializingRecyclerView()
        enableListeners()
        enableObservers()
        viewModel.getList()
        return binding.root
    }

    private fun initializingRecyclerView() {
        val listener = object : CreateCertificationItemClickListener {
            override fun onClick(p: Int) {
                val bundle = Bundle()
                bundle.putString("certificationName", viewModel.list[p].name)
                binding.root.findNavController()
                    .navigate(R.id.action_manageCertificationFragment_to_signinFragment, bundle)
            }
        }
        adapter.attachListener(listener)
        binding.certificationRecycler.adapter = adapter


    }

    private fun enableObservers() {
        viewModel.mSavedItem.observe(viewLifecycleOwner, {
            if (it) {
                binding.editTextTitleCreateCertificate.text.clear()
                viewModel.getList()
                viewModel.turnBackSavedToFalse()
            }
        })
        viewModel.mUpdatedList.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(viewModel.list)
                viewModel.turnBackUpdatedToFalse()
            }
        })
    }

    private fun enableListeners() {
        binding.createCertificationButton.setOnClickListener {
            viewModel.createCertification(binding.editTextTitleCreateCertificate.text.toString())
        }
    }


}