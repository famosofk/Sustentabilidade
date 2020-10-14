package com.example.sustentabilidade.fragment.certificationfragments.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentSigninBinding
import com.example.sustentabilidade.helpers.ScreenHelper


class SignInFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var viewModel: SignInViewModel
    private var itemType = ""
    private var certificationID = ""

    //TODO remover o trecho hardcoded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signin, container, false)
        viewModel.getCertification(arguments?.getString("certificationName")!!)
        certificationID = arguments?.getString("certificationID")!!
        binding.certificationTitle.text = arguments?.getString("certificationName")!!
        enableListeners()
        return binding.root
    }

    private fun enableListeners() {
        binding.dominionSpinner.onItemSelectedListener = this
        binding.createModelButton.setOnClickListener(this)
        binding.manageModelButton.setOnClickListener(this)


    }

    private fun enableNavigation(buttonID: Int) {
        val bundle = Bundle()
        bundle.putString("type", itemType)
        bundle.putString("operation", buttonID.toString())
        bundle.putString("certificationID", certificationID)

        if (buttonID == binding.createModelButton.id) {
            binding.root.findNavController()
                .navigate(R.id.action_certificationFragment_to_createModelFragment, bundle)
        } else if (buttonID == binding.manageModelButton.id) {
            binding.root.findNavController()
                .navigate(R.id.action_singinFragment_to_editComponentFragment, bundle)
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        itemType = resources.getStringArray(R.array.models)[p2]

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {


    }

    override fun onClick(p0: View?) {

        if (p0 != null) {
            val array = resources.getStringArray(R.array.models)
            when (itemType) {
                array[0] -> {
                    if (p0.id == R.id.createModelButton) {
                        enableNavigation(p0.id)
                    } else if (p0.id == R.id.manageModelButton) {
                        if (viewModel.verifyDominion()) {
                            enableNavigation(p0.id)
                        } else {
                            ScreenHelper.createToast(requireContext(), "Crie uma dimensão primeiro")
                        }
                    }
                }
                array[1] -> {
                    if (viewModel.verifyDominion()) {
                        enableNavigation(p0.id)
                    } else {
                        ScreenHelper.createToast(requireContext(), "Crie uma dimensão primeiro.")
                    }
                }
                array[2] -> {

                    if (viewModel.verifyTheme()) {
                        enableNavigation(p0.id)
                    } else {
                        ScreenHelper.createToast(requireContext(), "Crie um tema primeiro.")
                    }
                }
                array[3] -> {
                    if (viewModel.verifySubTheme()) {
                        enableNavigation(p0.id)
                    } else {
                        ScreenHelper.createToast(requireContext(), "Crie um subtema primeiro.")
                    }
                }
                array[4] -> {
                    if (p0.id == R.id.createModelButton) {
                        enableNavigation(p0.id)
                    } else if (p0.id == R.id.manageModelButton) {
                        if (viewModel.verifyLevel()) {
                            enableNavigation(p0.id)
                        } else {
                            ScreenHelper.createToast(requireContext(), "Crie um nível primeiro")
                        }
                    }
                }
                else -> ScreenHelper.createToast(
                    requireContext(),
                    "Erro ao selecionar operação. Contate o suporte."
                )
            }
        }

    }


}