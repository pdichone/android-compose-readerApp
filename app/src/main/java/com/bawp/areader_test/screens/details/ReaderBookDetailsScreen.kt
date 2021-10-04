package com.bawp.areader_test.screens.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.utils.Resource
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.core.text.HtmlCompat
import com.bawp.areader_test.components.RoundedButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
                     ) {

    val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
        value = viewModel.getBookInfo(bookId)
    }.value

    Surface(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(3.dp)) {
        Column(modifier = Modifier.padding(top = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ShowBookDetails(bookInfo, navController = navController)
        }

    }
}

@Composable
fun ShowBookDetails(book: Resource<Item>, navController: NavController) {
    val bookData = book.data?.volumeInfo
    val googleBookId = book.data?.id
    val isBookEmpty = remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier.padding(34.dp),
        //.size(90.dp),
        shape = CircleShape, elevation = 4.dp) {
        Image(painter = rememberImagePainter(bookData?.imageLinks?.thumbnail.toString()),
            contentDescription = "Book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp))

    }
    Text(text = bookData?.title.toString(),
        style = MaterialTheme.typography.h5,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19)

    Text(text = "Authors: ${bookData?.authors.toString()}")
    Text(text = "Page Count: ${bookData?.pageCount.toString()}")
    Text(text = "Categories: ${bookData?.categories.toString()}",
        style = MaterialTheme.typography.subtitle1)
    Text(text = "Published: ${bookData?.publishedDate.toString()}",
        style = MaterialTheme.typography.subtitle1)
    Spacer(modifier = Modifier.height(5.dp))

    val localDims = LocalContext.current.resources.displayMetrics
    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.Gray)) {
        LazyColumn(modifier = Modifier.padding(3.dp)) {
            item {

                 val cleanText = HtmlCompat.fromHtml(bookData?.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

                Text(text = cleanText,
                    modifier = Modifier.padding(4.dp),
                    )
            }
        }
    }

    //Buttons
    Row(
        modifier = Modifier.padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround) {

        RoundedButton(label = "Save" ){
            //save this book to firestore
            val db = FirebaseFirestore.getInstance()
            val bookToSave: MutableMap<String, Any> = HashMap()

            bookToSave["title"] = bookData?.title.toString()
            bookToSave["authors"] = bookData?.authors.toString()
            bookToSave["description"] = bookData?.description.toString()
            bookToSave["notes"] = ""
            bookToSave["categories"] = bookData?.categories.toString()
            bookToSave["page_count"] = bookData?.pageCount.toString()
            bookToSave["book_photo_url"] = bookData?.imageLinks?.thumbnail.toString()
            bookToSave["published_date"] = bookData?.publishedDate.toString()
            bookToSave["rating"] = 0.0
            //bookToSave["started_reading_at"] = Unit
            //bookToSave["finished_reading_at"] = Unit
            bookToSave["google_book_id"] = googleBookId.toString()
            bookToSave["user_id"] = FirebaseAuth.getInstance().currentUser?.uid.toString()

            if (!bookToSave.isNullOrEmpty()) {

                db.collection("books").add(bookToSave).addOnSuccessListener { documentReference ->
                    val stringId = documentReference.id
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.id)
                        //TODO: update this saved book with the documentId
                           db.collection("books").document(documentReference.id)
                               .update(hashMapOf("id" to stringId) as Map<String, Any>).addOnCompleteListener {
                                   if (it.isSuccessful){
                                       navController.popBackStack()
                                   }
                               }.addOnFailureListener {
                                   Log.w("TAG", "Error updating document", it)
                               }
                        //take them back...

                    }.addOnFailureListener { e ->
                        Log.w("TAG", "Error adding document", e)
                    }
            } else {
                
                isBookEmpty.value = true

            }
        }
       Spacer(modifier = Modifier.width(25.dp))

        RoundedButton(label = "Cancel" ){
            //save this book to firestore
            navController.popBackStack()
        }

    }
    if (isBookEmpty.value) Text(text = "Cannot save empty books", color = Color.Red.copy(alpha = 0.5f)
                               ) else Text(text = "")


}