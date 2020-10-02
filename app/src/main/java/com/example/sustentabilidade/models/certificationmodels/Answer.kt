package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class Answer : RealmObject() {

    var id = UUID.randomUUID().toString()
    var note = ""
    var parentID = ""
    var farmID = ""
    var value = 0f
    var deliveryDate = ""
}