package com.example.notevault.data.model.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class IconData(
    val imageVector: ImageVector,
    val contentDescription: String,
    val color: Color = Color.Gray
)
