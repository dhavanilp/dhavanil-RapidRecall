package com.example.dhavanil_rapidrecall

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope


// enumerating through the different stages of guessing game
enum class Screen { START, GAME, LOG, SUMMARY }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup MVC
        val gameModel = GameModel()
        val sessionLog = SessionLog()
        val controller = GameController(gameModel, sessionLog, lifecycleScope)

        val gameView = AndroidGameView()
        gameModel.addView(gameView)

        val logView = AndroidLogView()
        sessionLog.addView(logView)

        setContent {
            // Manage what screen is currently showing
            var currentScreen by remember { mutableStateOf(Screen.START) }

            Surface(modifier = Modifier.fillMaxSize()) {
                when (currentScreen) {
                    Screen.START -> StartScreen(
                        onStartGame = { currentScreen = Screen.GAME },
                        onViewLog = { currentScreen = Screen.LOG },
                        onViewSummary = { currentScreen = Screen.SUMMARY }
                    )
                    Screen.GAME -> GameScreen(
                        view = gameView,
                        controller = controller,
                        onBack = { currentScreen = Screen.START }
                    )
                    Screen.LOG -> LogScreen(
                        view = logView,
                        onBack = { currentScreen = Screen.START }
                    )
                    Screen.SUMMARY -> SummaryScreen(
                        view = logView,
                        onBack = { currentScreen = Screen.START }
                    )
                }
            }
        }
    }
}

@Composable
fun StartScreen(onStartGame: () -> Unit, onViewLog: () -> Unit, onViewSummary: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("RapidRecall", fontSize = 40.sp, modifier = Modifier.padding(bottom = 30.dp))
        Button(onClick = onStartGame) { Text("Start Game") }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onViewLog) { Text("View Log") }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onViewSummary) { Text("Attempt Summary") }
    }
}

@Composable
fun GameScreen(view: AndroidGameView, controller: GameController, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (view.phase) {
            GamePhase.SETUP -> {
                Text("How many numbers? (1-10)", fontSize = 20.sp)
                var lengthText by remember { mutableStateOf(view.length.toString()) }

                TextField(
                    value = lengthText,
                    onValueChange = {
                        lengthText = it
                        if (it.isNotEmpty() && it.toIntOrNull() != null) {
                            controller.onLengthChanged(it.toInt())
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { controller.onStartGameClicked() }) { Text("Start") }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = onBack) { Text("Back to Menu") }
            }
            GamePhase.FLASHING -> {
                Text(text = view.digit, fontSize = 100.sp)
            }
            GamePhase.INPUT -> {
                Text("Type the sequence:", fontSize = 20.sp)
                TextField(
                    value = view.guess,
                    onValueChange = { controller.onGuessChanged(it) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { controller.onSubmitClicked() }) { Text("Submit") }
            }
            GamePhase.RESULT -> {
                if (view.correct) {
                    Text("Correct!", fontSize = 35.sp)
                } else {
                    Text("Incorrect!", fontSize = 35.sp)
                }
                Text("Target was: ${view.target}")
                Text("You guessed: ${view.guess}")
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { controller.onPlayAgain() }) { Text("Play Again") }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = onBack) { Text("Back to Menu") }
            }
        }
    }
}

@Composable
fun LogScreen(view: AndroidLogView, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Game Log", fontSize = 30.sp)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(view.logs) { log ->
                Text("Length: ${log.length} | Target: ${log.target} | Guess: ${log.guess} | Correct: ${log.correct}")
                Divider()
            }
        }
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun SummaryScreen(view: AndroidLogView, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Summary", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Total Games Played: ${view.total}", fontSize = 20.sp)
        Text("Games Won: ${view.correct}", fontSize = 20.sp)

        // Format to 1 decimal place
        val accuracyString = String.format("%.1f", view.accuracy)
        Text("Accuracy: $accuracyString%", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}