package com.bawp.areader_test.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bawp.areader_test.ui.utils.LoadingState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
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

    fun createUserWEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit,
                                   ){
         if (_loading.value == false) {
             _loading.value = true
             auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                     task ->
                 if (task.isSuccessful) {
                     Log.d("TAG", "ModelView::Success!!!-->: ${task.result?.user?.email}")
                      home()
                 }
                 _loading.value = false
             }
         }
    }
    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        Log.d("Call", "Calling = createUserWithEmailAndPassword: ")
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    home()
                    Log.d("TAG", "ModelView::Success!!!-->: ${task.result?.user?.email}")
                }else {
                    Log.d("Failed", "Failed!!: ${task.result.toString()}")
                }
            }
        }catch (e: Exception){
            Log.d("EXC", "signInWithEmailAndPassword: ${e.message}")
        }
    }

//    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
//        try {
//            loadingState.emit(LoadingState.LOADING)
//            Firebase.auth.signInWithCredential(credential).await()
//            loadingState.emit(LoadingState.LOADED)
//        } catch (e: Exception) {
//            loadingState.emit(LoadingState.error(e.localizedMessage))
//        }
//    }
}
