package com.example.sustentabilidade.models.gestaomodels

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

open class FluxoCaixa {

    var farmID = ""
    var modificacao: String = "1"

    fun saveToDb() {
        val database = Firebase.database
        val db = database.reference.child("fluxoCaixa").child(farmID)
        db.setValue(this)
    }


}