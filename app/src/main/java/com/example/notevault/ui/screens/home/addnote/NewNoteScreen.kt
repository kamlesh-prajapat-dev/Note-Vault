package com.example.notevault.ui.screens.home.addnote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val newEntry by viewModel.noteEntry.collectAsState()
    val noOfCharacters by viewModel.noOfCharacters.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300) // small delay to ensure UI is ready
        viewModel.onChangeIsFocusedOnContent(focusRequester.requestFocus())
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(50.dp)
            )
        }
    }

    Column(
        modifier = Modifier.padding(
            top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged {
                    viewModel.onChangeIsFocusedOnTitle(it.isFocused)
                },
            value = newEntry.title,
            onValueChange = {
                viewModel.onChangeTitle(it)
            },
            placeholder = {
                Text(
                    text = "Title",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                disabledContainerColor = Color(0x0f0f0f0f),
                focusedContainerColor = Color(0x0f0f0f0f),
                unfocusedContainerColor = Color(0x0f0f0f0f),
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                disabledBorderColor = Color.White
            )
        )

        Text(
            text = "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, HH:mm", Locale.getDefault()))} | $noOfCharacters characters",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    viewModel.onChangeIsFocusedOnContent(it.isFocused)
                },
            value = newEntry.content,
            onValueChange = {
                viewModel.onChangeContent(it)
                viewModel.updateNoOfCharacters(it.length)
            },
            minLines = 40,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                disabledBorderColor = Color.White
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            placeholder = {
                Text(
                    text = "Content",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        )
    }
}