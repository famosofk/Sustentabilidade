package com.example.sustentabilidade.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var userType = ""
    private lateinit var viewModel: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind: FragmentRegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding = bind
        createListeners()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }


    private fun createObservers() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it) {
                createToasts(viewModel.errorCode)
            }
        })

        viewModel.mUser.observe(viewLifecycleOwner, Observer {
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
            viewModel.createUser(
                binding.editTextEmailRegister.text.toString(),
                binding.editTextPasswordRegister.text.toString(),
                userType
            )
        }
    }

    private fun createToasts(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

}