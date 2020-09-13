package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject

open class Question(var name: String = "") : RealmObject() {


    var type = 0
    var answer = ""

}



