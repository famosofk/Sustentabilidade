package com.example.sustentabilidade.fragment.joinfarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.SelectFarmAdapter
import com.example.sustentabilidade.databinding.JoinFarmFragmentBinding
import com.example.sustentabilidade.helpers.ClickListener

class JoinFarmFragment : Fragment() {

    private lateinit var viewModel: JoinFarmViewModel
    private val adapter = SelectFarmAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind: JoinFarmFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.join_farm_fragment, container, false)
        bind.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(JoinFarmViewModel::class.java)
        bind.recyclerSelectFarm.adapter = adapter

        val listener = object : ClickListener {
            override fun onClick(id: Int) {
                Toast.makeText(context, viewModel.list[id].codigoFazenda, Toast.LENGTH_SHORT).show()
            }
        }
        adapter.attachListener(listener)
        setObservers()

        arguments?.getString("Program")?.let { viewModel.getFarms(it) }
        return bind.root
    }

    private fun setObservers() {
        viewModel.mFarms.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(viewModel.list)
            }
        })
    }


}