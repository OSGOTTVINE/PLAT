package com.example.plat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plat.ui.theme.PLATTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PLATTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { vnutrenniyOtstup ->
                    MessengerScreen(modifier = Modifier.padding(vnutrenniyOtstup))
                }
            }
        }
    }
}

data class Soobshenie(
    val tekst: String,
    val vremya: String
)

@Composable
fun MessengerScreen(modifier: Modifier = Modifier) {
    var spisokChatov by remember { mutableStateOf(listOf("Чат 1", "Чат 2", "Чат 3")) }
    var vybrannyyChatIndex by remember { mutableStateOf(0) }
    val soobsheniyaDlyaChatov = remember {
        mutableStateListOf(
            mutableStateListOf<Soobshenie>(),
            mutableStateListOf<Soobshenie>(),
            mutableStateListOf<Soobshenie>()
        )
    }
    var tekushcheeSoobshenie by remember { mutableStateOf(TextFieldValue("")) }
    var spisokChatovVidim by remember { mutableStateOf(true) }

    fun otpravitSoobshenie() {
        if (tekushcheeSoobshenie.text.isBlank()) return

        val tekushcheeVremya = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        soobsheniyaDlyaChatov[vybrannyyChatIndex].add(
            Soobshenie(tekst = tekushcheeSoobshenie.text, vremya = tekushcheeVremya)
        )
        tekushcheeSoobshenie = TextFieldValue("")
    }

    fun dobavitChat() {
        spisokChatov = spisokChatov + "Новый чат ${spisokChatov.size + 1}"
        soobsheniyaDlyaChatov.add(mutableStateListOf())
    }

    Column(modifier = modifier.fillMaxSize()) {
        // кнопки (вверх)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { spisokChatovVidim = !spisokChatovVidim }) {
                Text(if (spisokChatovVidim) "Скрыть чаты" else "Показать чаты")
            }
            Button(onClick = { dobavitChat() }) {
                Text("Добавить чат")
            }
        }

        Row(modifier = Modifier.fillMaxSize()) {
            // список чатов
            if (spisokChatovVidim) {
                LazyColumn(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(8.dp)
                ) {
                    items(spisokChatov.size) { index ->
                        val vybran = index == vybrannyyChatIndex
                        Text(
                            text = spisokChatov[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(if (vybran) MaterialTheme.colorScheme.primary else Color.Transparent)
                                .clickable { vybrannyyChatIndex = index },
                            color = if (vybran) Color.White else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // сообщения и текст(ввод)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    items(soobsheniyaDlyaChatov[vybrannyyChatIndex]) { soobshenie ->
                        Text(
                            text = "${soobshenie.tekst} (${soobshenie.vremya})",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BasicTextField(
                        value = tekushcheeSoobshenie,
                        onValueChange = { tekushcheeSoobshenie = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(56.dp)
                            .border(1.dp, MaterialTheme.colorScheme.primary)
                            .background(Color.White),
                        decorationBox = { vnutrenniyTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                if (tekushcheeSoobshenie.text.isEmpty()) {
                                    Text(
                                        text = "Введите сообщение",
                                        color = Color.Gray
                                    )
                                }
                                vnutrenniyTextField()
                            }
                        }
                    )
                    Button(
                        onClick = { otpravitSoobshenie() },
                        modifier = Modifier.alignByBaseline()
                    ) {
                        Text("Отправить")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessengerScreenPreview() {
    PLATTheme {
        MessengerScreen()
    }
}