package com.example.notevault.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notevault.R
import com.example.notevault.ui.NotesAppScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavHostController
) {

    val loginRequest by viewModel.request.collectAsState()
    val errorMsgForPassword by viewModel.errorMsgForPassword.collectAsState()
    val errorMsgForEmail by viewModel.errorMsgForEmail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLogged by viewModel.isLogged.collectAsState()

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = colorResource(R.color.new_color)
                )
                .weight(1f)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an\nAccount ?",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.ic_person),
                contentDescription = "Person"
            )
        }

        Image(
            painter = painterResource(R.drawable.wave),
            contentDescription = "Wave"
        )

        Column(
            modifier = Modifier.weight(4.3f)
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column {
                    OutlinedTextField(
                        modifier = Modifier.width(330.dp),
                        value = loginRequest.userName,
                        onValueChange = {
                            viewModel.updateEmail(
                                email = it
                            )
                        },
                        label = {
                            Text(
                                text = "Email",
                                fontSize = 18.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = colorResource(R.color.new_color)
                            )},
                        textStyle = TextStyle(fontSize = 18.sp)

                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .height(23.dp)
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = errorMsgForEmail.ifEmpty { "" },
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    }
                }

                Column {
                    OutlinedTextField(
                        modifier = Modifier.width(330.dp),
                        value = loginRequest.password,
                        onValueChange = {
                            viewModel.updatePassword(
                                password = it
                            )
                        },
                        label = {
                            Text(
                                text = "Password",
                                fontSize = 18.sp
                            )},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Lock",
                                tint = colorResource(R.color.new_color)
                            )},
                        textStyle = TextStyle(fontSize = 18.sp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .height(23.dp)
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = errorMsgForPassword.ifEmpty { "" },
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = 10.dp)
                        .height(35.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.clickable(
                            onClick = {

                            }
                        ),
                        text = "Forgot Password",
                        color = colorResource(R.color.new_color),
                        textAlign = TextAlign.End
                    )
                }

                Button(
                    modifier = Modifier.width(330.dp),
                    onClick = {
                        viewModel.loginRequest()
                        if (errorMsgForEmail.isEmpty() && errorMsgForPassword.isEmpty()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000)

                                if (isLogged && !isLoading) {
                                    navController.navigate(NotesAppScreen.NotesList.name) { popUpTo(
                                        NotesAppScreen.Login.name) { inclusive = true }}
                                } else {
                                    navController.navigate(NotesAppScreen.ERROR.name) { popUpTo(
                                        NotesAppScreen.Login.name ) { inclusive = true }}
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.new_color)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "LOGIN",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "-OR-",
                    fontSize = 14.sp,
                    color = colorResource(R.color.new_color)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SignIn with",
                        fontSize = 14.sp,
                        color = colorResource(R.color.new_color)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
                ) {

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate(NotesAppScreen.Signup.name) {
                            popUpTo(NotesAppScreen.Login.name) { inclusive = true }
                        }
                    },
                    text = "Don't have account ? Sign up",
                    fontSize = 16.sp,
                    color = colorResource(R.color.new_color)
                )
            }

        }
    }
}

