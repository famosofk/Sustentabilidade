package com.example.sustentabilidade.fragment.certificationfragments.selectcomponent

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sustentabilidade.R
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class EditComponentViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var componentsList: List<String>
    private var certificatio2n = Certification
    private lateinit var realm: Realm
    private var listUpdated = MutableLiveData(false)
    val mListUpdated: LiveData<Boolean>
        get() = listUpdated

    fun getAdapterList(): List<String> {
        return componentsList
    }

    private fun initializeRealm() {
        realm = Realm.getDefaultInstance()
    }


    fun createList(type: String, resources: Resources, id: String) {
        initializeRealm()
        val array = resources.getStringArray(R.array.models)
        val certification = realm.where<Certification>().contains("id", id).findFirst()!!
        when (type) {
            array[0] -> {
                componentsList = certification.getAllNames(Certification.DOMINION)
            }
            array[1] -> {
                componentsList = certification.getAllNames(Certification.THEME)
            }
            array[2] -> {
                componentsList = certification.getAllNames(Certification.SUB_THEME)
            }
            array[3] -> {
                componentsList = certification.getAllNames(Certification.QUESTION)
            }
        }
    }


}