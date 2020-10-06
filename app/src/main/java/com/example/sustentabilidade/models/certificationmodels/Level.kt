package com.example.sustentabilidade.models.certificationmodels

import io.realm.RealmObject
import java.util.*

open class Level : RealmObject() {
    var id = UUID.randomUUID().toString()
    var name = ""
    var minPontuation = 0
}