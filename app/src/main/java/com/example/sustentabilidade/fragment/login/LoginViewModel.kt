package com.example.sustentabilidade.fragment.login

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var user = MutableLiveData(false)
    val mUser: MutableLiveData<Boolean>
        get() = user
    var error = MutableLiveData(false)
    var errorMessage = ""
    private val mAuth = FirebaseAuth.getInstance()

    init {
        if (mAuth.currentUser != null) {
            user.value = true
        }
    }


    fun signIn(activity: Activity, email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    user.value = true
                } else {

                    error.value = true
                    errorMessage = task.exception.toString().split(':')[1]
                }
            }
    }

    fun resetError() {
        error.value = false
    }

}