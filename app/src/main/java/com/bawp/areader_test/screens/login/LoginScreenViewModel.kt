package com.bawp.areader_test.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.areader_test.model.MUser
import com.bawp.areader_test.utils.LoadingState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    //from: https://github.com/pradyotprksh/development_learning/blob/main/jetpack_compose/FlashChat/app/src/main/java/com/project/pradyotprakash/flashchat/view/register/RegisterViewModel.kt
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
//    private val _currentUser = MutableLiveData(FirebaseAuth.getInstance().currentUser)
//     val currentUser: MutableLiveData<FirebaseUser?> = _currentUser

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit,
                                      ){
         if (_loading.value == false) {
             _loading.value = true
             auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                     task ->
                 if (task.isSuccessful) {
                     val displayName = task.result?.user?.email.toString().split('@')[0]
                     createUser(displayName)

                    // Log.d("TAG", "ModelView::Success!!!-->: ${task.result?.user?.email}")
                      home()
                 }
                 _loading.value = false
             }
         }
    }

    private fun createUser(displayName: String) {
        val userId = auth.currentUser?.uid
        val user = MUser(
               userId = userId!!,
               displayName = displayName,
                        avatarUrl = "https://i.pravatar.cc/300",
                        quote = "Hello there",
                        profession = "Android Developer",
                        id = null).toMap()
        //Log.d("MUser", "createUser: ${user.toMap()}")
        FirebaseFirestore.getInstance().collection("users")
            .add(user)

    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        Log.d("Call", "Calling = createUserWithEmailAndPassword: ")
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    home()

                }else {
                    Log.d("Failed", "Failed!!: ${task.result.toString()}")
                }
            }
        }catch (e: Exception){
            Log.d("EXC", "signInWithEmailAndPassword: ${e.message}")
        }
    }

//
}
