package com.example.notevault.ui.screens.home.eachnote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EachNoteContentScreen(viewModel: EachNoteViewModel) {

    val contentFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(200)
        contentFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.padding(
            top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val noteEntry by viewModel.eachNote.collectAsState()
        val noOfCharacters by viewModel.noOfCharacters.collectAsState()

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = when(noteEntry.title) {
                "Title" -> ""
                else -> noteEntry.title
            },
            onValueChange = {
                viewModel.updateTitle(it)
            },
            placeholder = {
                Text(
                    text = noteEntry.title
                )
            },
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            ),
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                focusedContainerColor = Color.Transparent,
            )
        )

        Text(
            text = "${noteEntry.date.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, HH:mm", Locale.getDefault()))} | $noOfCharacters characters",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )

        TextField(
            modifier = Modifier.fillMaxWidth()
                .focusRestorer(contentFocusRequester),
            value = when(noteEntry.content) {
                "Content" -> ""
                else -> noteEntry.content
            },
            onValueChange = {
                viewModel.updateContent(it)
                viewModel.updateNoOfCharacters(it.length)
            },
            minLines = 40
        )
    }
}