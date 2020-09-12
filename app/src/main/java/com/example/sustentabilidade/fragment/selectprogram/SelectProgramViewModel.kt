package com.example.sustentabilidade.fragment.selectprogram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sustentabilidade.models.gestaomodels.Program
import io.realm.Realm
import io.realm.kotlin.where

class SelectProgramViewModel : ViewModel() {
    var list = listOf<Program>()
    private var itensRecovered = MutableLiveData(false)
    val mItensRecovered: LiveData<Boolean>
        get() = itensRecovered


    fun getPrograms() {
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Program>().findAll()
        val mlist = mutableListOf<Program>()
        mlist.addAll(results)
        list = mlist
        itensRecovered.value = true
    }
}