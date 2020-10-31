package com.example.sustentabilidade.fragment.certificationfragments.managecertification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sustentabilidade.models.certificationmodels.Certification
import com.example.sustentabilidade.models.certificationmodels.CertificationFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.Realm

class ManageCertificationViewModel(application: Application) : AndroidViewModel(application) {

    private var realm: Realm = Realm.getDefaultInstance()
    private val savedItem = MutableLiveData(false)
    val mSavedItem: LiveData<Boolean>
        get() = savedItem

    private val updatedList = MutableLiveData(false)
    val mUpdatedList: LiveData<Boolean>
        get() = updatedList


    fun createCertification(name: String) {
        val certification = Certification()
        certification.name = name
        realm.beginTransaction()
        realm.copyToRealm(certification)
        realm.commitTransaction()

        savedItem.value = true
    }


    fun copyCertificationFromCloud() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
        val db = Firebase.database.getReference("certificados")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                realm.beginTransaction()
                snapshot.children.forEach {
                    val firebase = it.getValue(CertificationFirebase::class.java)
                    firebase?.let { it1 ->
                        val tempCert = Certification.createFromFirebase(it1)
                        realm.copyToRealm(tempCert)
                    }
                }
                realm.commitTransaction()
                getList()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        db.addListenerForSingleValueEvent(listener);
    }

    fun getList() {
        updatedList.value = true
    }


    fun turnBackSavedToFalse() {
        savedItem.value = false
    }

    fun turnBackUpdatedToFalse() {
        updatedList.value = false
    }


}