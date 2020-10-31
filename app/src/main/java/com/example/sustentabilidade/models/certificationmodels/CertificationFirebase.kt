package com.example.sustentabilidade.models.certificationmodels

class CertificationFirebase {
    var id = ""
    var name = ""
    var dominionList = ArrayList<DominionFirebase>()
    var dominionNameList = ArrayList<String>()
    var dominionNumber = 0
    var themeList = ArrayList<ThemeFirebase>()
    var themeNameList = ArrayList<String>()
    var themeNumber = 0
    var subThemeList = ArrayList<SubThemeFirebase>()
    var subThemeNameList = ArrayList<String>()
    var subThemeNumber = 0
    var questionList = ArrayList<QuestionFirebase>()
    var questionNameList = ArrayList<String>()
    var questionNumber = 0
    var levelList = ArrayList<LevelFirebase>()
    var levelNameList = ArrayList<String>()
    var levelNumber = 0

    companion object {
        fun createFromCertification(certification: Certification): CertificationFirebase {
            val certificationFirebase = CertificationFirebase()
            certificationFirebase.id = certification.id
            certificationFirebase.name = certification.name
            certification.dominionList.forEach {
                certificationFirebase.dominionList.add(
                    DominionFirebase(it.name, it.id)
                )
            }
            certification.dominionNameList.forEach { certificationFirebase.dominionNameList.add(it) }
            certification.themeList.forEach {
                certificationFirebase.themeList.add(
                    ThemeFirebase(
                        it.name,
                        it.id
                    )
                )
            }
            certification.themeNameList.forEach { certificationFirebase.themeNameList.add(it) }
            certification.subThemeList.forEach {
                certificationFirebase.subThemeList.add(
                    SubThemeFirebase(it.name, it.id)
                )
            }
            certification.subThemeNameList.forEach { certificationFirebase.subThemeNameList.add(it) }
            certification.questionList.forEach {
                certificationFirebase.questionList.add(
                    QuestionFirebase(it.name, it.id, it.index, it.weight, it.parent)
                )
            }
            certification.questionNameList.forEach { certificationFirebase.questionNameList.add(it) }
            certification.levelList.forEach {
                certificationFirebase.levelList.add(
                    LevelFirebase(
                        it.name,
                        it.id,
                        it.minPontuation
                    )
                )
            }
            certification.levelNameList.forEach { certificationFirebase.levelNameList.add(it) }
            certificationFirebase.dominionNumber = certification.dominionNumber
            certificationFirebase.themeNumber = certification.themeNumber
            certificationFirebase.subThemeNumber = certification.subThemeNumber
            certificationFirebase.questionNumber = certification.questionNumber
            certificationFirebase.levelNumber = certification.levelNumber
            return certificationFirebase
        }
    }
}