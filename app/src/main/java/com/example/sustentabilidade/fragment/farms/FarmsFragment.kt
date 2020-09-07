package com.example.sustentabilidade.fragment.farms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentFarmsBinding
import com.example.sustentabilidade.helpers.NetworkHelper
import com.example.sustentabilidade.helpers.ScreenHelper

class FarmsFragment : Fragment() {

    private lateinit var binding: FragmentFarmsBinding
    private lateinit var viewModel: FarmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_farms, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(FarmsViewModel::class.java)
        setObservers()
        startDownload()
        enableNavigation()

        return binding.root
    }

    private fun setObservers() {
        viewModel.downloaded.observe(viewLifecycleOwner, {
            if (it) {
                endDownload()
            }
        })
    }

    private fun startDownload() {

        if (NetworkHelper.isOnline(requireNotNull(context))) {
            changeProgressBarVisibility()
            ScreenHelper.enableLoading(requireActivity())
            viewModel.downloadingFarms()
        }
    }

    private fun endDownload() {
        changeProgressBarVisibility()
        ScreenHelper.disableLoading(requireActivity())
    }

    private fun changeProgressBarVisibility() {
        if (binding.downloadProgramsProgressBar.visibility == View.VISIBLE) {
            binding.downloadProgramsProgressBar.visibility = View.GONE
        } else {
            binding.downloadProgramsProgressBar.visibility = View.VISIBLE
        }
    }

    private fun enableNavigation() {
        binding.createFarmNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_farmsFragment_to_createFarmFragment)
        }
        binding.createProgramNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_farmsFragment_to_createProgramFragment)
        }
        binding.joinFarmNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_farmsFragment_to_selectProgramFragment)
        }
    }

}





