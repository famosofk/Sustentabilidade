package com.example.sustentabilidade.models.certificationmodels

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.where
import java.util.*

open class Certification : RealmObject() {
    var id = UUID.randomUUID().toString()
    var name: String = ""
    var dominionList = RealmList<Dominion>()
    var dominionNumber = 0

    fun addItem(dominion: Dominion) {
        dominionList.add(dominion)
        incrementNumber()
    }

    fun getDominion(name: String): Dominion? {
        dominionList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun getTheme(name: String): Theme? {
        dominionList.forEach { dominion ->
            dominion.themeList.forEach {
                if (it.name == name) {
                    return it
                }
            }
        }
        return null
    }

    fun getSubtheme(name: String): SubTheme? {
        dominionList.forEach { dominion ->
            dominion.themeList.forEach { theme ->
                theme.subThemeList.forEach {
                    if (it.name == name) {
                        return it
                    }
                }
            }
        }
        return null
    }

    fun removeItem(dominion: Dominion) {
        dominionList.remove(dominion)
        Realm.getDefaultInstance().where<Dominion>().contains("id", dominion.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    private fun incrementNumber() {
        dominionNumber++
    }

    private fun decrementNumber() {
        dominionNumber--
    }

}