package com.example.sustentabilidade.fragment.managecertification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class ManageCertificationViewModel(application: Application) : AndroidViewModel(application) {

    private var realm: Realm = Realm.getDefaultInstance()
    private val savedItem = MutableLiveData(false)
    val mSavedItem: LiveData<Boolean>
        get() = savedItem
    var list = listOf<Certification>()

    private val updatedList = MutableLiveData(false)
    val mUpdatedList: LiveData<Boolean>
        get() = updatedList


    fun createCertification(name: String) {
        val certification = Certification()
        certification.name = name
        realm.beginTransaction()
        realm.copyToRealm(certification)
        realm.commitTransaction()
        savedItem.value = true
    }

    fun getList() {
        list = realm.where<Certification>().findAll()
        updatedList.value = true
    }

    fun turnBackSavedToFalse() {
        savedItem.value = false
    }

    fun turnBackUpdatedToFalse() {
        updatedList.value = false
    }


}