package com.example.sustentabilidade.fragment.createprogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sustentabilidade.R

class CreateProgramFragment : Fragment() {

    companion object {
        fun newInstance() = CreateProgramFragment()
    }

    private lateinit var viewModel: CreateProgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_program_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateProgramViewModel::class.java)
        // TODO: Use the ViewModel
    }

}