package com.example.sustentabilidade.models.certificationmodels

import com.example.sustentabilidade.models.certificationmodels.components.Answer
import com.example.sustentabilidade.models.certificationmodels.components.Question
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
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
    var finished = UNFINISHED

    companion object {
        val FINISHED = "1"
        val UNFINISHED = "0"

        fun createRealmVersionFromFirebase(firebase: AnswerListFirebase): AnswerList {
            val answer = AnswerList()
            answer.id = firebase.id
            answer.creationDate = firebase.creationDate
            answer.certificationID = firebase.certificationID
            answer.farmCode = firebase.farmCode
            answer.answeredQuestions = firebase.answeredQuestions
            answer.pontuation = firebase.pontuation
            answer.finished = firebase.finished
            firebase.answerList.forEach {
                answer.answerList.add(Answer.createAnswerFromFirebaseVersion(it))
            }
            return answer
        }
    }

    fun sendAnswerListToCloud() {
        val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        creationDate = currentDate
        val answerListFirebase = AnswerListFirebase.createFirebaseAnswerList(this)
        val databaseReference =
            Firebase.database.reference.child("respostas").child(certificationID)
                .child(currentDate.trim())
        databaseReference.setValue(answerListFirebase)
    }

    fun calculatePontuation() {
        answerList.forEach {
            if (it.value == Question.ANSWER_POSSUI)
                pontuation++
        }
    }


}