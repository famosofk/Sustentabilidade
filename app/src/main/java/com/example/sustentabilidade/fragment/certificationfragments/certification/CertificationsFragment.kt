package com.example.sustentabilidade.fragment.certificationfragments.certification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentCertificationsBinding


class CertificationsFragment : Fragment() {

    private lateinit var binding: FragmentCertificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_certifications, container, false)
        enableNavigation()
        return binding.root
    }

    private fun enableNavigation() {
        binding.manageCertificationTextView.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_certificationsFragment_to_manageCertificationFragment)
        }
    }


}