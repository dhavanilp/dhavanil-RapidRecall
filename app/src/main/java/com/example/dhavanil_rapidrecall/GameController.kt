package com.example.dhavanil_rapidrecall

import kotlinx.coroutines.*


//Purpose: Takes user input and tells the models what to do.
//Design: Controller for the game

class GameController(
    private val gameModel: GameModel,
    private val sessionLog: SessionLog,
//  using scopes/coroutines to wait for any child process to finish before continuing
    private val scope: CoroutineScope
) {
    private var flashJob: Job? = null

    fun onLengthChanged(length: Int) = gameModel.setLength(length)

    fun onGuessChanged(guess: String) = gameModel.updateGuess(guess)

    fun onPlayAgain() = gameModel.reset()

    fun onStartGameClicked() {
        gameModel.startGame()

        flashJob?.cancel()
        flashJob = scope.launch {
            for (char in gameModel.targetSequence) {
                gameModel.showDigit(char.toString())
                delay(1000L) // show the number for 1 second
                gameModel.hideDigit()
                delay(200L)  // tiny pause so double numbers (like "33") don't look stuck
            }
            gameModel.finishFlashing()
        }
    }

    fun onSubmitClicked() {
        gameModel.submit()

        // Save to the log
        val attempt = GameAttempt(
            length = gameModel.sequenceLength,
            target = gameModel.targetSequence,
            guess = gameModel.userGuess,
            correct = gameModel.isCorrect
        )
        sessionLog.addAttempt(attempt)
    }
}