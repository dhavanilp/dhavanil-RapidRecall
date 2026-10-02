package com.example.dhavanil_rapidrecall

//Purpose: Stores data for game that was played.
//Design: Data class to store the results in a list.
data class GameAttempt(
    val length: Int,
    val target: String,
    val guess: String,
    val correct: Boolean,
    val time: Long = System.currentTimeMillis()
)

//Purpose: Keeps track of what part the game is on.
//Design: Easy to show the game phase using a if or when statement.

enum class GamePhase { SETUP, FLASHING, INPUT, RESULT }