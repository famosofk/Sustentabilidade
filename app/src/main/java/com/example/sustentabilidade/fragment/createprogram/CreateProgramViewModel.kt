package com.example.sustentabilidade.fragment.createprogram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sustentabilidade.models.gestaomodels.Program
import io.realm.Realm

class CreateProgramViewModel : ViewModel() {
    private var navigation = MutableLiveData(false)
    val mNavigation: LiveData<Boolean>
        get() = navigation

    fun createProgram(name: String) {
        val program = Program(name)
        program.saveToDb()
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(program)
        realm.commitTransaction()
        navigation.value = !navigation.value!!

    }


}