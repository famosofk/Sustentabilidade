package com.example.sustentabilidade.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val app = requireActivity().application
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = bind
        setListeners()
        setObservers()

        return binding.root
    }



    private fun setObservers() {
        viewModel.mUser.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.root.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        })
    }

    private fun setListeners() {
        binding.buttonLogin.setOnClickListener {
            callUserSignIn()
        }
        binding.cadastroLinearLayout.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun callUserSignIn() {
        viewModel.signIn(requireActivity(), userEmailLogin.text.toString(), userPassWordLogin.text.toString())
    }

}