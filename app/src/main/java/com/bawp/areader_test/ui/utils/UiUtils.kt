package com.bawp.areader_test.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BurgerLayout(
    coverId: Int? = null,
    backgroundAlpha: Float = 0.5f,
    progressBackgroundAlpha: Float = 0.5f,
    showProgress: Boolean = false,
    content: @Composable () -> Unit
                ) {
    Box(modifier = Modifier.fillMaxSize()) {
        //background image.
        if (coverId != null) {
            Image(
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(coverId),
                contentDescription = null,
                contentScale = ContentScale.Crop
                 )

            //half transparent box to draw shadow over the background.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = backgroundAlpha))
               )
        }

        //the content of the screen.
        content()

        //draw shadow over the screen then a progressbar at the center to indicate something is loading.
        if (showProgress) Progress(progressBackgroundAlpha)
    }
}

@Composable
fun Progress(progressBackgroundAlpha: Float = 0.5f) {
    Column(Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = progressBackgroundAlpha)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
          ) {
        CircularProgressIndicator()
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
              ) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text =  labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
                     )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
                 ) {
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
                                         ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        keyboardActions = onAction
                     )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    val description = if (visible) "" else "*******"
        //stringResource(id = if (visible) R.string.hide_password else R.string.show_password)
//    val icon =
//        painterResource(id = if (visible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off)

    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close
       //Icon(painter = null, contentDescription = description)
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
              ) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
              )
}

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    numberState: MutableState<String>,
    labelId: Int = 2345,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
                    ) {
    InputField(
        modifier = modifier,
        valueState = numberState,
        labelId = labelId.toString(),
        enabled = enabled,
        keyboardType = KeyboardType.Phone,
        imeAction = imeAction,
        onAction = onAction
              )
}

@Composable
fun Header(textId: Int) {
    Text(
        text = stringResource(id = textId),
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 15.dp),
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground
        )
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
                ) {
    Button(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        onClick = onClick
          ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}