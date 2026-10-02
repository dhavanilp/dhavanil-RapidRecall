package com.example.dhavanil_rapidrecall


//Purpose: Keeps track of the games played during session.
//Design: For log and summary to see current data.

class SessionLog : TModel<SessionLog>() {
    private val attemptsList = mutableListOf<GameAttempt>()

    val attempts: List<GameAttempt> get() = attemptsList
    val totalGames: Int get() = attemptsList.size
    val totalCorrect: Int get() = attemptsList.count { it.correct }

    val accuracy: Double get() {
        if (totalGames == 0) return 0.0
        return (totalCorrect.toDouble() / totalGames.toDouble()) * 100.0
    }

    fun addAttempt(attempt: GameAttempt) {
        attemptsList.add(attempt)
        notifyViews(this)
    }
}