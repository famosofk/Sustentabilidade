package com.example.sustentabilidade.models

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.RealmObject

open class BalancoPatrimonial : RealmObject() {

    fun saveToDb() {
        val database = Firebase.database
        val db = database.reference.child("balancoPatrimonial").child(this.farmID)
        db.setValue(this)
    }

    private var farmID = ""
    private var liquidezGeral = "0.00"
    private var liquidezCorrente = "0.00"
    private var margemLiquida = "0.00"
    private var margemBruta = "0.00"
    private var taxaRemuneracaoCapital = "0.06"
    private var receitaBruta = "0.00"
    private var custoOperacionalEfetivo = "0.00"
    private var custoOperacionalTotal = "0.00"
    private var totalDespesas = "0.00"
    private var totalReceitas = "0.00"
    private var ativo = "0.00"
    private var passivo = "0.00"
    private var patrimonioLiquido = "0.00"
    private var rentabilidade = "0.00"
    private var lucro = "0.00"
    private var saldo = "0.00"
    private var dividasLongoPrazo = "0.00"
    private var dinheiroBanco = "0.00"
    private var custoOportunidadeTrabalho = "0.00"
    private var trabalhoFamiliarNaoRemunerado = "0.00"
    private var pendenciasPagamento = "0.00"
    private var pendenciasRecebimento = "0.00"
    private var totalContasPagar = "0.00"
    private var totalContasReceber = "0.00"
    private var modificacao: String = ""

}