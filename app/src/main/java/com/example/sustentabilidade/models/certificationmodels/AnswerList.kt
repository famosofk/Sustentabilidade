package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class AnswerList : RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var creationDate = ""
    var certificationID: String = ""
    var farmCode: String = ""
    var answeredQuestions = 0
    var answerList = RealmList<Answer>()
    var pontuation = 0.0
    var finished = "0"

}