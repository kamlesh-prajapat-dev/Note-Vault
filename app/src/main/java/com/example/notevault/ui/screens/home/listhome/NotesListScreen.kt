package com.example.notevault.ui.screens.home.listhome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notevault.R
import com.example.notevault.data.model.note.NoteEntry
import com.example.notevault.ui.NotesAppScreen
import com.example.notevault.ui.components.IconComponent
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NotesListScreen(
    viewModel: NoteViewModel,
    onNavigation: (NoteEntry) -> Unit = {},
    navController: NavHostController,
) {
    val listOfNoteEntries by viewModel.listOfNoteEntries.collectAsState()
    val multipleSelectOption by viewModel.multipleSelectOption.collectAsState()
    val allSelected by viewModel.allSelected.collectAsState()
    val tokenTimeOut by viewModel.tokenTimeOut.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(tokenTimeOut) {
        delay(1000)

        if (tokenTimeOut) {
            navController.navigate(NotesAppScreen.Login.name) {
                popUpTo(NotesAppScreen.NotesList.name) { inclusive = true }
            }
        }
    }

    if (!tokenTimeOut && !isLoading) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                modifier = Modifier
                    .padding(
                        start = 20.dp, top = 40.dp, bottom = 10.dp
                    )
                    .fillMaxWidth(),
                text = stringResource(R.string.app_name),
                fontSize = 48.sp,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (multipleSelectOption) 10.dp else 0.dp), // Manage
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (!multipleSelectOption) {
                    IconButton(
                        onClick = {
                            viewModel.updateMultipleSelectOption(true)
                        }
                    ) {

                        IconComponent(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Cancel",
                            color = Color.Black
                        )
                    }

                    Text(
                        text = "${listOfNoteEntries.count { it.isSelected }}",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (multipleSelectOption) {
                        Checkbox(
                            checked = multipleSelectOption,
                            onCheckedChange = {
                                viewModel.updateMultipleSelectOption(it)
                            },
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = Color.White,
                                checkedColor = Color.Black
                            )
                        )
                    } else {
                        Checkbox(
                            checked = allSelected,
                            onCheckedChange = {
                                viewModel.toggleAllList(it)
                                viewModel.toggleAllSelected(it)
                            }
                        )
                    }

                    IconButton(
                        onClick = {
                            if (multipleSelectOption) {
                                // Search Action

                            } else {
                                // Delete Action
                            }
                        },
                        enabled = if (multipleSelectOption) true else {
                            listOfNoteEntries.count { it.isSelected } != 0
                        }
                    ) {

                        if (multipleSelectOption) {
                            IconComponent(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                color = Color.Black
                            )
                        } else {
                            IconComponent(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                color = when(listOfNoteEntries.count{ it.isSelected }) {
                                    0 -> Color.Gray
                                    else -> Color.Black
                                }
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            if (multipleSelectOption) {
                                // More Vert Action

                            } else {
                                // Move to Inbox Action

                            }
                        },
                        enabled = if (multipleSelectOption) true else {
                            listOfNoteEntries.count { it.isSelected } != 0
                        }
                    ) {

                        if (multipleSelectOption) {
                            IconComponent(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Vert",
                                color = Color.Black
                            )
                        } else {
                            IconComponent(
                                imageVector = Icons.Default.MoveToInbox,
                                contentDescription = "Move to Inbox",
                                color = when(listOfNoteEntries.count { it.isSelected }) {
                                    0 -> Color.Gray
                                    else -> Color.Black
                                }
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(if (multipleSelectOption) 10.dp else 0.dp), // Manage
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                if (listOfNoteEntries.isNotEmpty()) {
                    items(listOfNoteEntries) { note ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (!multipleSelectOption) {
                                Checkbox(
                                    checked = note.isSelected,
                                    onCheckedChange = {
                                        viewModel.toggleSelection(note.id, it)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.Blue,
                                        uncheckedColor = Color.Gray,
                                        disabledCheckedColor = Color.Gray,
                                        disabledUncheckedColor = Color.Gray
                                    )
                                )
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        onClick = {
                                            onNavigation(note)
                                        }
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
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
                } else {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No Note Create Yet",
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

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Color.Black
            )
        }
    }
}