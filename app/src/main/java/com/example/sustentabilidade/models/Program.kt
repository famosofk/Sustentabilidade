package com.example.sustentabilidade.models

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.RealmObject


open class Program(var name: String = "") : RealmObject() {

    fun saveToDb() {
        val database = Firebase.database
        val db = database.reference.child("programas").child(this.name)
        db.setValue(this)
    }


}