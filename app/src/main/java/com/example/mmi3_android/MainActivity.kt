package com.example.mmi3_android


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mmi3_android.ui.theme.MMI3_androidTheme


class MainActivity : ComponentActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MMI3_androidTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        /*.clickable {
                            val it = Intent(this, SecondActivity::class.java)
                            startActivity(it)
                        }*/,
                    color = MaterialTheme.colorScheme.background
                ){
                    Greeting()
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true, showSystemUi = true)
    fun Greeting() {

        val dialogState = remember { mutableStateOf(false) }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.icon_user),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text("Nom d'utilisateur") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Mot de passe") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (username.equals("etudiant", ignoreCase = true) && password.equals("AzertY")) {
                        val intent = Intent(this@MainActivity, SecondActivity::class.java)
                        startActivity(intent)
                    } else {
                        dialogState.value = true
                        /*Log.d(tag, "Nom d'utilisateur saisi : $username")
                        Log.d(tag, "Mot de passe saisi : $password")*/
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Connexion")
            }

            if (dialogState.value) {
                AlertDialog(
                    onDismissRequest = {
                        dialogState.value = false
                    },
                    title = {
                        Text("Erreur")
                    },
                    text = {
                        Text("Le mot de passe ou l'utilisateur est incorrect.")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                dialogState.value = false
                            }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
        }

        /*val textMod = Modifier
            .border(width = 2.dp, color = Color.Red)
            .padding(65.dp)

        Column (modifier = Modifier
            .background(color = Color.Yellow)
            .size(width = 1000.dp, height = 2500.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row {
                Text(text = "Elément1", modifier = textMod)
                Text(text = "Elément2", modifier = textMod)
            }

            Row {
                Text(text = "Elément1", modifier = textMod)
                Text(text = "Elément2", modifier = textMod)
            }

            Row {
                Text(text = "Elément1", modifier = textMod)
                Text(text = "Elément2", modifier = textMod)
            }
        }*/
    }

    /*@Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag,"onStop")
    }*/

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MMI3_androidTheme {
            Greeting()
        }
    }

}