package com.example.sustentabilidade.fragment.certificationfragments.answerquestionfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sustentabilidade.models.certificationmodels.Answer
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import com.example.sustentabilidade.models.certificationmodels.Question
import io.realm.Realm
import io.realm.kotlin.where

class AnswerQuestionViewModel(application: Application) : AndroidViewModel(application) {

    val realm: Realm = Realm.getDefaultInstance()
    private lateinit var answerList: AnswerList

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

        answerList.answerList.add(answer)
        realm.commitTransaction()

    }

    fun getAnswerList(farmCode: String, certification: String) {
        val results = realm.where<AnswerList>().contains("certificationID", certification)
            .contains("farmCode", farmCode).findAll()
        if (results.size == 0) {
            answerList = AnswerList()
            answerList.certificationID = certification
            answerList.farmCode = farmCode
        } else {
            answerList = results.first()!!
        }

    }

    fun getQuestion(id: String): Question {
        return realm.where<Question>().contains("id", id).findFirst()!!
    }

}