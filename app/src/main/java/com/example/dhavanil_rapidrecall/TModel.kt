package com.example.dhavanil_rapidrecall

// Purpose: For models to keep track of their views.
//Design: This allows the model to notify connected view

abstract class TModel<M> {
    private val views = mutableListOf<TView<M>>()

    fun addView(view: TView<M>) {
        if (!views.contains(view)) views.add(view)
    }
     fun notifyViews(model: M) {
        for (view in views) {
            view.update(model)
        }
    }
}