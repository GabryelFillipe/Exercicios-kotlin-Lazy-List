package com.example.listas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listas.model.Game
import com.example.listas.repository.getAllGames
import com.example.listas.repository.getGamesByStudio
import com.example.listas.repository.getGamesByTitle
import com.example.listas.ui.theme.ListasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GamesScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GamesScreen(modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }

    var gamesList by remember { mutableStateOf(getAllGames()) }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = userInput,
            onValueChange = {
                userInput = it
                gamesList = getGamesByTitle(userInput)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nome do estudio") },
            trailingIcon = {
                IconButton(
                    onClick = {gamesList = getGamesByStudio(userInput)}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "icone de busca"
                    )
                }
            }
        )
        LazyColumn() {
            items (gamesList) {
                GameCard(it)
            }
        }
    }
}



@Composable
fun GameCard(game: Game) {
    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Row( modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column( modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .weight(3f)
            ) {
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = game.studio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Text(
                modifier = Modifier.weight(1f)
                    .fillMaxWidth(),
                text = "${game.releaseYear}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}
