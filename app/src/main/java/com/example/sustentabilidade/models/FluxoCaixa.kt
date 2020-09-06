package com.example.sustentabilidade.models

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.RealmObject

open class FluxoCaixa : RealmObject() {

    private var farmID = ""
    private var modificacao: String = ""

    fun saveToDb() {
        val database = Firebase.database
        val db = database.reference.child("fluxoCaixa").child(this.farmID)
        db.setValue(this)
    }


}