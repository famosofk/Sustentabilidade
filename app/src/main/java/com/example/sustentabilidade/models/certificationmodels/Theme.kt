package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject

open class Theme : RealmObject() {

    var name = ""
    var subThemeList = RealmList<SubTheme>()
    var subThemeNumber = 0


    fun addItem(subTheme: SubTheme) {
        subThemeList.add(subTheme)
        incrementNumber()
    }

    fun removeItem(subTheme: SubTheme) {
        subThemeList.remove(subTheme)
        decrementNumber()
    }

    private fun incrementNumber() {
        subThemeNumber++
    }

    private fun decrementNumber() {
        subThemeNumber--
    }
}