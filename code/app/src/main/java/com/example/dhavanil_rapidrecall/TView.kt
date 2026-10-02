package com.example.dhavanil_rapidrecall


//Purpose: viewer can update depending on model
//Design: allows Models to notify Views

interface TView<M> {
    fun update(model: M)
}