package com.example.sustentabilidade.fragment.certificationfragments.createmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sustentabilidade.models.certificationmodels.*
import io.realm.Realm
import io.realm.kotlin.where

class CreateModelViewModel(application: Application) : AndroidViewModel(application) {

    val realm = Realm.getDefaultInstance()
    var list = mutableListOf<String>()
    private lateinit var certification: Certification
    private lateinit var modelType: Array<String>

    private var repeat = MutableLiveData(false)
    val mRepeat: LiveData<Boolean>
        get() = repeat
    private var finish = MutableLiveData(false)
    val mFinish: LiveData<Boolean>
        get() = finish


    fun getCertification(id: String) {
        certification = realm.where<Certification>().contains("id", id).findFirst()!!
    }

    fun initializeArray(array: Array<String>) {
        modelType = array
    }

    fun createModel(
        key: String,
        name: String,
        parent: String = "",
        command: Int = 0,
        weight: String,
        type: Int
    ) {
        realm.beginTransaction()
        when (key) {

            modelType[0] -> {
                val dominion = Dominion(name)
                certification.addItem(dominion)
            }
            modelType[1] -> {
                val theme = Theme(name)
                theme.parent = parent
                certification.addItem(theme)
            }
            modelType[2] -> {
                val subTheme = SubTheme(name)
                subTheme.parent = parent
                certification.addItem(subTheme)
            }
            modelType[3] -> {
                val question = Question(name)
                question.type = type
                question.parent = parent
                if (weight.isNotEmpty()) {
                    question.weight = weight.toFloat()
                }
                certification.addItem(question)

            }

        }
        realm.commitTransaction()
        if (command == 0) {
            repeat.value = true
        } else {
            finish.value = true
        }
    }

    fun turnRepetBackToFalse() {
        repeat.value = false
    }

    fun initializeList(type: String) {
        list.clear()
        when (type) {
            modelType[1] ->
                list = certification.dominionNameList

            modelType[2] ->
                list = certification.themeNameList

            modelType[3] ->
                list = certification.subThemeNameList
        }
    }
}