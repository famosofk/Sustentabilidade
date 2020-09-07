package com.example.sustentabilidade.fragment.createfarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sustentabilidade.models.AtividadesEconomicas
import com.example.sustentabilidade.models.BalancoPatrimonial
import com.example.sustentabilidade.models.Farm
import com.example.sustentabilidade.models.FluxoCaixa
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import java.util.*

class CreateFarmViewModel : ViewModel() {

    private var selectedProgram = ""
    private var navigation = MutableLiveData(false)
    val mNavigation: LiveData<Boolean>
        get() = navigation


    fun defineDefaultProgram(it: String) {
        selectedProgram = it
    }

    fun createFarm(name: String, pass: String, realm: Realm) {
        val farm = Farm()
        farm.codigoFazenda = name
        farm.id = UUID.randomUUID().toString()
        farm.senha = pass
        val fluxoCaixa = FluxoCaixa()
        val balanco = BalancoPatrimonial()
        val atividadesEconomicas = AtividadesEconomicas("Geral")
        atividadesEconomicas.fazendaID = farm.id
        fluxoCaixa.farmID = farm.id
        balanco.farmID = farm.id
        val db = Firebase.database.reference.child("fazendas").child(selectedProgram).child(farm.id)
        db.setValue(farm)
        fluxoCaixa.saveToDb()
        balanco.saveToDb()
        atividadesEconomicas.saveToDb()
        realm.beginTransaction()
        realm.copyToRealm(farm)
        realm.commitTransaction()
        navigation.value = true


    }
}