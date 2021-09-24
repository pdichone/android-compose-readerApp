package com.bawp.jetstate.readerapp.readerapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bawp.areader_test.R


@Preview
@Composable
fun RoundedButton(label: String = "lal", radius: Int = 29) {

    Surface(
        modifier = Modifier
            //.size(200.dp)
            .clip(
                RoundedCornerShape(
                    bottomEndPercent = radius, topStartPercent = radius
                                  )
                 ),
        color = Color(0xff92cbdf)

           ) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = TextStyle(color = Color.White, fontSize = 15.sp,),
                // modifier = Modifier.padding(20.dp)
                )
        }


    }


}

@Composable
fun TitleSection(modifier: Modifier = Modifier,label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 10.dp)) {
        Column {
            Text(
                text = label,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left,
                fontSize = 19.sp
                )

        }

    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56),
        elevation = 4.dp,
        color = Color.White
           ) {

        Column(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.padding(3.dp)
                )
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)
        }

    }

}
    @Composable
    fun ListCard(
        image: Int /*for now */,
        title: String,
        auth: String,
        rating: Double,
        onPressDetails: (String) -> Unit
                ) {

        val context = LocalContext.current
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        // Compute the screen width using the actual display width and the density of the display.
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val spacing = 10.dp

        Card(
            shape = RoundedCornerShape(29.dp),
            backgroundColor = Color.White,
            elevation = 6.dp,
            modifier = Modifier
                .padding(16.dp)
                .height(242.dp)
                .width(202.dp)
                .clickable { onPressDetails.invoke(title) },

            ) {
            Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
                horizontalAlignment = Alignment.Start,
                content = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                       ) {
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = "book image",
                            modifier = Modifier
                                .height(140.dp)
                                .width(100.dp)
                                .padding(4.dp)

                             )
                        Spacer(modifier = Modifier.width(50.dp))
                        Column(
                            modifier = Modifier.padding(top = 45.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                              ) {
                            //heart here
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.padding(bottom = 15.dp)
                                )

                            //card with star on top and 4.5 bottom
                            BookRating(score = rating)
                        }
                    }

                    Text(
                        text = title, modifier = Modifier.padding(4.dp),
                        fontWeight = FontWeight.Bold,
                        )
                    Text(
                        text = auth,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.caption
                        )


                })

            //Details and reading
            Row(

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Text(text = "Details",
                    modifier = Modifier.padding( 12.dp),
                    style = MaterialTheme.typography.caption)

                RoundedButton(label = "Read", radius = 70)
            }

        }

    }

@Composable
fun HorizontalScrollableComponent(books: List<String>, onCardPress: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .horizontalScroll(scrollState)
       ) {

        for ((index) in books.withIndex()) {
            ListCard(image = R.drawable.profile_image,
                title = "My Best book Ever $index",
                auth = "James Brown $index",
                rating = 3.4,
                onPressDetails = {
                    onCardPress(it)

                })
        }


    }
}

