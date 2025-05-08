package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import kotlinx.coroutines.launch
import kotlin.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        lifecycleScope.launch {
            RunGemini()
        }
    }

    suspend fun RunGemini() {
        try {
            // 모델 준비
            val model = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.apiKey
            )

            // 챗 준비
            val chat = model.startChat()

            // 첫 번째 질문
            val response1 = chat.sendMessage("우리집 고양이 이름은 레오입니다.")
            android.util.Log.d("gemini", response1.text.toString())

            // 두 번째 질문
            val response2 = chat.sendMessage("우리집 고양이 이름을 불러주세요")
            android.util.Log.d("gemini", response2.text.toString())

            // 대화 이력 표시
            for (h in chat.history) {
                android.util.Log.d("gemini", h.role + ": " + h.parts[0].asTextOrNull())
            }
        } catch (e: Exception) {
            android.util.Log.e("gemini", "Error: ${e.message}", e)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}