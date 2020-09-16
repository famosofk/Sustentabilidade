package com.example.sustentabilidade.models.certificationmodels

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.where
import java.util.*

open class Theme(var name: String = "") : RealmObject() {
    var id = UUID.randomUUID().toString()
    var subThemeList = RealmList<SubTheme>()
    var subThemeNumber = 0
    var parent = ""


    fun addItem(subTheme: SubTheme) {
        subThemeList.add(subTheme)
        incrementNumber()
    }

    fun removeItem(subTheme: SubTheme) {
        subThemeList.remove(subTheme)
        Realm.getDefaultInstance().where<SubTheme>().contains("id", subTheme.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    private fun incrementNumber() {
        subThemeNumber++
    }

    private fun decrementNumber() {
        subThemeNumber--
    }
}