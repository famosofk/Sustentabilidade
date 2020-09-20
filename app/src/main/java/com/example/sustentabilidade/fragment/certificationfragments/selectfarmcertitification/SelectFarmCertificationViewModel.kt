package com.example.sustentabilidade.fragment.certificationfragments.selectfarmcertitification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sustentabilidade.models.gestaomodels.Farm
import com.example.sustentabilidade.models.gestaomodels.Program
import io.realm.Realm
import io.realm.kotlin.where

class SelectFarmCertificationViewModel(application: Application) : AndroidViewModel(application) {

    private var realm = Realm.getDefaultInstance()
    private val listProgram = mutableListOf<String>()
    private val listFarm = mutableListOf<String>()


    fun getProgramList(): List<String> {
        if (listProgram.size != 0) {
            return listProgram
        }
        val results = realm.where<Program>().findAll()
        results.forEach {
            listProgram.add(it.name)
        }
        return listProgram
    }

    fun getFarmList(key: String = ""): List<String> {
        if (listFarm.size != 0) {
            return listFarm
        }
        val results = realm.where<Farm>().contains("programa", key).findAll()
        listFarm.clear()
        results.forEach {
            listFarm.add(it.codigoFazenda)
        }
        return listFarm
    }


}