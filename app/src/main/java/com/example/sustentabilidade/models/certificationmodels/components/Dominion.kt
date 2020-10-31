package com.example.sustentabilidade.models.certificationmodels.components

import io.realm.RealmObject
import java.io.Serializable
import java.util.*

open class Dominion(var name: String = "") : RealmObject(), Serializable {
    var id = UUID.randomUUID().toString()
}