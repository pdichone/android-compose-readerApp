package com.bawp.areader_test.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.bawp.areader_test.utils.InputField

@ExperimentalComposeUiApi
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book!",
    hint: String = "Search",
    onSearch: (String) -> Unit = {}) {
    Column() {
        val textFieldValue = rememberSaveable{ mutableStateOf(defaultValue) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue.value) {
            textFieldValue.value.trim().isNotEmpty()
        }
        InputField(
            modifier.fillMaxWidth()
                .height(140.dp)
                .padding(3.dp)
                .background(Color.White, CircleShape)
               .padding(horizontal = 20.dp, vertical = 12.dp),
               isSingleLine = false,

            valueState = textFieldValue, labelId = "Enter your thoughts" ,
            enabled = true, onAction = KeyboardActions {
                //The submit button is disabled unless the inputs are valid. wrap this in if statement to accomplish the same.
                if (!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())

                keyboardController?.hide() //(to use this we need to use @ExperimentalComposeUiApi
            } )

    }

}