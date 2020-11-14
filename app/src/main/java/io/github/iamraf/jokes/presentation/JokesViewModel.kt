package io.github.iamraf.jokes.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.iamraf.jokes.domain.entity.Joke
import io.github.iamraf.jokes.framework.exception.ResponseException
import io.github.iamraf.jokes.usecase.FetchRandomJokesUseCase
import kotlinx.coroutines.launch

class JokesViewModel @ViewModelInject constructor(private val fetchRandomJokesUseCase: FetchRandomJokesUseCase) : ViewModel() {
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchJokes() = viewModelScope.launch {
        _loading.value = true

        try {
            _jokes.value = fetchRandomJokesUseCase.fetchRandomJokes()
            _loading.value = false
        } catch (exception: ResponseException) {
            _error.value = exception.message
            _loading.value = false
        }
    }
}