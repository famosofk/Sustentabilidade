package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmList
import io.realm.RealmObject

open class SubTheme(var name: String = "") : RealmObject() {


    var questionList = RealmList<Question>()
    var questionNumber = 0

    fun addItem(question: Question) {
        questionList.add(question)
        incrementNumber()
    }

    fun removeItem(question: Question) {
        questionList.remove(question)
        decrementNumber()
    }

    private fun incrementNumber() {
        questionNumber++
    }

    private fun decrementNumber() {
        questionNumber--
    }

}