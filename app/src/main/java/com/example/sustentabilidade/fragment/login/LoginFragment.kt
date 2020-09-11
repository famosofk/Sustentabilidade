package com.example.sustentabilidade.fragment.login

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
import com.example.sustentabilidade.databinding.FragmentLoginBinding
import com.example.sustentabilidade.helpers.ScreenHelper
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setListeners()
        setObservers()



        return binding.root
    }



    private fun setObservers() {
        viewModel.mUser.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(context, viewModel.errorMessage, Toast.LENGTH_SHORT).show()
                manageProgressBar()
                viewModel.resetError()

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

    private fun manageProgressBar() {
        if (binding.progressBar.visibility == View.VISIBLE) {
            binding.progressBar.visibility = View.GONE
            ScreenHelper.disableLoading(requireActivity())
        } else {
            binding.progressBar.visibility = View.VISIBLE
            ScreenHelper.disableLoading(requireActivity())
        }
    }

    private fun callUserSignIn() {
        manageProgressBar()
        viewModel.signIn(
            requireActivity(),
            userEmailLogin.text.toString(),
            userPassWordLogin.text.toString()
        )
    }

}