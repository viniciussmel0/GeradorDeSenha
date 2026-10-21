package com.viniciusmelo.geradordesenha

import android.content.ClipData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viniciusmelo.geradordesenha.ui.theme.GeradorDeSenhaTheme
import com.viniciusmelo.geradordesenha.ui.theme.Gray100
import com.viniciusmelo.geradordesenha.ui.theme.Gray900
import com.viniciusmelo.geradordesenha.ui.theme.Teal700
import com.viniciusmelo.geradordesenha.ui.theme.White
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Home()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    var password by remember { mutableStateOf("") }
    var strength by remember {mutableStateOf("")}

    val clipboardManager = LocalClipboard.current
    val clipData = ClipData.newPlainText("Senha", password)
    val clipEntry = ClipEntry(clipData)
    val scope = rememberCoroutineScope()

    val specialCharacteres = arrayOf(
        "!","$","%","(",")",",",":","<",">","/","]","~","[","@","?","|"
    )

    val uppercaseLetters = arrayOf(
        "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    )

    val lowercaseLetters = arrayOf(
        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Gerador de senha")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Gray100
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text(text = "Password")
                    },
                    textStyle = TextStyle(
                        color = Gray900,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .width(250.dp)
                        .padding(10.dp)
                )

                IconButton(
                    onClick = {
                        scope.launch {
                            clipboardManager.setClipEntry(clipEntry)
                        }
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = Teal700,
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = "Content Copy",
                        tint = White
                    )
                }

                IconButton(
                    onClick = {

                        val numbers = Random.nextInt(100000, 999999)

                        password = "${specialCharacteres.random()}${specialCharacteres.random()}" +
                                "$numbers" +
                                "${uppercaseLetters.random()}${uppercaseLetters.random()}" +
                                "${lowercaseLetters.random()}${lowercaseLetters.random()}"
                        strength = "Senha Forte"

                    },
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            width = 1.dp,
                            color = Gray900,
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Content Copy",
                        tint = Gray900
                    )
                }

            }

            Text(
                text = strength,
                fontSize = 18.sp,
                color = Teal700
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}