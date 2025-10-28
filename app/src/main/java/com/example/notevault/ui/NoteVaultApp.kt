package com.example.notevault.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.notevault.data.model.note.NoteEntry
import com.example.notevault.ui.components.BottomBar
import com.example.notevault.ui.components.IconComponent
import com.example.notevault.ui.components.listOfTopBarIconData
import com.example.notevault.ui.screens.error.ErrorScreen
import com.example.notevault.ui.screens.home.eachnote.EachNoteContentScreen
import com.example.notevault.ui.screens.home.eachnote.EachNoteViewModel
import com.example.notevault.ui.screens.home.listhome.NoteViewModel
import com.example.notevault.ui.screens.home.listhome.NotesListScreen
import com.example.notevault.ui.screens.login.AuthViewModel
import com.example.notevault.ui.screens.login.LoginScreen
import com.example.notevault.ui.screens.register.SignupScreen
import com.example.notevault.ui.screens.splash.SplashScreen

enum class NotesAppScreen() {
    SPLASH,
    Login,
    NotesList,
    EachNoteContent,
    Signup,
    AddNewNote,
    ERROR
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteVault(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory
    ),
    eachNoteViewModel: EachNoteViewModel = viewModel(
        factory = EachNoteViewModel.Factory
    )
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 5.dp),
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(NotesAppScreen.NotesList.name)
                        }
                    ) {
                        IconComponent (
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Arrow Back",
                            color = Color.Black
                        )
                    }
                },
                actions = {
                    if (currentDestination == NotesAppScreen.EachNoteContent.name) {
                        LazyRow (
                            modifier = Modifier.width(120.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            items(listOfTopBarIconData) { iconData ->

                                IconButton(
                                    modifier = Modifier.size(30.dp),
                                    onClick = {
                                        when(iconData.contentDescription) {
                                            "Submit" -> {
                                                eachNoteViewModel.updateNoteEntry()
                                            }

                                            "Undo" -> {

                                            }

                                            "Redo" -> {

                                            }
                                        }
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
                }
            )
        },

        bottomBar = {
            BottomBar(currentDestination)
        },

        floatingActionButton = {
            if (currentDestination == NotesAppScreen.NotesList.name) {
                IconButton(
                    modifier = Modifier.size(50.dp),
                    onClick = {
                        navController.navigate(NotesAppScreen.EachNoteContent.name)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Green
                    )
                ) {

                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            } else {

            }
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = NotesAppScreen.SPLASH.name
        ) {

            composable(route = NotesAppScreen.SPLASH.name) {
                SplashScreen(
                    navController = navController
                )
            }

            composable(route = NotesAppScreen.Login.name) {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = authViewModel,
                    navController = navController
                )
            }


            composable(route = NotesAppScreen.NotesList.name) {
                // NotesListScreen
                NotesListScreen(
                    viewModel = viewModel(
                        factory = NoteViewModel .Factory
                    ),
                    onNavigation = { noteEntry ->
                        eachNoteViewModel.updateNote(noteEntry)
                        navController.navigate(NotesAppScreen.EachNoteContent.name)
                    },
                    navController = navController,
                )
            }

            composable(route = NotesAppScreen.EachNoteContent.name) {
                // EachNoteContentScreen
                EachNoteContentScreen(
                    viewModel = eachNoteViewModel
                )
            }

            composable(route = NotesAppScreen.Signup.name) {
                SignupScreen(
                    viewModel = viewModel(),
                    navController = navController
                )
            }

            composable(route = NotesAppScreen.ERROR.name) {
                ErrorScreen()
            }

            composable(route = NotesAppScreen.AddNewNote.name) {
                eachNoteViewModel.updateNote(NoteEntry())
                EachNoteContentScreen(
                    viewModel = eachNoteViewModel
                )
            }
        }
    }
}
