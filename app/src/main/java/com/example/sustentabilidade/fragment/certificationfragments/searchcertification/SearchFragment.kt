package com.example.sustentabilidade.fragment.certificationfragments.searchcertification

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.AnswerListAdapter
import com.example.sustentabilidade.databinding.SearchFragmentBinding
import com.example.sustentabilidade.helpers.ClickListener
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener, TextWatcher {
    private lateinit var viewModel: SearchViewModel
    private val adapter = AnswerListAdapter()
    private var farmList = mutableListOf<AnswerList>()
    private var certificationList = mutableListOf<Certification>()
    private var certificationNameList = mutableListOf<String>()
    private lateinit var realm: Realm

    //TODO configurar a busca por nome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SearchFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        realm = Realm.getDefaultInstance()
        farmList = realm.where<AnswerList>().findAll()
        val click = object : ClickListener {
            override fun onClick(id: Int) {
                val bundle = Bundle()
                bundle.putString("answerListId", farmList[id].id)
                binding.root.findNavController()
                    .navigate(R.id.action_searchFragment_to_visualizeCertificationFragment, bundle)
            }
        }
        certificationList = realm.where<Certification>().findAll()
        certificationList.forEach { certification ->
            certificationNameList.add(certification.name)
        }
        val spinnerAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    certificationNameList
                )
            }
        binding.spinner2.adapter = spinnerAdapter
        adapter.attachListener(click)
        binding.searchRecyclerView.adapter = adapter
        binding.spinner2.onItemSelectedListener = this
        adapter.submitList(farmList)
        binding.searchTermFarmCode.addTextChangedListener(this)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        farmList =
            realm.where<AnswerList>().contains("certificationID", certificationList[position].id)
                .findAll()
        adapter.submitList(null)
        adapter.submitList(farmList)
        adapter.notifyDataSetChanged()
    }

    override fun afterTextChanged(s: Editable?) {
        val hand = Handler()
        hand.run {
            farmList.filter { it.farmCode.contains(s.toString()) }
            val tempList = mutableListOf<AnswerList>()
            for (item in farmList)
                if (item.farmCode.contains(s.toString()))
                    tempList.add(item)
            farmList = tempList
            adapter.submitList(farmList)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


}