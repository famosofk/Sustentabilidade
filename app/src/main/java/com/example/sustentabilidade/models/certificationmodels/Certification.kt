package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject

open class Certification : RealmObject() {

    var name: String = ""
    var dominionList = RealmList<Dominion>()
    var dominionNumber = 0

    fun addItem(dominion: Dominion) {
        dominionList.add(dominion)
        incrementNumber()
    }

    fun removeItem(dominion: Dominion) {
        dominionList.remove(dominion)
        decrementNumber()
    }

    private fun incrementNumber() {
        dominionNumber++
    }

    private fun decrementNumber() {
        dominionNumber--
    }

}