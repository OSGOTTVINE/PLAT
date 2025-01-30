package com.example.plat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plat.ui.theme.PLATTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PLATTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { vnutrenniyOtstup ->
                    RegistrationScreen(modifier = Modifier.padding(vnutrenniyOtstup))
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }

    fun validateAndRegister() {
        if (name.text.isBlank() || email.text.isBlank() || password.text.isBlank()) {
            errorMessage = "Все поля обязательны для заполнения"
        } else {
            // Логика для регистрации пользователя (например, отправка данных на сервер)
            errorMessage = ""
            // В этом месте можно добавить логику для перехода на другой экран после успешной регистрации
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Регистрация",
            style = MaterialTheme.typography.titleLarge, // Используем titleLarge вместо h4
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Имя
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .background(Color.White),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    if (name.text.isEmpty()) {
                        Text(text = "Введите имя", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        // Email
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .background(Color.White)
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    if (email.text.isEmpty()) {
                        Text(text = "Введите email", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        // Пароль
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .background(Color.White),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    if (password.text.isEmpty()) {
                        Text(text = "Введите пароль", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        // Сообщение об ошибке
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        // Кнопка регистрации
        Button(
            onClick = { validateAndRegister() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Зарегистрироваться")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    PLATTheme {
        RegistrationScreen()
    }
}

//для проверки работы гита