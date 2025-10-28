package com.example.notevault.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.notevault.data.model.component.IconData

@Composable
fun IconComponent(imageVector: ImageVector, contentDescription: String, color: Color) {

    Icon(
        modifier = Modifier.size(25.dp),
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = color
    )
}

// Bottom Bar Icon Data for When Open Each Note Content
val listOfIconDataForEachNote = listOf<IconData>(
    IconData(Icons.Default.Alarm, "Alarm"),
    IconData(Icons.Default.Theaters, "Theme"),
    IconData(Icons.Default.Delete, "Trash"),
    IconData(Icons.Default.MoveToInbox, "Move To Inbox"),
    IconData(Icons.Default.Lock, "Lock"),
    IconData(Icons.Default.Share, "Share"),
)

val listOfIconDataForAddNewNote = listOf<IconData>(
    IconData(Icons.Default.Title, "Title"),
    IconData(Icons.Default.CheckCircle, "Check Circle"),
    IconData(Icons.Default.Alarm, "Alarm"),
    IconData(Icons.Default.PhotoAlbum, "Photo Album"),
    IconData(Icons.Default.Mic, "Mic"),
    IconData(Icons.Rounded.MoreVert, "More Vert"),
)

val listOfIconDataForNoteLists = listOf<IconData>(
    IconData(Icons.Default.Home, "Home"),
    IconData(Icons.Default.Folder, "Folder")
)

val listOfTopBarIconData = listOf<IconData>(
    IconData(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo"),
    IconData(Icons.AutoMirrored.Filled.Redo, contentDescription = "Redo"),
    IconData(Icons.Default.Check, contentDescription = "Submit"),
)