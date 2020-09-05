package com.example.sustentabilidade.fragment.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private var user = MutableLiveData(false)
    val mUser: MutableLiveData<Boolean>
        get() = user
    private val mAuth = FirebaseAuth.getInstance()


    fun signIn(activity: Activity, email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    user.value = true
                    Log.e("sucesso", "aqui")
                }
            }
    }

}