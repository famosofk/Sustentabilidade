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

    companion object {
        const val DOMINION = 0
        const val THEME = 1
        const val SUB_THEME = 2
        const val QUESTION = 3
    }

    fun getAllNames(parameter: Int): List<String> {
        val mList = mutableListOf<String>()
        when (parameter) {
            Certification.DOMINION -> {
                mList.addAll(dominionNameList)
            }
            Certification.THEME -> {
                dominionList.forEach {
                    mList.addAll(it.themeNameList)
                }
            }
            Certification.SUB_THEME -> {
                dominionList.forEach { dominion ->
                    dominion.themeList.forEach {
                        mList.addAll(it.subThemeNameList)
                    }
                }
            }
            Certification.QUESTION -> {
                dominionList.forEach { dominion ->
                    dominion.themeList.forEach { theme ->
                        theme.subThemeList.forEach {
                            mList.addAll(it.questionNameList)
                        }
                    }
                }
            }
        }
        return mList
    }

    fun addItem(dominion: Dominion) {
        dominionList.add(dominion)
        dominionNameList.add(dominion.name)
        incrementNumber()
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
        dominionList.forEach { dominion ->
            dominion.themeList.forEach {
                if (it.name == name) {
                    return it
                }
            }
        }
        return null
    }

    fun getSubTheme(name: String): SubTheme? {
        dominionList.forEach { dominion ->
            dominion.themeList.forEach { theme ->
                theme.subThemeList.forEach {
                    if (it.name == name) {
                        return it
                    }
                }
            }
        }
        return null
    }

    fun getQuestion(name: String): Question? {
        dominionList.forEach { dominion ->
            dominion.themeList.forEach { theme ->
                theme.subThemeList.forEach { subtheme ->
                    subtheme.questionList.forEach {
                        if (name == it.name) {
                            return it
                        }
                    }
                }
            }
        }
        return null
    }

    fun removeItem(dominion: Dominion) {
        dominionList.remove(dominion)
        dominionNameList.remove(dominion.name)
        dominionNumber -= 1;
        Realm.getDefaultInstance().where<Dominion>().contains("id", dominion.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    fun removeItem(theme: Theme) {
        val parent = getDominion(theme.parent)!!
        parent.themeNameList.remove(theme.name)
        parent.themeList.remove(theme)
        parent.themeNumber -= 1;

        Realm.getDefaultInstance().where<Theme>().contains("id", theme.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    fun removeItem(subtheme: SubTheme) {
        val parent = getTheme(subtheme.parent)!!
        parent.subThemeNameList.remove(subtheme.name)
        parent.subThemeList.remove(subtheme);
        parent.subThemeNumber -= 1
        Realm.getDefaultInstance().where<SubTheme>().contains("id", subtheme.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    fun removeItem(question: Question) {
        val parent = getSubTheme(question.parent)!!
        parent.questionNameList.remove(question.name)
        parent.questionList.remove(question)
        parent.questionNumber -= 1
        Realm.getDefaultInstance().where<Dominion>().contains("id", question.id).findAll()
            .deleteAllFromRealm()
        decrementNumber()
    }

    private fun incrementNumber() {
        dominionNumber++
    }

    private fun decrementNumber() {
        dominionNumber--
    }

}