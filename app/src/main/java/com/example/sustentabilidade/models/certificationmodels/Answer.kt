package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class Answer : RealmObject() {

    var id = UUID.randomUUID().toString()
    var observacao = ""
    var parentID = ""
    var farmID = ""

    var value = 0f

}