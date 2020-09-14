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
    lateinit var list: List<String>
    private lateinit var certification: Certification
    private lateinit var modelType: Array<String>

    private var repeat = MutableLiveData(false)
    val mRepeat: LiveData<Boolean>
        get() = repeat
    private var finish = MutableLiveData(false)
    val mFinish: LiveData<Boolean>
        get() = finish

    fun turnFinishBackToFalse() {
        finish.value = false
    }

    fun turnBackRepeatToFalse() {
        repeat.value = false
    }


    fun getCertification(id: String) {
        certification = realm.where<Certification>().contains("id", id).findFirst()!!
    }

    fun initializeArray(array: Array<String>) {
        modelType = array
    }

    fun createModel(key: String, name: String, parent: String = "", command: Int = 0) {
        realm.beginTransaction()
        when (key) {

            modelType[0] -> {
                val dominion = Dominion(name)
                certification.addItem(dominion)
            }
            modelType[1] -> {
                val theme = Theme(name)
                certification.getDominion(parent)?.addItem(theme)
            }
            modelType[2] -> {
                val subTheme = SubTheme(name)
                certification.getTheme(parent)?.addItem(subTheme)
            }
            modelType[3] -> {
                val question = Question(name)
                certification.getSubtheme(parent)?.addItem(question)
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
        val mList = mutableListOf<String>()
        when (type) {
            modelType[1] -> {
                certification.dominionList.forEach { mList.add(it.name) }
                list = mList
            }
            modelType[2] -> {
                certification.dominionList.forEach { dominion ->
                    dominion.themeList.forEach { mList.add(it.name) }
                }
                list = mList
            }
            modelType[3] -> {
                certification.dominionList.forEach { dominion ->
                    dominion.themeList.forEach { theme ->
                        theme.subThemeList.forEach {
                            mList.add(it.name)
                        }
                    }
                }
                list = mList
            }
        }

    }


}