package com.example.sustentabilidade.models.certificationmodels

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.where
import java.util.*

open class SubTheme(var name: String = "") : RealmObject() {

    var id = UUID.randomUUID().toString()
    var questionList = RealmList<Question>()
    var questionNameList = RealmList<String>()
    var questionNumber = 0
    var parent = ""

    fun addItem(question: Question) {
        questionList.add(question)
        questionNameList.add(question.name)
        incrementNumber()
    }

    fun getQuestionsFiltered(parent: String): List<Question> {
        val list = mutableListOf<Question>()
        for (item in questionList) {
            if (item.parent.equals(parent)) {
                list.add(item)
            }
        }
        return list;
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