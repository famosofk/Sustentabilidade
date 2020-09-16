package com.example.sustentabilidade.models.certificationmodels

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.where
import java.util.*

open class SubTheme(var name: String = "") : RealmObject() {

    var id = UUID.randomUUID().toString()
    var questionList = RealmList<Question>()
    var questionNumber = 0
    var parent = ""

    fun addItem(question: Question) {
        questionList.add(question)
        incrementNumber()
    }

    fun removeItem(question: Question) {
        questionList.remove(question)
        Realm.getDefaultInstance().where<Question>().contains("id", question.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    private fun incrementNumber() {
        questionNumber++
    }

    private fun decrementNumber() {
        questionNumber--
    }

}