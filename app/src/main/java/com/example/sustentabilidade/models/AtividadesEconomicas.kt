package com.example.sustentabilidade.models


import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.RealmList
import io.realm.RealmObject

open class AtividadesEconomicas(var nome: String = "") : RealmObject() {

    private var fazendaID: String = ""
    private var rateio: Double = 1.0
    private var custoDeProducao: Double = 0.0
    private var vendasAtividade: Double = 0.0
    private var arrayCustos =
        RealmList<Double>(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    private var custoSemente = 0.0
    private var custoFertilizante = 0.0
    private var custoDefensivo = 0.0
    private var custoMaodeobra = 0.0
    private var custoMaquina = 0.0
    private var custoOutros = 0.0
    private var modificacao: String = ""

    fun saveToDb() {
        val database = Firebase.database
        val db = database.reference.child("atividadeEconomica").child(this.fazendaID)
        db.setValue(this)
    }

}