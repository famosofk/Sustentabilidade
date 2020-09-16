package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class Question(var name: String = "") : RealmObject() {
    var id = UUID.randomUUID().toString()
    var type = 0
    var weight = 1f
    var parent = ""

    companion object {
        val BOOLEAN_INDICATOR_TYPE = 1
        val VALUE_INDICATOR_TYPE = 2
    }

}



