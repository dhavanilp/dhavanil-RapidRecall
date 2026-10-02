package com.example.dhavanil_rapidrecall

//Purpose: Maintains the session history
//Design: store data of last attempts for log and summary calc.


class AttemptStore : TModel<AttemptStore>() {
    private val _attempts = mutableListOf<GameAttempt>()

    val attempts: List<GameAttempt> get() = _attempts.toList()
    val totalAttempts: Int get() = _attempts.size
    val correctAttempts: Int get() = _attempts.count { it.correct }
    val accuracyPercent: Double get() = if (totalAttempts == 0) 0.0 else (correctAttempts.toDouble() / totalAttempts) * 100

    fun addAttempt(attempt: GameAttempt) {
        _attempts.add(attempt)
        notifyViews(this)
    }
}