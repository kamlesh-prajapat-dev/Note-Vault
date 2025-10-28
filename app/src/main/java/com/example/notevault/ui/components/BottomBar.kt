package com.example.notevault.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomBar(specifier: String?) {

    when(specifier) {
        "NotesList" -> {
            LazyRow (
                modifier = Modifier.fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                items(listOfIconDataForNoteLists) { iconData ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = {},
                        ) {

                            IconComponent(
                                imageVector = iconData.imageVector,
                                contentDescription = iconData.contentDescription,
                                color = iconData.color
                            )
                        }

                        Text(
                            text = iconData.contentDescription,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        "EachNoteContent" -> {
            LazyRow (
                modifier = Modifier.fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                items(listOfIconDataForEachNote) { iconData ->
                    IconButton(
                        onClick = {

                        }
                    ) {

                        IconComponent(
                            imageVector = iconData.imageVector,
                            contentDescription = iconData.contentDescription,
                            color = iconData.color
                        )
                    }
                }
            }
        }

        "AddNewNote" -> {
            LazyRow (
                modifier = Modifier.fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                items(listOfIconDataForAddNewNote) { iconData ->
                    IconButton(
                        onClick = {

                        }
                    ) {

                        IconComponent(
                            imageVector = iconData.imageVector,
                            contentDescription = iconData.contentDescription,
                            color = iconData.color
                        )
                    }
                }
            }
        }

        else -> {}
    }


}