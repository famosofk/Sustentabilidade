package com.example.sustentabilidade.models


import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.RealmList

class AtividadesEconomicas(var nome: String = "") {

    var fazendaID: String = ""
    var rateio: Double = 1.0
    var custoDeProducao: Double = 0.0
    var vendasAtividade: Double = 0.0
    var arrayCustos = RealmList<Double>(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

    var custoSemente = 0.0
    var custoFertilizante = 0.0
    var custoDefensivo = 0.0
    var custoMaodeobra = 0.0
    var custoMaquina = 0.0
    var custoOutros = 0.0
    var modificacao: String = "1"

    fun saveToDb() {
        val database = Firebase.database
        val db = database.reference.child("atividadesEconomicas").child(this.fazendaID).child(nome)
        db.setValue(this)
    }

}