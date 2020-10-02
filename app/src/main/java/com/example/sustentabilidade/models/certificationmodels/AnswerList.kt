package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject

open class AnswerList : RealmObject() {

    var creationDate = ""
    var certificationID: String = ""
    var farmCode: String = ""
    var answeredQuestions = 0
    var answerList = RealmList<Answer>()
    var pontuation = 0.0
    var finished = false

}