package com.example.flowpractice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesApi @Inject constructor() {
    val getMovies: Flow<Movie> = flow {
        delay(5000)
        for (i in 1..5){
            delay(2000)
            emit(
                Movie(
                    id = i,
                    title = "Movie $i",
                    genre = Genre.ACTION
                )
            )
        }
    }


}