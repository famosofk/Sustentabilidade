package com.example.sustentabilidade.fragment.register

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
import com.example.sustentabilidade.databinding.FragmentRegisterBinding
import com.example.sustentabilidade.helpers.ScreenHelper

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var userType = ""
    private lateinit var viewModel: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        createObservers()
        createListeners()
        return binding.root
    }




    private fun createObservers() {
        viewModel.error.observe(viewLifecycleOwner, {
            if (it) {
                manageProgressBar()
                viewModel.resetErrrorCode()
                createToasts(viewModel.errorCode)
            }
        })

        viewModel.mUser.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController()
                    .navigate(R.id.action_registerFragment_to_mainFragment)
            }
        })
    }

    private fun selectMemberType() {
        if (binding.productorButtonRegister.isChecked) {
            binding.editTextAgroplusPassword.visibility = View.INVISIBLE
            binding.textViewAgroplusMemberPassword.visibility = View.INVISIBLE
        } else {
            binding.editTextAgroplusPassword.visibility = View.VISIBLE
            binding.textViewAgroplusMemberPassword.visibility = View.VISIBLE
        }
    }

    private fun createListeners(){
        binding.consultantButtonRegister.setOnClickListener {
            selectMemberType()
            userType = requireActivity().resources.getString(R.string.consultant_term)
        }
        binding.productorButtonRegister.setOnClickListener {
            selectMemberType()
            userType = requireActivity().resources.getString(R.string.productor_term)
        }
        binding.professorButtonRegister.setOnClickListener {
            selectMemberType()
            userType = requireActivity().resources.getString(R.string.professor_term)
        }
        binding.buttonAgroplusRegister.setOnClickListener {
            if (userType != "") {
                manageProgressBar()
                viewModel.createUser(
                    binding.editTextEmailRegister.text.toString(),
                    binding.editTextPasswordRegister.text.toString(),
                    binding.editTextAgroplusPassword.text.toString(),
                    userType
                )
            }
        }
    }

    private fun createToasts(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    private fun manageProgressBar() {
        if (binding.progressBarRegister.visibility == View.VISIBLE) {
            binding.progressBarRegister.visibility = View.GONE
            ScreenHelper.disableLoading(requireActivity())
        } else {
            binding.progressBarRegister.visibility = View.VISIBLE
            ScreenHelper.enableLoading(requireActivity())
        }
    }

}