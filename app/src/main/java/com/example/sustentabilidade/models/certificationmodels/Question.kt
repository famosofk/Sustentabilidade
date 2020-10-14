package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class Question(var name: String = "") : RealmObject() {
    var id = UUID.randomUUID().toString()
    var index = 0
    var weight = 1f
    var parent = ""

    companion object {
        val ANSWER_NAO_SE_APLICA = -1f
        val ANSWER_NAO_POSSUI = 0f
        val ANSWER_POSSUI = 1f
    }

}



