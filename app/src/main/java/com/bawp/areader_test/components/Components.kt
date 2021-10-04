package com.bawp.areader_test.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.bawp.areader_test.model.MBook
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.bawp.areader_test.R
import com.bawp.areader_test.screens.home.HomeScreenViewModel


@Preview
@Composable
fun RoundedButton(
    label: String = "lal", radius: Int = 29,
    onPress: () -> Unit = {},
                 ) {

    Surface(modifier = Modifier
        //.size(200.dp)
        .clip(RoundedCornerShape(bottomEndPercent = radius, topStartPercent = radius)),
        color = Color(0xff92cbdf)

           ) {
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = TextStyle(color = Color.White, fontSize = 15.sp),
                // modifier = Modifier.padding(20.dp)
                )
        }


    }


}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(text = label,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left,
                fontSize = 19.sp)

        }

    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(56),
        elevation = 4.dp,
        color = Color.White) {

        Column(modifier = Modifier.padding(4.dp)) {
            Icon(imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.padding(3.dp))
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)
        }

    }

}


//https://levelup.gitconnected.com/android-jetpack-compose-basics-app-review-c4350bb430a
@Composable
fun PetCardListItem(book: MBook, onPressDetails: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                //onPetClick(pet)
            },
        elevation = 8.dp
        ) {

        Row(horizontalArrangement = Arrangement.Start) {

            Image(painter = rememberImagePainter(book.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(topStart = 120.dp,
                        topEnd = 20.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 20.dp))
                 )
            Column {
                Row {
                    //AgeChip(pet = book)
                   // GenderIcon(pet = book)


                }
               // BehaviourChip(book)
                Text(book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                    )
                Text(
                    book.authors.toString(),
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 0.dp)
                    )
                Text(
                    book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
                    )
            }


        }

    }

}

//Rating Bar
@ExperimentalComposeUiApi
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onPressRating: (Int) -> Unit
             ) {
    var ratingState by remember {
        mutableStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy)
                                )

    Row(
        modifier = Modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
       ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = "star",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressRating(i)
                                ratingState = i
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
                )
        }
    }
}


@Composable
fun ListCard(
    book: MBook,
    onPressDetails: (String) -> Unit,
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
            .clickable { onPressDetails.invoke(book.title!!) },

        ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start,
            content = {
                Row(horizontalArrangement = Arrangement.Center) {
//
                  //  Log.d("CARD", "ListCard: Imageurl:${book.photoUrl.toString()}")
                    Image(painter = rememberImagePainter(book.photoUrl.toString()),
                        contentDescription = null,
                        modifier = Modifier
                            .height(140.dp)
                            .width(100.dp)
                            .padding(4.dp))
                    Spacer(modifier = Modifier.width(50.dp))
                    Column(modifier = Modifier.padding(top = 25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        //heart here
                        Icon(imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier.padding(bottom = 1.dp))

                        //card with star on top and 4.5 bottom
                        BookRating(score = book.rating!!)
                    }
                }

                Text(
                    text = book.title.toString(), modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                    )
                Text(text = book.authors.toString(),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)


            })

        //Details and reading
        val isStartedReading = remember {
            mutableStateOf(false)
        }
        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom) {
            isStartedReading.value = book.startedReading != null

            Spacer(modifier = Modifier.padding(end = 12.dp))
//            Text(text = "Details",
//                modifier = Modifier.padding(12.dp),
//                style = MaterialTheme.typography.caption)

            RoundedButton(label = if (isStartedReading.value) "Reading" else "Not Started",
                radius = 70)
        }

    }

}

@Composable
fun HorizontalScrollableComponent(books: List<MBook>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPress: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(modifier = Modifier.fillMaxWidth()
        .height(280.dp)
        .horizontalScroll(scrollState)) {

        if (viewModel.data.value.loading == true) {
            LinearProgressIndicator()
            Log.d("TAG", "HorizontalScrollableComponent: Loading...")
        } else {
            Log.d("TAG", "HorizontalScrollableComponent: Done loading...")
            if (books.isNullOrEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No books found. Add a book.",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                                         ))
                }
            } else {
                for (book in books) {
                    Log.d("PH", "HorizontalScrollableComponent: ${book.googleBookId}")
                    ListCard(book) {

                        onCardPress(book.googleBookId.toString())

                    }
                }
            }
        }

    }
}

