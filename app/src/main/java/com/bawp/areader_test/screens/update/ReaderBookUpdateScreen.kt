package com.bawp.areader_test.screens.update


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bawp.areader_test.model.MBook
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.bawp.areader_test.components.*
import com.bawp.areader_test.data.DataOrException
import com.bawp.areader_test.navigation.ReaderScreens
import com.bawp.areader_test.screens.home.HomeScreenViewModel
import com.bawp.areader_test.utils.formatDate
import com.bawp.areader_test.utils.showToast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookUpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel(),
                    ) {
    Scaffold(
        topBar = {
        ReaderAppBar(title = "Update Book", icon = Icons.Default.ArrowBack, showProfile = false){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }

    },) {

        //Let's get the equivalent MBook object too
        /*
          TODO: Create another ViewModel to search books by
          book id (google books api).
          Then, we'll use it to update and save to firestore!
         */

        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean,
                Exception>>(initialValue = DataOrException(data = emptyList(),true, Exception(""))) {
            value = viewModel.data.value
           // value = viewModel.getBookInfo(bookId)
        }.value

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(3.dp),

            ) {
            Column(modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Log.d("TAGINFO", "BookUpdateScreen: ${viewModel.data.value.data.toString()}")
                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {

                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)

                    }


                    //Add Textfield
                    ShowSimpleForm(book = viewModel.data.value.data?.first { mBook ->
                        Log.d("IN", "IDSS: ${mBook.googleBookId} ==> $bookItemId")
                        mBook.googleBookId == bookItemId
                    }!!, navController = navController)


                }


            }


        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun ShowSimpleForm(book: MBook, navController: NavController) {
    val context = LocalContext.current
    val isStartedReading = remember{
        mutableStateOf(false)
    }
    val isFinishedReading = remember{
        mutableStateOf(false)
    }
    val ratingVal = remember {
        mutableStateOf(0)
    }
    val notesText = remember {
        mutableStateOf("")
    }

     SimpleForm(
        hint = "What do you think about the book?",
        defaultValue = if (book.notes.toString().isNotEmpty()) book.notes.toString()
        else "No thoughts available :("

              ){ note ->
         notesText.value = note
         Log.d("Note", "ShowSimpleForm: $note")
        //invoke the buttons save click event
    }

    Row(modifier = Modifier.padding(4.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Start) {
        TextButton(
            onClick = {
            isStartedReading.value = true
         }, enabled = book.startedReading == null) {
            if (book.startedReading == null){
                if (!isStartedReading.value){

                    Text(text =  "Start Reading")
                }else {

                    Text(text = "Started reading!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                        )
                }
            }else{
                Text(text = "Started on: ${formatDate(book.startedReading!!)}")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = {
          isFinishedReading.value = true
         }, enabled =  book.finishedReading == null) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value){

                    Text(text = "Mark as Read")
                }else {
                    Text(text = "Finished Reading!")
                }
            }else {
                Text(text = "Finished on: ${formatDate(book.finishedReading!!)}")
            }

        }

    }
    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))
    book.rating?.toInt()?.let { it ->
        RatingBar(rating = it){rating ->
            ratingVal.value = rating
            Log.d("Rating", "ShowSimpleForm: $rating")
        }
    }
    Spacer(modifier = Modifier.padding(bottom = 15.dp))

    Row() {

        //Only update if the fields have new data!
        val changedNotes = book.notes != notesText.value
        val changedRating = book.rating?.toInt() != ratingVal.value

        val isFinishedTimeStamp = if (isFinishedReading.value) Timestamp.now() else book.finishedReading
        val isStartedTimeStamp = if (isStartedReading.value) Timestamp.now() else book.startedReading

        val bookUpdate = changedNotes || changedRating
                || isStartedReading.value  || isFinishedReading.value

        val bookToUpdate = hashMapOf(
            "finished_reading_at" to isFinishedTimeStamp,
            "started_reading_at" to isStartedTimeStamp,
            "rating" to ratingVal.value,
            "notes" to notesText.value).toMap()

        RoundedButton(label = "Update"){
            if (bookUpdate) {
                FirebaseFirestore.getInstance().collection("books").document(book.id!!)
                    .update(bookToUpdate).addOnCompleteListener {

                         showToast(context,"Book Updated Successfully!")
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        Log.d("Update", "ShowSimpleForm: ${it.result.toString()}")
                    }.addOnFailureListener {
                        Log.w("TAG", "Error updating document", it)
                    }
            }
            //save or update
        }
        Spacer(modifier = Modifier.width(100.dp))
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value){
            ShowAlertDialog(title = "Are you sure you want to delete this book? \n" +
                    "This action is not reversible", openDialog = openDialog) {

                FirebaseFirestore.getInstance().collection("books").document(book.id!!)
                    .delete().addOnCompleteListener {
                        if (it.isSuccessful){
                            openDialog.value = false
                            /*
                              Don't popBackStack() if we want the immediate recomposition
                              of the MainScreen UI, instead navigate to the mainScreen!
                             */
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)

                        }
                    }.addOnFailureListener{
                        Log.d("ERRor", "ShowSimpleForm: $it")
                    }


            }
        }
        RoundedButton(label = "Delete"){
            openDialog.value = true

        }
    }


}

@Composable
fun ShowAlertDialog(
    title: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit
                   ) {


    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Delete Book")
            },
            text = {
                Column() {
                    Text(title)
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                   ) {
                    TextButton(onClick = { onYesPressed.invoke()

                    }) {
                        Text(text = "Yes")
                    }
                    TextButton(
                        onClick = { openDialog.value = false }
                          ) {
                        Text("No")
                    }
                }
            }
                   )
    }

}



@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
    bookItemId: String
                  ) {

    Row {
        Spacer(modifier = Modifier.width(43.dp))
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.Center,
                  ) {

                PetCardListItem(book = bookInfo.data!!.first { mBook ->
                   // Log.d("IN", "IDSS: ${mBook.googleBookId} ==> $bookItemId")
                    mBook.googleBookId == bookItemId
                }, onPressDetails = {})
            }
        }
    }
}



