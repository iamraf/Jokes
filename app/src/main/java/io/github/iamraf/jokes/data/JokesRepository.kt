package io.github.iamraf.jokes.data

import io.github.iamraf.jokes.data.model.JokeResponse

interface JokesRepository {
    suspend fun fetchRandomJokes(): JokeResponse
}