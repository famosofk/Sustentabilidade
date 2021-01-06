package com.example.sustentabilidade.models.certificationmodels

import java.util.*

class AnswerListFirebase {

    var id = UUID.randomUUID().toString()
    var creationDate = ""
    var certificationID: String = ""
    var farmCode: String = ""
    var answeredQuestions = 0
    var answerList = ArrayList<AnswerFirebase>()
    var pontuation = 0.0
    var finished = "0"

    companion object {
        fun createFirebaseAnswerList(answerList: AnswerList): AnswerListFirebase {
            val answerListFirebase = AnswerListFirebase()
            answerListFirebase.id = answerList.id
            answerListFirebase.creationDate = answerList.creationDate
            answerListFirebase.certificationID = answerList.certificationID
            answerListFirebase.farmCode = answerList.farmCode
            answerListFirebase.answeredQuestions = answerList.answeredQuestions
            answerListFirebase.pontuation = answerList.pontuation
            answerListFirebase.finished = answerList.finished
            answerList.answerList.forEach {
                val answer =
                    AnswerFirebase(it.id, it.index, it.note, it.parentID, it.value, it.deliveryDate)
                answerListFirebase.answerList.add(answer)
            }
            return answerListFirebase
        }
    }
}