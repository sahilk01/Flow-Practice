package com.example.flowpractice

sealed class Outcome <out T> {
    class Loading<out T> : Outcome<T>()
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Failure<out T>(val throwable: Throwable) : Outcome<T>()
}

