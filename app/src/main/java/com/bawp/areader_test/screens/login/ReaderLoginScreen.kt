package com.bawp.areader_test.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bawp.areader_test.components.ReaderLogo
import com.bawp.areader_test.utils.EmailInput
import com.bawp.areader_test.utils.PasswordInput
import com.bawp.areader_test.utils.SubmitButton
import com.bawp.areader_test.navigation.ReaderScreens

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


@ExperimentalComposeUiApi
@Composable
fun ReaderLoginScreen(navController: NavController? = null, viewModel: LoginScreenViewModel = viewModel()) {
    val showLoginForm = rememberSaveable{ mutableStateOf(true) }
    val state by viewModel.loadingState.collectAsState()

    Surface( modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            ReaderLogo()
           if(showLoginForm.value) UserForm(false) { email, password ->

               viewModel.signInWithEmailAndPassword(email, password){
                   navController?.navigate(ReaderScreens.ReaderHomeScreen.name)
               }

           }
           else UserForm(loading = false, isCreateAccount = true){ email, password ->
              // Log.d("TAG", "CreateAccount: $email and $password")
               viewModel.createUserWithEmailAndPassword(email.trim(), password.trim()){
                   navController?.navigate(ReaderScreens.ReaderHomeScreen.name)
               }
           }
           }
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
               ){
                val text = if (showLoginForm.value) "Sign up" else "Login"
                Text("New User?")
                Text(text,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),

                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant)
            }
        }

    }

/*
 @author: https://github.com/ameencarpenter/login-template/blob/master/app/src/main/java/com/carpenter/login/utils/uiUtils.kt

 */
@ExperimentalComposeUiApi
@Composable
fun UserForm(
    loading: Boolean = false, isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit,
            ) {
    val email = rememberSaveable{ mutableStateOf("")}
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequester = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        if (isCreateAccount) Text(text = "Please enter a valid email and password that is at least 6 characters",
                                 modifier = Modifier.padding(4.dp)) else {
            Text(text = "")
        }
        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequester.requestFocus()
        })

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequester),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                //The submit button is disabled unless the inputs are valid. wrap this in if statement to accomplish the same.
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
                //keyboardController?.hide() (to use this we need to use @ExperimentalComposeUiApi
            }
                     )
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login" ,
            loading = loading,
            validInputs = valid
                    ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }

    }

}

/**
 * @author: https://github.com/iampawan/JetpackComposeLooks/blob/master/app/src/main/java/com/example/composelookstutorial/composables/LoginPage1.kt
 *
 */
@Composable
fun AlternativeLoginForm() {
    val emailState = remember { mutableStateOf(TextFieldValue("")) }
    val passState = remember { mutableStateOf(TextFieldValue("")) }
    Surface(color = Color.White, modifier = Modifier
        .height(600.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
              ) {

            val modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

            Icon(imageVector = Icons.Default.Favorite, contentDescription = "icon")
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(value = emailState.value, onValueChange = {
                emailState.value = it
            }, modifier = modifier, label = {Text(text = "Email Address")}, leadingIcon = {
                Icons.Filled.Email
            })

            Spacer(modifier = Modifier.padding(6.dp))

            OutlinedTextField(value = passState.value, onValueChange = {
                passState.value = it
            }, modifier = modifier,  label = {Text(text = "Password")}, leadingIcon = {
                Icons.Filled.Lock
            }, keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password))



            Spacer(modifier = Modifier.padding(vertical = 12.dp))
//            ProvideEmphasis(emphasis = EmphasisAmbient.current.disabled) {
//                Text(text = "Forgot password?", textAlign = TextAlign.End, modifier = modifier)
//            }
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Button(onClick = {}, shape = CircleShape,
                modifier = modifier, contentPadding = PaddingValues(16.dp)
                  ) {
                Text(text = "Log In")
            }
        }
    }
}





