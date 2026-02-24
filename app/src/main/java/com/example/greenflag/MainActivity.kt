package com.example.greenflag

import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenflag.ui.theme.GreenFlagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenFlagTheme {

                var showSignUp by rememberSaveable() { mutableStateOf(false) }
                var showFormSubmissionConfirmationScreen by rememberSaveable() { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showFormSubmissionConfirmationScreen) {
                        ConfirmationScreen(modifier = Modifier.padding(innerPadding))
                    } else if (showSignUp) {
                        SignUpScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSubmitClick = { showFormSubmissionConfirmationScreen = true }
                        )
                    } else {
                    LandingPage(
                        modifier = Modifier.padding(innerPadding),
                        onGetStartedClick = { showSignUp = true }
                    )

                    }
                }
            }
        }
    }
}



@Composable
fun LandingPage(modifier: Modifier = Modifier, onGetStartedClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(90.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_green_flag),
            contentDescription = "My image",
            modifier = Modifier
                .size(200.dp)

        )
        Text("GreenFlag customers can\n" +
                "create an account to access",
            color = Color.White
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Adds space between each row
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.tick), contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(Modifier.size(12.dp))
                Text("Car health updates", color = Color.White)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.tick), contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(Modifier.size(12.dp))
                Text("Request a rescue online", color = Color.White)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.tick), contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(Modifier.size(12.dp))
                Text("Policy information", color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))


            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(
                        role = Role.Button,
                        onClick = { onGetStartedClick() },
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gradient_button_background),
                    contentDescription = null
                )
                Text(
                    "Create an account",
                    color = Color.White
                )
            }

            }
        }


    }


@Composable
fun SignUpScreen(modifier: Modifier = Modifier,  onSubmitClick: () -> Unit) {
    var email by rememberSaveable() { mutableStateOf("") }
    var password by rememberSaveable() { mutableStateOf("") }
    var confirmPassword by rememberSaveable() { mutableStateOf("") }

    val isEmailValid = email.contains("@") && email.contains(".")

    val emailError = email.isNotEmpty() && !isEmailValid
    val passwordError = password != confirmPassword && confirmPassword.isNotEmpty()

    val passwordsMatch = password == confirmPassword && password.isNotEmpty()
    val isFormValid = isEmailValid && passwordsMatch


    Column(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Adds even gaps between fields
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Text("Please enter your information",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
                )

        Row() {
        TextField(
            value = email,
            onValueChange = { email = it},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text(
                        text = "Please enter a valid email address"
                    )
                }
            }
            )

        }
        Row() {
            TextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),

            )

        }
        Row() {
            TextField(
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError,
                supportingText = {
                    if (passwordError) {
                        Text(
                            text = "Passwords don't match"
                        )
                    }

                }
            )

        }
    Spacer(modifier = Modifier.weight(1f))

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                enabled = isFormValid,
                role = Role.Button,
                onClick = { onSubmitClick()  },
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradient_button_background),
            contentDescription = null,
            alpha = if (isFormValid) 1.0f else 0.5f
        )
        Text(
            "Create an account",
            color = if (isFormValid) Color.White else Color.Gray

        )
    }
    }


}

@Composable
fun ConfirmationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Account Created, please check your email", color = Color.White, fontSize = 24.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreenFlagTheme {

    }
}