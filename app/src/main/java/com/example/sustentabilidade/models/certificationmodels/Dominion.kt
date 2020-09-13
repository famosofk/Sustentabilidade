package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject

open class Dominion(var name: String = "") : RealmObject() {

    var themeList = RealmList<Theme>()
    var themeNumber = 0


    fun addItem(theme: Theme) {
        themeList.add(theme)
        incrementNumber()
    }

    fun removeItem(theme: Theme) {
        themeList.remove(theme)
        decrementNumber()
    }

    private fun incrementNumber() {
        themeNumber++
    }

    private fun decrementNumber() {
        themeNumber--
    }


}