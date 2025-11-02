package com.example.notevault.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notevault.ui.NotesAppScreen
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController
) {
    val query by viewModel.searchQuery.collectAsState()
    val filterNoteEntries by viewModel.filteredNoteEntries.collectAsState()

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester),
            value = query,
            onValueChange = {
                viewModel.onSearchQueryChange(it)
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        navController.navigate(NotesAppScreen.NotesList.name) {
                            popUpTo(NotesAppScreen.Search.name) { inclusive = true }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Arrow Back"
                    )
                }
            },
            placeholder = {
                Text(
                    text = "Search for notes",
                    color = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp),
            contentPadding = PaddingValues( vertical = 10.dp, horizontal = 5.dp), // Manage
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            if (filterNoteEntries.isNotEmpty() && query.isNotEmpty()) {
                items(filterNoteEntries) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0x0f0f0f0f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "${note.title} ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = note.content,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "${
                                        note.date.format(
                                            DateTimeFormatter.ofPattern(
                                                "MMMM dd",
                                                Locale.getDefault()
                                            )
                                        )
                                    }",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            } else if (filterNoteEntries.isEmpty() && query.isNotEmpty()){
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Not Found",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}