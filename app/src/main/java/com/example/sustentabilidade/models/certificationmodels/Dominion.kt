package com.example.sustentabilidade.models.certificationmodels

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.where
import java.util.*

open class Dominion(var name: String = "") : RealmObject() {
    var id = UUID.randomUUID().toString()
    var themeList = RealmList<Theme>()
    var themeNumber = 0



    fun addItem(theme: Theme) {
        themeList.add(theme)
        incrementNumber()
    }

    fun removeItem(theme: Theme) {
        themeList.remove(theme)
        Realm.getDefaultInstance().where<Theme>().contains("id", theme.id).findAll()
            .deleteAllFromRealm();
        decrementNumber()
    }

    private fun incrementNumber() {
        themeNumber++
    }

    private fun decrementNumber() {
        themeNumber--
    }


}