package com.bawp.areader_test.screens

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BookDetailsScreen( navController: NavController, text: String = "") {
    Surface() {
        Text(text = text )

    }
}