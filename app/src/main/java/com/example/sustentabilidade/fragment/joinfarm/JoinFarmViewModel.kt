package com.example.sustentabilidade.fragment.joinfarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sustentabilidade.models.Farm
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.Realm

class JoinFarmViewModel : ViewModel() {
    var list = listOf<Farm>()
    private var farms = MutableLiveData(false)
    val mFarms: LiveData<Boolean>
        get() = farms

    private var navigation = MutableLiveData(false)
    val mNavigation: LiveData<Boolean>
        get() = navigation


    fun getFarms(program: String) {
        val db = Firebase.database.reference.child("fazendas").child(program)
        val listener = object : ValueEventListener {
            val mList = mutableListOf<Farm>()
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val farm = it.getValue(Farm::class.java)
                    farm?.let { it1 -> mList.add(it1) }
                }
                list = mList
                farms.value = true
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        db.addListenerForSingleValueEvent(listener)

    }

    fun copyFarmToDevice(pos: Int) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(list[pos])
        realm.commitTransaction()
        navigation.value = true
    }


}