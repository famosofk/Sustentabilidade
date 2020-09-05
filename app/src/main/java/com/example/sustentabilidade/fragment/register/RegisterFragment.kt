package com.example.sustentabilidade.fragment.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind : FragmentRegisterBinding = DataBindingUtil.inflate(inflater,  R.layout.fragment_register, container, false)
        binding = bind
        createListeners()
        return binding.root
    }


    private fun selectMemberType(){
        if(binding.productorButtonRegister.isChecked){
            binding.editTextAgroplusPassword.visibility = View.INVISIBLE
            binding.textViewAgroplusMemberPassword.visibility = View.INVISIBLE
        }else{
            binding.editTextAgroplusPassword.visibility = View.VISIBLE
            binding.textViewAgroplusMemberPassword.visibility = View.VISIBLE
        }
    }

    private fun createListeners(){
        binding.consultantButtonRegister.setOnClickListener {
            selectMemberType()
        }
        binding.productorButtonRegister.setOnClickListener {
            selectMemberType()
        }
        binding.professorButtonRegister.setOnClickListener {
            selectMemberType()
        }
    }

}