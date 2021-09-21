package com.bawp.areader_test.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReaderLogo(modifier: Modifier = Modifier){
    Text(
        modifier = modifier.padding(bottom = 16.dp),
        text = "A. Reader", style = MaterialTheme.typography.h3, color = Color.Red.copy(alpha = 0.5f))
}