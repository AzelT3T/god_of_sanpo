package com.example.sanpokami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (whereList, whatList) = loadPrompts()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F5F5)) {
                    SanpoApp(whereList, whatList)
                }
            }
        }
    }

    private fun loadPrompts(): Pair<List<String>, List<String>> {
        val text = assets.open("sanpo_kami_personalized_v5.txt").use { stream ->
            BufferedReader(InputStreamReader(stream, "UTF-8")).readText()
        }

        val where = mutableListOf<String>()
        val what = mutableListOf<String>()

        var mode = "none"
        text.lines().forEach { line ->
            when {
                line.contains("[どこで]") -> mode = "where"
                line.contains("[なにをする]") -> mode = "what"
                line.trim().matches(Regex("^\\d{2}\\..*")) -> {
                    val parts = line.split(".", limit = 2)
                    if (parts.size >= 2) {
                        val item = parts[1].trim()
                        if (mode == "where") where.add(item) else if (mode == "what") what.add(item)
                    }
                }
                else -> {}
            }
        }

        return Pair(where, what)
    }
}

@Composable
fun SanpoApp(whereList: List<String>, whatList: List<String>) {
    var whereSelected by remember { mutableStateOf<String?>(null) }
    var whatSelected by remember { mutableStateOf<String?>(null) }
    var isRunning by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("さんぽ神 — ランダム散歩遊び", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Card(modifier = Modifier.weight(1f).padding(8.dp)) {
                Box(modifier = Modifier.height(140.dp).fillMaxWidth().background(Color.White), contentAlignment = Alignment.Center) {
                    Text(whereSelected ?: "（どこで）", fontSize = 18.sp)
                }
            }

            Card(modifier = Modifier.weight(1f).padding(8.dp)) {
                Box(modifier = Modifier.height(140.dp).fillMaxWidth().background(Color.White), contentAlignment = Alignment.Center) {
                    Text(whatSelected ?: "（なにをする）", fontSize = 18.sp)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                if (!isRunning) {
                    isRunning = true
                    whereSelected = null
                    whatSelected = null
                    // launch small animation
                    LaunchedEffect(Unit) {
                        val rnd = Random(System.currentTimeMillis())
                        val rounds = 30
                        repeat(rounds) { i ->
                            whereSelected = whereList[rnd.nextInt(whereList.size)]
                            whatSelected = whatList[rnd.nextInt(whatList.size)]
                            delay(50L + (i * 2))
                        }
                        // final pick
                        whereSelected = whereList[rnd.nextInt(whereList.size)]
                        whatSelected = whatList[rnd.nextInt(whatList.size)]
                        isRunning = false
                    }
                }
            }) {
                Text("テーマを決める")
            }

            Button(onClick = {
                whereSelected = null
                whatSelected = null
            }) {
                Text("リセット")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("決まったら他の情報は不要です。オフラインで動作します。", fontSize = 12.sp, color = Color.DarkGray)
    }
}
