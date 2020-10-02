package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class SubTheme(var name: String = "") : RealmObject() {
    var id = UUID.randomUUID().toString()
    var parent = ""
}