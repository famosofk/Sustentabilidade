package com.example.sustentabilidade.fragment.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel : ViewModel() {

    private var mAuth = FirebaseAuth.getInstance()
    private var user = MutableLiveData(false)
    val mUser: MutableLiveData<Boolean>
        get() = user
    var error = MutableLiveData(false)
    var errorCode = ""


    fun createUser(email: String, password: String, type: String) {

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
                errorCode = task.exception.toString()
                changeErrorCode()
            }
        }

    }

    private fun changeUserState() {
        user.value = true
    }

    private fun changeErrorCode() {
        error.value = true
    }


}