package com.example.sustentabilidade.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        enableNavigation(bind)

        return bind.root
    }

    private fun enableNavigation(binding: FragmentMainBinding) {

        binding.textViewCertificationNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_mainFragment_to_certificationsFragment)
        }
        binding.textViewFarmNavigation.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_mainFragment_to_farmsFragment)
        }

    }

}