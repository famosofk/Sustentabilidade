package com.example.sustentabilidade.models.certificationmodels

import java.util.*

class Answer {

    var id = UUID.randomUUID().toString()
    var observacao = ""
    var párentID = ""
    var farmID = ""
    var dominionPosition = 0
    var themePosition = 0
    var subThemePosition = 0
    var questionPosition = 0

    var value = 0f

}