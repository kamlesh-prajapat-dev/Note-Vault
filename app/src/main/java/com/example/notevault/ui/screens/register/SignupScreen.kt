package com.example.notevault.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
fun SignupScreen(
    viewModel: SignupViewModel,
    navController: NavHostController
) {

    val user by viewModel.user.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsgForPassword by viewModel.errorMsgForPassword.collectAsState()
    val errorMsgForEmail by viewModel.errorMsgForEmail.collectAsState()
    val errorMsgForCP by viewModel.errorMsgForCP.collectAsState()
    val isSignUp by viewModel.isSignUp.collectAsState()

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

    Column{
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
                text = "Welcome\nCreate an Account ?",
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
                        value = user.userName,
                        onValueChange = {
                            viewModel.updateEmail(it)
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
                        textStyle = TextStyle(fontSize = 18.sp),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .height(23.dp)
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = errorMsgForEmail,
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    }
                }

                Column {
                    OutlinedTextField(
                        modifier = Modifier.width(330.dp),
                        value = user.password,
                        onValueChange = {
                            viewModel.updatePassword(it)
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
                            text =  errorMsgForPassword,
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    }
                }

                Column {
                    OutlinedTextField(
                        modifier = Modifier.width(330.dp),
                        value = confirmPassword,
                        onValueChange = {
                            viewModel.updateCP(it)
                        },
                        label = {
                            Text(
                                text = "Confirm Password",
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
                            text =  errorMsgForCP,
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
                        viewModel.signUp()

                        if(errorMsgForPassword.isEmpty() && errorMsgForEmail.isEmpty() && errorMsgForCP.isEmpty()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000)
                                if (isSignUp && !isLoading) {
                                    navController.navigate(NotesAppScreen.Login.name) {
                                        popUpTo(NotesAppScreen.Signup.name) { inclusive = true }
                                    }
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
                        text = "Signup",
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
                        text = "Signup with",
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
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate(NotesAppScreen.Login.name) {
                                popUpTo(NotesAppScreen.Login.name) { inclusive = true }
                            }
                        }
                    ),
                    text = "Already have an Account ? Login",
                    fontSize = 16.sp,
                    color = colorResource(R.color.new_color)
                )
            }
        }
    }
}
