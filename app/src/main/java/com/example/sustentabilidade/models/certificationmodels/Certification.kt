package com.example.sustentabilidade.models.certificationmodels

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.where
import java.util.*

open class Certification : RealmObject() {
    var id = UUID.randomUUID().toString()
    var name: String = ""
    var dominionList = RealmList<Dominion>()
    var dominionNameList = RealmList<String>()
    var dominionNumber = 0
    var themeList = RealmList<Theme>()
    var themeNameList = RealmList<String>()
    var themeNumber = 0
    var subThemeList = RealmList<SubTheme>()
    var subThemeNameList = RealmList<String>()
    var subThemeNumber = 0
    var questionList = RealmList<Question>()
    var questionNameList = RealmList<String>()
    var questionNumber = 0
    var levelList = RealmList<Level>()
    var levelNameList = RealmList<String>()
    var levelNumber = 0

    companion object {
        const val DOMINION = 0
        const val THEME = 1
        const val SUB_THEME = 2
        const val QUESTION = 3
        const val LEVEL = 4
    }

    fun getAllNames(parameter: Int): List<String> {
        when (parameter) {
            DOMINION -> return questionNameList
            THEME -> return themeNameList
            SUB_THEME -> return subThemeNameList
            QUESTION -> return questionNameList
            LEVEL -> return levelNameList
        }
        return listOf()
    }

    fun addItem(level: Level) {
        levelList.add(level)
        levelNameList.add(level.name)
    }

    fun addItem(dominion: Dominion) {
        dominionList.add(dominion)
        dominionNameList.add(dominion.name)
        dominionNumber++
    }

    fun addItem(theme: Theme) {
        themeList.add(theme)
        themeNameList.add(theme.name)
        themeNumber++
    }

    fun addItem(subTheme: SubTheme) {
        subThemeList.add(subTheme)
        subThemeNameList.add(subTheme.name)
        subThemeNumber++
    }

    fun addItem(question: Question) {
        questionList.add(question)
        questionNameList.add(question.name)
        questionNumber++
    }

    fun getDominion(name: String): Dominion? {
        dominionList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }
    fun getTheme(name: String): Theme? {
        themeList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }
    fun getSubTheme(name: String): SubTheme? {
        subThemeList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun getQuestion(name: String): Question? {
        questionList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun getLevel(name: String): Level? {
        levelList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }


    fun removeItem(dominion: Dominion) {
        dominionList.remove(dominion)
        dominionNameList.remove(dominion.name)
        dominionNumber -= 1
        Realm.getDefaultInstance().where<Dominion>().contains("id", dominion.id).findAll()
            .deleteAllFromRealm()
        dominionNumber--
    }

    fun removeItem(level: Level) {
        levelList.remove(level)
        levelNameList.remove(level.name)
        dominionNumber -= 1
        Realm.getDefaultInstance().where<Dominion>().contains("id", level.id).findAll()
            .deleteAllFromRealm()
    }

    fun removeItem(theme: Theme) {
        themeNameList.remove(theme.name)
        themeList.remove(theme)
        themeNumber -= 1
        Realm.getDefaultInstance().where<Theme>().contains("id", theme.id).findAll()
            .deleteAllFromRealm()
        themeNumber--
    }

    fun removeItem(subTheme: SubTheme) {
        subThemeNameList.remove(subTheme.name)
        subThemeList.remove(subTheme)
        subThemeNumber -= 1
        Realm.getDefaultInstance().where<SubTheme>().contains("id", subTheme.id).findAll()
            .deleteAllFromRealm()
        subThemeNumber--
    }

    fun removeItem(question: Question) {
        questionList.remove(question)
        questionNameList.remove(question.name)
        questionNumber -= 1
        Realm.getDefaultInstance().where<Question>().contains("id", question.id).findAll()
            .deleteAllFromRealm()
        questionNumber--
    }

    fun getThemesOfDominion(parentName: String): List<String> {
        val list = mutableListOf<String>()
        themeList.forEach {
            if (it.parent == parentName) {
                list.add(it.name)
            }
        }
        return list
    }

    fun getSubThemesOfTheme(parentName: String): List<String> {
        val list = mutableListOf<String>()
        subThemeList.forEach {
            if (it.parent == parentName) {
                list.add(it.name)
            }
        }
        return list
    }

    fun getQuestionsOfSubTheme(parentName: String): List<String> {
        val list = mutableListOf<String>()
        questionList.forEach {
            if (it.parent == parentName) {
                list.add(it.name)
            }
        }
        return list
    }


}