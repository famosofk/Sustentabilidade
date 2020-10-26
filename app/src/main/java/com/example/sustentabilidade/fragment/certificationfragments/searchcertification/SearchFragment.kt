package com.example.sustentabilidade.fragment.certificationfragments.searchcertification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.AnswerListAdapter
import com.example.sustentabilidade.databinding.SearchFragmentBinding
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import io.realm.Realm
import io.realm.kotlin.where

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private val adapter = AnswerListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SearchFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        val realm = Realm.getDefaultInstance()
        val listOfAnswerList = realm.where<AnswerList>().findAll()
        binding.searchRecyclerView.adapter = adapter
        adapter.submitList(listOfAnswerList)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}