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

            // 추론 실행
            model.generateContentStream("Google DeepMind에 관해 알려주세요.").collect { chunk ->
                android.util.Log.d("gemini", chunk.text.toString())
                android.util.Log.d("gemini", "--")
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