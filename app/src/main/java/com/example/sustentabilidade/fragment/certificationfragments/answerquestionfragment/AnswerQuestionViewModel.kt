package com.example.sustentabilidade.fragment.certificationfragments.answerquestionfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sustentabilidade.models.certificationmodels.Answer
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import com.example.sustentabilidade.models.certificationmodels.Certification
import com.example.sustentabilidade.models.certificationmodels.Question
import io.realm.Realm
import io.realm.kotlin.where

class AnswerQuestionViewModel(application: Application) : AndroidViewModel(application) {

    val realm: Realm = Realm.getDefaultInstance()
    private lateinit var answerList: AnswerList
    private lateinit var certification: Certification

    fun saveQuestion(answer: Answer) {
        realm.beginTransaction()
        var tempAnswer: Answer? = null
        for (savedAnswers in answerList.answerList) {
            if (savedAnswers.parentID == answer.parentID) {
                tempAnswer = savedAnswers
            }
        }
        if (tempAnswer != null) {
            answerList.answerList.remove(tempAnswer)
            realm.where<Answer>().contains("id", tempAnswer.id).findAll().deleteAllFromRealm()
        }
        answerList.answeredQuestions++
        if (answerList.answeredQuestions == certification.questionNumber) {
            answerList.finished = true
        }
        answerList.answerList.add(answer)
        realm.commitTransaction()

    }

    fun getAnswerList(farmCode: String, certificationID: String) {
        certification =
            realm.where<Certification>().contains("certificationID", certificationID).findFirst()!!
        val results = realm.where<AnswerList>().contains("certificationID", certificationID)
            .contains("farmCode", farmCode).contains("finished", false.toString()).findAll()
        if (results.size == 0) {
            answerList = AnswerList()
            answerList.certificationID = certificationID
            answerList.farmCode = farmCode
        } else {
            answerList = results.first()!!
        }

    }

    fun getQuestion(id: String): Question {
        return realm.where<Question>().contains("id", id).findFirst()!!
    }

}