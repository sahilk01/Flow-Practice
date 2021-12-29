package com.example.flowpractice

data class Movie(
    val id: Int,
    val title: String,
    var genre: Genre
)

enum class Genre {
    ACTION,
    COMEDY,
    SCIENCE_FICTION
}