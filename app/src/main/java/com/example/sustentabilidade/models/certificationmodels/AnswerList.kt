package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject

open class AnswerList : RealmObject() {

    var certificationID: String = ""
    var farmCode: String = ""
    var answerList = RealmList<Answer>()

}