package com.example.sustentabilidade.models.certificationmodels.components

import io.realm.RealmObject
import java.io.Serializable
import java.util.*

open class Level : RealmObject(), Serializable {
    var id = UUID.randomUUID().toString()
    var name = ""
    var minPontuation = 0
}