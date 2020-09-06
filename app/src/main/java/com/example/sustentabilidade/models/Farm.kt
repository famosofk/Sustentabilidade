package com.example.sustentabilidade.models

import io.realm.RealmObject

open class Farm : RealmObject() {

    var codigoFazenda: String = ""
    var programa: String = ""
    var senha: String = ""
    var id: String = ""

}