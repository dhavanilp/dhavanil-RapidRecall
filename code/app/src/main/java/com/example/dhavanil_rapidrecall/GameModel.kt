package com.example.dhavanil_rapidrecall

import kotlin.random.Random


//Purpose: Handles the data/rules and current state of game
//Design Rationale: Model keeping track of the sequence and user input
class GameModel : TModel<GameModel>() {
    var sequenceLength: Int = 3
        private set
    var targetSequence: String = ""
        private set
    var currentDigitToShow: String = ""
        private set
    var userGuess: String = ""
        private set
    var phase: GamePhase = GamePhase.SETUP
        private set
    var isCorrect: Boolean = false
        private set

    fun setLength(length: Int) {
        if (length in 1..10) {
            sequenceLength = length
            notifyViews(this)
        }
    }

    fun startGame() {
        // Generate random sequence
        targetSequence = ""
        for (i in 1..sequenceLength) {
            targetSequence += Random.nextInt(0, 10).toString()
        }
        userGuess = ""
        currentDigitToShow = ""
        phase = GamePhase.FLASHING
        notifyViews(this)
    }

    fun showDigit(digit: String) {
        currentDigitToShow = digit
        notifyViews(this)
    }

    fun hideDigit() {
        currentDigitToShow = ""
        notifyViews(this)
    }

    fun finishFlashing() {
        currentDigitToShow = ""
        phase = GamePhase.INPUT
        notifyViews(this)
    }

    fun updateGuess(guess: String) {
        // Only allow numbers and don't let them type more than the target length
        if (guess.length <= targetSequence.length) {
            userGuess = guess
            notifyViews(this)
        }
    }

    fun submit() {
        isCorrect = (userGuess == targetSequence)
        phase = GamePhase.RESULT
        notifyViews(this)
    }

    fun reset() {
        phase = GamePhase.SETUP
        notifyViews(this)
    }
}