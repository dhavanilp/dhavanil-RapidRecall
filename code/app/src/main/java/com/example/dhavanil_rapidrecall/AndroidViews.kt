package com.example.dhavanil_rapidrecall

import androidx.compose.runtime.*

//Purpose: Connects the MVC models to Jetpack Compose
//Design: receive updates from models and update game view
class AndroidGameView : TView<GameModel> {
    var phase by mutableStateOf(GamePhase.SETUP)
    var length by mutableIntStateOf(3)
    var target by mutableStateOf("")
    var digit by mutableStateOf("")
    var guess by mutableStateOf("")
    var correct by mutableStateOf(false)

    override fun update(model: GameModel) {
        phase = model.phase
        length = model.sequenceLength
        target = model.targetSequence
        digit = model.currentDigitToShow
        guess = model.userGuess
        correct = model.isCorrect
    }
}

//Purpose: Connects the MVC models to Jetpack Compose
//Design: receive updates from models and update log view
class AndroidLogView : TView<SessionLog> {
    var logs by mutableStateOf(listOf<GameAttempt>())
    var total by mutableStateOf(0)
    var correct by mutableStateOf(0)
    var accuracy by mutableStateOf(0.0)

    override fun update(model: SessionLog) {
        logs = model.attempts
        total = model.totalGames
        correct = model.totalCorrect
        accuracy = model.accuracy
    }
}