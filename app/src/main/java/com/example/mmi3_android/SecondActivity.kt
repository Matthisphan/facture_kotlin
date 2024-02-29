package com.example.mmi3_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mmi3_android.ui.theme.MMI3_androidTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MMI3_androidTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Greeting2() {
    val context = LocalContext.current
    val dialogState = remember { mutableStateOf(false) }
    val dialogState2 = remember { mutableStateOf(false) }

    var isFidele by remember { mutableStateOf(false) }
    var isNonFidele by remember { mutableStateOf(true) }
    var valide by remember { mutableStateOf(true) }

    var quantite by remember { mutableStateOf("0") }
    var prix_unitaire by remember { mutableDoubleStateOf(0.0) }
    var montant_ht by remember { mutableStateOf("0") }
    var tva by remember { mutableStateOf("0") }
    var remise by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.facture),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        TextField(
            value = quantite,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    quantite = it
                }
            },
            label = { Text("Quantité") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = prix_unitaire.toString(),
            onValueChange = {
                if (it.toDoubleOrNull() != null) {
                    prix_unitaire = it.toDouble()
                }
            },
            label = { Text("Prix unitaire") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        TextField(
            value = montant_ht,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    montant_ht = it
                }
            },
            label = { Text("Montant HT") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = false
        )

        TextField(
            value = tva,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    tva = it
                }
            },
            label = { Text("Taux TVA (en %)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = isFidele,
                onCheckedChange = {
                    isFidele = it
                    if (it) {
                        isNonFidele = false
                    }
                },
                modifier = Modifier.padding(end = 1.dp)
            )
            Text("Fidèle")

            Checkbox(
                checked = isNonFidele,
                onCheckedChange = {
                    isNonFidele = it
                    if (it) {
                        isFidele = false
                    }
                },
                modifier = Modifier.padding(start = 1.dp)
            )
            Text("Non fidèle")
        }

        TextField(
            value = remise,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    remise = it
                }
            },
            label = { Text("Remise (en %)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = isFidele
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (quantite.isEmpty()) {
                        quantite = "0"
                    }

                    if (prix_unitaire.isNaN()) {
                        prix_unitaire = 0.0
                    }

                    if (montant_ht.isEmpty()) {
                        montant_ht = "0"
                    }

                    if (tva.isEmpty()) {
                        tva = "0"
                    }

                    if (remise.isEmpty() || isNonFidele) {
                        remise = "0"
                    }

                    if (prix_unitaire <= 0) {
                        dialogState.value = true
                        valide = false
                    }

                    if ((quantite.toIntOrNull() ?: 0) <= 0) {
                        dialogState2.value = true
                        valide = false
                    }

                    val quantiteValue = quantite.toDouble()
                    val prixUnitaireValue = prix_unitaire
                    val tvaValue = tva.toDouble()
                    val remiseValue = remise.toDouble()

                    val montantHtResult = quantiteValue * prixUnitaireValue
                    val total = montantHtResult + (montantHtResult * (tvaValue / 100))
                    val total_ttc = total - (total * (remiseValue / 100))

                    montant_ht = montantHtResult.toString()

                    /*Log.d("Greeting", "Quantité: $quantite")
                    Log.d("Greeting", "Prix unitaire: $prix_unitaire")
                    Log.d("Greeting", "Montant HT: $montant_ht")
                    Log.d("Greeting", "Taux TVA: $tva")
                    Log.d("Greeting", "Remise: $remise")
                    Log.d("Greeting", "Fidèle: $isFidele")
                    Log.d("Greeting", "Non fidèle: $isNonFidele")
                    Log.d("Greeting", "total: $total")
                    Log.d("Greeting", "total_ttc: $total_ttc")*/

                    if (valide){
                        val intent2 = Intent(context, ThirdActivity::class.java)
                        intent2.putExtra("TOTAL_TTC", total_ttc)
                        context.startActivity(intent2)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            ) {
                Text("Calculer")
            }

            Button(
                onClick = {
                    quantite = 0.toString()
                    prix_unitaire = 0.0
                    montant_ht = 0.toString()
                    tva = 0.toString()
                    remise = 0.toString()
                    isNonFidele = true
                    isFidele = false
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            ) {
                Text("Remettre à zéro")
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Déconnexion")
        }

        if (dialogState.value) {
            AlertDialog(
                onDismissRequest = {
                    dialogState.value = false
                },
                title = {
                    Text("Erreur de saisie !")
                },
                text = {
                    Text("le prix unitaire ne peut pas être égal à 0")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            dialogState.value = false
                            valide = true
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        if (dialogState2.value) {
            AlertDialog(
                onDismissRequest = {
                    dialogState2.value = false
                },
                title = {
                    Text("Erreur de saisie !")
                },
                text = {
                    Text("La quantité ne peut pas être égal à 0")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            dialogState2.value = false
                            valide = true
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Greeting2Preview() {
    MMI3_androidTheme {
        Greeting2()
    }
}