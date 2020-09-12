package com.example.sustentabilidade.models.gestaomodels

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

open class BalancoPatrimonial {

 fun saveToDb() {
  val database = Firebase.database
  val db = database.reference.child("balancoPatrimonial").child(farmID)
  db.setValue(this)
 }

 var farmID = ""
 var liquidezGeral = "0.00"
 var liquidezCorrente = "0.00"
 var margemLiquida = "0.00"
 var margemBruta = "0.00"
 var taxaRemuneracaoCapital = "0.06"
 var receitaBruta = "0.00"
 var custoOperacionalEfetivo = "0.00"
 var custoOperacionalTotal = "0.00"
 var totalDespesas = "0.00"
 var totalReceitas = "0.00"
 var ativo = "0.00"
 var passivo = "0.00"
 var patrimonioLiquido = "0.00"
 var rentabilidade = "0.00"
 var lucro = "0.00"
 var saldo = "0.00"
 var dividasLongoPrazo = "0.00"
 var dinheiroBanco = "0.00"
 var custoOportunidadeTrabalho = "0.00"
 var trabalhoFamiliarNaoRemunerado = "0.00"
 var pendenciasPagamento = "0.00"
 var pendenciasRecebimento = "0.00"
 var totalContasPagar = "0.00"
 var totalContasReceber = "0.00"
 var modificacao: String = "1"

}