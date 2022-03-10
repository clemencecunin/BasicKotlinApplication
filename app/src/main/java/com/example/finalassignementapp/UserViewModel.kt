package com.example.finalassignementapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {

    var username = mutableStateOf("Test")

    fun login(email: String, passwd: String){
        Firebase.auth
            .signInWithEmailAndPassword(email,passwd)
            .addOnSuccessListener {
                username.value = email
            }
    }

    fun logout(){
        Firebase.auth.signOut()
        username.value = ""
    }
}