package com.example.sustentabilidade.fragment.certificationfragments.answerquestionfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

    var allQuestionsAnswered = MutableLiveData(false)

    fun saveQuestion(answer: Answer) {
        realm.beginTransaction()
        if (!removeRepeatedAnswer(answer))
            answerList.answeredQuestions = answerList.answeredQuestions + 1
        if (answerList.answeredQuestions == certification.questionNumber) {
            answerList.finished = "1"
            allQuestionsAnswered.value = true
        }
        answerList.answerList.add(answer)
        realm.commitTransaction()

    }

    fun getAnswerList(farmCode: String, certificationID: String) {
        certification =
            realm.where<Certification>().contains("id", certificationID).findFirst()!!
        val results = realm.where<AnswerList>().contains("certificationID", certificationID)
            .contains("farmCode", farmCode).contains("finished", "0").findAll()
        if (results.size == 0) {
            answerList = AnswerList()
            answerList.certificationID = certificationID
            answerList.farmCode = farmCode
            realm.beginTransaction()
            realm.copyToRealm(answerList)
            realm.commitTransaction()
        } else {
            answerList = results.first()!!
        }
        Log.e("teste", "${answerList.answeredQuestions} ${certification.questionNumber}")
    }

    fun getQuestion(id: String): Question {
        return realm.where<Question>().contains("id", id).findFirst()!!
    }

    private fun removeRepeatedAnswer(answer: Answer): Boolean {
        var tempAnswer: Answer? = null
        for (savedAnswers in answerList.answerList)
            if (savedAnswers.index == answer.index)
                tempAnswer = savedAnswers
        if (tempAnswer != null) {
            answerList.answerList.remove(tempAnswer)
            realm.where<Answer>().contains("id", tempAnswer.id).findFirst()?.deleteFromRealm()
            return true
        }
        return false
    }

}