package com.example.flowpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    // converting a flow to live data
    val actionMoviesLiveData = movieRepository.getMoviesWithGenre(Genre.ACTION).asLiveData()

    // declaring a stateflow
    private val _actionMoviesFlow = MutableStateFlow<Outcome<Movie>>(
        Outcome.Loading()
    )

    val actionMoviesFlow: StateFlow<Outcome<Movie>> get() = _actionMoviesFlow

    // converting a flow to state flow
    val actionMovieFlowWithState = movieRepository.getMoviesWithGenre(Genre.ACTION)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Movie(0, "", Genre.valueOf("")))

    init {
        getActionMovies()
    }

    private fun getActionMovies() {
        viewModelScope.launch {
            movieRepository.getMoviesWithGenre(Genre.ACTION)
                .catch { cause: Throwable ->
                    _actionMoviesFlow.value = Outcome.Failure(cause)
                }
                .collect {
                    _actionMoviesFlow.value = Outcome.Success(it)
                }
        }
    }
}