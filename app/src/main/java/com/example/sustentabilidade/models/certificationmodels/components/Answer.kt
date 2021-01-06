package com.example.sustentabilidade.models.certificationmodels.components

import com.example.sustentabilidade.models.certificationmodels.AnswerFirebase
import io.realm.RealmObject
import java.util.*

open class Answer : RealmObject() {
    var id = UUID.randomUUID().toString()
    var index = 0
    var note = ""
    var parentID = ""
    var value = 0f
    var deliveryDate = ""

    companion object {
        fun createAnswerFromFirebaseVersion(it: AnswerFirebase): Answer {
            val elem = Answer()
            elem.index = it.index
            elem.value = it.value
            elem.id = it.id
            elem.deliveryDate = elem.deliveryDate
            elem.note = elem.note
            elem.parentID = elem.parentID
            return elem
        }
    }
}