package com.example.flowpractice

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val moviesApi: MoviesApi
) {

    fun getMoviesWithGenre(genre: Genre): Flow<Movie> {
        return moviesApi.getMovies
            .onEach {
                saveToDb(it)
                if (it.id > 2) {
                    throw Throwable("it is below 2")
                }
            }
            .filter { it.genre == genre }
            .flowOn(Dispatchers.Default)
    }

    suspend fun getMoviesInList(): Flow<List<Movie>> {
        return flow {
            val mutableList = mutableListOf<Movie>()
            moviesApi.getMovies.onEach {
                mutableList.add(it)
            }
                .onCompletion {
                    emit(mutableList.toList())
                }
        }
    }


    private fun saveToDb(movie: Movie) {
        Log.d("MovieRepository", "savedToDb: $movie")
    }
}