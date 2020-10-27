package com.example.sustentabilidade.fragment.certificationfragments.answerquestionfragment

import android.app.Application
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
    var repeat = MutableLiveData<Boolean>(null)
    private lateinit var answerList: AnswerList
    private lateinit var certification: Certification

    fun saveAnswer(answer: Answer, repeatValue: Boolean) {
        verifyRepeatedAnswer(answer.index)
        realm.beginTransaction()
        answerList.answerList.add(answer)
        answerList.answeredQuestions = answerList.answerList.size
        if (answerList.answeredQuestions == certification.questionNameList.size) {
            answerList.finished = "1"
        }
        realm.copyToRealmOrUpdate(answerList)
        realm.commitTransaction()
        repeat.value = repeatValue
    }

    fun initializeAnswerList(id: String, farmCode: String) {
        certification = realm.where<Certification>().contains("id", id).findFirst()!!
        val results =
            realm.where<AnswerList>().contains("certificationID", id).contains("finished", "0")
                .findAll()

        if (results.size > 0) {
            answerList = results.first()!!
        } else {
            answerList = AnswerList()
            answerList.certificationID = id
            answerList.farmCode = farmCode
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(answerList)
            realm.commitTransaction()
        }
    }

    fun getQuestion(id: String): Question {
        return realm.where<Question>().contains("id", id).findFirst()!!
    }

    private fun verifyRepeatedAnswer(index: Int) {
        realm.beginTransaction()
        answerList.answerList.removeIf { s -> s.index == index }
        realm.commitTransaction()
    }

}