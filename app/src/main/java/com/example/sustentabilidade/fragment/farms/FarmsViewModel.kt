package com.example.sustentabilidade.fragment.farms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sustentabilidade.models.gestaomodels.Program
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.kotlin.where

class FarmsViewModel(application: Application) : AndroidViewModel(application) {

    private var mDownloaded = MutableLiveData(false)
    val downloaded: LiveData<Boolean>
        get() = mDownloaded


    fun downloadingFarms() {
        val db = Firebase.database.reference.child("programas")
        val realm = Realm.getDefaultInstance()
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                realm.beginTransaction()
                val result = realm.where<Program>().findAll()
                snapshot.children.forEach {
                    val program = it.getValue(Program::class.java)
                    if (program != null && (result.where().contains("name", program.name)
                            .count() == 0.toLong())
                    ) {
                        realm.copyToRealm(program)
                    }
                }
                realm.commitTransaction()
                mDownloaded.value = true
            }
        }
        db.addListenerForSingleValueEvent(listener)

    }
}