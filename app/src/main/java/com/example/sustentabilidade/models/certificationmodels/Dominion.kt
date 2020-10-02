package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class Dominion(var name: String = "") : RealmObject() {
    var id = UUID.randomUUID().toString()
    var parentID = ""
}