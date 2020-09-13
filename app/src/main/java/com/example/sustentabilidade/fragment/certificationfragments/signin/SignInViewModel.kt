package com.example.sustentabilidade.fragment.certificationfragments.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    val realm = Realm.getDefaultInstance()
    private lateinit var certification: Certification

    fun getCertification(name: String) {
        certification = realm.where<Certification>().contains("name", name).findFirst()!!
    }

    fun verifyDominion(): Boolean {
        if (certification.dominionNumber > 0) {
            return true
        }
        return false
    }

    fun verifyTheme(): Boolean {
        if (verifyDominion()) {
            certification.dominionList.forEach {
                if (it.themeNumber > 0) {
                    return true
                }
            }
        }
        return false
    }


    fun verifySubTheme(): Boolean {
        if (verifyTheme()) {
            certification.dominionList.forEach { it ->
                it.themeList.forEach { itTheme ->
                    if (itTheme.subThemeNumber > 0) {
                        return true
                    }
                }
            }
        }
        return false
    }

}