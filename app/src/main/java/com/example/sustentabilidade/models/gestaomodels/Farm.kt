package com.example.sustentabilidade.models.gestaomodels

import io.realm.RealmObject

open class Farm : RealmObject() {

    var codigoFazenda: String = ""
    var programa: String = ""
    var senha: String = ""
    var id: String = ""
    var area = 0.0
    var metaMargemLiquida = 0.0
    var metaMargemBruta = 0.0
    var metaRendaBruta = 0.0
    var metaPatrimonioLiquido = 0.0
    var metaLucro = 0.0
    var metasaldo = 0.0
    var metaLiquidezGeral = 0.0
    var metaLiquidezCorrente = 0.0
    var observacao: String = ""
    var modificacao: String = "1"
    var rentabilidade = 0.0

}