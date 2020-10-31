package com.example.sustentabilidade.models.certificationmodels

data class DominionFirebase(var name: String = "", var id: String = "")
data class ThemeFirebase(var name: String = "", var id: String = "")
data class SubThemeFirebase(var name: String = "", var id: String = "")
data class QuestionFirebase(
    var name: String = "",
    var id: String = "",
    var index: Int = 0,
    var weight: Float = 1f,
    var parent: String = ""
)

data class LevelFirebase(var name: String = "", var id: String = "", var minPontuation: Int = 0)