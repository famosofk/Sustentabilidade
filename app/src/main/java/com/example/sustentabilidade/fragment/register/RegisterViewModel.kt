package com.example.sustentabilidade.fragment.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var mAuth = FirebaseAuth.getInstance()
    private var user = MutableLiveData(false)
    val mUser: MutableLiveData<Boolean>
        get() = user
    var error = MutableLiveData(false)
    var errorCode = ""


    fun createUser(email: String, password: String, agroplus: String, type: String) {

        var proceed = false

        when (type) {
            "Produtor" -> proceed = true
            "Consultor" -> if (agroplus.equals("244466666")) proceed = true
            "Professor" -> if (agroplus.equals("senhaprofessor")) proceed = true
        }

        if (proceed) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(type).build()
                    val user = mAuth.currentUser
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            changeUserState()
                        }
                    }
                } else {
                    errorCode = task.exception.toString().split(':')[1]
                    changeErrorCode()
                }
            }
        } else {
            errorCode = "Senha incorreta"
            changeErrorCode()
        }

    }

    private fun changeUserState() {
        user.value = true
    }

    private fun changeErrorCode() {
        error.value = true
    }

    fun resetErrrorCode() {
        error.value = false
    }


}