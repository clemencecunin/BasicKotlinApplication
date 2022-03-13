package com.example.finalassignementapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {

    var useremail = mutableStateOf("")
    var username = mutableStateOf("")

    fun login(email: String, passwd: String){
        Firebase.auth
            .signInWithEmailAndPassword(email,passwd)
            .addOnSuccessListener {
                useremail.value = email
                username.value = email.substringBefore("@")
            }
    }

    fun logout(){
        Firebase.auth.signOut()
        username.value = ""
        useremail.value = ""
    }
}