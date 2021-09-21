package com.bawp.areader_test.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bawp.areader_test.components.ReaderLogo


@Preview
@Composable
fun ReaderLoginScreen(navController: NavController? = null) {
    Surface( modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            ReaderLogo(modifier = Modifier.scale(scale = 1.5f))
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(3.dp)
                    .width(250.dp),
                shape = CircleShape) {

                Text("Login with Email")

            }
            Spacer(modifier = Modifier.height(85.dp))
            Row(
                modifier = Modifier.padding(2.dp),
                horizontalArrangement = Arrangement.SpaceAround
               ){

                Text("New User?")
                Text("Sign up",
                    modifier = Modifier.clickable {  }.padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray)
            }
        }

    }

}

