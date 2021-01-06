package com.example.sustentabilidade.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sustentabilidade.R
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import com.example.sustentabilidade.models.certificationmodels.AnswerListFirebase
import com.example.sustentabilidade.models.certificationmodels.Certification
import com.example.sustentabilidade.models.certificationmodels.CertificationFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    companion object {
        val ANSWERS_NODE = "respostas"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
        val thread = FetchThread()
        thread.run()

    }

    class FetchThread : Thread() {
        val realm = Realm.getDefaultInstance()
        override fun run() {
            super.run()
            val database = Firebase.database.reference.child(MainActivity.ANSWERS_NODE)
            database.addValueEventListener(createValueEventListenerForAnswers())
            copyCertificationFromCloud()
        }

        private fun createValueEventListenerForAnswers(): ValueEventListener {
            return object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    realm.beginTransaction()
                    snapshot.children.forEach { node ->
                        node.children.forEach {
                            val answer = it.getValue(AnswerListFirebase::class.java)
                            val realmAnswerVersion =
                                AnswerList.createRealmVersionFromFirebase(answer!!)
                            realm.where<AnswerList>().contains("id", answer.id).findAll()
                                .deleteAllFromRealm()
                            realm.copyToRealm(realmAnswerVersion)
                        }
                    }
                    realm.commitTransaction()
                }

                override fun onCancelled(error: DatabaseError) {}
            }
        }

        fun copyCertificationFromCloud() {
            realm.beginTransaction()
            realm.where<Certification>().findAll().deleteAllFromRealm()
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
                }

                override fun onCancelled(error: DatabaseError) {}
            }
            db.addListenerForSingleValueEvent(listener);
        }
    }

}
