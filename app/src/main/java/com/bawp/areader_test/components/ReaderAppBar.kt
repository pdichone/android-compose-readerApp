package com.bawp.areader_test.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReaderAppBar(title: String, icon: ImageVector? = null, showProfile:Boolean = true, onBackArrowClicked: () -> Unit = {}) {
    TopAppBar(
        title = {
            Row {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = "Logo",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        })
                }
                val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
                //Spacer(modifier = Modifier.width())
                Text(text = title,
                    modifier = Modifier.padding(start = screenWidth.dp.times(0.08f),
                        bottom = 19.dp)
                    ,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
            }
        },
        actions = {
            IconButton(onClick = {}) {
                if (showProfile) Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Icon(
                            Icons.Filled.AccountCircle, contentDescription = null,

                            )
                        Text("P.D",
                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.overline,
                            color = Color.Red)
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow Right",
                        modifier = Modifier.clickable {})

                } else Surface() {}


            }
        }, backgroundColor = Color.Transparent, elevation = 0.dp)

}
